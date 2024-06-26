package com.example.dernierespoirsae.modele;

import com.example.dernierespoirsae.Observateur.ObservateurActeurs;
import com.example.dernierespoirsae.Observateur.ObservateurObjets;
import com.example.dernierespoirsae.modele.Acteurs.*;
import com.example.dernierespoirsae.modele.Acteurs.Acteur;
import com.example.dernierespoirsae.modele.Objets.Armes.Bave;
import com.example.dernierespoirsae.modele.Objets.Projectile.Projectile;
import com.example.dernierespoirsae.modele.Objets.Objets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.dernierespoirsae.algo.BFS;
import java.util.ArrayList;

public class Environnement{

    private Acteur joueur;
    private Terrain terrain;
    private BFS bfs;
    private int[] infoTuile;
    private ObservableList<Acteur> acteurs;
    private ObservableList<Bave> listBave;
    private ObservableList<Objets> listObjetsEnvironnement;
    private ObservableList<Projectile> listProjectile;
    private int temps;

    public Environnement(int tailleTuile,int nombreDeTuileLongueur,int nombreDeTuileLargeur){
        this.infoTuile = new int[3];
        this.infoTuile[0] = tailleTuile;
        this.infoTuile[1] = nombreDeTuileLongueur; //nombre de colonnes
        this.infoTuile[2] = nombreDeTuileLargeur; //nombre de lignes
        this.terrain = new Terrain();
        this.listObjetsEnvironnement = FXCollections.observableArrayList();
        this.acteurs = FXCollections.observableArrayList();
        this.joueur = null;
        this.listProjectile = FXCollections.observableArrayList();
        this.listBave = FXCollections.observableArrayList();
        temps=0;
    }

    public void addActeurs(Acteur acteur) {
        this.acteurs.add(acteur);
    }

    public ObservableList<Objets> getlistObjetsEnvironnement() {
        return this.listObjetsEnvironnement;
    }
    public ObservableList<Acteur> getListActeurs(){
        return this.acteurs;
    }

    public Joueur getJoueur() {
        return (Joueur) this.joueur;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public void setJoueur(Acteur joueur) {
        this.joueur = joueur;
        addActeurs(this.joueur);
    }

    public void setListenerActeurs(ObservateurActeurs acteursObserve){
        acteurs.addListener(acteursObserve);
    }

    public void setListenerArmeEnvironnement(ObservateurObjets objetsObserve){
        listObjetsEnvironnement.addListener(objetsObserve);
    }

    public int[] getInfoTuile() {
        return this.infoTuile;
    }

    public void setBfs(BFS bfs) {
        this.bfs = bfs;
    }

    public BFS getBfs() {
        return bfs;
    }

    public ObservableList<Projectile> getListProjectile() {
        return listProjectile;
    }

    public void addProjectile(Projectile projectile){
        this.listProjectile.add(projectile);
    }

    public ObservableList<Bave> getListBave() {
        return listBave;
    }

    public void addBave(Bave bave){
        this.listBave.add(bave);
    }
    public void agit() {
        for (int i = 0; i < getListActeurs().size(); i++) {
            getListActeurs().get(i).agit(); //On fait agir les acteurs

            if (getListActeurs().get(i) != getJoueur()) {
                getListActeurs().get(i).meurtOuVie(); //Supprime les acteurs qui sont morts
            }
        }
        //Faire agir les projectiles
        for(int i=0;i< getListProjectile().size();i++) {
            getListProjectile().get(i).agit();
        }
    }
    public void unTour(){
        agit();
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

}