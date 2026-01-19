package com.upb.agripos;

import com.upb.agripos.view.ProductTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("AgriPOS - Week 13");
        stage.setScene(new Scene(new ProductTableView(), 900, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

