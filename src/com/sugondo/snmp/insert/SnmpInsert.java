package com.sugondo.snmp.insert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import javafx.scene.SnapshotParameters;
import model.InitConfiguration;
import process.SimpleInReader;
import process.SimpleReader;
import process.SimpleStringProvider;
import process.SimpleTableViews;

public class SnmpInsert {

//    public static void main(String[] args) throws IOException {
//        doTableView();
//        System.out.println("test");
//    }

    List <String> snmpList;

    public SnmpInsert() throws IOException {
        this.snmpList = doTableView();
    }

    public List<String> getSnmpList() throws IOException {
        snmpList = doTableView();
        return snmpList;
    }

    public List<String> doTableView() throws IOException {
        try {
            SimpleReader sReader = new SimpleReader();
            SimpleStringProvider sProvider = new SimpleStringProvider();
            SimpleInReader siReader = new SimpleInReader();

            InputStream configStream = this.getClass().getResourceAsStream("/config.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(configStream));
            String json = siReader.readJsonFile(bufferedReader);

            //String json = sReader.readJsonFile("config.json");
            //String json = sProvider.stringProvieder();
            //System.out.println(json);
            InitConfiguration initConfig = new Gson().fromJson(json, InitConfiguration.class);
            SimpleTableViews simpleTableViews = new SimpleTableViews();
            List<String> isGetTable = simpleTableViews.checkSnmpTableView(initConfig);
            if(! isGetTable.isEmpty()) {
                return  isGetTable;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
