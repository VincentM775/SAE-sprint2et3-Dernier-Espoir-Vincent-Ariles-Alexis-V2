package com.example.dernierespoirsae.modele;

import com.example.dernierespoirsae.modele.Acteurs.Acteur;

public class Collision {

    private int longueur;
    private int hauteur;
    private Acteur acteur;
    private int tailleTuile;
    private int longMap;
    private int largeMap;

    public Collision(int longueur, int hauteur, Acteur acteur){
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.acteur = acteur;
        this.tailleTuile = this.acteur.getEnvironnement().getInfoTuile()[0];
        this.longMap = this.acteur.getEnvironnement().getInfoTuile()[1];
        this.largeMap = this.acteur.getEnvironnement().getInfoTuile()[2];
    }
    public int getHauteur() {
        return hauteur;
    }

    public int getLongueur() {
        return longueur;
    }

    /**
     * Quand l'Acteur veut aller à droite, il vérifie que tous ses pixels de droite ne rentrent pas en collision avec un obstacle
     */
    public boolean collisionDroite(int vitesse){

        int positionPixelX = this.acteur.getX() + vitesse + this.longueur;
        int positionPixelY;
        int tuileSousPosition;

        for(int i = 0; i < hauteur; i++){
            positionPixelY = this.acteur.getY() + i;
            tuileSousPosition = (positionPixelX / this.tailleTuile) + ((positionPixelY) / this.tailleTuile * this.longMap);
            if(collisionMap("right", positionPixelX, positionPixelY) || acteur.getEnvironnement().getTerrain().estObstacle(tuileSousPosition) || collisionEntreActeurs(positionPixelX, positionPixelY)){
                return false;
            }
        }
        return true;
    }

    /**
     * Quand l'Acteur veut aller à gauche, il vérifie que tous ses pixels de gauche ne rentrent pas en collision avec un obstacle
     */
    public boolean collisionGauche(int vitesse) {
        int positionPixelX = this.acteur.getX() - vitesse;
        int positionPixelY;
        int tuileSousPosition;

        for(int i = 0; i < hauteur; i++){
            positionPixelY = this.acteur.getY() + i;
            tuileSousPosition = (positionPixelX / this.tailleTuile) + (positionPixelY / this.tailleTuile * this.longMap);
            if(collisionMap("left", positionPixelX, positionPixelY) || acteur.getEnvironnement().getTerrain().estObstacle(tuileSousPosition) || collisionEntreActeurs(positionPixelX, positionPixelY)){
                return false;
            }
        }
        return true;
    }

    /**
     * Quand l'Acteur veut aller en haut, il vérifie que tous ses pixels du haut ne rentrent pas en collision avec un obstacle
     */
    public boolean collisionHaut(int vitesse) {
        int positionPixelX;
        int positionPixelY = this.acteur.getY() - vitesse;
        int tuileSousPosition;

        for(int i = 1; i <= longueur; i++){
            positionPixelX = this.acteur.getX() + i;
            tuileSousPosition = (positionPixelX / this.tailleTuile) + (positionPixelY / this.tailleTuile * this.longMap);
            if(collisionMap("up", positionPixelX, positionPixelY) || acteur.getEnvironnement().getTerrain().estObstacle(tuileSousPosition) || collisionEntreActeurs(positionPixelX, positionPixelY)){
                return false;
            }
        }
        return true;
    }

    /**
     * Quand l'Acteur veut aller en bas, il vérifie que tous ses pixels du bas ne rentrent pas en collision avec un obstacle
     */
    public boolean collisionBas(int vitesse) {
        int positionPixelX;
        int positionPixelY = this.acteur.getY() + this.hauteur + vitesse;
        int tuileSousPosition;

        for(int i = 1; i <= longueur; i++){
            positionPixelX = this.acteur.getX() + i;
            tuileSousPosition = (positionPixelX / this.tailleTuile) + (positionPixelY / this.tailleTuile * this.longMap);
            if(collisionMap("down", positionPixelX, positionPixelY) || acteur.getEnvironnement().getTerrain().estObstacle(tuileSousPosition) || collisionEntreActeurs(positionPixelX, positionPixelY)){
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie la collision entre l'acteur et les autres acteurs
     */
    public boolean collisionEntreActeurs(int xActeur, int yActeur) {
        for (Acteur autreActeur : this.acteur.getEnvironnement().getListActeurs()) {
            if (autreActeur != this.acteur) {
                if (xActeur >= autreActeur.getX() && xActeur <= autreActeur.getX() + autreActeur.getHitBox().getLongueur() &&
                        yActeur >= autreActeur.getY() && yActeur <= autreActeur.getY() + autreActeur.getHitBox().getHauteur()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collisionMap(String direction, int x, int y){
        int limiteMapLong = this.acteur.getLongTuile() * this.longMap;
        int limiteMapLarge = this.acteur.getLargeTuile() * this.largeMap;


        if(direction.equals("right")){
            if(x >= limiteMapLong)
                return true;
        }

        if(direction.equals("left")){
            if(x <= 0)
                return true;
        }

        if(direction.equals("up")){
            if(y <= 0)
                return true;
        }

        if(direction.equals("down")){
            if(y >= limiteMapLarge)
                return true;
        }
        return false;
    }
}