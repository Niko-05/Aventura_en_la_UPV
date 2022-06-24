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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class ElegirProblema implements Initializable {
    
    
    private List<Problem> problemas;

    
    private ObservableList<String> datos = null;
    
    private User usuario;
    private int aciertos;
    private int fallos;

    private MapaLoged controllerLoged;

    @FXML
    private ListView<String> problemasListView;
    @FXML
    private Button seleccionarButton;
    @FXML
    private Button backButton;
    @FXML
    private VBox ventanaPrincipal;
    @FXML
    private HBox ventanaTop;
    @FXML
    private HBox ventanaAbajo;
    @FXML
    private HBox boxListView;
    @FXML
    private HBox ventanaTop2;
    @FXML
    private Label labelTop;
    @FXML
    private HBox ventanaTop1;
    @FXML
    private HBox ventanaTop3;
    private Stage stageActual;
    private Stage stageMapa;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ventanaPrincipal.widthProperty().addListener((obs, oldV, newV) -> {
        
//            ventanaTop1.setPrefWidth(300);
//            labelTop.setPrefWidth(((double) newV) - 210);
            ventanaTop1.setPrefWidth((((double) newV) / 2) - 140);
            ventanaTop3.setPrefWidth((((double) newV) / 2) - 140);
            ventanaAbajo.setPrefWidth((double) newV);
            boxListView.setPrefWidth((double) newV);
            problemasListView.setPrefWidth((double) newV);
        });
        
        ventanaPrincipal.heightProperty().addListener((obs, oldV, newV) -> {
            
            boxListView.setPrefHeight((double) newV);
        });
        
        seleccionarButton.setDisable(true);
        
        try {
            problemas = Navegacion.getSingletonNavegacion().getProblems();
        } catch (NavegacionDAOException ex) {}
        
        List<String> problemasList = new ArrayList<String>();
        for(int i = 0; i < problemas.size(); i++){
            problemasList.add(problemas.get(i).getText());
        
        }

		datos = FXCollections.observableArrayList(problemasList);
		problemasListView.setItems(datos);

                
                
                problemasListView.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
                    if (newVal.intValue() != -1) {
                        seleccionarButton.setDisable(false);
                    }
                });
    }    

    @FXML
    private void seleccionarAction(ActionEvent event) throws IOException{
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VentanaResponder.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);;
        stage.setTitle("Resolucion de problemas");
//        stage.setResizable(false);

        VentanaResponder controladorTest = loader.getController();

        
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setController(controllerLoged);
        controladorTest.setStage(stageMapa);
        
        stage.setScene(scene);
        controladorTest.setRandomness(false,problemasListView.getSelectionModel().getSelectedIndex());
        
        
//        problemasListView.getSelectionModel().getSelectedIndex();
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        ((Stage) problemasListView.getScene().getWindow()).close();
        controllerLoged.closeProblemas();
        
    }
    
    
    void setUsuario(User user){
        usuario = user;
    }
    
    void setResultados(int a, int f){
        aciertos = a;
        fallos = f;
    }
    
    void setController(MapaLoged contr) {
        controllerLoged = contr;
    }
    
    void setStage(Stage aux){
        stageActual = aux;
        
        stageActual.setOnCloseRequest(e -> {
            controllerLoged.closeProblemas();
        });
    }

    void setStageMapa(Stage stageActual) {
        stageMapa = stageActual;
    }
}
