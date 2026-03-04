package com.example.dernierespoirsae.controleur;

import com.example.dernierespoirsae.Main;
import com.example.dernierespoirsae.Observateur.*;
import com.example.dernierespoirsae.algo.BFS;
import com.example.dernierespoirsae.Vue.*;
import com.example.dernierespoirsae.modele.*;
import com.example.dernierespoirsae.modele.Acteurs.*;
import com.example.dernierespoirsae.modele.Objets.Armes.*;
import com.example.dernierespoirsae.modele.Objets.AutreObjets.BoiteDeMunition;
import com.example.dernierespoirsae.modele.Objets.AutreObjets.PiedDeBiche;
import com.example.dernierespoirsae.modele.Objets.Objets;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.net.URL;

public class Controleur implements Initializable {

    @FXML
    private TilePane terrainPane;
    @FXML
    private Pane persoPane, barreViePane, principalPane;
    @FXML
    private BorderPane fenetre;
    @FXML
    private Pane armePaneEquipee;
    @FXML
    private VBox inventaireVBox;
    @FXML
    private Pane objetPaneMap;
    @FXML
    private Pane projectilePane;
    private Environnement environnement;
    private Timeline gameLoop;
    private int temps;
    private BFS bfs;
    private Button boutonRelance;
    private ImageView background;
    private Label labelPerdu;

    public void initialize(URL location, ResourceBundle ressource) {

        boutonRelance = new Button();
        boutonRelance.setOnAction(actionEvent -> {
            relance();
            stopfreeze();
        });
        boutonRelance.setTranslateX(505);
        boutonRelance.setTranslateY(275);
        boutonRelance.getStyleClass().add("boutonLancement");
        boutonRelance.setText("Jouer !");
        boutonRelance.setDisable(false);
        boutonRelance.setVisible(true);
        principalPane.getChildren().add(boutonRelance);
        background = new ImageView(new Image(getClass().getResource("/com/example/dernierespoirsae/images/background.jpg").toExternalForm(),1100, 1000, false, false));
        principalPane.getChildren().add(background);
        background.setTranslateY(-100);
        labelPerdu = new Label("Perdu !");
        labelPerdu.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: red;");
        labelPerdu.setVisible(false);
        principalPane.getChildren().add(labelPerdu);
        boutonRelance.toFront();
        inventaireVBox.setVisible(false);
    }

    public void lancement(){

        LoadJSON loadJSON = new LoadJSON("/com/example/dernierespoirsae/terrain0.json");

        inventaireVBox.setVisible(true);
        barreViePane.setVisible(true);

        //creation de l'environement
        this.environnement = new Environnement(32, 100, 100);

        //Definition de la taille des tuiles du FXML dépendamment de celles indiquées lors de la création de l'environnement
        this.terrainPane.setPrefTileWidth(this.environnement.getInfoTuile()[0]);
        this.terrainPane.setPrefTileHeight(this.environnement.getInfoTuile()[0]);

        //Definition du nombre de colonnes et nombres de ligne du Terrain, dépendamment de celles indiquées lors de la création de l'environnement
        this.terrainPane.setPrefWidth(this.environnement.getInfoTuile()[1] * this.environnement.getInfoTuile()[0]);
        this.terrainPane.setPrefHeight(this.environnement.getInfoTuile()[2] * this.environnement.getInfoTuile()[0]);

        //Creation du joueur
        Joueur joueur = new Joueur(environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());

        ObservateurInventaire observateurInventaire =new ObservateurInventaire(inventaireVBox, joueur.getInventaire(), joueur);
        joueur.getInventaire().getListeObjetsInventaire().addListener(observateurInventaire);

        //Initialisation de la vue Terrain
        VueTerrain vueTerrain =  new VueTerrain(environnement.getTerrain(), this.terrainPane,loadJSON.getMapColision(), loadJSON.getMapFond(),loadJSON.getMapAutres(),environnement);

        /* ObservateurActeurs est une methode qui va observer les changement (ajout ou supression)
        *  dans la liste d'acteur de l'environement (qui est une liste Observable) */
        ObservateurActeurs observateurActeurs = new ObservateurActeurs(persoPane,barreViePane,terrainPane,environnement,vueTerrain);

        joueur.getArmeEquipeeProperty().addListener((observable, oldValue, newValue) -> {
            new VueObjetEquipee(joueur.getArmeEquipeeProperty().getValue(),joueur, this.armePaneEquipee);
        });

        //Lie l'observateur d'acteur a l'environnement
        environnement.setListenerActeurs(observateurActeurs);

        //Ajout du joueur a l'environnement
        environnement.setJoueur(joueur);

        //Initialise un observateur pour une liste d'arme
        ObservateurObjets observateurObjets = new ObservateurObjets(objetPaneMap,environnement,vueTerrain,terrainPane);

        //Lie cet observateur a la liste d'arme dans l'environnement
        environnement.setListenerArmeEnvironnement(observateurObjets);

        //Creer des haches
        Objets hache = new Hache(1344,1088,environnement);

        //Creer un pistolet
        Objets pistolet = new Pistolet(3072,288,environnement);

        //Crer une boite de munition
        Objets boiteMunition = new BoiteDeMunition(environnement, 96,3008);
        Objets boiteMunitio2 = new BoiteDeMunition(environnement, 1088,2536);
        Objets boiteMunitio3 = new BoiteDeMunition(environnement, 3072,352);
        Objets cocktailMolotov = new CocktailMolotov(environnement, 1088,2600);
        Objets cocktailMolotov2 = new CocktailMolotov(environnement, 1952,2600);
        PiedDeBiche piedDeBiche = new PiedDeBiche(environnement,128,3008);

        //Ajoute les Objets à l'environnement
        environnement.getlistObjetsEnvironnement().add(hache);
        environnement.getlistObjetsEnvironnement().add(pistolet);
        environnement.getlistObjetsEnvironnement().add(boiteMunition);
        environnement.getlistObjetsEnvironnement().add(boiteMunitio2);
        environnement.getlistObjetsEnvironnement().add(boiteMunitio3);
        environnement.getlistObjetsEnvironnement().add(cocktailMolotov);
        environnement.getlistObjetsEnvironnement().add(cocktailMolotov2);
        environnement.getlistObjetsEnvironnement().add(piedDeBiche);

        //Génére un terrain avec des tuiles aléatoires
        environnement.getTerrain().generTerrain(environnement.getInfoTuile()[1] * environnement.getInfoTuile()[2]);

        //Création d'un premier zombie MasticartorZ
        Ennemi acteur1 = new MasticatorZ(1600,1088, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur1);
        Ennemi acteur12 = new MasticatorZ(1600,1024, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur12);
        Ennemi acteur13 = new MasticatorZ(1504,896, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur13);

        //Création d'un 2e zombie LeZamikaze
        Ennemi acteur2 = new Zamikaze(1364,2600, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur2);
        Ennemi acteur22 = new Zamikaze(1300,2350, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur22);
        Ennemi acteur23 = new Zamikaze(1450,2500, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur23);

        //Création d'un 3e zombie le Bave-Zmort
        Ennemi acteur3 = new BaveZmort(1364,2350, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur3);
        Ennemi acteur32 = new BaveZmort(1172,400, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur32);
        Ennemi acteur33 = new BaveZmort(1172,464, environnement,(int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(acteur33);

        PNJ roger = new RogerPNJ(1088,1088, environnement, (int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(roger);

        PNJ barnard = new BarnardPNJ(1300,2600, environnement, (int) this.terrainPane.getPrefTileWidth(), (int) this.terrainPane.getPrefTileHeight(), this.terrainPane.getPrefColumns());
        environnement.addActeurs(barnard);

        //Créer le lien entre la liste Des Projectiles et la class observableProjectile
        environnement.getListProjectile().addListener(new ObservateurProjectile(this.projectilePane,this.persoPane,environnement,vueTerrain,terrainPane));

        //Créer le lien entre la liste Des flaques de baves et la class observableBave
        environnement.getListBave().addListener(new ObservateurTrainerBave(this.environnement,this.terrainPane,vueTerrain));

        ObservateurPositionX obsX = new ObservateurPositionX(principalPane, joueur);
        ObservateurPositionY obsY = new ObservateurPositionY(principalPane, joueur);

        joueur.xProperty().addListener(obsX);

        joueur.yProperty().addListener(obsY);

        //Initialialisation du keyHandler pour la gestions des entrées clavier utilisateur
        KeyHandler keyHandler = new KeyHandler(environnement);
        persoPane.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
        persoPane.addEventHandler(KeyEvent.KEY_RELEASED, keyHandler);

        ClickHandler clickHandler= new ClickHandler(environnement,obsX,obsY);
        fenetre.setOnMouseClicked(clickHandler);

        //Initialisation du BFS
        this.bfs = new BFS(this.environnement,19,33);

        //Ajout du BFS dans l'environnement
        this.environnement.setBfs(this.bfs);

        initAnimation();
    }

    private void initAnimation() {
        this.gameLoop = new Timeline();
        this.temps=0;
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
            // on définit le FPS (nbre de frame par seconde)
            Duration.seconds((0.040)),
            // on définit ce qui se passe à chaque frame
            // c'est un eventHandler d'ou le lambda
            (ev ->{
                if(environnement.getJoueur().getVie() <= 0){
                    int joueurX = environnement.getJoueur().getX();
                    int joueurY = environnement.getJoueur().getY();
                    clear();
                    boutonRelance.setTranslateX(joueurX - 45);
                    boutonRelance.setTranslateY(joueurY - 25);
                    background.setTranslateX(joueurX - (double) Main.longeur / 2);
                    background.setTranslateY(joueurY - (double) Main.largeur / 2 - 100);
                    boutonRelance.setDisable(false);
                    boutonRelance.setVisible(true);
                    inventaireVBox.setVisible(false);
                    barreViePane.setVisible(false);
                    background.setVisible(true);
                    labelPerdu.setTranslateX(joueurX - 70);
                    labelPerdu.setTranslateY(joueurY - 80);
                    labelPerdu.setVisible(true);
                    labelPerdu.toFront();
                    boutonRelance.toFront();
                    gameLoop.stop();
                }
                else {
                    this.environnement.setTemps(this.environnement.getTemps() + 1);
                    this.environnement.unTour();
                    this.temps++;
                }

//                    if(temps==10){
//                        System.out.println("boucle fini");
//                        gameLoop.stop();
//                    }
            })
        );
        this.gameLoop.getKeyFrames().add(kf);
    }


//    public void mouseClicked(MouseEvent mouseEvent) {
//        persoPane.requestFocus();
//    }

    public void relance(){
        clear();
        lancement();
    }

    public void clear(){
        persoPane.getChildren().clear();
        terrainPane.getChildren().clear();
        inventaireVBox.getChildren().clear();
        this.environnement = null;
        boutonRelance.setVisible(false);
        background.setVisible(false);
        labelPerdu.setVisible(false);
    }

    public void stopfreeze(){
        persoPane.requestFocus();
        gameLoop.play();
    }

}