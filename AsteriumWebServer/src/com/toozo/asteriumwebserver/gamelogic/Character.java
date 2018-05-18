package com.toozo.asteriumwebserver.gamelogic;

public class Character {
	
	private static final int DEFAULT_HEALTH = 10;
	
	private static final int DEFAULT_STARTING_STAT = 1;

	private Stats stats;
	
	private String characterName = "";
	
	public Character() {
		this.stats = new Stats();
	}
	
	public String getCharacterName() {
		return this.characterName;
	}
	
	public final Stats getStats() {
		return stats;
	}
	
	public void setCharacterName(final String name) {
		this.characterName = name;
	}
	
	public void setStats(final Stats stats) {
		this.stats = stats;
	}
	
	public static class Stats {
		private int health = DEFAULT_HEALTH;
		
		private int stamina;
		
		private int luck;
		
		private int intuition;
		
		public Stats() {
			this(DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT);
		}
		
		public Stats(final int stamina, final int luck, final int intuition) {
			this.stamina = stamina;
			this.luck = luck;
			this.intuition = intuition;
		}
		
		public int getStamina() {
			return this.stamina;
		}
		
		public int getLuck() {
			return this.luck;
		}
		
		public int getIntuition() {
			return this.intuition;
		}
		
		public int getHealth() {
			return this.health;
		}
		
		public void setStamina(final int stamina) {
			this.stamina = stamina;
		}
		
		public void setLuck(final int luck) {
			this.luck = luck;
		}
		
		public void setIntuition(final int intuition) {
			this.intuition = intuition;
		}
		
		public void setHealth(final int health) {
			this.health = health;
		}
		
	}
}