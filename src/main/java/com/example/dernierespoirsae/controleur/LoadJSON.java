package com.example.dernierespoirsae.controleur;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LoadJSON {

    private ArrayList<Integer> terrainColision;
    private ArrayList<Integer> terrainAutres;
    private ArrayList<Integer> terrainFond;
    private int PrefColumns;
    private int PrefRows;

    public LoadJSON(String file){
        loadMap(file);
    }

    public void loadMap(String resourcePath) {
        ArrayList<Integer> elementsMapFond = new ArrayList<>();
        ArrayList<Integer> elementsMapAutres = new ArrayList<>();
        ArrayList<Integer> elementsMapColision = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray layersArray = (JSONArray) jsonObject.get("layers");

            JSONObject terrainFond = (JSONObject) layersArray.get(0);
            JSONObject terrainAutres = (JSONObject) layersArray.get(1);
            JSONObject terrainColision = (JSONObject) layersArray.get(2);


            JSONArray dataArrayFond = (JSONArray) terrainFond.get("data");
            JSONArray dataArrayAutres = (JSONArray) terrainAutres.get("data");
            JSONArray dataArrayColision = (JSONArray) terrainColision.get("data");

            this.PrefColumns = (int) (long) terrainFond.get("height");
            this.PrefRows = (int) (long) terrainFond.get("width");

            for (Object data : dataArrayFond) {
                elementsMapFond.add(Math.toIntExact((Long) data));
            }
            for (Object data : dataArrayAutres) {
                elementsMapAutres.add(Math.toIntExact((Long) data));
            }
            for (Object data : dataArrayColision) {
                elementsMapColision.add(Math.toIntExact((Long) data));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        this.terrainFond = elementsMapFond;
        this.terrainAutres = elementsMapAutres;
        this.terrainColision = elementsMapColision;

    }

    public ArrayList<Integer> getMapColision() {
        return terrainColision;
    }
    public ArrayList<Integer> getMapFond() {
        return terrainFond;
    }
    public ArrayList<Integer> getMapAutres() {
        return terrainAutres;
    }

    public int getPrefColumns() {
        return PrefColumns;
    }
    public int getPrefRows() {
        return PrefRows;
    }
}
