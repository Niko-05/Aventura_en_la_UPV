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
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class MapaLoged implements Initializable {

    @FXML
    private Slider zoom_slider;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private ImageView imgUsuario;
    @FXML
    private MenuButton usuarioButton;
    private int aciertos;
    private int fallos;
    private User usuario;

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
                
                ModPerfil controladorModPerfil = loader.getController();
                
                stage.setScene(scene);
                controladorModPerfil.setUsuario(usuario);
                controladorModPerfil.setResultados(aciertos, fallos);
    }

    @FXML
    private void cerrarSesionAction(ActionEvent event) {
    }

    @FXML
    private void probListAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ElegirProblema.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Elegir problemas");

        ElegirProblema controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
    }

    @FXML
    private void probAleatorioAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Test.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Resolucion de problemas");

        Test controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setRandomnes(true);
    }

    @FXML
    private void resultadosAction(ActionEvent event) {
    }
    
    
    
    
    
    void setUsuario(User user){
        usuario = user;
        imgUsuario.setImage(usuario.getAvatar());
        usuarioButton.setText(usuario.getNickName());
    }
    
    void setResultados(int a, int f){
        aciertos = a;
        fallos = f;
    }
}
