/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class Mapa implements Initializable {

    @FXML
    private Slider zoom_slider;
    @FXML
    private ScrollPane map_scrollpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void zoomOut(ActionEvent event) {
    }

    @FXML
    private void zoomIn(ActionEvent event) {
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
    }

    @FXML
    private void punteroAction(ActionEvent event) {
    }

    @FXML
    private void lineaAction(ActionEvent event) {
    }

    @FXML
    private void arcoAction(ActionEvent event) {
    }

    @FXML
    private void escribirAction(ActionEvent event) {
    }

    @FXML
    private void colorAction(ActionEvent event) {
    }

    @FXML
    private void reglaAction(ActionEvent event) {
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
    }

    @FXML
    private void limpiarAction(ActionEvent event) {
    }

    @FXML
    private void logInAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicial.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();
            stage.setHeight(prevHeight);
            stage.setWidth(prevWidth);
            stage.setTitle("LogIn");
            stage.setScene(scene);
    }
    
}
