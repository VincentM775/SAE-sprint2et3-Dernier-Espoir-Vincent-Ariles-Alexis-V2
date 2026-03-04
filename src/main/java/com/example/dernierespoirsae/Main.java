package com.example.dernierespoirsae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static int longeur = 1100;
    public static int largeur = 600;
    @Override
    public void start(Stage stage) throws IOException{
        Image tileset = new Image(Main.class.getResource("/com/example/dernierespoirsae/tiles.png").toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/dernierespoirsae/vueDernierEspoir.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),longeur,largeur);
        scene.getStylesheets().add(getClass().getResource("dernierEspoir.css").toExternalForm());
        stage.setTitle("Dernier Espoir");
        stage.setScene((scene));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}

