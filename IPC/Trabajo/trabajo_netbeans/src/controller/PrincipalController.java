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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class PrincipalController implements Initializable {

    @FXML
    private ImageView imgUsuario;
    @FXML
    private Button buttonModPerfil;
    @FXML
    private Button buttListProblm;
    @FXML
    private Button buttProblAuto;
    @FXML
    private Button buttVerResult;
    @FXML
    private Button buttonCerrarSesion;
    @FXML
    private Label usuarioLAB;

    
    private User usuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Acceder a usuario de inicial
        FXMLLoader loaderIni = new FXMLLoader(getClass().getResource("/view/Inicial.fxml"));
        InicialController controladorInicial = loaderIni.getController();
        if (controladorInicial.isUserLoged()) {
            usuario = controladorInicial.getUser();
        } else {

            //Acceder al usuario registrado
            FXMLLoader loaderReg = new FXMLLoader(getClass().getResource("/view/Registrarse.fxml"));
            RegistrarseController controladorRegistro = loaderReg.getController();
            if (controladorRegistro.isUserRegistered()) {
                usuario = controladorRegistro.getUser();
            }
        }
        
        imgUsuario.setImage(usuario.getAvatar());
        usuarioLAB.setText(usuario.getNickName());
    }    

    @FXML
    private void modPerfilAction(ActionEvent event) {
    }

    @FXML
    private void listaProblmAction(ActionEvent event) {
    }

    @FXML
    private void problAutoAction(ActionEvent event) {
    }

    @FXML
    private void verResultAction(ActionEvent event) {
    }

    @FXML
    private void cerrarSesionAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inicial.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Pestaña Inicial");
        stage.setScene(scene);
    }
    
}
