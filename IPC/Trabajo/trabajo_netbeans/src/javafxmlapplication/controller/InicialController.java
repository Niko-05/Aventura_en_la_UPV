/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class InicialController implements Initializable {

    @FXML
    private BorderPane borderPane;
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
    @FXML
    private Button buttRegistrar;
    
    
   User usuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        errNomLab.setVisible(false);
        errConLab.setVisible(false);
        
    }    



    @FXML
    private void buttAceptarAction(ActionEvent event) throws Exception {
        if(Navegacion.getSingletonNavegacion().exitsNickName(nombreField.getText())){
            if(Navegacion.getSingletonNavegacion().loginUser(nombreField.getText(), contraField.getText()) == null){
            errConLab.setVisible(true);
            } else {
            
                // pasar a pantalla principal
                
            }
        }else {errNomLab.setVisible(true);}
    }

    @FXML
    private void buttRegAction(ActionEvent event) throws IOException {
        
       FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Registrarse.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Modificar Persona");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.showAndWait();
    }


    
    
}

