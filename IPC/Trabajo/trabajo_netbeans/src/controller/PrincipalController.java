/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Navegacion;
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
    private int fallos;
    private int aciertos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void modPerfilAction(ActionEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModificarPerfil.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                double prevWidth = stage.getWidth();
                double prevHeight = stage.getHeight();
                stage.setHeight(prevHeight);
                stage.setWidth(prevWidth);
                stage.setTitle("Modificar Perfil");
                
                ModPerfilController controladorModPerfil = loader.getController();
                
                stage.setScene(scene);
                controladorModPerfil.setUsuario(usuario);
                controladorModPerfil.setResultados(aciertos, fallos);
//                
            }

    @FXML
    private void listaProblmAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ElegirProblema.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Elegir problemas");

        ElegirProblemaController controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
    }

    @FXML
    private void problAutoAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Test.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Resolucion de problemas");

        TestController controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setRandomness(true);
        
        
    }

    @FXML
    private void verResultAction(ActionEvent event) {
    }

    @FXML
    private void cerrarSesionAction(ActionEvent event) throws IOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("Cerrar Sesion");
        //alerta.showAndWait();
        Optional<ButtonType> result = alerta.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Inicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();
            stage.setHeight(prevHeight);
            stage.setWidth(prevWidth);
            stage.setTitle("Pestaña Inicial");
            stage.setScene(scene);

        }
    }
    
    void setUsuario(User user){
        usuario = user;
        imgUsuario.setImage(usuario.getAvatar());
        usuarioLAB.setText(usuario.getNickName());
    }
    
    void setResultados(int a, int f){
        aciertos = a;
        fallos = f;
    }
}
