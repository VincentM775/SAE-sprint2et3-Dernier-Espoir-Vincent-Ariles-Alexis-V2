package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Environnement;
import com.example.dernierespoirsae.modele.Objets.Projectile.CocktailM;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class VueCocktailMolotov extends VueProjectile {

    public VueCocktailMolotov(Pane projectilePane, CocktailM projectile, Pane persoPane, Environnement environnement, VueTerrain vueTerrain,TilePane terrainPane) {
        super(projectilePane, projectile,persoPane, environnement,vueTerrain,terrainPane);
        ChangeListener<Boolean> explosion = ((obs,old,nouv)-> explosion());
        projectile.aExploseeProperty().addListener(explosion);
    }

    @Override
    public String nomFichierVue() {
        return getClass().getResource("/com/example/dernierespoirsae/images/cocktailMolotov.png").toExternalForm();
    }
    public void explosion(){
        int tuileAcolonne = getProjectile().getX() / getEnvironnement().getInfoTuile()[0];
        int tuileAligne = getProjectile().getY() / getEnvironnement().getInfoTuile()[0];
        int tuilePositionEListe; // récupère la position de l'ennemi dans la liste

        ModifVue.addGifToPane(getProjectile().getX(), getProjectile().getY(), 96, getClass().getResource("/com/example/dernierespoirsae/images/explosionFeu.gif").toExternalForm(), 480,getPersoPane());

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {

                if (tuileAligne + y >= 0 && tuileAligne + y < getEnvironnement().getInfoTuile()[1] &&
                        tuileAcolonne + x >= 0 && tuileAcolonne + x < getEnvironnement().getInfoTuile()[1]) {

                    tuilePositionEListe = getEnvironnement().getInfoTuile()[1] * (tuileAligne + y) + (tuileAcolonne + x);
                    if (getEnvironnement().getTerrain().getTerrain().get(tuilePositionEListe)==2) {

                        // Mettre à jour le modèle
                        getVueTerrain().getTerrainAutres().set(tuilePositionEListe, 65 + 1); // 121 représente une tuile brûlée dans le modèle

                        // Utiliser setImageAtIndex pour mettre à jour l'image de la tuile
                        ModifVue.setImageAtIndex(tuilePositionEListe, getVueTerrain().getTiles()[65], getEnvironnement(), getTerrainPane());
                    }
                }
            }
        }
    }
}
