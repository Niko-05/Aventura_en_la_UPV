/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Navegacion;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class Resultados implements Initializable {
    
    private ObservableList<Session> datos = null;

    @FXML
    private TableColumn<Session, String> fechaColum;
    @FXML
    private TableColumn<Session, String> horaColum;
    @FXML
    private TableColumn<Session, Integer> aciertosColum;
    @FXML
    private TableColumn<Session, Integer> fallosColum;
    @FXML
    private TableColumn<Session, Integer> intentosColum;
    @FXML
    private TableView<Session> tableView;
    
    private User usuario;
    private ObservableValue<LocalDate> dia;
    private ArrayList<Session> listaFinal;
    private ArrayList<Session> listaFinal1 = new ArrayList<Session>();
    private Stage stageActual;
    private MapaLoged controllerLoged;
    @FXML
    private VBox ventanaPrincipal;
    @FXML
    private DatePicker controlFecha;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
//        try {
//            usuario = Navegacion.getSingletonNavegacion().loginUser("prueba", "123456aA!");
//            
//            
//        } catch (NavegacionDAOException ex) {
//        }
      
        controlFecha.valueProperty().addListener((obsV, oldV, newV) -> {
            if (controlFecha.getValue() != null) {
                listaFinal = new ArrayList<Session>();
//                System.out.println("prueba");
                for (int i = 0; i < listaFinal1.size(); i++) {
                    if (0 >= controlFecha.getValue().compareTo(usuario.getSessions().get(i).getLocalDate())) {
                        listaFinal.add(usuario.getSessions().get(i));
//                        System.out.println("prueba3");
                    }
                }
                if (listaFinal.size() > 0) {
                    datos = FXCollections.observableList(listaFinal);
                    tableView.setItems(datos);
//                    System.out.println("prueba2");
                }
            }
        });



        ventanaPrincipal.heightProperty().addListener((obs,oldV,newV) -> {
            tableView.setPrefHeight((double) newV);
        });
          
        
        aciertosColum.setCellValueFactory(row->new SimpleIntegerProperty(row.getValue().getHits()).asObject());
        fallosColum.setCellValueFactory(row->new SimpleIntegerProperty(row.getValue().getFaults()).asObject());
        intentosColum.setCellValueFactory(row -> new SimpleIntegerProperty(row.getValue().getFaults() + row.getValue().getHits()).asObject());
        fechaColum.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))));
        horaColum.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getTimeStamp().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        

    }

    
    @FXML
    private void backAction(ActionEvent event) {
        ((Stage) tableView.getScene().getWindow()).close();
        controllerLoged.closeProblemas();
    }
    
    void setStage(Stage aux){
        stageActual = aux;
        
        stageActual = aux;
        
        stageActual.setOnCloseRequest(e -> {
            controllerLoged.closeProblemas();
        });
    }
    
    void setController(MapaLoged contr){
        controllerLoged = contr;
    }
    
    void setUsuario(User user) {
        usuario = user;
        for (int i = 0; i < usuario.getSessions().size(); i++) {
            listaFinal1.add(usuario.getSessions().get(i));
        }

        datos = FXCollections.observableList(listaFinal1);
        tableView.setItems(datos);
    }

    @FXML
    private void limpiarAction(ActionEvent event) {
        controlFecha.setValue(null);
        datos = FXCollections.observableList(listaFinal1);
        tableView.setItems(datos);
    }


}

