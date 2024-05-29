package com.example.dernierespoirsae.Vue;
import com.example.dernierespoirsae.modele.Acteur;
import com.example.dernierespoirsae.modele.Armes.Armes;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ObservateurArmes implements ListChangeListener<Armes> {

    @FXML
    public Pane armePaneMap;
    @FXML
    private Pane paneHache;
    private Acteur joueur;

    public ObservateurArmes(Pane paneHache, Acteur joueur, Pane armePaneMap) {
        this.armePaneMap = armePaneMap;
        this.joueur = joueur;
        this.paneHache = paneHache;
    }

    public void onChanged(ListChangeListener.Change<? extends Armes> Arme) {

        while (Arme.next()){
            for(int i = 0; i < Arme.getAddedSize(); i++){
                new VueArmes(paneHache, Arme.getAddedSubList().get(i), this.joueur, this.joueur.getInventaire(), armePaneMap);
            }
            for(int i = 0; i < Arme.getRemovedSize(); i++){
                new VueArmes(Arme.getRemoved().get(i), this.joueur, paneHache, armePaneMap);
            }
        }
    }
}