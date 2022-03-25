/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class FXMLPersonasTableController implements Initializable {

    
    private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.
    
    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button delButton;
    @FXML
    private TableView<Persona> personasTable;
    @FXML
    private TableColumn<Persona, String> nomColum;
    @FXML
    private TableColumn<Persona, String> apellColum;
    @FXML
    private Button upButton;
    @FXML
    private Button movDownButton;

    
    private void inicializaModelo() {
        ArrayList<Persona> misdatos = new ArrayList<Persona>();
        misdatos.add(new Persona("Pepe", "García","Valencia"));
        misdatos.add(new Persona("María", "Pérez","Madrid"));
        datos= FXCollections.observableList(misdatos);
            }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        inicializaModelo();
        personasTable.setItems(datos);
        nomColum.setCellValueFactory(row->row.getValue().NombreProperty());
        apellColum.setCellValueFactory(row->row.getValue().ApellidosProperty());
        
        
        delButton.setDisable(true);
        modButton.setDisable(true);
        movDownButton.setDisable(true);
        upButton.setDisable(true);
        
        personasTable.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (personasTable.isFocused()) {
                        delButton.setDisable(false);
                        modButton.setDisable(false);
                        movDownButton.setDisable(false);
                        upButton.setDisable(false);
                    //    labelSELECTED.setText(datos.get(vistadeListafxID.getSelectionModel().getSelectedIndex()).toString);
                    }
        });
        

//    apellColum.setCellValueFactory(row->new SimpleStringProperty(row.getValue().getDireccion()));
        
    }    

//    firstNameColumn.setCellvalueFactory(cellData -> cellData.getValue().nombreProperty());
    
    @FXML
    private void addAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLPersona.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir Persona");
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.show();
        stage.showAndWait();
        
        FXMLPersonaController controlador2 = loader.getController();
        
        if(controlador2.isOKPulsado()){
            datos.add(controlador2.getPersona());
        }
        
        
    }

    @FXML
    private void modAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLPersona.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Modificar Persona");
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.show();
        stage.showAndWait();
        
        FXMLPersonaController controlador2 = loader.getController();
        
        controlador2.setPersona(personasTable.getSelectionModel().getSelectedItem());
//        if(controlador2.isOKPulsado()){
//            datos.add(controlador2.getPersona());
//        }
    }

    @FXML
    private void delAction(ActionEvent event) {
        int i = personasTable.getSelectionModel().getSelectedIndex();
    	if (i>=0) datos.remove(i);
    }

    @FXML
    private void upAction(ActionEvent event) {
        try {
        int i = personasTable.getSelectionModel().getSelectedIndex();
        Persona aux = datos.get(i);
        Persona aux2 = datos.get(i-1);
        datos.set(i,aux2);
        datos.set(i-1,aux);
        personasTable.getSelectionModel().select(i-1);
        } catch (IndexOutOfBoundsException e) {}
    }

    @FXML
    private void downAction(ActionEvent event) {
        try {
        int i = personasTable.getSelectionModel().getSelectedIndex();
        Persona aux = datos.get(i);
        Persona aux2 = datos.get(i+1);
        datos.set(i,aux2);
        datos.set(i+1,aux);
        personasTable.getSelectionModel().select(i+1);
        } catch (IndexOutOfBoundsException e) {}
    }
    
}
