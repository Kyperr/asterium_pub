package gamelogic;

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
	
	private class Stats {
		private int health = DEFAULT_HEALTH;
		
		private int luck;
		
		private int stamina;
		
		private int intuition;
		
		public Stats() {
			this(DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT);
		}
		
		public Stats(final int luck, final int stamina, final int intuition) {
			this.luck = luck;
			this.stamina = stamina;
			this.intuition = intuition;
		}
		
		public int getLuck() {
			return this.luck;
		}
		
		public int getStamina() {
			return this.stamina;
		}
		
		public int getIntuition() {
			return this.intuition;
		}
		
		public int getHealth() {
			return this.health;
		}
		
		public void setLuck(final int luck) {
			this.luck = luck;
		}
		public void setStamina(final int stamina) {
			this.stamina = stamina;
		}
		
		public void setIntuition(final int intuition) {
			this.intuition = intuition;
		}
		
		public void setHealth(final int health) {
			this.health = health;
		}
		
	}
}
