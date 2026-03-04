package com.example.dernierespoirsae.Vue;

import com.example.dernierespoirsae.modele.Acteurs.Joueur;
import com.example.dernierespoirsae.modele.Inventaire;
import com.example.dernierespoirsae.modele.Objets.Armes.CocktailMolotov;
import com.example.dernierespoirsae.modele.Objets.AutreObjets.BoiteDeMunition;
import com.example.dernierespoirsae.modele.Objets.Objets;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VueInventaireObjets {

    private VBox inventaireVBox;
    private Inventaire inventaire;
    private Joueur joueur;
    public VueInventaireObjets(VBox inventaireVBox, Objets objets, Inventaire inventaire, Joueur joueur) {
        this.inventaireVBox = inventaireVBox;
        this.inventaire = inventaire;
        this.joueur = joueur;
        addViewAutreObjetsInventaire(objets);
    }

    /**
     * @param objets -> L'objet associée a l'image à la Pane mère
     *             <p>
     * Cette methode créer un Pane qui contient:    - L'affichage de l'objet
     *                                              - Un label indiquant sa quantitée
     * uniquement s'il n'en existe pas déjà, dans ce cas, on réaffiche pas l'objet mais on met à jour la quantité
     */
    public void addViewAutreObjetsInventaire(Objets objets) {


        //On creer un nouvel emplacement dans l'inventaire
       if (objets.quantiteProperty().getValue() == 1) {

           Label label = new Label();
           String idLabel = "labelNb" + objets.getType();
           Pane emplacement = new Pane();
           emplacement.setOnMouseClicked(event -> setClick(emplacement));

            //Dans le cas ou l'objet peut être multiple dans l'inventaire
            if( ! objets.ObjetUnique() ){

                label.setId(idLabel);
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                label.setTranslateY(58);
                label.setTranslateX(75);
                if(objets instanceof CocktailMolotov){
                    label.textProperty().bind(Bindings.convert(joueur.quantiteCocktailMolotovProperty()));

                } else label.textProperty().bind(Bindings.convert(joueur.quantiteMunitionsProperty()));
            }

            emplacement.getChildren().add(label);

            //Charger l'image de l'objet
            Image imageObjet = new Image(getClass().getResource("/com/example/dernierespoirsae/images/" + objets.getType() + ".png").toExternalForm());

            //Stock l'image de l'objet dans une imageView pour pouvoir les afficher
            ImageView imageView = new ImageView(imageObjet);

            //Gère le style de l'emplacement de chaque objet
            emplacement.setStyle("-fx-border-width: 1; -fx-border-color: black; -fx-background-color: #77B5FE; -fx-border-radius: 10; -fx-background-radius: 10" );

            //Ajoute ce Pane a la vue Inventaire
            this.inventaireVBox.getChildren().add(emplacement);

            //Défini la taille de l'image dans le Pane
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);

            //Attribue a ce Pane le type de l'objet qu'elle contient a l'ID
            emplacement.setId(objets.getType());

            //Ajoute l'image et le label a la Pane
            emplacement.getChildren().add(imageView);
        }
    }

    public void setClick(Pane emplacement){
        if(this.inventaire.getEnvironnement().getJoueur().getClickSouris().equals("d"))
            this.inventaire.getEnvironnement().getJoueur().setArmeEquipee(emplacement.getId());
    }
}
