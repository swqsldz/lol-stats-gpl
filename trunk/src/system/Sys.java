package system;

import java.io.IOException;

import database.Database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.ds.Summoner;
import log.ds.game.Game;
import log.parser.Parser;
import options.Options;

public class Sys {
		
	private final static String SEPARATOR = System.getProperty("file.separator");
	private static String PARSED_GAMES_PATH = "src" + SEPARATOR + "data" + SEPARATOR + "logs" + SEPARATOR + "parsedGames.txt";
	
	/** Instancia para aplicar el patrón Singleton */
	private static Sys instance;
	
	/** Conjunto de ficheros ya parseados */
	private static Set<String> parsedFiles;
	
	public static void main(String[] args) {
		try {
			Database.getInstance().resetFiles();
			Sys system = Sys.getInstance();
			system.updateGames();
			system.saveFiles();
		} catch (IOException ex) {
			Logger.getLogger(Sys.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
	}
	
	private Sys() {
	
	}
	
	public static Sys getInstance() {
		if (instance == null)
			instance = new Sys();
		return instance;
	}
	
	public void updateGames() throws IOException {
		String logsPath = Options.getInstance().getLogsPath();
		Parser parser = new Parser();
		
		File logs = new File(logsPath);
		for (String logPath : logs.list(new LogFilter(getParsedFiles()))) {
			System.out.println(logPath);
			HashMap<String, Summoner> players = Database.getInstance().getSummoners();
			HashMap<Long, Game> games = Database.getInstance().getGames();
			parser.readFile(logsPath + SEPARATOR + logPath, games, players);
			parsedFiles.add(logPath);
		}
	}
	
	public boolean login(String username) {
		Database database = Database.getInstance();
		
		Iterator<Summoner> summoners = database.getSummoners().values().iterator();
		boolean found = false;
		while(summoners.hasNext() && !found) {
			Summoner summoner = summoners.next();
			found = summoner.getSummonerName().equals(username);
		}
		
		return found;
	}
	
	public void saveFiles() {
		// Se guarda la lista de juegos parseados
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(PARSED_GAMES_PATH);
			pw = new PrintWriter(fw);
			
			Iterator<String> iFile = parsedFiles.iterator();
			while (iFile.hasNext()) {
				String fileName = iFile.next();
				pw.println(fileName);
			}
			
		} catch(IOException e) {e.printStackTrace();}
		finally {
			try {
				if (fw != null)
					fw.close();
			} catch(Exception e) {e.printStackTrace();}
		}
		
		// Se guarda la información de la base de juegos y jugadores
		Database.getInstance().saveDatabase();
	}
	
	private Set<String> getParsedFiles() {
		if (parsedFiles == null)
			readParsedFiles();
		return parsedFiles;
	}
	
	private void readParsedFiles() {
		parsedFiles = new HashSet<String>() {};
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(PARSED_GAMES_PATH);
			br = new BufferedReader(fr);
			
			String line;
			String lastFile = null;
			while ((line = br.readLine()) != null) {
				parsedFiles.add(line);
				lastFile = line;
			}
		} catch(IOException e) {e.printStackTrace();}
		finally {
			try {
				if (fr != null)
					fr.close();
			} catch(Exception e) {e.printStackTrace();}
		}
	}
}

class LogFilter implements FilenameFilter {
	
	private static Set<String> parsedFiles;
	
	public LogFilter(Set<String> parsedFiles) {
		LogFilter.parsedFiles = parsedFiles;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(".log") && !parsedFiles.contains(name);
	}
}