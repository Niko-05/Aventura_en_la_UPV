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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class ModPerfil implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField correoField;
    @FXML
    private Label errCorreoLab;
    @FXML
    private TextField nombreField;
    @FXML
    private Label errNomLab;
    @FXML
    private TextField contraField;
    @FXML
    private Label errConLab;
    @FXML
    private DatePicker fechaField;
    @FXML
    private Label errFechaLab;
    @FXML
    private ImageView avatarField;
    @FXML
    private Button buttonAceptar;
    
    private User usuario;
    private int fallos;
    private int aciertos;
    @FXML
    private ImageView avatarField1;
    @FXML
    private ImageView avatarField11;
    @FXML
    private ImageView avatarField111;
    @FXML
    private ImageView avatarField1111;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MapaLoged.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Mapa");

        MapaLoged controladorPrin = loader.getController();
//                controladorPrin.setUsuario(usuario);

        stage.setScene(scene);
        controladorPrin.setUsuario(usuario);
        controladorPrin.setResultados(aciertos, fallos);
    }

    private void buttAvatarAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChoseAvatar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Modificar Persona");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        ChoseAvatar controladorAvatar = loader.getController();
        if(controladorAvatar.getAvatar() != null) avatarField.setImage(controladorAvatar.getAvatar());
        
    }

    @FXML
    private void buttAceptarAction(ActionEvent event) {
        
    }
    
     void setUsuario(User user){
        usuario = user;
    }
     public void setAvatar(Image avatar) {
        avatarField.setImage(avatar);
    }

    void setResultados(int a, int f) {
        aciertos = a;
        fallos = f;
    }
}
