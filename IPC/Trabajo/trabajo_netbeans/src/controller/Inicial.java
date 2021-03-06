/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Navegacion;
import model.User;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * 22/05/2022
 * @author:
 * Marcial Carreras Arencibia
 * Nicolas montoliu zarza
 * Vicente Morell Amat
 * 
 */
public class Inicial implements Initializable {
    

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
    
    private User usuario;
    private Stage stageMapa;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
//        nombreField.setText("prueba");
//        contraField.setText("123456aA!");
        // TODO
        errNomLab.setVisible(false);
        errConLab.setVisible(false);
        
        
        contraField.focusedProperty().addListener((obs, preV, newV) -> {
            errConLab.setVisible(false);
        });
        nombreField.focusedProperty().addListener((obs, preV, newV) -> {
            errNomLab.setVisible(false);
        });
        
        nombreField.requestFocus();
    }    



    @FXML
    private void buttAceptarAction(ActionEvent event) throws Exception {
        if(Navegacion.getSingletonNavegacion().exitsNickName(nombreField.getText())){
            if(Navegacion.getSingletonNavegacion().loginUser(nombreField.getText(), contraField.getText()) == null){
            errConLab.setVisible(true);
            } else {
//                loged = true;
                
                usuario = Navegacion.getSingletonNavegacion().loginUser(nombreField.getText(), contraField.getText());
                //sleep(1000);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MapaLoged.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Mapa");
                stage.setResizable(true);

                MapaLoged controladorPrin = loader.getController();
                ((Stage)nombreField.getScene().getWindow()).close();
//                controladorPrin.setUsuario(usuario);

                stage.setScene(scene);
                stageMapa.close();
                stage.show();
                controladorPrin.setUsuario(usuario);
                controladorPrin.setStage(stage);
                stage.setHeight(Screen.getPrimary().getBounds().getHeight() - 250);
                stage.setWidth(Screen.getPrimary().getBounds().getWidth() - 600);
//                
            }
        }else {errNomLab.setVisible(true);}
    }

    @FXML
    private void buttRegAction(ActionEvent event) throws Exception {
        
       FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Registrarse.fxml"));
        Parent root = loader.load();
//        
//        Scene scene = new Scene(root);
//        
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Demo vista de lista de personas");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.show();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Pesta??a registrarse");
        stage.setScene(scene);
        stage.setHeight(480);
        stage.setWidth(520);
        
        Registrarse controladorPrin = loader.getController();
        controladorPrin.setController(stageMapa);
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {

           ((Stage)nombreField.getScene().getWindow()).close();

    }

    void setController(Stage cnt){
        stageMapa = cnt;
    }
    
}

