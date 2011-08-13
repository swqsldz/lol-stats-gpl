package options;

import java.io.BufferedReader;
import java.io.FileReader;

public class Options {

	private final static String SEPARATOR = System.getProperty("file.separator");
	
	/** Ruta al fichero de opciones */
	private static final String OPTIONS_PATH = "src" + SEPARATOR + "data" + SEPARATOR + "options.txt";

	/** Instancia para aplicar el patrón Singleton */
	private static Options instance = null;

	/** Ruta a la carpeta que contiene los ficheros de logs */
	private String logsPath;

	/** Recordar usuario? */
	private boolean rememberUsername;
	
	/** Último usuario logueado en el sistema */
	private String lastLoggedUsername;

	private Options() {
		initOptions();
	}

	public static Options getInstance() {
		if (instance == null)
			instance = new Options();
		return instance;
	}

	private void initOptions() {
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(OPTIONS_PATH);
			br = new BufferedReader(fr);

			String line;
			String[] splittedLine;
			while ((line = br.readLine()) != null) {
				splittedLine = line.split("=");
				if (splittedLine[0].equals("logsPath"))
					logsPath = splittedLine[1];
				else if (splittedLine[0].equals("rememberUsername"))
					rememberUsername = Boolean.parseBoolean(splittedLine[1]);
				else if(splittedLine[0].equals("lastLoggedUsername"))
					lastLoggedUsername = splittedLine[1];

			}
		} catch (Exception e) {e.printStackTrace();}
		  finally {
			try {
				if (fr != null)
					fr.close();
			} catch(Exception e) {e.printStackTrace();}
		}
	}
	
	public void saveOptions() {
		//TODO
	}

	public String getLogsPath() {return logsPath;}
	public void setLogsPath(String logsPath) {this.logsPath = logsPath;}
	
	public boolean getRememberUsername() {return rememberUsername;}
	public void setRememberUsername(boolean rememberUsername) {this.rememberUsername = rememberUsername;}
	
	public String getLastLoggedUsername() {return lastLoggedUsername;}
	public void setLastLoggedUsername(String lastLoggedUsername) {this.lastLoggedUsername = lastLoggedUsername;}
}
