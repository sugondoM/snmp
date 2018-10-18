package snmp;

import java.io.IOException;

import com.google.gson.Gson;
import model.InitConfiguration;
import process.SimpleReader;
import process.SimpleTableViews;

public class Main {

	public static void main(String[] args) throws IOException {
    	doTableView();
    }
    
    public static void doTableView() throws IOException {
    	try {
    		SimpleReader sReader = new SimpleReader();
    		String json = sReader.readJsonFile("config.json");
    		InitConfiguration initConfig = new Gson().fromJson(json, InitConfiguration.class);
    		SimpleTableViews simpleTableViews = new SimpleTableViews();
    		Boolean isGetTable = simpleTableViews.checkSnmpTableView(initConfig);
    		if(isGetTable) {
    			//System.out.println("Process Done");
    		}else {
    			//System.out.println("Process Failed");
    		}
		} catch (Exception e) {
			//e.printStackTrace();
		}
    }

}
