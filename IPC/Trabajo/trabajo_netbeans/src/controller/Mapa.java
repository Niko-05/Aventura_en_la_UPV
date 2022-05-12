/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class Mapa implements Initializable {

    @FXML
    private Slider zoom_slider;
    @FXML
    private ImageView cartaNautica;
    private double mousePosX;
    private double mousePosY;
    private Group zoomGroup;
    @FXML
    private ScrollPane map_scrollpane;
    private boolean crearLinea;
    private Line linePainting;
    @FXML
    private HBox botonesBox;
    @FXML
    private VBox ventanaPrincipal;
    @FXML
    private HBox hboxClear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ventanaPrincipal.widthProperty().addListener((obs, oldV, newV) -> {
            botonesBox.setSpacing((double) newV / 25);
            hboxClear.setPrefWidth((double) newV);
        });
        
        zoom_slider.setMin(0.1);
        zoom_slider.setMax(1.3);
        zoom_slider.setValue(0.15);
        
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        zoom(0.15);
        
//        botonesBox.getScene().widthProperty().addListener((obs, oldV, newV) -> {
//            botonesRegion.setLayoutX(newV.doubleValue());
//        });
//        botonesRegion.widthProperty().
//        botonesBox.widthProperty().bind
        
    }    

    @FXML
    private void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal -= 0.05);
    }

    @FXML
    private void zoomIn(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.05);
    }


    @FXML
    private void punteroAction(ActionEvent event) {
    }

    @FXML
    private void lineaAction(ActionEvent event) {
//        if (!crearLinea) {
            crearLinea = true;
            map_scrollpane.setPannable(false);
//        } else {crearLinea = false;}
    }

    @FXML
    private void arcoAction(ActionEvent event) {
    }

    @FXML
    private void escribirAction(ActionEvent event) {
    }

    @FXML
    private void colorAction(ActionEvent event) {
    }

    @FXML
    private void reglaAction(ActionEvent event) {
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
    }

    @FXML
    private void limpiarAction(ActionEvent event) {
    }

    @FXML
    private void logInAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicial.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            double prevWidth = stage.getWidth();
            double prevHeight = stage.getHeight();
            stage.setHeight(prevHeight);
            stage.setWidth(prevWidth);
            stage.setTitle("LogIn");
            stage.setScene(scene);
    }

    @FXML
    private void getMousePosition(MouseEvent event) {
        mousePosX = event.getX();
        mousePosY = event.getY();
    }
    
     private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }


    @FXML
    private void mouseDragged(MouseEvent event) {
        if (crearLinea) {
            linePainting.setEndX(event.getX());
            linePainting.setEndY(event.getY());
        event.consume();
        }
    }

    @FXML
    private void mousePressed(MouseEvent event) {
        if (crearLinea) {
            linePainting = new Line(event.getX(), event.getY(), event.getX(), event.getY());
            zoomGroup.getChildren().add(linePainting);
            linePainting.setStrokeWidth(4);
        }

    }

    @FXML
    private void mouseReleased(MouseEvent event) {
    if(crearLinea){
        crearLinea = false;
        map_scrollpane.setPannable(true);
    }
    
    }

    @FXML
    private void contextMenuRequested(ContextMenuEvent event) {
        System.out.println(event.getX() + " - " + event.getY());
        linePainting.setOnContextMenuRequested(e -> {
            ContextMenu menuContext = new ContextMenu();
            MenuItem borrarItem = new MenuItem("eliminar");
            menuContext.getItems().add(borrarItem);
            borrarItem.setOnAction(ev -> {
                zoomGroup.getChildren().remove((Node)e.getSource());
                ev.consume();
            });
            menuContext.show(linePainting, e.getSceneX(), e.getSceneY());
//            menuContext.sho
            e.consume();
        });
    }
    
}
