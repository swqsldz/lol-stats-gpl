package log.ds;

import database.Database;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import log.ds.game.Game;

public class Summoner implements Serializable {
	
	private static final long serialVersionUID = -2428863601835711934L;

	/** Id del invocador */
	private long summonerId;
	
	/** Nombre del invocador */
	private String summonerName;
	
	/** Icono utilizado por el invocador */
	private int summonerIcon;
	
	/** Estadísticas del jugador en partidas contra bots */
	private Statistics botStats;
	
	/** Estadísticas del jugador en partidas normales */
	private Statistics normalStats;
	
	/** Estadísticas del jugador en partidas clasificatorias de 3v3 */
	private Statistics ranked3pStats;
	
	/** Estadísticas del jugador en partidas clasifitatorias de 5v5 solo */
	private Statistics ranked5sStats;
	
	/** Estadísticas del jugador en partidas clasificatorias de 5v5 premade */
	private Statistics ranked5pStats;
	
	/** Nivel del invocador */
	private int level;
	
	/** Fecha de la última actualización de los datos del jugador */
	private Calendar lastUpdate;
	
	/** Lista de partidas a las que ha jugado el invocador */
	private HashSet<Long> games;
	
	public Summoner(SummonerStats summonerStats) {
		summonerName = summonerStats.getSummonerName();
		summonerId = summonerStats.getSummonerId();
		botStats = new Statistics();
		normalStats = new Statistics();
		ranked3pStats = new Statistics();
		ranked5pStats = new Statistics();
		ranked5sStats = new Statistics();
		games = new HashSet<Long>();
		updateStats(summonerStats);
	}
	
	public final void updateStats(SummonerStats summonerStats) {
		Game game = Database.getInstance().getGames().get(summonerStats.getGameId());
		
		if (game.getQueueType() == QueueType.BOT)
			botStats.updateStats(game, summonerStats);
		else if (game.getQueueType() == QueueType.NORMAL)
			normalStats.updateStats(game, summonerStats);
		else if (game.getQueueType() == QueueType.RANKED_PREMADE_3x3)
			ranked3pStats.updateStats(game, summonerStats);
		else if (game.getQueueType() == QueueType.RANKED_PREMADE_5x5)
			ranked5pStats.updateStats(game, summonerStats);
		else if (game.getQueueType() == QueueType.RANKED_SOLO_5x5)
			ranked5sStats.updateStats(game, summonerStats);
		
		if (lastUpdate == null || game.getCreationTime().after(lastUpdate)) {
			summonerIcon = summonerStats.getSummonerIcon();
			level = summonerStats.getLevel();
			lastUpdate = game.getCreationTime();
		}
		
		games.add(game.getId());
	}
	
	@Override
	public String toString() {
		return summonerName;
	}
	
	/* Accesoras y modificadoras */
	public long getSummonerId() {return summonerId;}
	public void setSummonerId(long summonerId) {this.summonerId = summonerId;}
	
	public String getSummonerName() {return summonerName;}
	public void setSummonerName(String summonerName) {this.summonerName = summonerName;}
	
	public int getSummonerIcon() {return summonerIcon;}
	public void setSummonerIcon(int summonerIcon) {this.summonerIcon = summonerIcon;}
	
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level = level;}
}

class Statistics implements Serializable {
	/** ELO del invocador */
	private int elo;
	
	/** ELO máximo del invocador */
	private int maxElo;
	
	/** Victorias del invocador */
	private int wins;
	
	/** Derrotas del invocador */
	private int losses;
	
	/** Abandonos del jugador */
	private int leaves;
	
	/** Última actualización de la información */
	private Calendar lastUpdate;
	
	public Statistics() {
		elo = -1;
		maxElo = -1;
		wins = -1;
		losses = -1;
		leaves = -1;
		lastUpdate = null;
	}
	
	public void updateStats(Game game, SummonerStats summonerStats) {
		if (lastUpdate == null || game.getCreationTime().after(lastUpdate)) {
			if (summonerStats.getElo() != -1)
				elo = summonerStats.getElo();
			maxElo = Math.max(maxElo, summonerStats.getElo());
			wins = summonerStats.getWins();
			losses = summonerStats.getLosses();
			leaves = summonerStats.getLeaves();
			lastUpdate = game.getCreationTime();
		}
	}
	
	@Override
	public String toString() {
		return "ELO: " + elo +
				", Max. ELO: " + maxElo +
				", Wins: " + wins + 
				", Losses: " + losses + 
				", Leaves: " + leaves;
	}
	
	/* Accesoras y modificadoras */	
	public int getElo() {return elo;}
	public void setElo(int elo) {this.elo = elo;}
	
	public int getMaxElo() {return maxElo;}
	public void setMaxElo(int maxElo) {this.maxElo = maxElo;}
	
	public int getWins() {return wins;}
	public void setWins(int wins) {this.wins = wins;}
	
	public int getLosses() {return losses;}
	public void setLosses(int losses) {this.losses = losses;}
	
	public int getLeaves() {return leaves;}
	public void setLeaves(int leaves) {this.leaves = leaves;}
	
	public Calendar getLastUpdate() {return lastUpdate;}
	public void setLastUpdate(Calendar lastUpdate) {this.lastUpdate = lastUpdate;} 
}
