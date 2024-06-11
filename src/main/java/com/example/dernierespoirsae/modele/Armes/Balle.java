package com.example.dernierespoirsae.modele.Armes;

import com.example.dernierespoirsae.modele.Acteurs.Acteur;
import com.example.dernierespoirsae.modele.Environnement;

public class Balle extends Projectile {

    public Balle(int degats, Environnement environnement, Acteur acteurQuiALancer) {
        super(degats, environnement,acteurQuiALancer);
    }

    @Override
    public void agit() {
        if (getEstVivant()) {
            if (!testProjectileArriverSurJoueur()) {
                avance();
            }
            else
                setEstVivant(false);
        }
        else{
            getEnvironnement().getListProjectile().remove(this);
        }
    }

    @Override
    public int jeVaisEnX() {
        return getEnvironnement().getJoueur().getxDeLaSouris();
    }

    @Override
    public int jeVaisEnY() {
        return getEnvironnement().getJoueur().getyDeLaSouris();

    }

}
