package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Objets.Objets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class VueObjet {
    private Objets objets;

    public VueObjet(Objets objets) {
        this.objets = objets;
    }

    public void creeViewObjet() {

        //Recuperation de l'image de l'arme a afficher
        Image imageObjet = new Image(getClass().getResource("/com/example/dernierespoirsae/images/"+objets.getType()+".png").toExternalForm());

        //Ajout de cette image a imageView pour pouvoir l'afficher
        ImageView imageView = new ImageView(imageObjet);

        //Definition des dimentions
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        //Associe l'ID de l'arme réprensetée à l'image
        imageView.setId(String.valueOf(this.objets.getId()));

        //Associe la position de l'arme a l'image
        setPositionArme(imageView);

        //Ajoute l'image a la Pane armePane qui sera affiché dans la fenêtre
        ajoutImagePane(imageView);
    }

    public abstract void ajoutImagePane(ImageView imageView);
    public abstract void setPositionArme(ImageView imageView);

    public Objets getObjets() {
        return objets;
    }
}

