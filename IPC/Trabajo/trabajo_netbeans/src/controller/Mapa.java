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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.security.auth.callback.TextOutputCallback;
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
    private boolean dibujando;

    private double mousePosX;
    private double mousePosY;

    private Line linePainting = new Line();
    private Circle circlePainting;
    private double inicioXArc;

    private Group zoomGroup;
    
    private ImageView transportador = new ImageView(new Image("/icons/transportador.png"));
   
    
    

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
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        zoomGroup.getChildren().add(transportador);
        zoom(0.15);
        
        
        transportador.setVisible(false);
        transportador.setX(1500);
        transportador.setY(1500);
        transportador.setScaleX((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 2.5);
        transportador.setScaleY((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 2.5);
        transportador.setOpacity(0.6);
        zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
            transportador.setScaleX((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 2.5);
            transportador.setScaleY((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 2.5);
        });
   
        transportador.setOnMousePressed(e -> {
            if (dibujando) {
                mousePressed(e);
            } else {
                eliminarButton.setSelected(false);
                colorButton.setSelected(false);
                transportador.setX(e.getX() - 175);
                transportador.setY(e.getY() - 180);
            }
        });

        transportador.setOnMouseDragged(e -> {
            if (!dibujando) {
                map_scrollpane.setPannable(false);
                transportador.setX(e.getX() - 175);
                transportador.setY(e.getY() - 180);

            } else {
                mouseDragged(e);
                map_scrollpane.setPannable(true);
            }
        });
        
        transportador.setOnMouseReleased(e -> {
            if (!dibujando) {
                map_scrollpane.setPannable(true);
                
            } else {
                mouseReleased(e);
            }
        });

//        cartaNautica.addMouseWhellListener(new MouseWheelListener() {
//
//            public void mouseWheelMoved(MouseWheelEvent e) {
//                int notches = e.getWheelRotation();
//                if (notches < 0) {
//                    double sliderVal = zoom_slider.getValue();
//                    zoom_slider.setValue(sliderVal += 0.05);
//                } else {
//                    double sliderVal = zoom_slider.getValue();
//                    zoom_slider.setValue(sliderVal -= 0.05);
//                }
//
//            }
//        });
        
//        botonesBox.getScene().widthProperty().addListener((obs, oldV, newV) -> {
//            botonesRegion.setLayoutX(newV.doubleValue());
//        });
//        botonesRegion.widthProperty().
//        botonesBox.widthProperty().bind
        



        linePainting.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent ae) {
                System.out.println(".handle()");
            }
        });
        linePainting.setOnContextMenuRequested(e -> {
            ContextMenu menuContext = new ContextMenu();
            MenuItem borrarItem = new MenuItem("eliminar");
            menuContext.getItems().add(borrarItem);
            borrarItem.setOnAction(ev -> {
                zoomGroup.getChildren().remove((Node)e.getSource());
                ev.consume();
            });
            menuContext.show(linePainting, e.getSceneX(), e.getSceneY());
            e.consume();
        });
    }    
    
    
    @FXML
    private void logInAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Inicial.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(300);
    }
    
    @FXML
    private void registrarseAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registrarse.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Pestaña registrarse");
        stage.setScene(scene);
        stage.setHeight(480);
        stage.setWidth(500);
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
        dibujando = true;
        
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        
        if (!crearPuntero) {
            crearPuntero = true;
            map_scrollpane.setPannable(false);
        } else {
            dibujando = false;
            crearPuntero = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
        
    }

    @FXML
    private void lineaAction(ActionEvent event) {
        dibujando = true;
        
        crearPuntero = false;
        crearArco = false;
        crearTexto = false;
    
        if (!crearLinea) {
            crearLinea = true;
            map_scrollpane.setPannable(false);
        } else {
            dibujando = false;
            crearLinea = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
    }
    
    @FXML
    private void arcoButton(ActionEvent event) {
        dibujando = true;
        
        crearPuntero = false;
        crearLinea = false;
        crearTexto = false;
        
        if (!crearArco) {
            crearArco = true;
            map_scrollpane.setPannable(false);
        } else {
            dibujando = false;
            crearArco = false;
            map_scrollpane.setPannable(true);
            map_scrollpane.requestFocus();
        }
        
    }


    @FXML
    private void escribirAction(ActionEvent event) {
        dibujando = true;
        
        crearPuntero = false;
        crearLinea = false;
        crearArco = false;
        
        if (!crearTexto) {
            crearTexto = true;
            map_scrollpane.setPannable(false);
        } else {
            dibujando = false;
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
        
        if(!colorButton.isSelected()) dibujando = false;
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        crearPuntero = false;
        
        if(!eliminarButton.isSelected()) dibujando = false;
    }

    @FXML
    private void limpiarAction(ActionEvent event) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Confirmación")
                    .text("Se ha registrado el usuario correctamente")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);

            Platform.runLater(() -> {

                notificationBuilder.showInformation();
            });

            
            Node aux = cartaNautica;
            while(zoomGroup.getChildren().size() > 0){ zoomGroup.getChildren().remove(0); }
            zoomGroup.getChildren().add(aux);
            zoomGroup.getChildren().add(transportador);
            zoom(0.15);
        
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
        if(crearPuntero) {
        
            circlePainting = new Circle(1);
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
            zoomGroup.getChildren().add(circlePainting);
            circlePainting.setStrokeWidth(4);
            circlePainting.setRadius((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 35);

//            zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
//                circlePainting.setRadius((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 35);
//            });

            circlePainting.setOnContextMenuRequested(e -> contextMenu(e,"punto"));
            circlePainting.setOnMousePressed(e -> {
                clicked(e);
                if (colorButton.isSelected()) {
                    cambiarColor("punto");
                }
            });
            circlePainting.setOnMouseDragged(e -> mouseDragged(e));
            circlePainting.setOnMouseReleased(e -> mouseReleased(e));
            crearPuntero = false;
            
            zoomGroup.getChildren().remove(transportador);
            zoomGroup.getChildren().add(transportador);
        }
        
        if (crearLinea) {
            linePainting = new Line(event.getX(), event.getY(), event.getX(), event.getY());
            zoomGroup.getChildren().add(linePainting);
            linePainting.setStrokeWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 7);
            
//            zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
//                linePainting.setStrokeWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 7);
//            });
          
            linePainting.setOnContextMenuRequested(e -> contextMenu(e,"linea"));
            linePainting.setOnMousePressed(e -> {
                clicked(e);
                if (colorButton.isSelected()) {
                    cambiarColor("linea");
                }
            });
            linePainting.setOnMouseDragged(e -> mouseDragged(e));
            linePainting.setOnMouseReleased(e -> mouseReleased(e));
            
            zoomGroup.getChildren().remove(transportador);
            zoomGroup.getChildren().add(transportador);
        }
        
        if (crearArco) {
            circlePainting = new Circle(1);
            circlePainting.setStroke(Color.RED);
            circlePainting.setFill(Color.TRANSPARENT);
            zoomGroup.getChildren().add(circlePainting);
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
            circlePainting.setStrokeWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 7);
            inicioXArc = event.getX();
            
//            zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
//                circlePainting.setStrokeWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 7);
//            });

            circlePainting.setOnContextMenuRequested(e -> contextMenu(e,"circle"));
            circlePainting.setOnMousePressed(e -> {
                clicked(e);
                if (colorButton.isSelected()) {
                    cambiarColor("circle");
                }
            });
            circlePainting.setOnMouseDragged(e -> mouseDragged(e));
            circlePainting.setOnMouseReleased(e -> mouseReleased(e));

            
            zoomGroup.getChildren().remove(transportador);
            zoomGroup.getChildren().add(transportador);
        }
        
        if (crearTexto){
            TextField texto = new TextField();
            texto.setLayoutX(event.getX());
            texto.setLayoutY(event.getY());
//            texto.requestFocus();
            texto.setPrefWidth((zoom_slider.getMax() - (zoom_slider.getValue() - 0.1)) * 800);
            texto.setStyle("-fx-font-size:" + (zoom_slider.getMax() - (zoom_slider.getValue() - 0.1)) * 70 + ";");
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
                textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: " + (zoom_slider.getMax() -(zoom_slider.getValue() -0.1)) * 80 + ";");
                zoom_slider.valueProperty().addListener((obs,oldV,newV) -> {
                    textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: " + (zoom_slider.getMax() -(zoom_slider.getValue() -0.1)) * 80 + ";");
                });
                zoomGroup.getChildren().remove(texto);
                
//                textoT.setOnContextMenuRequested(e -> {});
//                textoT.setOnMousePressed(e -> clicked(e));
//                textoT.setOnMouseDragged(e -> mouseDragged(e));
//                textoT.setOnMouseReleased(e -> mouseReleased(e));
                
                e.consume();
            });
            
            zoomGroup.getChildren().remove(transportador);
            zoomGroup.getChildren().add(transportador);
                
        }

    }


    @FXML
    private void mouseDragged(MouseEvent event) {
        if (crearLinea) {
            linePainting.setEndX(event.getX());
            linePainting.setEndY(event.getY());
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
        dibujando = false;

    }

    private void contextMenu(ContextMenuEvent e, String item) {
        ContextMenu menuContext = new ContextMenu();
        MenuItem borrarItem = new MenuItem("Eliminar");
        MenuItem cambioColor = new MenuItem("Cambiar color");
        menuContext.getItems().add(borrarItem);
        menuContext.getItems().add(cambioColor);
        if ("punto".equals(item)) {
            MenuItem ejesPunto = new MenuItem("Mostrar ejes");
            menuContext.getItems().add(ejesPunto
            );
            ejesPunto.setOnAction(ev -> {
            linePainting = new Line(((Circle)e.getSource()).getCenterX(), 0, e.getX(), 5500);
            zoomGroup.getChildren().add(linePainting);
            linePainting.setStrokeWidth(4);
            
//            linePainting.setOnContextMenuRequested(e -> contextMenu(e,"linea"));
//            linePainting.setOnMousePressed(e -> {
//                clicked(e);
//                if (colorButton.isSelected()) {
//                    cambiarColor("linea");
//                }
//            });
//            linePainting.setOnMouseDragged(e -> mouseDragged(e));
//            linePainting.setOnMouseReleased(e -> mouseReleased(e));
            
            linePainting = new Line(0, e.getY(), 8800, e.getY());
            zoomGroup.getChildren().add(linePainting);
            linePainting.setStrokeWidth(4);
            ev.consume();
        });
        }

        borrarItem.setOnAction(ev -> {
            zoomGroup.getChildren().remove((Node) e.getSource());
            ev.consume();
        });
        cambioColor.setOnAction(ev -> {
            cambiarColor(item);
        });

        menuContext.show(map_scrollpane, e.getScreenX(), e.getScreenY());
//        menuContext.autoHideProperty().set(true);
        menuContext.setHideOnEscape(true);
        menuContext.setAutoHide(true);
        menuContext.focusedProperty().addListener((obs, oldV, newV) -> {
             menuContext.hide();
        });

        e.consume();
    }

    private void clicked(MouseEvent e) {
        if (eliminarButton.isSelected()) {
            zoomGroup.getChildren().remove((Node) e.getSource());
            e.consume();
        }
        
        mousePressed(e);
    }
    
    private void cambiarColor(String item) {
        switch (item) {
            case "circle":
                circlePainting.setStroke(pickerColor.getValue());
                colorButton.setSelected(false);
                break;
            case "linea":
                linePainting.setStroke(pickerColor.getValue());
                colorButton.setSelected(false);
                break;
            case "punto":
                circlePainting.setFill(pickerColor.getValue());
                colorButton.setSelected(false);
                break;
            case "texto":
                break;
        }

    }
   
}
