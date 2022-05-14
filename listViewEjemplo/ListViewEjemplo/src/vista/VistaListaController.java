/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Vicente
 */
public class VistaListaController implements Initializable {

    @FXML
    private ListView<String> lista;
    
    private ObservableList<String> dato =null;
    private TextField campoTexto;
    @FXML
    private Button down;
    @FXML
    private Button up;
    @FXML
    private CheckMenuItem bitcoin;
    @FXML
    private CheckMenuItem ether;
    @FXML
    private CheckMenuItem litecoin;
    @FXML
    private Label cripto;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ArrayList<String> misdatos = new ArrayList<String>();
        dato = FXCollections.observableArrayList(misdatos);
        lista.setItems(dato);
        
        //campoTexto.focusedProperty().addListener(())
        
        lista.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
        if (newVal.intValue() == lista.getItems().size()-1) {
            down.setDisable(true);
            up.setDisable(false);
        } else {
            down.setDisable(false);
        }
        if(newVal.intValue() == 0){
            down.setDisable(false);
            up.setDisable(true);
        } else {
            up.setDisable(false);
        }
        });
    }    

    private void datos(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("your dni \n your name");
        alert.showAndWait();
    }

    private void enter(KeyEvent event) {
        if(!(campoTexto.getText()).isEmpty() && event.getCode() == KeyCode.ENTER){
            dato.add(campoTexto.getText());
            campoTexto.clear();
            campoTexto.requestFocus();
        }
    }

    private void enter(ActionEvent event) {
        if(!(campoTexto.getText()).isEmpty()){
            dato.add(campoTexto.getText());
            campoTexto.clear();
            campoTexto.requestFocus();
        }
    }

    @FXML
    private void bajar(ActionEvent event) {
    }

    @FXML
    private void subir(ActionEvent event) {
    }

    @FXML
    private void bc(ActionEvent event) {
    }

    @FXML
    private void et(ActionEvent event) {
    }

    @FXML
    private void lc(ActionEvent event) {
    }
    
}
