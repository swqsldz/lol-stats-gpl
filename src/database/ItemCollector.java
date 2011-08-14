package database;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import log.ds.Item;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class ItemCollector {
	
	private final static String SEPARATOR = System.getProperty("file.separator");
	private static final String ITEMS_URL = "http://www.leagueoflegends.com/items";
	private static final String ITEM_IMAGES_PATH = "src" + SEPARATOR + "resources" + SEPARATOR + "items" + SEPARATOR;
	private static final String ITEM_XML_FILE = "src" + SEPARATOR + "data" + SEPARATOR + "items" + SEPARATOR + "items.xml";
	
	public static void main(String[] args) {
		HashMap<Integer, Item> items = new HashMap<Integer, Item>();
		Item item;
		
		try {
			Parser parser = new Parser(ITEMS_URL);
			NodeList nodelist = parser.parse(null);
			
			// Se extraen los nodos de los objetos
			NodeList nlItems = nodelist.extractAllNodesThatMatch(new NodeFilterTableChampionItem(), true);
			
			SimpleNodeIterator nodeIt = nlItems.elements();
			while (nodeIt.hasMoreNodes()) {
				Node node = nodeIt.nextNode().getChildren().elementAt(1);
				
				item = new Item();
				
				// Se extrae el identificador y la imagen del objeto				
				parseItemIcon(item, node.getChildren().elementAt(1)
										.getChildren().elementAt(1)
										.getChildren().elementAt(1));
				// Se extrae el nombre, la descripción y los materiales
				parseItemInfo(item, node.getChildren().elementAt(3));
				// Se extraen los precios del objeto
				parseItemPrices(item, node.getChildren().elementAt(5));
				
				items.put(item.getItemId(), item);
			}
			
		} catch (ParserException e) {e.printStackTrace();}
		
		// Se descargan las imágenes de internet
		downloadItemImgs(items);
		// Se guarda la información de los objetos en formato XML
		writeItemsFile(items);
	}
	
	private static void downloadItemImgs(HashMap<Integer, Item> items) {
		try {
			Iterator<Item> itemIt = items.values().iterator();
			while (itemIt.hasNext()) {
				Item item = itemIt.next();
				URL imgURL = new URL(item.getImgURL());
				URLConnection urlCon = imgURL.openConnection();
				
				InputStream is = urlCon.getInputStream();
				
				FileOutputStream fos = new FileOutputStream(ITEM_IMAGES_PATH + item.getItemId() + ".gif");
				
				byte[] buffer = new byte[1000];
				
				int read = is.read(buffer);
				while (read > 0) {
					fos.write(buffer, 0, read);
					read = is.read(buffer);
				}
				
				is.close();
				fos.close();
			}
			
		} catch(Exception e) {e.printStackTrace();}
	}

	private static void writeItemsFile(HashMap<Integer, Item> items) {
		FileWriter file = null;
		PrintWriter pw = null;
		
		try {
			file = new FileWriter(ITEM_XML_FILE);
			pw = new PrintWriter(file);
			
			pw.println("<items>");
			Iterator<Item> itemIt = items.values().iterator();
			while (itemIt.hasNext()) {
				Item item = itemIt.next();
				pw.println("\t<item>");
				pw.println("\t\t<id>" + item.getItemId() + "</id>");
				pw.println("\t\t<name>" + item.getName() + "</name>");
				pw.println("\t\t<img>" + item.getItemId() + ".gif</img>");
				pw.println("\t\t<description>" + item.getDescription() + "</description>");
				pw.println("\t\t<price>" + item.getPrice() + "</price>");
				pw.println("\t\t<totalPrice>" + item.getTotalPrice() + "</totalPrice>");
				pw.println("\t\t<builtFrom>");
				if (item.getBuiltFrom() != null)
					for (int itemId : item.getBuiltFrom())
						pw.println("\t\t\t<itemId>" + itemId + "</itemId>");
				pw.println("\t\t</builtFrom>");
				pw.println("\t\t<buildsInto>");
				if (item.getBuildsInto() != null)
					for (int itemId : item.getBuildsInto())
						pw.println("\t\t\t<itemId>" + itemId + "</itemId>");
				pw.println("\t\t</buildsInto>");
				pw.println("\t</item>");
			}
			pw.println("</items>");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null)
					file.close();
			} catch(Exception e) {e.printStackTrace();}
		}
		
		
	}

	private static void parseItemPrices(Item item, Node node) {
		item.setPrice(Integer.parseInt(node.getChildren().elementAt(1)
										   .getChildren().elementAt(0)
										   .getText()));
		item.setTotalPrice(Integer.parseInt(node.getChildren().elementAt(1)
										   .getChildren().elementAt(4)
										   .getText()));
	}

	private static void parseItemIcon(Item item, Node node) {
		item.setImgURL(getImgURL(node.getText()));
		item.setItemId(getItemId(node.getText()));		
	}

	private static void parseItemInfo(Item item, Node node) {
		// Nombre del objeto
		Node nodeName = node.getChildren().elementAt(1)
							.getChildren().elementAt(1)
							.getFirstChild();
		item.setName(nodeName.getText());
		// Descripción del objeto
		Node nodeDescription = node.getChildren().elementAt(3)
								   .getFirstChild();
		item.setDescription(nodeDescription.getText());
		
		NodeList nlBuiltFrom = node.getChildren().extractAllNodesThatMatch(new NodeFilterBuiltFrom(), true);
		if (nlBuiltFrom.size() != 0) {
			Node nList = nlBuiltFrom.elementAt(0).getParent().getNextSibling().getNextSibling();
			item.setBuiltFrom(parseItemList(nList));
		}
		NodeList nlBuildsInto = node.getChildren().extractAllNodesThatMatch(new NodeFilterBuildsInto(), true);
		if (nlBuildsInto.size() != 0) {
			Node nList = nlBuildsInto.elementAt(0).getParent().getNextSibling().getNextSibling();
			item.setBuildsInto(parseItemList(nList));
		}
	}

	private static ArrayList<Integer> parseItemList(Node nList) {
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		
		SimpleNodeIterator itemIt = nList.getChildren().elements();
		
		while (itemIt.hasMoreNodes()) {
			Node node = itemIt.nextNode();
			if (node.getText().contains("li")) {
				itemList.add(getItemId(node.getFirstChild().getFirstChild().getText()
										   .split(" ")[1].split("=")[1]));
			}
		}
		return itemList;
	}

	private static String getImgURL(String imgURL) {
		String[] splittedImgURL = imgURL.split("\"");
		return splittedImgURL[1];
	}
	
	private static int getItemId(String imgURL) {
		String[] splittedImgURL = imgURL.split("/");
		return Integer.parseInt(splittedImgURL[splittedImgURL.length - 1].substring(0,4));
	}
}

class NodeFilterTableChampionItem implements NodeFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(Node node) {
		return node.getText().equals("table class=\"champion_item\"");
	}
}

class NodeFilterBuiltFrom implements NodeFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(Node node) {
		return node.getText().equals("Built From");
	}
}

class NodeFilterBuildsInto implements NodeFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(Node node) {
		return node.getText().equals("Builds Into");
	}
}
