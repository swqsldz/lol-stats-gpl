package system;

import java.util.ArrayList;
import database.Database;
import java.util.HashMap;
import java.util.HashSet;
import log.ds.Summoner;
import log.ds.SummonerStats;
import log.ds.game.Game;

public class LoggedSummoner {
	
	/** Nombre del invocador logueado en el sistema */
	private String summonerName;
	
	/** Lista ordenada según los campeones más usados */
	private ArrayList<UsedChampion> mostUsed;
	
	
	
	public LoggedSummoner(String summonerName) {
		this.summonerName = summonerName;
		Database db = Database.getInstance();
		
		Summoner summoner = db.getSummoners().get(summonerName);
		HashMap<Long, Game> games = db.getGames();
		HashSet<Long> summonerGames = summoner.getGames();
		for (long gameId : summonerGames) {
			if (gameId == -1) continue;
			Game game = games.get(gameId);
			
			SummonerStats summonerStats;
			summonerStats = game.getTeam1().get(summonerName);
			if (summonerStats == null)
				summonerStats = game.getTeam2().get(summonerName);
			
			
			System.out.println(summonerStats.getSelectedChampion());
		}
		
	}
	
	
	/** Clase para guardar el número de veces que se ha usado un campeón */
	private class UsedChampion implements Comparable {

		private String champion;
		private int timesUsed;
		
		public UsedChampion(String champion) {
			this.champion = champion;
			this.timesUsed = 0;
		}
		
		public void use() {
			timesUsed++;
		}
		
		public int compareTo(Object o) {
			if (o instanceof UsedChampion)
				return timesUsed - ((UsedChampion)o).timesUsed;
			else return 0;
		}
		
	}
}
