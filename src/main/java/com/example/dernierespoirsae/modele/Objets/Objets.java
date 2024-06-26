package com.example.dernierespoirsae.modele.Objets;

import com.example.dernierespoirsae.modele.Environnement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Objets {
    private int id;
    private Environnement environnement;
    private IntegerProperty xProperty, yProperty;
    private String type;
    private static int idStatic=0;
    private IntegerProperty quantiteObjets;
    private boolean objetUnique;
    public Objets(Environnement environnement,int x, int y, String type, int quantiteObjets, boolean objetUnique){
        this.environnement = environnement;
        this.xProperty = new SimpleIntegerProperty(x);
        this.yProperty = new SimpleIntegerProperty(y);
        this.type = type;
        this.id = idStatic++;
        this.quantiteObjets = new SimpleIntegerProperty();
        this.objetUnique = objetUnique;
        setQuantiteObjets(quantiteObjets);
    }
    public Environnement getEnvironnement() {
        return environnement;
    }
    public int getX() {
        return xProperty.get();
    }

    public IntegerProperty xProperty() {
        return xProperty;
    }

    public int getY() {
        return yProperty.get();
    }

    public IntegerProperty yProperty() {
        return yProperty;
    }

    public void setX(int x) {
        this.xProperty.set(x);
    }

    public void setY(int y) {
        this.yProperty.set(y);
    }
    public abstract void incremeterDecremeterQuantiteInventaire(int val);

    public abstract IntegerProperty quantiteProperty();

    public String getType() {
        return type;
    }
    public int getId() {
        return id;
    }
    public abstract void agirAvecJoueur();
    public abstract void agir();

    public int getQuantiteObjets() {
        return quantiteObjets.get();
    }

    public IntegerProperty quantiteObjetsProperty() {
        return quantiteObjets;
    }

    public void setQuantiteObjets(int quantiteObjets) {
        this.quantiteObjets.set(quantiteObjets);
    }

    public boolean ObjetUnique() {
        return objetUnique;
    }
}
