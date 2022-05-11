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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Navegacion;
import model.User;
import org.controlsfx.control.Notifications;

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
    
    private User usuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        errNomLab.setVisible(false);
        errConLab.setVisible(false);
        
        
        contraField.focusedProperty().addListener((obs, preV, newV) -> {
            errConLab.setVisible(false);
        });
        nombreField.focusedProperty().addListener((obs, preV, newV) -> {
            errNomLab.setVisible(false);
        });
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Principal.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                double prevWidth = stage.getWidth();
                double prevHeight = stage.getHeight();
                stage.setHeight(prevHeight);
                stage.setWidth(prevWidth);
                stage.setTitle("Pestaña Principal");
                
                PrincipalController controladorPrin = loader.getController();
//                controladorPrin.setUsuario(usuario);
                
                stage.setScene(scene);
                controladorPrin.setUsuario(usuario);
//                
            }
        }else {errNomLab.setVisible(true);}
    }

    @FXML
    private void buttRegAction(ActionEvent event) throws Exception {
        
//       FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Registrarse.fxml"));
//        Parent root = loader.load();
//        
//        Scene scene = new Scene(root);
//        
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Demo vista de lista de personas");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.show();
        
        
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/Registrarse.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Pestaña registrarse");
        stage.setScene(scene);
    }

    @FXML
    private void pruebas(ActionEvent event) {
        
//        Notifications notificationBuilder = Notifications.create()
//                    .title("Confirmacion")
//                    .text("Se ha registrado el usuario correctamente")
//                    .graphic(null)
//                    .hideAfter(Duration.seconds(5))
//                    .position(Pos.BOTTOM_RIGHT);
//
//            Platform.runLater(() -> {
//
//                notificationBuilder.showInformation();
//            });

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.initStyle(StageStyle.DECORATED);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("DNI: 652764243\nNombre: nfdsuhgiie");
        alerta.showAndWait();
        
        
//        System.out.print((int) Math.floor(Math.random()*(10-0+1)+0));
                
        
//        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
//        alerta.initStyle(StageStyle.TRANSPARENT);
//        alerta.setTitle("Confirmacion");
//        alerta.setContentText("Se ha registrado el usuario correctamente");
//        alerta.show();
    }
    
    
}

