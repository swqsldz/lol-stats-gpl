package log.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import log.ds.Champion;
import log.ds.GameType;
import log.ds.Item;
import log.ds.SummonerStats;
import log.ds.QueueType;
import log.ds.Spell;

public class ParserUtils {
	
	public static GameType parseGameType(String gameType) {
		if (gameType.equals("\"NORMAL_GAME\""))
			return GameType.NORMAL_GAME;
		else if (gameType.equals("\"MATCHED_GAME\""))
			return GameType.MATCHED_GAME;
		else return null;
	}
	
	public static QueueType parseQueueType(String queueType) {
		if (queueType.equals("\"NORMAL\""))
			return QueueType.NORMAL;
		else if (queueType.equals("\"BOT\""))
			return QueueType.BOT;
		else if (queueType.equals("\"RANKED_PREMADE_3x3\""))
			return QueueType.RANKED_PREMADE_3x3;
		else if (queueType.equals("\"RANKED_PREMADE_5x5\""))
			return QueueType.RANKED_PREMADE_5x5;
		else if (queueType.equals("\"RANKED_SOLO_5x5\""))
			return QueueType.RANKED_SOLO_5x5;
		else return null;		
	}
	
	public static GregorianCalendar parseCreationTime(String creationTime) {		
		String[] splittedDate = creationTime.split(" ");
		String[] splittedTime = splittedDate[3].split(":");
		int year = Integer.parseInt(splittedDate[5]);
		int month = parseMonth(splittedDate[1]);
		int day = Integer.parseInt(splittedDate[2]);
		int hour = Integer.parseInt(splittedTime[0]);
		int minute = Integer.parseInt(splittedTime[1]);
		int second = Integer.parseInt(splittedTime[2]);
		
		GregorianCalendar c = new GregorianCalendar(year, month, day, hour, minute, second);
		c.setTimeZone(TimeZone.getTimeZone(splittedDate[4]));
		
		return c;
	}
	
	private static int parseMonth(String month) {
		if (month.equals("Jan")) return 1;
		else if (month.equals("Feb")) return 2;
		else if (month.equals("Mar")) return 3;
		else if (month.equals("Apr")) return 4;
		else if (month.equals("May")) return 5;
		else if (month.equals("Jun")) return 6;
		else if (month.equals("Jul")) return 7;
		else if (month.equals("Aug")) return 8;
		else if (month.equals("Sep")) return 9;
		else if (month.equals("Oct")) return 10;
		else if (month.equals("Nov")) return 11;
		else if (month.equals("Dec")) return 12;
		else return -1;
	}
	
	public static ArrayList<Champion> parseBannedChampions(BufferedReader br) {
		ArrayList<Champion> bannedChampions = new ArrayList<Champion>();
		int length;
		
		try {
			br.readLine();	// filterFunction = (null)
			br.readLine();	// length = x
			br.readLine();	// list = (...)
			length = Integer.parseInt(br.readLine().trim().split(" = ")[1]);	// length = x
			br.readLine();	// source = (Array)#y
			for (int iChamp = 0; iChamp < length; iChamp++) {
				bannedChampions.add(parseChampion(br.readLine().trim().split(" ")[1]));	// [n] "Champion"
			}
			br.readLine();	// sort = (null)
			br.readLine();	// source = (Array)#y
		} catch (IOException e) {e.printStackTrace();}
		
		return bannedChampions;
	}
	
	public static Champion parseChampion(String championName) {
		Champion[] champions = Champion.values();
		Champion champion = null;
		String championNameUC = championName.replace("\"", "").toUpperCase();
		System.out.println(championName);
		for (int iChampion = 0; iChampion < champions.length && champion == null; iChampion++) {
			if (champions[iChampion].toString().equals(championNameUC))
				champion = champions[iChampion];
		}
		return champion;
	}
	
	// TODO Falta asociar el entero con el correspondiente hechizo
	public static Spell parseSpell(int nSummonerSpell) {
		return null;
	}

	public static HashMap<String, SummonerStats> parseTeam(BufferedReader br) {
		HashMap<String, SummonerStats> team = new HashMap<String, SummonerStats>();
		SummonerStats player;
		int length;
		
		try {
			br.readLine();	// filterFunction = (null)
			br.readLine();	// length = x
			br.readLine();	// list = (...)
			length = Integer.parseInt(br.readLine().trim().split(" = ")[1]);	// length = x
			br.readLine();	// source = (Array)#y
			for (int iPlayer = 0; iPlayer < length; iPlayer++) {
				player = parsePlayer(br);
				team.put(player.getSummonerName(), player);
			}
		} catch (IOException e) {e.printStackTrace();}
		
		return team;
	}
	
	private static SummonerStats parsePlayer(BufferedReader br) {
		SummonerStats player = new SummonerStats();
		try {
			br.readLine();	// [n] (com.riotgames.platform.gameclient.domain::PlayerParticipantStatsSummary)#m
			br.readLine();	// _profileIconId
			player.setSummonerName(br.readLine().trim().split(" = ")[1].replace("\"", ""));	// _summonerName
			player.setBot(Boolean.parseBoolean(br.readLine().trim().split(" = ")[1]));	// botPlayer
			player.setElo(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// elo
			player.setEloChange(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// eloChange
			try {
				player.setGameId(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// gameId
			} catch(NumberFormatException e) {
				player.setGameId(-1);
			}
			br.readLine();	// gameItems
			br.readLine();	// inChat
			player.setMe(Boolean.parseBoolean(br.readLine().trim().split(" = ")[1]));	// isMe
			player.setLeaver(Boolean.parseBoolean(br.readLine().trim().split(" = ")[1]));	// leaver
			player.setLeaves(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// leaves
			player.setLevel(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// level
			player.setLosses(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// losses
			player.setSummonerIcon(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// profileIconId
			player.setSelectedChampion(parseChampion(br.readLine().trim().split(" = ")[1]));	// skinName
			player.setSummonerSpell1(parseSpell(Integer.parseInt(br.readLine().trim().split(" = ")[1])));	// spell1Id
			player.setSummonerSpell2(parseSpell(Integer.parseInt(br.readLine().trim().split(" = ")[1])));	// spell2Id
			parsePlayerStatistics(player, br);	// playerStatistics
			br.readLine();	// summonerName
			player.setTeamId(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// teamId
			br.readLine(); //player.setSummonerId(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// summonerId
			player.setWins(Integer.parseInt(br.readLine().trim().split(" = ")[1]));	// wins
		} catch (IOException e) {e.printStackTrace();}		
		
		return player;
	}

	private static void parsePlayerStatistics(SummonerStats player, BufferedReader br) {
		int length;
		try {
			br.readLine();	// statistics
			br.readLine();	// filterFunction
			br.readLine();	// length
			br.readLine();	// list
			length = Integer.parseInt(br.readLine().trim().split(" = ")[1]);	// length = x
			br.readLine();	// source
			for (int iStatistic = 0; iStatistic < length; iStatistic++) {
				parseStatistic(player, br);
			}
			br.readLine();	// uid
			br.readLine();	// sort
			br.readLine();	// source
		} catch (IOException e) {e.printStackTrace();}	
		
	}

	private static void parseStatistic(SummonerStats player, BufferedReader br) {
		ArrayList<Item> items = new ArrayList<Item>(6);
		String statistic;
		int value;
		
		for (int i = 0; i < 6; i++) items.add(null);
		
		try {
			br.readLine();	// [n] (com.riotgames.platform.gameclient.domain::RawStatDTO)#m
			br.readLine();	// displayName
			statistic = br.readLine().trim().split(" = ")[1].replace("\"", "");	//statTypeName
			try {
				value = Integer.parseInt(br.readLine().trim().split(" = ")[1]);	// value
			} catch(NumberFormatException e) {
				value = -1;
			}
			
			if (statistic.equals("TOTAL_DAMAGE_TAKEN"))
				player.setDamageTaken(value);
			else if (statistic.equals("MAGIC_DAMAGE_TAKEN"))
				player.setMagicDamageTaken(value);
			else if (statistic.equals("LARGEST_CRITICAL_STRIKE"))
				player.setLargestCriticalStrike(value);
			else if (statistic.equals("ASSISTS"))
				player.setAssists(value);
			else if (statistic.equals("MAGIC_DAMAGE_DEALT_PLAYER"))
				player.setMagicDamageDealt(value);
			else if (statistic.equals("PHYSICAL_DAMAGE_DEALT_PLAYER"))
				player.setPhysicalDamageDealt(value);
			else if (statistic.equals("TOTAL_TIME_SPENT_DEAD"))
				player.setTimeSpentDead(value);
			else if (statistic.equals("LARGEST_MULTI_KILL"))
				player.setLargestMultiKill(value);
			else if (statistic.equals("PHYSICAL_DAMAGE_TAKEN"))
				player.setPhysicalDamageTaken(value);
			else if (statistic.equals("NUM_DEATHS"))
				player.setDeaths(value);
			else if (statistic.equals("BARRACKS_KILLED"))
				player.setBarracksDestroyed(value);
			else if (statistic.equals("LARGEST_KILLING_SPREE"))
				player.setLargestKillingSpree(value);
			else if (statistic.equals("LEVEL"))
				player.setChampionLevel(value);
			else if (statistic.equals("NEUTRAL_MINIONS_KILLED"))
				player.setNeutralMonstersSlain(value);
			else if (statistic.equals("MINIONS_KILLED"))
				player.setMinionsSlain(value);
			else if (statistic.equals("GOLD_EARNED"))
				player.setGold(value);
			else if (statistic.equals("TURRETS_KILLED"))
				player.setTurretsDestroyed(value);
			else if (statistic.equals("TOTAL_DAMAGE_DEALT"))
				player.setDamageDealt(value);
			else if (statistic.equals("CHAMPIONS_KILLED"))
				player.setKills(value);
			else if (statistic.equals("TOTAL_HEAL"))
				player.setHealthRestored(value);
			else if (statistic.startsWith("ITEM")) {
				items.set(Integer.parseInt(statistic.substring(4)), parseItem(value));
			}
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private static Item parseItem(int nItem) {
		return null;
	}
}
