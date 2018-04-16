package main;

import general.AbstractAction;


public class Example {

	public static void main(String[] args) {
		Parser parser = new Parser();
		
		/*Gson gson = new Gson();
		
		Player p = new Player("test1");
		Map<String, Object> argsMap = new HashMap<String, Object>();;
		argsMap.put("lobby_id", "abcde");
		argsMap.put("player", p);
		Request req = new Request("join_lobby", argsMap, "12345");
		System.out.println(gson.toJson(req));*/
		
		String msg = "{\"authToken\":\"12345\",\"isRequest\":true,\"actionName\":\"join_lobby\",\"argsList\":{\"lobby_id\":\"abcde\",\"player\":{\"name\":\"test1\"}}}";
		
		AbstractAction a = parser.parse(msg);
		a.run();

	}
	
	

}
