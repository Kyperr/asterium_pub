package main;

import general.AbstractAction;


public class Example {

	public static void main(String[] args) {
		Parser parser = new Parser();
		
		String msg = "{\"request\":{\"auth_token\":\"12345\",\"action_name\":\"join_as_player\",\"join_as_player\":{\"lobby_id\":\"abcde\",\"player\":{\"name\":\"Dude\"}}}}";
		
		AbstractAction a = parser.parse(msg);
		a.run();

	}
	
	

}
