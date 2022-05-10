/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.Answer;
import model.Navegacion;
import model.Problem;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class TestController implements Initializable {

    @FXML
    private RadioButton Respuesta_1;
    @FXML
    private RadioButton Respuesta_2;
    @FXML
    private RadioButton Respuesta_3;
    @FXML
    private Label preguntaField;
    
    
    private boolean res1;
    private boolean res2;
    private boolean res3;
    
    private User usuario;
    private IntegerProperty aciertos;
    private IntegerProperty fallos;
    private int acierto = 0;
    private int fallo = 0;
    
    private int selectedProblem;
    private boolean random = true;
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
    
    private String cosa = "gola";
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        fallos.set(0);
        aciertos.set(0);
        
        ToggleGroup tGroup = new ToggleGroup();
        Respuesta_1.setToggleGroup(tGroup);
        Respuesta_2.setToggleGroup(tGroup);
        Respuesta_3.setToggleGroup(tGroup);
        

        
        fallosLabel.textProperty().bind(fallos.asString());
        aciertosLabel.textProperty().bind(aciertos.asString());
//        Integer.toString(aciertos)
        
        try {
            problemas = Navegacion.getSingletonNavegacion().getProblems();
            max = Navegacion.getSingletonNavegacion().getProblems().size();
        } catch (NavegacionDAOException ex) {}

        if (random) {
            setRandomProblem();
            

        }

        // TODO
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
//                controladorPrin.setUsuario(usuario);

        stage.setScene(scene);
        controladorPrin.setUsuario(usuario);
        controladorPrin.setResultados(aciertos.get(), fallos.get());
    }

    @FXML
    private void siguienteAction(ActionEvent event) {
        
        if(random){
            setRandomProblem();
            comprobarButton.setDisable(false);
            Respuesta_1.setStyle("-fx-text-fill: black");
            Respuesta_2.setStyle("-fx-text-fill: black");
            Respuesta_3.setStyle("-fx-text-fill: black");
            
        }
        
    }
    
    private void setRandomProblem() {
        int num = (int) Math.floor(Math.random() * (max - min + 1) + min);

        preguntaField.setText(problemas.get(num).getText());
        Respuesta_1.setText(problemas.get(num).getAnswers().get(1).getText());
        Respuesta_2.setText(problemas.get(num).getAnswers().get(2).getText());
        Respuesta_3.setText(problemas.get(num).getAnswers().get(3).getText());
        selectedProblem = num;
        
        res1 = (problemas.get(num).getAnswers().get(1).getValidity());
        res2 = (problemas.get(num).getAnswers().get(2).getValidity());
        res3 = (problemas.get(num).getAnswers().get(3).getValidity());

        
    }
    
     @FXML
    private void comprobarAction(ActionEvent event) {
  
            // Comprobar Error
         if (Respuesta_1.isSelected() && !res1) {
             Respuesta_1.setStyle("-fx-text-fill: red");
             fallos.setValue(fallos.get() + 1);
         } else if (Respuesta_2.isSelected() && !res2) {
             Respuesta_2.setStyle("-fx-text-fill: red");
             fallos.setValue(fallos.get() + 1);
         } else if (Respuesta_3.isSelected() && !res3) {
             Respuesta_3.setStyle("-fx-text-fill: red");
             fallos.setValue(fallos.get() + 1);

         } else if (Respuesta_1.isSelected() && res1) {
             Respuesta_3.setStyle("-fx-text-fill: green");
             aciertos.setValue(aciertos.get() + 1);
         } else if (Respuesta_3.isSelected() && res2) {
             Respuesta_3.setStyle("-fx-text-fill: green");
             aciertos.setValue(aciertos.get() + 1);
         } else if (Respuesta_3.isSelected() && res3) {
             Respuesta_3.setStyle("-fx-text-fill: green");
             aciertos.setValue(aciertos.get() + 1);
         }
         comprobarButton.setDisable(true);
         System.out.println("Aciertos: " + aciertos + " | Fallos: " + fallos);
        
    }
    
    
    void setUsuario(User user){
        usuario = user;
    }
    
    void setResultados(int a, int f){
        acierto = a;
        fallo = f;
    }
    
    void setRandomness(boolean rdm) {
        random = rdm;
    }

   
}
