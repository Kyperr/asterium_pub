package com.toozo.asteriumwebserver.gamelogic;

public interface Activity {

	public static final String SEARCH = "search";
	
	public void doActivity(Game game, PlayerCharacter character, Location location);
	
	public static Activity searchActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			// TODO Auto-generated method stub
			
		}
	};
}