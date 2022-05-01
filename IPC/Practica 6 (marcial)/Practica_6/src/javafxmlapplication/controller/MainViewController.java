/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication.controller;

import static java.lang.Thread.sleep;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author usuario
 */
public class MainViewController implements Initializable {

    @FXML
    private Label labelReloj;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TaskReloj taskReloj = new TaskReloj();
        labelReloj.textProperty().bind(taskReloj.messageProperty());
        Thread thread = new Thread(taskReloj);
        thread.setDaemon(true);
        thread.start();
        
        labelReloj.textProperty().addListener((obs, preV, newV) -> {
        
        });
        
        
        // TODO
    }    

    class TaskReloj extends Task<Void>{

        @Override
        protected Void call() throws Exception {
            while(true){
//                updateMessage(LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
                Platform.runLater(()->{labelReloj.setText(LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));});
                Thread.sleep(1000);
                if(isCancelled())break;
            }
            return null;
        }
    }
    
}
