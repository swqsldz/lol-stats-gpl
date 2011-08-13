package log.ds;

import java.io.Serializable;
import java.util.ArrayList;

public class SummonerStats implements Serializable {
	
	private static final long serialVersionUID = 8668057616612900583L;

	/** Identificador de la partida a la que pertenecen las estadísticas */
	private long gameId;
	
	/** ¿Es propietario de la partida? */
	private boolean isGameOwner;
	
	/** ¿Es el usuario? */
	private boolean isMe;
	
	/** ¿Es un bot? */
	private boolean isBot;
	
	/** ¿Abandonó la partida? */
	private boolean isLeaver;
	
	/** Campeón seleccionado*/
	private Champion selectedChampion;
	
	/** Hechizo del invocador 1 */
	private Spell summonerSpell1;
	
	/** Hechizo del invocador 2 */
	private Spell summonerSpell2;
	
	/** ELO del jugador */
	private int elo;
	
	/** Cambio en el ELO del jugador */
	private int eloChange;
	
	/** Nivel del invocador */
	private int level;
	
	/** Equipo del jugador */
	private int teamId;
	
	/** Identificador del usuario */
	private long summonerId;
	
	/** Abandonos del jugador */
	private int leaves;
	
	/** Partidas perdidas */
	private int losses;
	
	/** Partidas ganadas */
	private int wins;
	
	/** Nombre de invocador */
	private String summonerName;
	
	/** Icono utilizado por el invocador */
	private int summonerIcon;
	
	/** Lista de objetos */
	private ArrayList<Item> items;
	
	/** Número de asesinatos */
	private int kills;
	
	/** Número de muertes */
	private int deaths;
	
	/** Número de asistencias */
	private int assists;
	
	/** Número de minions asesinados */
	private int minionsSlain;
	
	/** Número de inhibidores destruidos */
	private int barracksDestroyed;
	
	/** Número de torretas destruidas */
	private int turretsDestroyed;
	
	/** Número de monstruos neutrales asesinados */
	private int neutralMonstersSlain;
	
	/** Daño causado */
	private int damageDealt;
	
	/** Daño físico causado */
	private int physicalDamageDealt;
	
	/** Daño mágico causado */
	private int magicDamageDealt;
	
	/** Mayor golpe crítico */
	private int largestCriticalStrike;
	
	/** Mayor racha de asesinatos */
	private int largestKillingSpree;
	
	/** Mayor asesinato múltiple */
	private int largestMultiKill;
	
	/** Daño recibido */
	private int damageTaken;
	
	/** Daño físico recibido */
	private int physicalDamageTaken;
	
	/** Daño mágico recibido */
	private int magicDamageTaken;
	
	/** Vida regenerada */
	private int healthRestored;
	
	/** Oro conseguido */
	private int gold;
	
	/** Tiempo transcurrido muerto */
	private int timeSpentDead;
	
	/** Nivel del campeón */
	private int championLevel; 
	
	public SummonerStats() {
		gameId = -1;
		isGameOwner = false;
		isMe = false;
		isBot = false;
		isLeaver = false;
		selectedChampion = null;
		summonerSpell1 = null;
		summonerSpell2 = null;
		elo = -1;
		eloChange = -1;
		level = -1;
		teamId = -1;
		summonerId = -1;
		leaves = -1;
		losses = -1;
		wins = -1;
		summonerName = null;
		summonerIcon = -1;
		items = null;
		kills = -1;
		deaths = -1;
		assists = -1;
		minionsSlain = -1;
		barracksDestroyed = -1;
		turretsDestroyed = -1;
		neutralMonstersSlain = -1;
		damageDealt = -1;
		physicalDamageDealt = -1;
		magicDamageDealt = -1;
		largestCriticalStrike = -1;
		largestKillingSpree = -1;
		largestMultiKill = -1;
		damageTaken = -1;
		physicalDamageTaken = -1;
		magicDamageTaken = -1;
		healthRestored = -1;
		gold = -1;
		timeSpentDead = -1;
	}

	/* Accesoras y modificadoras */
	public long getGameId() {return gameId;}
	public void setGameId(long gameId) {this.gameId = gameId;}
	
	public boolean isGameOwner() {return isGameOwner;}
	public void setGameOwner(boolean isGameOwner) {this.isGameOwner = isGameOwner;}

	public boolean isMe() {return isMe;}
	public void setMe(boolean isMe) {this.isMe = isMe;}

	public boolean isBot() {return isBot;}
	public void setBot(boolean isBot) {this.isBot = isBot;}

	public boolean isLeaver() {return isLeaver;}
	public void setLeaver(boolean isLeaver) {this.isLeaver = isLeaver;}
	
	public Champion getSelectedChampion() {return selectedChampion;}
	public void setSelectedChampion(Champion selectedChampion) {this.selectedChampion = selectedChampion;}

	public Spell getSummonerSpell1() {return summonerSpell1;}
	public void setSummonerSpell1(Spell summonerSpell1) {this.summonerSpell1 = summonerSpell1;}

	public Spell getSummonerSpell2() {return summonerSpell2;}
	public void setSummonerSpell2(Spell summonerSpell2) {this.summonerSpell2 = summonerSpell2;}

	public int getElo() {return elo;}
	public void setElo(int elo) {this.elo = elo;}

	public int getEloChange() {return eloChange;}
	public void setEloChange(int eloChange) {this.eloChange = eloChange;}
	
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level = level;}

	public int getTeamId() {return teamId;}
	public void setTeamId(int teamId) {this.teamId = teamId;}
	
	public long getSummonerId() {return summonerId;}
	public void setSummonerId(long summonerId) {this.summonerId = summonerId;}
	
	public int getLeaves() {return leaves;}
	public void setLeaves(int leaves) {this.leaves = leaves;}
	
	public int getLosses() {return losses;}
	public void setLosses(int losses) {this.losses = losses;}
	
	public int getWins() {return wins;}
	public void setWins(int wins) {this.wins = wins;}
	
	public String getSummonerName() {return summonerName;}
	public void setSummonerName(String summonerName) {this.summonerName = summonerName;}
	
	public int getSummonerIcon() {return summonerIcon;}
	public void setSummonerIcon(int summonerIcon) {this.summonerIcon = summonerIcon;}

	public ArrayList<Item> getItems() {return items;}
	public void setItems(ArrayList<Item> items) {this.items = items;}

	public int getKills() {return kills;}
	public void setKills(int kills) {this.kills = kills;}

	public int getDeaths() {return deaths;}
	public void setDeaths(int deaths) {this.deaths = deaths;}

	public int getAssists() {return assists;}
	public void setAssists(int assists) {this.assists = assists;}

	public int getMinionsSlain() {return minionsSlain;}
	public void setMinionsSlain(int minionsSlain) {this.minionsSlain = minionsSlain;}

	public int getBarracksDestroyed() {return barracksDestroyed;}
	public void setBarracksDestroyed(int barracksDestroyed) {this.barracksDestroyed = barracksDestroyed;}

	public int getTurretsDestroyed() {return turretsDestroyed;}
	public void setTurretsDestroyed(int turretsDestroyed) {this.turretsDestroyed = turretsDestroyed;}

	public int getNeutralMonstersSlain() {return neutralMonstersSlain;}
	public void setNeutralMonstersSlain(int neutralMonstersSlain) {this.neutralMonstersSlain = neutralMonstersSlain;}
	
	public int getDamageDealt() {return damageDealt;}
	public void setDamageDealt(int damageDealt) {this.damageDealt = damageDealt;}

	public int getPhysicalDamageDealt() {return physicalDamageDealt;}
	public void setPhysicalDamageDealt(int physicalDamageDealt) {this.physicalDamageDealt = physicalDamageDealt;}

	public int getMagicDamageDealt() {return magicDamageDealt;}
	public void setMagicDamageDealt(int magicDamageDealt) {this.magicDamageDealt = magicDamageDealt;}

	public int getLargestCriticalStrike() {return largestCriticalStrike;}
	public void setLargestCriticalStrike(int largestCriticalStrike) {this.largestCriticalStrike = largestCriticalStrike;}

	public int getLargestKillingSpree() {return largestKillingSpree;}
	public void setLargestKillingSpree(int largestKillingSpree) {this.largestKillingSpree = largestKillingSpree;}

	public int getLargestMultiKill() {return largestMultiKill;}
	public void setLargestMultiKill(int largestMultiKill) {this.largestMultiKill = largestMultiKill;}

	public int getDamageTaken() {return damageTaken;}
	public void setDamageTaken(int damageTaken) {this.damageTaken = damageTaken;}

	public int getPhysicalDamageTaken() {return physicalDamageTaken;}
	public void setPhysicalDamageTaken(int physicalDamageTaken) {this.physicalDamageTaken = physicalDamageTaken;}

	public int getMagicDamageTaken() {return magicDamageTaken;}
	public void setMagicDamageTaken(int magicDamageTaken) {this.magicDamageTaken = magicDamageTaken;}

	public int getHealthRestored() {return healthRestored;}
	public void setHealthRestored(int healthRestored) {this.healthRestored = healthRestored;}

	public int getGold() {return gold;}
	public void setGold(int gold) {this.gold = gold;}

	public int getTimeSpentDead() {return timeSpentDead;}
	public void setTimeSpentDead(int timeSpentDead) {this.timeSpentDead = timeSpentDead;}
	
	public int getChampionLevel() {return championLevel;}
	public void setChampionLevel(int championLevel) {this.championLevel = championLevel;}
}
