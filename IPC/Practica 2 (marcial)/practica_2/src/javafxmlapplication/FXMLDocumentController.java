/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import static javafxmlapplication.Utils.*;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Slider hSlider;
    @FXML
    private Slider vSlider;
    @FXML
    private Rectangle miRectangulo;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //hSlider.valueProperty().addListener((a, b, c) -> {
        //    miRectangulo.setWidth((double) c);
        //});
        //
        //vSlider.valueProperty().addListener((a, b, c) -> {
        //    miRectangulo.setHeight((double) c);
        //});
        
        miRectangulo.heightProperty().bind(vSlider.valueProperty());
        miRectangulo.widthProperty().bind(hSlider.valueProperty());
    }    

    

        // trabajo opcional: completar practica 1 con toolbar (splid pane)
        // boton (selecion "toggle button"): hacer el circulo hueco (straw borde) (fill relleno) 
        // colorpicker: cambiar color circulo
        // slider: 
    
        // imageView para mostrar imagenes en boton
}