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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class InicialController implements Initializable {

    @FXML
    private TextField nombreField;
    @FXML
    private Label errNomLab;
    @FXML
    private TextField contraField;
    @FXML
    private Label errConLab;
    @FXML
    private Button buttonAceptar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttAceptarAction(ActionEvent event) {
    }
    
}
