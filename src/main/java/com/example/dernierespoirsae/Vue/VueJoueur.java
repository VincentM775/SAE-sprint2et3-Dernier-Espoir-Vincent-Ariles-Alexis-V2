package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Acteurs.Acteur;
import com.example.dernierespoirsae.modele.Environnement;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class VueJoueur extends VueActeur{


    public VueJoueur(Pane persoPane,Pane barreViePane, TilePane terrainPane, Acteur acteur, Environnement environnement) {
        super(persoPane,barreViePane, terrainPane, acteur, environnement);
    }

    @Override
    public String imageACreer() {
        return getClass().getResource("/com/example/dernierespoirsae/images/joueur0.png").toExternalForm();
    }

    @Override
    public int[] placementImage() {
        return new int[]{6, 12};
    }


    @Override
    public void definitionbarreDeVie() {
        getBarreVie().getStyleClass().add("health-bar-joueur");
        getBarreViePane().getChildren().add(getBarreVie());
        getBarreVie().translateXProperty().setValue(0);
        getBarreVie().translateYProperty().setValue(0);
    }
}
