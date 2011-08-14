package log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import log.ds.Summoner;
import log.ds.SummonerStats;

import log.ds.game.Game;

public class Parser {

	private final static String SEPARATOR = System.getProperty("file.separator");
	
	public Parser() {}
	
	public void readFile(String filePath, HashMap<Long, Game> games, HashMap<String, Summoner> players) throws IOException {
		int i = 0;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			
			String line;
			while ((line = br.readLine()) != null) {
				i++;
				if (isGame(line)) {
					Game game = parseMatch(br);
					// Se insertan/actualizan los juegos
					if (games.containsKey(game.getId()))
						games.get(game.getId()).merge(game);
					else
						games.put(game.getId(), game);
					// Se insertan/actualizan los invocadores
					for (SummonerStats summonerStats : game.getSummoners().values()) {
						if (players.containsKey(summonerStats.getSummonerName()))
							players.get(summonerStats.getSummonerName()).updateStats(summonerStats);
						else
							players.put(summonerStats.getSummonerName(), new Summoner(summonerStats));
					}
				}
			}
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch(Exception e) {e.printStackTrace();}
		}
	}
	
	private static boolean isGame(String line) {
		return line.contains("body = (com.riotgames.platform.gameclient.domain::EndOfGameStats");
	}
	
	private static Game parseMatch(BufferedReader br) {
		Game game = new Game();
		String splittedLine[];
		String line;
		try {
			while ((line = br.readLine()) != null &&
					line.startsWith("  ")) {
				line = line.trim();
				splittedLine = line.split(" = ");
				
				if (splittedLine[0].equals("gameId") || splittedLine[0].equals("id"))
					try {
						game.setId(Long.parseLong(splittedLine[1]));
					} catch(NumberFormatException e) {
						game.setId(-1);
					}
				else if (splittedLine[0].equals("gameType"))
					game.setGameType(ParserUtils.parseGameType(splittedLine[1]));
				else if (splittedLine[0].equals("difficulty"))
					game.setDifficulty(splittedLine[1]);
				else if (splittedLine[0].equals("gameLength"))
					game.setGameLength(Integer.parseInt(splittedLine[1]));	
				else if (splittedLine[0].equals("creationTime"))
					game.setCreationTime(ParserUtils.parseCreationTime(splittedLine[1]));
				else if (splittedLine[0].equals("name"))
					game.setName(splittedLine[1]);
				else if (splittedLine[0].equals("passwordSet"))
					game.setHasPassword(Boolean.parseBoolean(splittedLine[1]));
				else if (splittedLine[0].equals("queueType"))
					game.setQueueType(ParserUtils.parseQueueType(splittedLine[1]));
				else if (splittedLine[0].equals("bannedChampionNames"))
					game.setBannedChampions(ParserUtils.parseBannedChampions(br));
				else if (splittedLine[0].equals("teamPlayerParticipantStats"))
					game.setTeam1(ParserUtils.parseTeam(br));
				else if (splittedLine[0].equals("otherTeamPlayerParticipantStats"))
					game.setTeam2(ParserUtils.parseTeam(br));
			}
		} catch (IOException e) {e.printStackTrace();}
		
		return game;
	}
	
}

