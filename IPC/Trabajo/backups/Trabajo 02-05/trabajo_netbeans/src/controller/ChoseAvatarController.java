/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class ChoseAvatarController implements Initializable {

    @FXML
    private ToggleButton buttonAvatar1;
    @FXML
    private ToggleButton buttonAvatar2;
    @FXML
    private ToggleButton buttonAvatar3;
    @FXML
    private ToggleButton buttonAvatar4;
    @FXML
    private ToggleButton buttonAvatarDef;
    @FXML
    private ToggleButton buttAvatarUpload;
    @FXML
    private ImageView avatarUpload;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    private ImageView avatarDef;

    private FileChooser selector = new FileChooser();
    private String ruta;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup tGroup = new ToggleGroup();
        buttonAvatar1.setToggleGroup(tGroup);
        buttonAvatar2.setToggleGroup(tGroup);
        buttonAvatar3.setToggleGroup(tGroup);
        buttonAvatar4.setToggleGroup(tGroup);
        buttonAvatarDef.setToggleGroup(tGroup);
        buttAvatarUpload.setToggleGroup(tGroup);
        
        buttAvatarUpload.setDisable(true);
        


        
                
        // TODO
        buttonAvatar1.getToggleGroup().selectedToggleProperty().addListener((obs, preV, newV) -> {
            avatar.setImage(getSelected());
            
        });
        
        
    }

    @FXML
    private void cancelarAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        if ("/view/Registrarse.fxml".equals(ruta)) {
            stage.setTitle("Pestaña Registrarse");
            stage.setScene(scene);
        }
    }

    @FXML
    private void aceptarAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);

        if ("/view/Registrarse.fxml".equals(ruta)) {
            stage.setTitle("Pestaña Registrarse");
            RegistrarseController controladorReg = loader.getController();
            stage.setScene(scene);
            controladorReg.setAvatar(avatar.getImage());
        }
        
        if ("/view/ModificarPerfil.fxml".equals(ruta)) {
            stage.setTitle("Modificar Perfil");
            ModPerfilController controladorMod = loader.getController();
            stage.setScene(scene);
            controladorMod.setAvatar(avatar.getImage());
        }
        
        
        
    }

    @FXML
    private void importarAction(ActionEvent event) {
        selector.setTitle("Avatar Selector");
        selector.setInitialDirectory(new File(System.getProperty("user.home")));
        selector.getExtensionFilters().clear();
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes ", "*.png","*.jpg","*.gif"));
        File file = selector.showOpenDialog(null);
        if(file != null){
            avatarUpload.setImage(new Image(file.toURI().toString()));
            avatarUpload.setPreserveRatio(false);
            avatarUpload.resize(50, 50);
            buttAvatarUpload.setDisable(false);
            buttAvatarUpload.requestFocus();
            buttAvatarUpload.setSelected(true);
            avatar.setImage(getSelected());
        }
        
    }
    
    private Image getSelected(){
        
        if(buttonAvatar1.isSelected()) return avatar1.getImage();
        if(buttonAvatar2.isSelected()) return avatar2.getImage();
        if(buttonAvatar3.isSelected()) return avatar3.getImage();
        if(buttonAvatar4.isSelected()) return avatar4.getImage();
        if(buttonAvatarDef.isSelected()) return avatarDef.getImage();
        if(buttAvatarUpload.isSelected()) return avatarUpload.getImage();
        return null;
    }
    
    public void setRuta(String ruta){
        this.ruta = ruta;
    }
}
