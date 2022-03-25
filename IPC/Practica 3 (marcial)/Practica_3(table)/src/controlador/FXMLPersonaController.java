/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class FXMLPersonaController implements Initializable {

    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidosText;
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCANCEL;

    private boolean OKPulsado = false;

    private Persona persona;

    private BooleanProperty hayApellido;
    private BooleanProperty hayNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        hayApellido = new SimpleBooleanProperty();   
        hayNombre = new SimpleBooleanProperty();
        
        hayApellido.setValue(Boolean.FALSE);
        hayNombre.setValue(Boolean.FALSE);
        
        BooleanBinding hayTexto = Bindings.and(hayNombre, hayApellido);
        buttonCANCEL.disableProperty().bind(Bindings.not(hayTexto));
        buttonOK.disableProperty().bind(Bindings.not(hayTexto));
        
//        nombreText.lengthProperty().addListener((observable, oldV, newV) -> {
//            //if(newV > 0){
//                if(nombreText.getText().trim().length() > 0){
//                    hayNombre.setValue(Boolean.TRUE);
//                }
//            //} 
//        });
        
        apellidosText.focusedProperty().addListener((observable, oldV, newV) -> {
            if(!newV){
                if(apellidosText.getText().trim().length() > 0){
                    hayApellido.setValue(Boolean.TRUE);
                }
            } 
        });
    

        
    }    

    @FXML
    private void okAction(ActionEvent event) {
        OKPulsado = true;
        if (persona == null) {
            persona = new Persona("", "");
        }
        persona.setApellidos(apellidosText.getText());
        persona.setNombre(nombreText.getText());

        ((Stage) nombreText.getScene().getWindow()).close();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        ((Stage) nombreText.getScene().getWindow()).close();
    }

    boolean isOKPulsado() {
        return OKPulsado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona selectedItem) {
        persona = selectedItem;
        apellidosText.setText(persona.getApellidos());
        nombreText.setText(persona.getNombre());
    }

}
