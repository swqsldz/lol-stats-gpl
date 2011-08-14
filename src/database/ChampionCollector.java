package database;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.htmlparser.NodeFilter;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class ChampionCollector {
	private final static String SEPARATOR = System.getProperty("file.separator");
	private static final String CHAMPIONS_URL = "http://www.leagueoflegends.com/champions";
	private static final String CHAMPION_IMAGES_PATH = "src" + SEPARATOR + "resources" + SEPARATOR + "champions" + SEPARATOR;
	
	public static void main(String[] args) {
		HashMap<String, String> champions = new HashMap<String, String>();

		try {
			Parser parser = new Parser(CHAMPIONS_URL);
			NodeList nodelist = parser.parse(null);
			
			// Se extraen los nodos de los campeones
			NodeList nlItems = nodelist.extractAllNodesThatMatch(new NodeFilterTableChampionItem(), true);
			
			SimpleNodeIterator nodeIt = nlItems.elements();
			while (nodeIt.hasMoreNodes()) {
				Node node = nodeIt.nextNode();
				TableRow row = ((TableTag)node).getRow(0);
				String url = row.getColumns()[0].getFirstChild().getFirstChild().getText().split("\"")[1];
				String championName = row.getColumns()[1].getChild(1).getFirstChild().getFirstChild().getText();
				champions.put(championName, url);
			}
			
		} catch (ParserException e) {e.printStackTrace();}
		
		// Se descargan las imágenes de internet
		downloadChampionImgs(champions);
	}
	
	private static void downloadChampionImgs(HashMap<String, String> champions) {
		try {
			Iterator<Entry<String,String>> championIt = champions.entrySet().iterator();
			while (championIt.hasNext()) {
				Entry<String,String> champion = championIt.next();
				URL championURL = new URL(champion.getValue());
				URLConnection urlCon = championURL.openConnection();
				
				InputStream is = urlCon.getInputStream();
				
				FileOutputStream fos = new FileOutputStream(CHAMPION_IMAGES_PATH + champion.getKey() + ".jpg");
				
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
}

class NodeFilterTableChampions implements NodeFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean accept(Node node) {
		return node.getText().equals("table class=\"champion_item\"");
	}
}