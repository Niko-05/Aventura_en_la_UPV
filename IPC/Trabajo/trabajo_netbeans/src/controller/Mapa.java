/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.security.auth.callback.TextOutputCallback;
import model.User;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class Mapa implements Initializable {
    
    private boolean crearLinea;
    private boolean crearPuntero;
    private boolean crearArco;
    private boolean crearTexto;

    private double mousePosX;
    private double mousePosY;

    private Line linePainting = new Line();
    private Circle circlePainting;
    private double inicioXArc;

    private Group zoomGroup;
    
//    private ImageView transportador = new ImageView(new Image("/icons/transportador.png"));
   
    
    

    @FXML
    private Slider zoom_slider;
    @FXML
    private ImageView cartaNautica;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private HBox botonesBox;
    @FXML
    private VBox ventanaPrincipal;
    @FXML
    private HBox hboxClear;
    @FXML
    private HBox hboxabajo3;
    @FXML
    private ToggleButton punteroButton;
    @FXML
    private ToggleButton lineaButton;
    @FXML
    private ToggleButton arcoButton;
    @FXML
    private ToggleButton escribirButton;
    @FXML
    private ToggleButton reglaButton;
    @FXML
    private ToggleButton colorButton;
    @FXML
    private ToggleButton eliminarButton;
    @FXML
    private ColorPicker pickerColor;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView transportador;
    private ContextMenu menuContext = new ContextMenu();
    private Tooltip t;
    @FXML
    private ChoiceBox<Integer> grosorChoice;

    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        grosorChoice.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        grosorChoice.getSelectionModel().select(9);
        
        t = new Tooltip("Dibujar un punto");
        Tooltip.install(punteroButton, t);
        
        t = new Tooltip("Dibujar una linea");
        Tooltip.install(lineaButton, t);
        
        t = new Tooltip("Dibujar un arco");
        Tooltip.install(arcoButton, t);
        
        t = new Tooltip("Escribir un texto");
        Tooltip.install(escribirButton, t);
        
        t = new Tooltip("Mostar / Ocultar regla");
        Tooltip.install(reglaButton, t);
        
        t = new Tooltip("Cambiar color");
        Tooltip.install(colorButton, t);
        
        t = new Tooltip("Elimitar objeto");
        Tooltip.install(eliminarButton, t);
        
        t = new Tooltip("Elegir color");
        Tooltip.install(pickerColor, t);
        
        t = new Tooltip("Elegir grosor");
        Tooltip.install(grosorChoice, t);
        
        ToggleGroup tGroup = new ToggleGroup();
        punteroButton.setToggleGroup(tGroup);
        lineaButton.setToggleGroup(tGroup);
        arcoButton.setToggleGroup(tGroup);
        escribirButton.setToggleGroup(tGroup);
        eliminarButton.setToggleGroup(tGroup);
        colorButton.setToggleGroup(tGroup);
        
        
        
        ventanaPrincipal.widthProperty().addListener((obs, oldV, newV) -> {
            botonesBox.setSpacing((double) newV / 25);
            hboxClear.setPrefWidth((double) newV);
            hboxabajo3.setPrefWidth((double) newV / 3);
            hboxabajo3.setSpacing((double) newV / 25);
            map_scrollpane.setPrefWidth((double) newV);
        });
        
        ventanaPrincipal.heightProperty().addListener((obs, oldV, newV) -> {
            map_scrollpane.setPrefHeight((double) newV);
        });
        
//        ventanaPrincipal.setMinHeight(600);
        
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
        
//        stackPane.getChildren().add(transportador);
        transportador.setVisible(false);
        transportador.setX(1500);
        transportador.setY(1500);
        transportador.setOpacity(0.6);
        
   
        transportador.setOnMousePressed(e -> {
                eliminarButton.setSelected(false);
                colorButton.setSelected(false);
                transportador.setTranslateX(e.getSceneX() - 200);
                transportador.setTranslateY(e.getSceneY() - 260);
        });

        transportador.setOnMouseDragged(e -> {
                transportador.setTranslateX(e.getSceneX() - 200);
                transportador.setTranslateY(e.getSceneY() - 260);
        }); 

    }    
    
    
    @FXML
    private void logInAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicial.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(300);
        stage.initModality(Modality.APPLICATION_MODAL);
        Inicial controladorPrin = loader.getController();
        controladorPrin.setController((Stage)lineaButton.getScene().getWindow());
        stage.show();
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
        
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        
        if (!crearPuntero) {
            crearPuntero = true;
            map_scrollpane.setPannable(false);
        } else {
            crearPuntero = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
        
    }

    @FXML
    private void lineaAction(ActionEvent event) {
        
        crearPuntero = false;
        crearArco = false;
        crearTexto = false;
    
        if (!crearLinea) {
            crearLinea = true;
            map_scrollpane.setPannable(false);
        } else {
            crearLinea = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
    }
    
    @FXML
    private void arcoButton(ActionEvent event) {

        crearPuntero = false;
        crearLinea = false;
        crearTexto = false;
        
        if (!crearArco) {
            crearArco = true;
            map_scrollpane.setPannable(false);
        } else {
            crearArco = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
        
    }


    @FXML
    private void escribirAction(ActionEvent event) {
        
        crearPuntero = false;
        crearLinea = false;
        crearArco = false;
        
        if (!crearTexto) {
            crearTexto = true;
            map_scrollpane.setPannable(false);
        } else {
            crearTexto = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
        
    }
    
    @FXML
    private void reglaAction(ActionEvent event) {
        if(reglaButton.isSelected()){
            
            transportador.setVisible(true);
        } else {transportador.setVisible(false);}
    }

    @FXML
    private void colorAction(ActionEvent event) {

        
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        crearPuntero = false;
        
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        crearPuntero = false;
        
    }

    @FXML
    private void limpiarAction(ActionEvent event) {
        
//        if(!User.checkNickName("123456aA*")){
//            Notifications notificationBuilder = Notifications.create()
//                    .title("Confirmación")
//                    .text("Se ha registrado el usuario correctamente")
//                    .graphic(null)
//                    .hideAfter(Duration.seconds(5))
//                    .position(Pos.BOTTOM_RIGHT);
//
//            Platform.runLater(() -> {
//
//                notificationBuilder.showInformation();
//            });
//        
//        }
            

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("¿Quiere borrar todos los elementos del mapa?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result = alerta.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
//            Node aux = map_scrollpane.getContent();
            while(zoomGroup.getChildren().size() > 1) { zoomGroup.getChildren().remove(1); }
            
//            zoomGroup.getChildren().clear();
//            zoomGroup.getChildren().add(aux);
            zoom(0.15);
        }   
    }

    

    @FXML
    private void getMousePosition(MouseEvent event) {
        mousePosX = event.getScreenX();
        mousePosY = event.getScreenY();
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
    private void mousePressed(MouseEvent event) {
        
        if (menuContext.isShowing()) {
            menuContext.hide();
        }
        
        if (eliminarButton.isSelected()  && !(event.getSource() instanceof ImageView)) {
            zoomGroup.getChildren().remove((Node) event.getSource());
            eliminarButton.setSelected(false);
            event.consume();
        }
        
        if (colorButton.isSelected() && !(event.getSource() instanceof ImageView)) {
            cambiarColor(event.getSource());
            event.consume();
        }

        if (crearPuntero) crearPuntero(event);
        if (crearLinea) crearLinea(event);
        if (crearArco) crearArco(event);
        if (crearTexto) crearTexto(event);
    }


    @FXML
    private void mouseDragged(MouseEvent event) {
        if (crearLinea) {
            linePainting.setEndX(event.getX()-80);
            linePainting.setEndY(event.getY()-45);
        event.consume();
        }
        
        if (crearArco){
            double radio = Math.abs(event.getX() - inicioXArc);
            circlePainting.setRadius(radio);
            event.consume(); 
        }
    }

    @FXML
    private void mouseReleased(MouseEvent event) {
        if (crearLinea) {
            crearLinea = false;
            map_scrollpane.setPannable(true);
            lineaButton.setSelected(false);
        }
        
        if (crearArco) {
            crearArco = false;
            map_scrollpane.setPannable(true);
            arcoButton.setSelected(false);
        }
        
        if(crearTexto){
            crearTexto = false;
            escribirButton.setSelected(false);
        }

        punteroButton.setSelected(false);
        arcoButton.setSelected(false);
        escribirButton.setSelected(false);
        map_scrollpane.setPannable(true);

    }

    private void contextMenu(ContextMenuEvent e) {
        menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("Eliminar");
        MenuItem cambioColor = new MenuItem("Cambiar color");
        MenuItem cambioTamaño = new MenuItem("Ajustar tamaño");
        menuContext.getItems().add(borrarItem);
        menuContext.getItems().add(cambioColor);
        menuContext.getItems().add(cambioTamaño);
        if (e.getSource() instanceof Circle && !((Circle) e.getSource()).getFill().equals(Color.TRANSPARENT)) {
            MenuItem ejesPunto = new MenuItem("Mostrar ejes");
            menuContext.getItems().add(ejesPunto
            );
            ejesPunto.setOnAction(ev -> {
                linePainting = new Line(((Circle) e.getSource()).getCenterX(), 0, ((Circle) e.getSource()).getCenterX(), 5500);
                zoomGroup.getChildren().add(linePainting);
                linePainting.setStrokeWidth(4);

                linePainting.setOnContextMenuRequested(this::contextMenu);
                linePainting.setOnMousePressed(this::mousePressed);
                linePainting.setOnMouseDragged(this::mouseDragged);
                linePainting.setOnMouseReleased(this::mouseReleased);

                linePainting = new Line(0, ((Circle) e.getSource()).getCenterY(), 8800, ((Circle) e.getSource()).getCenterY());
                zoomGroup.getChildren().add(linePainting);
                
                linePainting.setOnContextMenuRequested(this::contextMenu);
                linePainting.setOnMousePressed(this::mousePressed);
                linePainting.setOnMouseDragged(this::mouseDragged);
                linePainting.setOnMouseReleased(this::mouseReleased);
                
                linePainting.setStrokeWidth(4);
                ev.consume();
            });
        }

        borrarItem.setOnAction(ev -> {
            zoomGroup.getChildren().remove((Node) e.getSource());
            ev.consume();
        });
        cambioColor.setOnAction(ev -> {
            cambiarColor(e.getSource());
        });
        cambioTamaño.setOnAction(ev -> {
            cambiarTamaño(e.getSource());
        });

        menuContext.show(map_scrollpane, e.getScreenX(), e.getScreenY());
//        menuContext.setHideOnEscape(true);
        menuContext.setAutoHide(true);
        menuContext.focusedProperty().addListener((obs, oldV, newV) -> {
             menuContext.hide();
        });

        e.consume();
    }
    
    private void cambiarColor(Object e) {
        if (e instanceof Circle) {
            if (((Circle) e).getFill().equals(Color.TRANSPARENT)) {
                ((Circle) e).setStroke(pickerColor.getValue());
                colorButton.setSelected(false);

            } else {
                ((Circle) e).setFill(pickerColor.getValue());
                colorButton.setSelected(false);
            }
            
        }
        
        if (e instanceof Line) {
            ((Line) e).setStroke(pickerColor.getValue());
            
        }
        
        if (e instanceof Text) {
            ((Text) e).setFill(pickerColor.getValue());
            colorButton.setSelected(false);
        }
    
    }
    
        private void cambiarTamaño(Object e) {
        if (e instanceof Circle) {
            if (((Circle) e).getFill().equals(Color.TRANSPARENT)) {
                ((Circle) e).setStrokeWidth(grosorChoice.getValue());
                colorButton.setSelected(false);

            } else {
                ((Circle) e).setRadius(grosorChoice.getValue() * 2);
                colorButton.setSelected(false);
            }
            
        }
        
        if (e instanceof Line) {
            ((Line) e).setStrokeWidth(grosorChoice.getValue());
            colorButton.setSelected(false);
        }
        
        if (e instanceof Text) {
            ((Text) e).setStyle("-fx-font-size:" + grosorChoice.getValue() * 10 + ";");
            colorButton.setSelected(false);
        }
//        
//        if (e instanceof Text) {
//            System.out.println(toRgbString(pickerColor.getValue()));
//            ((Text) e).setStyle("-fx-text-fill: #" + pickerColor.getValue().toString().substring(2,8) + ";");
//        }
    
    }
    
    private void crearPuntero(MouseEvent event){
        circlePainting = new Circle(1);
            circlePainting.setCenterX(event.getX()-80);
            circlePainting.setCenterY(event.getY()-45);
            circlePainting.setFill(pickerColor.getValue());
            zoomGroup.getChildren().add(circlePainting);
            circlePainting.setRadius(grosorChoice.getValue() * 2);


            circlePainting.setOnContextMenuRequested(this::contextMenu);
            circlePainting.setOnMousePressed(this::mousePressed);
            circlePainting.setOnMouseDragged(this::mouseDragged);
            circlePainting.setOnMouseReleased(this::mouseReleased);
            crearPuntero = false;
    }
    
    private void crearArco(MouseEvent event){
        circlePainting = new Circle(1);
            circlePainting.setStroke(pickerColor.getValue());
            circlePainting.setFill(Color.TRANSPARENT);
            zoomGroup.getChildren().add(circlePainting);
            circlePainting.setCenterX(event.getX()-80);
            circlePainting.setCenterY(event.getY()-45);
            circlePainting.setStrokeWidth(grosorChoice.getValue());
            inicioXArc = event.getX();
            
//            zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
//                circlePainting.setStrokeWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 7);
//            });

            circlePainting.setOnContextMenuRequested(this::contextMenu);
            circlePainting.setOnMousePressed(this::mousePressed);
            circlePainting.setOnMouseDragged(this::mouseDragged);
            circlePainting.setOnMouseReleased(this::mouseReleased);

    }
 
    private void crearLinea(MouseEvent event) {

        linePainting = new Line(event.getX()-80, event.getY()-45, event.getX()-80, event.getY()-45);
        zoomGroup.getChildren().add(linePainting);
        linePainting.setStrokeWidth(grosorChoice.getValue());
        linePainting.setStroke(pickerColor.getValue());

        linePainting.setOnContextMenuRequested(this::contextMenu);
        linePainting.setOnMousePressed(this::mousePressed);
        linePainting.setOnMouseDragged(this::mouseDragged);
        linePainting.setOnMouseReleased(this::mouseReleased);
    }
   
    private void crearTexto(MouseEvent event) {
        TextField texto = new TextField();
        texto.setLayoutX(event.getX()-80);
        texto.setLayoutY(event.getY()-45);
//            texto.requestFocus();
        texto.setPrefWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.1)) * 800);
        texto.setStyle("-fx-font-size:" + grosorChoice.getValue() * 10 + ";");
        zoomGroup.getChildren().add(texto);
        texto.promptTextProperty().set("Rellenar");

        texto.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) {
                zoomGroup.getChildren().remove(texto);
            }

        });

//            texto.requestFocus();
        texto.setOnAction(e -> {
            Text textoT = new Text(texto.getText());
            textoT.setX(texto.getLayoutX());
            textoT.setY(texto.getLayoutY());

            zoomGroup.getChildren().add(textoT);
            textoT.setStyle("-fx-font-size:" + grosorChoice.getValue() * 10 + ";");
            textoT.setFill(pickerColor.getValue());
            zoomGroup.getChildren().remove(texto);

            textoT.setOnContextMenuRequested(this::contextMenu);
            textoT.setOnMousePressed(this::mousePressed);
            textoT.setOnMouseDragged(this::mouseDragged);
            textoT.setOnMouseReleased(this::mouseReleased);
            e.consume();
        });
    }
}
