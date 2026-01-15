package com.upb.agripos;

import com.upb.agripos.view.ProductFormView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Agri-POS - GUI Dasar JavaFX");
        stage.setScene(new Scene(new ProductFormView(), 600, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

