package application;

import java.util.HashMap;

public class DataProvider {
	
	static public HashMap<Environment, HashMap<String,String>> provideSetOfData() {
		HashMap<String, String> development = new HashMap<String, String>();
		HashMap<String, String> test = new HashMap<String, String>();
		HashMap<String, String> production = new HashMap<String, String>();
		
		development.put("dev1","12");
		development.put("dev2","34");
		development.put("dev3", "56");
		
		test.put("test1","12");
		test.put("test2","34");
		test.put("test3","56");
		
		production.put("prod1","12");
		production.put("prod2","34");
		production.put("prod3","56");
		
		HashMap<Environment, HashMap<String,String>> hashMap = new HashMap<Environment, HashMap<String,String>>();
		hashMap.put(application.Environment.PRODUCTION, production);
		hashMap.put(application.Environment.TEST, test);
		hashMap.put(application.Environment.DEVELOPMENT, development);
		return hashMap;
	}
	

}
