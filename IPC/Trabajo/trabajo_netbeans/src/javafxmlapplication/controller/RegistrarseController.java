/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class RegistrarseController implements Initializable {
    
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty validname;
    private BooleanProperty validage;
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
    private Button buttonAvatar;
    @FXML
    private ImageView avatarField;
    @FXML
    private Button buttonAceptar;


    /**
     * Initializes the controller class.
     */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        validage = new SimpleBooleanProperty();
        validname = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        validage.setValue(Boolean.FALSE);
        validname.setValue(Boolean.FALSE);
        
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(validage).and(validname);
        buttonAceptar.disableProperty().bind(Bindings.not(validFields));
        
        
        correoField.focusedProperty().addListener((observable, oldV, newV) -> {
            if(!newV){
                if(User.checkEmail(correoField.getText())){
                errCorreoLab.setVisible(true);
                validEmail.setValue(Boolean.FALSE);
                } else {errCorreoLab.setVisible(false); validEmail.setValue(Boolean.TRUE);}
            } 
        });
    }    

    @FXML
    private void buttAvatarAction(ActionEvent event) {
    }

    @FXML
    private void buttAceptarAction(ActionEvent event) {
    }
    
}
