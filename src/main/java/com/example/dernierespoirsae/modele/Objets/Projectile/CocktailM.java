package com.example.dernierespoirsae.modele.Objets.Projectile;

import com.example.dernierespoirsae.modele.Acteurs.Acteur;
import com.example.dernierespoirsae.modele.Environnement;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CocktailM extends Projectile{
    private BooleanProperty aExplosee;
    public CocktailM(int degats, Environnement environnement, Acteur acteurQuiALancer) {
        super(degats, environnement, acteurQuiALancer, 12, 320);
        this.aExplosee = new SimpleBooleanProperty(false);
    }

    @Override
    public int jeVaisEnX() {
        return getEnvironnement().getJoueur().getxDeLaSouris();
    }

    @Override
    public int jeVaisEnY() {
        return getEnvironnement().getJoueur().getyDeLaSouris();
    }

    @Override
    public void effet() {
        int tuileAcolonne = getX() / getEnvironnement().getInfoTuile()[0];
        int tuileAligne = getY() / getEnvironnement().getInfoTuile()[0];

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (tuileAligne + y >= 0 && tuileAligne + y < getEnvironnement().getInfoTuile()[1] && tuileAcolonne + x >= 0 && tuileAcolonne + x < getEnvironnement().getInfoTuile()[1]) {
                    getEnvironnement().getTerrain().getTerrain().remove(caseAExploser(y, x)); //case à remplacer selon x et y
                    getEnvironnement().getTerrain().getTerrain().add(caseAExploser(y, x), 2); //case à remplacer selon x et y
                    setaExplosee(true);
                }
            }
        }
        //TODO Il faut regarder les 8 cases autour de lui + là où il est et compter celle qui sont le sol et des arbres.
        // Il faut remplacer toute ces cases par de la brulure
        // Mettre un effet de feu côté vue
        // choisir de facon aléatoire entre 1case et le max de cases compter au début et laisser des flammes
        // à ces cases pendant 4000 ms au même endroit.
        // Faire des dégâts quand un acteur va dans les flammes
        // Incrémenter / desincrémenter le nombre de cocktailMolotov dans Joueur
        // et même choses côté vue
    }
    public int caseAExploser(int caseY,int caseX){
        return (((getY()/getEnvironnement().getInfoTuile()[0])+caseY)*getEnvironnement().getInfoTuile()[1]+(getX()/getEnvironnement().getInfoTuile()[0])+caseX);
    }

    public void setaExplosee(boolean aExplosee) {
        this.aExplosee.set(aExplosee);
    }

    public BooleanProperty aExploseeProperty() {
        return aExplosee;
    }

    public boolean getaExplosee() {
        return aExplosee.getValue();
    }

}