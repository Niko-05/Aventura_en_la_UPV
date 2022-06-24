/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class RegistrarseController implements Initializable {

    @FXML
    private Label usuarioLab;
    @FXML
    private Label errNomLab;
    @FXML
    private Label correoLab;
    @FXML
    private Label errCorreoLab;
    @FXML
    private Label contraseñaLab;
    @FXML
    private Label errContraseñaLab;
    @FXML
    private Label nacimientoLab;
    @FXML
    private ImageView avatarField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonBack(ActionEvent event) {
    }

    @FXML
    private void avatarDefaultAction(ActionEvent event) {
    }

    @FXML
    private void avatar1Action(ActionEvent event) {
    }

    @FXML
    private void avatar2Action(ActionEvent event) {
    }

    @FXML
    private void avatar3Action(ActionEvent event) {
    }

    @FXML
    private void avatar4Action(ActionEvent event) {
    }

    @FXML
    private void avatarImportAction(ActionEvent event) {
    }

    @FXML
    private void modCorreoAction(ActionEvent event) {
    }

    @FXML
    private void modContraseñaAction(ActionEvent event) {
    }

    @FXML
    private void modFechaAction(ActionEvent event) {
    }
    
}
