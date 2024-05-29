package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Acteur;
import com.example.dernierespoirsae.modele.Environnement;
import com.example.dernierespoirsae.modele.Joueur;
import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ObservateurActeurs implements ListChangeListener<Acteur> {

    private Pane persoPane;
    public ObservateurActeurs(Pane pane) {
        this.persoPane = pane;
    }

    @Override
    public void onChanged(Change<? extends Acteur> acteurs) {

        while (acteurs.next()){
            //Parcours la liste des acteurs ajoutés a la liste et le créer a l'affichage
            for(int i = 0; i < acteurs.getAddedSize(); i++){
                new VueActeur(persoPane, acteurs.getAddedSubList().get(i));
            }

            //Parcours la liste des acteurs supprimés a la liste et le supprime a l'affichage
            for(int i = 0; i < acteurs.getRemovedSize(); i++){
                suprimerSprite(acteurs.getRemoved().get(i));
            }
        }
    }

    public void suprimerSprite(Acteur acteur){
        this.persoPane.getChildren().remove(this.persoPane.lookup("#"+acteur.getId()));
    }
}
