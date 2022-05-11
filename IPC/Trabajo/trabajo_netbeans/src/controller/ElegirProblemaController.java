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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class ElegirProblemaController implements Initializable {

    @FXML
    private ListView<String> problemasListView;
    
    
    private List<Problem> problemas;

    
    private ObservableList<String> datos = null;
    
    private User usuario;
    private int aciertos;
    private int fallos;
    @FXML
    private Button seleccionarButton;
    @FXML
    private Button backButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Test.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Resolucion de problemas");

        TestController controladorTest = loader.getController();

        
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setRandomness(false,problemasListView.getSelectionModel().getSelectedIndex());
        stage.setScene(scene);
        
        
        
//        problemasListView.getSelectionModel().getSelectedIndex();
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
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

        stage.setScene(scene);
        controladorPrin.setUsuario(usuario);
        controladorPrin.setResultados(aciertos, fallos);
    }
    
    
    void setUsuario(User user){
        usuario = user;
    }
    
    void setResultados(int a, int f){
        aciertos = a;
        fallos = f;
    }

    @FXML
    private void pruebas(ActionEvent event) {
        System.out.println(problemasListView.getSelectionModel().getSelectedIndex());
    }
}
