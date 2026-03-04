package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Environnement;
import com.example.dernierespoirsae.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;

public class VueTerrain {
    private TilePane terrainPane;
    private Image[] tiles;
    private ArrayList<Integer> terrainFond;
    private ArrayList<Integer> terrainAutres;
    private ArrayList<Integer> terrainColision;
    private Environnement environnement;

    public VueTerrain(Terrain terrain, TilePane terrainPane, ArrayList<Integer> terrainColision, ArrayList<Integer> terrainFond, ArrayList<Integer> terrainAutres, Environnement environnement) {
        this.terrainPane = terrainPane;
        this.terrainColision = terrainColision;
        this.terrainFond = terrainFond;
        this.terrainAutres = terrainAutres;
        this.environnement = environnement;
        terrain.setTerrain(terrainColision);

        Image tileset = new Image(getClass().getResource("/com/example/dernierespoirsae/tiles.png").toExternalForm(), 1792, 1120, false, false);

        int tileWidth = 32;
        int tileHeight = 32;
        int colonne = (int) (tileset.getWidth() / tileWidth);
        int ligne = (int) (tileset.getHeight() / tileHeight);
        tiles = new Image[colonne * ligne];

        for (int y = 0; y < ligne; y++) {
            for (int x = 0; x < colonne; x++) {
                tiles[y * colonne + x] = new WritableImage(tileset.getPixelReader(), (x * tileWidth), (y * tileHeight), tileWidth, tileHeight);
            }
        }
        afficherTerrain();
    }

    public void afficherTerrain() {
        for (int i = 0; i < terrainFond.size(); i++) {
            ImageView imageViewFond = null;
            ImageView imageViewAutres = null;
            ImageView imageViewColision = null;

            int tileIndexFond = terrainFond.get(i);
            int tileIndexAutres = terrainAutres.get(i);
            int tileIndexColision = terrainColision.get(i);

            if (tileIndexFond >= 1 && tileIndexFond <= tiles.length) {
                imageViewFond = new ImageView(tiles[tileIndexFond - 1]);
            }
            if (tileIndexAutres >= 1 && tileIndexAutres <= tiles.length) {
                imageViewAutres = new ImageView(tiles[tileIndexAutres - 1]);
            }
            if (tileIndexColision >= 1 && tileIndexColision <= tiles.length) {
                imageViewColision = new ImageView(tiles[tileIndexColision - 1]);
            }

            StackPane stackPane = new StackPane();
            if (imageViewFond != null) stackPane.getChildren().add(imageViewFond);
            if (imageViewAutres != null) stackPane.getChildren().add(imageViewAutres);
            if (imageViewColision != null) stackPane.getChildren().add(imageViewColision);

            this.terrainPane.getChildren().add(stackPane);
        }
    }

    public Image obtenirImageTerrainFond(int ligne, int colonne,int valeurDanslaListe) {
        return retournerImageNimporteQuelterrain(this.terrainFond,ligne,colonne,valeurDanslaListe);

    }
    public Image obtenirImageTerrainAutres(int ligne, int colonne,int valeurDanslaListe) {
        return retournerImageNimporteQuelterrain(this.terrainAutres,ligne,colonne,valeurDanslaListe);
    }
    public Image retournerImageNimporteQuelterrain(ArrayList<Integer> terrain,int ligne, int colonne,int valeurDanslaListe){
        int index;
        int tileIndex;
        if (valeurDanslaListe==-1) {
            index = ligne * environnement.getInfoTuile()[1] + colonne;
        }
        else {
            index = valeurDanslaListe;
        }

        if (index >= 0 && index < terrain.size()) {
            tileIndex = terrain.get(index) - 1;
            if (tileIndex >= 0 && tileIndex < tiles.length) {
                return tiles[tileIndex];
            }
        }
        return null;
    }

    public Image[] getTiles() {
        return tiles;
    }

    public ArrayList<Integer> getTerrainAutres() {
        return terrainAutres;
    }
}
