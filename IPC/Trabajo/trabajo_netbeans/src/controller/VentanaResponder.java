/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.beans.binding.Bindings.not;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.Answer;
import model.Navegacion;
import model.Problem;
import model.User;
import org.sqlite.date.FastDateFormat;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class VentanaResponder implements Initializable {

    @FXML
    private RadioButton Respuesta_0;
    @FXML
    private RadioButton Respuesta_1;
    @FXML
    private RadioButton Respuesta_2;
    @FXML
    private RadioButton Respuesta_3;
    @FXML
    private Label preguntaField;
    
    
    private boolean res0;
    private boolean res1;
    private boolean res2;
    private boolean res3;
    
    private User usuario;
//    private IntegerProperty aciertos;
//    private IntegerProperty fallos;
    private int aciertos = 0;
    private int fallos = 0;
    
    private int selectedProblem;
    private boolean random;
    private int max;
    private final int min = 0;
    private List<Problem> problemas;
    //      (int) Math.floor(Math.random() * (max - min + 1) + min)
    @FXML
    private Button comprobarButton;
    @FXML
    private Label aciertosLabel;
    @FXML
    private Label fallosLabel;
    @FXML
    private Button siguienteButton;
    @FXML
    private VBox ventanaPrincipal;
    @FXML
    private VBox hBoxPrincipal;
    @FXML
    private VBox vBoxEnunciado;
    @FXML
    private HBox hBoxAbajo1;
    @FXML
    private HBox hBoxAbajo2;
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ventanaPrincipal.widthProperty().addListener((obs, oldV, newV) -> {
        
            hBoxAbajo1.setPrefWidth((double) newV / 2);
            hBoxAbajo2.setPrefWidth((double) newV / 2);

            hBoxPrincipal.setPrefWidth((double) newV);
        });
        
        ventanaPrincipal.heightProperty().addListener((obs, oldV, newV) -> {
        
            hBoxPrincipal.setPrefHeight((double) newV);
            vBoxEnunciado.setPrefHeight((double) newV / 2);
        });
        
        
        comprobarButton.setDisable(true);
        siguienteButton.setDisable(true);
        

        ToggleGroup tGroup = new ToggleGroup();
        Respuesta_0.setToggleGroup(tGroup);
        Respuesta_1.setToggleGroup(tGroup);
        Respuesta_2.setToggleGroup(tGroup);
        Respuesta_3.setToggleGroup(tGroup);
        
        

       tGroup.selectedToggleProperty().addListener((obs, preV, newV) -> {
            comprobarButton.setDisable(false);
       });
        
        try {
            problemas = Navegacion.getSingletonNavegacion().getProblems();
            max = Navegacion.getSingletonNavegacion().getProblems().size() - 1;
        } catch (NavegacionDAOException ex) {}


            
        // TODO
    }

    private void backAction(ActionEvent event) throws IOException {
        if(random){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MapaLoged.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();
            stage.setHeight(prevHeight);
            stage.setWidth(prevWidth);
            stage.setTitle("Mapa");

            MapaLoged controladorPrin = loader.getController();
//                controladorPrin.setUsuario(usuario);

            stage.setScene(scene);
            controladorPrin.setUsuario(usuario);
            controladorPrin.setResultados(aciertos, fallos);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ElegirProblema.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();
            stage.setHeight(prevHeight);
            stage.setWidth(prevWidth);
            stage.setTitle("Elegir problemas");

            ElegirProblema controladorTest = loader.getController();

            stage.setScene(scene);
            controladorTest.setUsuario(usuario);
            controladorTest.setResultados(aciertos, fallos);
        
        }
    }

    @FXML
    private void siguienteAction(ActionEvent event) throws IOException {
        
        if(random){
            setRandomProblem();
            comprobarButton.setDisable(false);
            Respuesta_0.setStyle("-fx-text-fill: black");
            Respuesta_1.setStyle("-fx-text-fill: black");
            Respuesta_2.setStyle("-fx-text-fill: black");
            Respuesta_3.setStyle("-fx-text-fill: black");
            
            Respuesta_0.setSelected(false);
            Respuesta_1.setSelected(false);
            Respuesta_2.setSelected(false);
            Respuesta_3.setSelected(false);

            Respuesta_0.setDisable(false);
            Respuesta_1.setDisable(false);
            Respuesta_2.setDisable(false);
            Respuesta_3.setDisable(false);
            
            comprobarButton.setDisable(true);
            siguienteButton.setDisable(true);
        } else {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ElegirProblema.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();
            stage.setHeight(prevHeight);
            stage.setWidth(prevWidth);
            stage.setTitle("Elegir problemas");

            ElegirProblema controladorTest = loader.getController();

            stage.setScene(scene);
            controladorTest.setUsuario(usuario);
            controladorTest.setResultados(aciertos, fallos);
        }
        
    }
    
    private void setRandomProblem() {
        
        selectedProblem = (int) Math.floor(Math.random() * (max - min + 1) + min);   
        preguntaField.setText(problemas.get(selectedProblem).getText());
        
//        selectedProblem = 0;
        List<Integer> list = new ArrayList(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(list);
        
        Respuesta_0.setText(problemas.get(selectedProblem).getAnswers().get(list.get(0)).getText());
        Respuesta_1.setText(problemas.get(selectedProblem).getAnswers().get(list.get(1)).getText());
        Respuesta_2.setText(problemas.get(selectedProblem).getAnswers().get(list.get(2)).getText());
        Respuesta_3.setText(problemas.get(selectedProblem).getAnswers().get(list.get(3)).getText());

        res0 = (problemas.get(selectedProblem).getAnswers().get(list.get(0)).getValidity());
        res1 = (problemas.get(selectedProblem).getAnswers().get(list.get(1)).getValidity());
        res2 = (problemas.get(selectedProblem).getAnswers().get(list.get(2)).getValidity());
        res3 = (problemas.get(selectedProblem).getAnswers().get(list.get(3)).getValidity());

        
    }
    
     @FXML
    private void comprobarAction(ActionEvent event) {
  
        //             fallos.setValue(fallos.get() + 1);
        //             aciertos.setValue(aciertos.get() + 1);
         // Comprobar Error
         if (Respuesta_0.isSelected() && !res0) {
             Respuesta_0.setStyle("-fx-text-fill: red");
             fallos += 1;
         } else if (Respuesta_1.isSelected() && !res1) {
             Respuesta_1.setStyle("-fx-text-fill: red");
             fallos += 1;
         } else if (Respuesta_2.isSelected() && !res2) {
             Respuesta_2.setStyle("-fx-text-fill: red");
             fallos += 1;
         } else if (Respuesta_3.isSelected() && !res3) {
             Respuesta_3.setStyle("-fx-text-fill: red");
             fallos += 1;
             
         } else if (Respuesta_0.isSelected() && res0) {
             Respuesta_0.setStyle("-fx-text-fill: green");
             aciertos += 1;
         } else if (Respuesta_1.isSelected() && res1) {
             Respuesta_1.setStyle("-fx-text-fill: green");
             aciertos += 1;
         } else if (Respuesta_2.isSelected() && res2) {
             Respuesta_2.setStyle("-fx-text-fill: green");
             aciertos += 1;
         } else if (Respuesta_3.isSelected() && res3) {
             Respuesta_3.setStyle("-fx-text-fill: green");
             aciertos += 1;
         }
         comprobarButton.setDisable(true);
//         System.out.println("Aciertos: " + aciertos + " | Fallos: " + fallos);
         
         fallosLabel.setText(Integer.toString(fallos));
         aciertosLabel.setText(Integer.toString(aciertos));
         
         Respuesta_0.setDisable(true);
         Respuesta_1.setDisable(true);
         Respuesta_2.setDisable(true);
         Respuesta_3.setDisable(true);
         
         siguienteButton.setDisable(false);
    }
    
    
    void setUsuario(User user){
        usuario = user;
    }
    
    void setResultados(int a, int f){
        aciertos = a;
        fallos = f;
        fallosLabel.setText(Integer.toString(fallos));
        aciertosLabel.setText(Integer.toString(aciertos));
    }
    
    void setRandomnes(boolean rdm) {
        random = rdm;
        setRandomProblem();
    }
    
    void setRandomness(boolean rdm,int prb) {
        random = rdm;
        selectedProblem = prb;
      
        preguntaField.setText(problemas.get(selectedProblem).getText());
        
//        List<Answer> listaAUX = problemas.get(selectedProblem).getAnswers();
//        Collections.shuffle(Arrays.asList(listaAUX.toString()));

        List<Integer> list = new ArrayList(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(list);
        
        Respuesta_0.setText(problemas.get(selectedProblem).getAnswers().get(list.get(0)).getText());
        Respuesta_1.setText(problemas.get(selectedProblem).getAnswers().get(list.get(1)).getText());
        Respuesta_2.setText(problemas.get(selectedProblem).getAnswers().get(list.get(2)).getText());
        Respuesta_3.setText(problemas.get(selectedProblem).getAnswers().get(list.get(3)).getText());

        res0 = (problemas.get(selectedProblem).getAnswers().get(list.get(0)).getValidity());
        res1 = (problemas.get(selectedProblem).getAnswers().get(list.get(1)).getValidity());
        res2 = (problemas.get(selectedProblem).getAnswers().get(list.get(2)).getValidity());
        res3 = (problemas.get(selectedProblem).getAnswers().get(list.get(3)).getValidity());
    }
   
}
