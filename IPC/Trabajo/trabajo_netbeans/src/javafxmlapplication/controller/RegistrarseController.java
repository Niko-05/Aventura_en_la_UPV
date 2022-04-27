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
import javafx.fxml.Initializable;

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
        
//        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(validage).and(validname);
//        buttonAceptar.disableProperty().bind(Bindings.not(validFields));
//        
//        
//        correoField.focusedProperty().addListener((observable, oldV, newV) -> {
//            if(!newV){
//                if(!esCorreo(correoField.getText())){
//                errMail.setVisible(true);
//                validEmail.setValue(Boolean.FALSE);
//                } else {errMail.setVisible(false); validEmail.setValue(Boolean.TRUE);}
//            } 
//        });
    }    
    
}
