package com.example.dernierespoirsae.modele.Objets.Armes;

import com.example.dernierespoirsae.modele.Environnement;
import com.example.dernierespoirsae.modele.Objets.Objets;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Arme extends Objets {
    private int degats;

    public Arme(int degats,int x,int y, String type, Environnement environnement) {
        super(environnement,x,y,type);
        this.degats = degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }


    public int getDegats() {
        return degats;
    }
    public abstract void attaquer();
}