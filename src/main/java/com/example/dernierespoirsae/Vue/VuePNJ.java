package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Acteurs.Acteur;
import com.example.dernierespoirsae.modele.Acteurs.PNJ;
import com.example.dernierespoirsae.modele.Environnement;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

public class VuePNJ extends VueActeur{

    private Label messagePNJ;
    private Acteur acteur;

    public VuePNJ(Pane persoPane, Pane barreViePane, TilePane terrainPane, Acteur acteur, Environnement environnement) {
        super(persoPane, barreViePane, terrainPane, acteur, environnement);
        messagePNJ = new Label();
        creerLabelMessage();
        ChangeListener<Boolean> interaction = ((obs, old, nouv)-> enInteraction());
        ((PNJ)acteur).enInteraction().addListener(interaction);
    }
    @Override
    public String imageACreer() {
        return getClass().getResource("/com/example/dernierespoirsae/images/"+getActeur().getNom()+".png").toExternalForm();
    }

    @Override
    public int[] placementImage() {
        return new int[]{0, 0};
    }

    @Override
    public void definitionbarreDeVie() {
        //Pas de barre de vie
    }

    public void creerLabelMessage(){

        messagePNJ.getStyleClass().add("messagePNJ");
        super.getPersoPane().getChildren().add(messagePNJ);

        messagePNJ.setVisible(false);
    }

    public void enInteraction(){
        if(((PNJ)getActeur()).getEnInteraction()) {
            messagePNJ.setTranslateX(getActeur().getX() - 150);
            messagePNJ.setTranslateY(getActeur().getY() - 30);
            messagePNJ.setText(((PNJ) getActeur()).getTexte());
            messagePNJ.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), event -> {
                messagePNJ.setVisible(false);
                ((PNJ) getActeur()).setEnInteraction(false);
            }));
            timeline.setCycleCount(1); // Exécuter une seule fois
            timeline.play();
        }

    }
}