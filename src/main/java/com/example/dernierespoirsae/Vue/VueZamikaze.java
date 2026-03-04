package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Acteurs.Acteur;
import com.example.dernierespoirsae.modele.Acteurs.Zamikaze;
import com.example.dernierespoirsae.modele.Environnement;
import com.example.dernierespoirsae.modele.Terrain;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;

public class VueZamikaze extends VueZombie{
    private VueTerrain vueTerrain;

    public VueZamikaze(Pane persoPane,Pane barreViePane, TilePane terrainPane, Zamikaze acteur, Environnement environnement, VueTerrain vueTerrain) {
        super(persoPane,barreViePane, terrainPane, acteur, environnement);
        this.vueTerrain = vueTerrain;
        ChangeListener<Boolean> explosion = ((obs,old,nouv)-> explosion());
        acteur.getAExploseeProperty().addListener(explosion);
    }

    @Override
    public String imageACreer() {
        return getClass().getResource("/com/example/dernierespoirsae/images/zamikaze0.png").toExternalForm();
    }

    public void explosion() {
        int tuileAcolonne = getActeur().getX() / getEnvironnement().getInfoTuile()[0];
        int tuileAligne = getActeur().getY() / getEnvironnement().getInfoTuile()[0];
        int tuilePositionEListe; // récupère la position de l'ennemi dans la liste
        int val;

        ModifVue.addGifToPane(getActeur().getX(), getActeur().getY(), 96, getClass().getResource("/com/example/dernierespoirsae/images/explosion.gif").toExternalForm(), 480,getPersoPane());

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {

                if (tuileAligne + y >= 0 && tuileAligne + y < getEnvironnement().getInfoTuile()[1] &&
                        tuileAcolonne + x >= 0 && tuileAcolonne + x < getEnvironnement().getInfoTuile()[1]) {
                    val = (tuileAligne + y) * getEnvironnement().getInfoTuile()[1] + (tuileAcolonne + x);
                    if (getEnvironnement().getTerrain().getTerrain().get(val) == 2) {
                        tuilePositionEListe = getEnvironnement().getInfoTuile()[1] * (tuileAligne + y) + (tuileAcolonne + x);

                        // Mettre à jour le modèle
                        vueTerrain.getTerrainAutres().set(tuilePositionEListe, 65 +1); // 121 représente une tuile brûlée dans le modèle

                        // Utiliser setImageAtIndex pour mettre à jour l'image de la tuile
                        ModifVue.setImageAtIndex(tuilePositionEListe, vueTerrain.getTiles()[65], getEnvironnement(), getTerrainPane());
                    }
                }
            }
        }
        ((Zamikaze) getActeur()).meurt();
    }
    @Override
    public int[] placementImage() {
        return new int[]{0, 0};
    }
}
