package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Environnement;
import com.example.dernierespoirsae.modele.Objets.Projectile.Projectile;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class VueBave extends VueProjectile{

    public VueBave(Pane projectilePane, Projectile projectile,Pane persoPane, Environnement environnement, VueTerrain vueTerrain, TilePane terrainPane) {
        super(projectilePane, projectile,persoPane, environnement,vueTerrain,terrainPane);
    }

    @Override
    public String nomFichierVue() {
        return getClass().getResource("/com/example/dernierespoirsae/images/bave_projectile.png").toExternalForm();
    }
}
