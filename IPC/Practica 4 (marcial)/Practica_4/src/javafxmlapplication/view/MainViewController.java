/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class MainViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void salir(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setDialogPane(null);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("¿Terminar la aplicacion?");
        alerta.showAndWait();
        Optional<ButtonType> result = alerta.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK){
            Platform.exit();
        }
    }

    @FXML
    private void amazon(ActionEvent event) {
    }

    @FXML
    private void blogger(ActionEvent event) {
    }

    @FXML
    private void ebay(ActionEvent event) {
    }

    @FXML
    private void facebook(ActionEvent event) {
    }

    @FXML
    private void google(ActionEvent event) {
    }
    
}
