/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;


/**
 *
 * @author svalero
 */
public class FXMLSignUpController implements Initializable {


 
    //properties to control valid fieds values. 
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;  

    //private BooleanBinding validFields;
    
    //When to strings are equal, compareTo returns zero
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField MailField;
    @FXML
    private TextField PasswordField2;
    @FXML
    private Button botonAceptar;
    @FXML
    private Button botonCancelar;
    @FXML
    private Label errMail;
    @FXML
    private Label errPass1;
    @FXML
    private Label errPass2;


    
   
    

    /**
     * Updates the boolProp to false.Changes to red the background of the edit. 
     * Makes the error label visible and sends the focus to the edit. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
 
    }
    /**
     * Updates the boolProp to true. Changes the background 
     * of the edit to the default value. Makes the error label invisible. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    /**
     * Changes to red the background of the edit and
     * makes the error label visible
     * @param errorLabel
     * @param textField 
     */
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    /**
     * Changes the background of the edit to the default value
     * and makes the error label invisible.
     * @param errorLabel
     * @param textField 
     */
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }


    

    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
       
        botonCancelar.setOnAction( (event)->{
        botonCancelar.getScene().getWindow().hide();
        });
        
        
        BooleanBinding validFields = Bindings.and(validEmail, equalPasswords);//.and(equalPasswords);
        botonAceptar.disableProperty().bind(Bindings.not(validFields));
        
        MailField.focusedProperty().addListener((observable, oldV, newV) -> {
            if(!newV){
                if(!esCorreo(MailField.getText())){
                errMail.setVisible(true);
                validEmail.setValue(Boolean.FALSE);
                }
            } else {errMail.setVisible(false); validEmail.setValue(Boolean.TRUE);}
        });
        
        PasswordField.focusedProperty().addListener((observable, oldV, newV) -> {
            if(!newV){
                if(!esContraseña(PasswordField.getText())){
                errPass1.setVisible(true);
                validPassword.setValue(Boolean.FALSE);
                }
            } else {errPass1.setVisible(false); validPassword.setValue(Boolean.TRUE);}
        });
        
        PasswordField2.focusedProperty().addListener((observable, oldV, newV) -> {
            if(!newV){
                if(!(PasswordField.getText().equals(PasswordField2.getText()))){
                errPass2.setVisible(true);
                equalPasswords.setValue(Boolean.FALSE);
                PasswordField.requestFocus();
                PasswordField.textProperty().setValue("");
                PasswordField2.textProperty().setValue("");
                }
            } else {errPass2.setVisible(false); equalPasswords.setValue(Boolean.TRUE);}
        });


    } 
    
    
    private boolean esCorreo(String correoSTR) {
        char[] correoArray = correoSTR.toCharArray();
        int sumAUX = 0;
        
        for (int i = 0; correoArray.length > i; i++) {
            if (correoArray[i] == '@' || correoArray[i] == '.' || correoArray[i] == ' ') {
                sumAUX++;
            }
        }
        if (sumAUX > 2) { return false; }

        for (int i = 1; correoArray.length > i; i++) {
            if (correoArray[i] == '@') {
                for (int j = i + 2; correoArray.length - 1 > j; j++) {
                    if (correoArray[j] == '.') { return true; }
                }
            }
        }
        
        return false;
    }
    
    private boolean esContraseña(String contraSTR) {
        char[] contraArray = contraSTR.toCharArray();
        int sumAUX = 0;
        
        for (int i = 0; contraArray.length > i; i++) {
            if (contraArray[i] == ' ') {
                sumAUX++;
            }
        }
        if (sumAUX > 0 || contraArray.length < 8 || contraArray.length > 15) { return false; }
        
        return true;
    }

    @FXML
    private void pulsarAceptar(ActionEvent event) {
        System.out.println("Correo introducido: " + MailField.getText());
        System.out.println("Contraseña introducida: " + PasswordField.getText());
        System.out.println("Contraseña2 introducida: " + PasswordField2.getText());
        System.out.println("------------------------------------"); 
        System.out.println(esCorreo(MailField.getText()));
        
        if(!esCorreo(MailField.getText())){
            errMail.setVisible(true);
            validEmail.setValue(Boolean.FALSE);
        }
        
        if(!esContraseña(PasswordField.getText())){
            errPass1.setVisible(true);
            validPassword.setValue(Boolean.FALSE);
        }
        
        if(!(PasswordField.getText().equals(PasswordField2.getText()))){
            errPass2.setVisible(true);
            equalPasswords.setValue(Boolean.FALSE);
        }

    }

    
}