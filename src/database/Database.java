package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import log.ds.Item;
import log.ds.Summoner;
import log.ds.Spell;
import log.ds.game.Game;

public class Database {
	
	/** Ruta al fichero XML con la información de los objetos */
	private static final String ITEMS_FILE_PATH = "src/data/items/items.xml";
	
	/** Ruta al fichero que almacena los datos de los jugadores */
	private static final String SUMMONERS_FILE_PATH = "src/data/logs/players.ltp";
	
	/** Ruta al fichero que almacena los datos de los juegos */
	private static final String GAMES_FILE_PATH = "src/data/logs/games.ltp";
	
	/** Instancia para aplicar el patrón Singleton */
	private static Database instance = null;
	
	/** Tabla con la información de todos los objetos */
	private HashMap<Integer, Item> items;
	
	/** Tabla con la información de los hechizos */
	private HashMap<Integer, Spell> spells;
	
	/** Tabla con todas los juegos almacenados */
	private HashMap<Long, Game> games;
	
	/** Tabla con todos los usuarios registrados en el sistema */
	private HashMap<String, Summoner> summoners;
	
	public static void main(String[] args) {
		Database.getInstance().resetFiles();
	}
	
	private Database() {
		initDatabase();
	}
	
	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}

	private void initDatabase() {
		loadItems();
		//TODO loadSpells();
		loadPlayers();
		loadGames();
	}
	
	@SuppressWarnings("unchecked")
	private void loadPlayers() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUMMONERS_FILE_PATH));
			summoners = (HashMap<String, Summoner>) ois.readObject();
			ois.close();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	@SuppressWarnings("unchecked")
	private void loadGames() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAMES_FILE_PATH));
			games = (HashMap<Long, Game>) ois.readObject();
			ois.close();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public void saveDatabase() {
		saveSummoners();
		saveGames();
	}

	private void saveSummoners() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUMMONERS_FILE_PATH));
			oos.writeObject(summoners);
			oos.close();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	private void saveGames() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAMES_FILE_PATH));
			oos.writeObject(games);
			oos.close();
		} catch(Exception e) {e.printStackTrace();}		
	}

	private void loadItems() {
		items = new HashMap<Integer, Item>();
		try {
			File file = new File(ITEMS_FILE_PATH);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList itemsNodeList = doc.getElementsByTagName("item");
			
			for (int iNodeItem = 0; iNodeItem < itemsNodeList.getLength(); iNodeItem++) {
				Node itemNode = itemsNodeList.item(iNodeItem);
				Item item = new Item();
				item.setItemId(Integer.parseInt(itemNode.getChildNodes().item(1).getTextContent()));
				item.setName(itemNode.getChildNodes().item(3).getTextContent());
				item.setImage(itemNode.getChildNodes().item(5).getTextContent());
				item.setDescription(itemNode.getChildNodes().item(7).getTextContent());
				item.setPrice(Integer.parseInt(itemNode.getChildNodes().item(9).getTextContent()));
				item.setTotalPrice(Integer.parseInt(itemNode.getChildNodes().item(11).getTextContent()));
				item.setBuiltFrom(parseItemList(itemNode.getChildNodes().item(13)));
				item.setBuildsInto(parseItemList(itemNode.getChildNodes().item(15)));
				
				items.put(item.getItemId(), item);
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private ArrayList<Integer> parseItemList(Node node) {
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		NodeList itemIdList = node.getChildNodes();
		
		for (int iNode = 0; iNode < itemIdList.getLength(); iNode++) {
			Node idNode = itemIdList.item(iNode);
			if (idNode.getNodeName().equals("itemId"))
				itemList.add(Integer.parseInt(idNode.getTextContent()));
		}
		
		return itemList;
	}
	
	public void resetFiles() {
		HashMap<Long, Summoner> s = new HashMap<Long, Summoner>();
		HashMap<Long, Game> g = new HashMap<Long, Game>();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUMMONERS_FILE_PATH));
			oos.writeObject(s);
			oos.close();
			
			oos = new ObjectOutputStream(new FileOutputStream(GAMES_FILE_PATH));
			oos.writeObject(g);
			oos.close();
		} catch(Exception e) {e.printStackTrace();}
		
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			String SEPARATOR = System.getProperty("file.separator");
			fw = new FileWriter("src" + SEPARATOR + "data" + SEPARATOR + "logs" + SEPARATOR + "parsedGames.txt");
			pw = new PrintWriter(fw);
			pw.print("");			
		} catch(IOException e) {e.printStackTrace();}
		finally {
			try {
				if (fw != null)
					fw.close();
			} catch(Exception e) {e.printStackTrace();}
		}
	}
	
	public HashMap<String, Summoner> getSummoners() {
		return summoners;
	}
	
	public HashMap<Long, Game> getGames() {
		return games;
	}
}
