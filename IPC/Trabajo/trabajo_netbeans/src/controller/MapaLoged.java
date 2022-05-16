/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import static java.lang.Boolean.FALSE;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import static javafx.beans.binding.Bindings.not;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class MapaLoged implements Initializable {

    private int aciertos;
    private int fallos;
    private User usuario;
    private Stage stage;
    private Stage stageActual;
    private BooleanProperty stageOpen = new SimpleBooleanProperty();
    private MapaLoged controllerLoged;

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
    @FXML
    private ImageView imgUsuario;
    @FXML
    private MenuButton usuarioButton;
    @FXML
    private Label aciertosLab;
    @FXML
    private Label fallosLab;
    @FXML
    private Button verProblemasButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        stageActual.setOnCloseRequest(e -> {
//            System.out.println("hola");
//        });
        
        
        stageOpen.setValue(Boolean.FALSE);
        verProblemasButton.disableProperty().bind(not(stageOpen));

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
                zoomGroup.getChildren().remove((Node) e.getSource());
                ev.consume();
            });
            menuContext.show(linePainting, e.getSceneX(), e.getSceneY());
            e.consume();
        });
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
    private void colorAction(ActionEvent event) {
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        crearPuntero = false;

        if (!colorButton.isSelected()) {
            dibujando = false;
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
    private void reglaAction(ActionEvent event) {
        if (reglaButton.isSelected()) {

            transportador.setVisible(true);
        } else {
            transportador.setVisible(false);
        }
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        crearLinea = false;
        crearArco = false;
        crearTexto = false;
        crearPuntero = false;

        if (!eliminarButton.isSelected()) {
            dibujando = false;
        }
    }

    @FXML
    private void limpiarAction(ActionEvent event) {
        Node aux = cartaNautica;
        while (zoomGroup.getChildren().size() > 0) {
            zoomGroup.getChildren().remove(0);
        }
        zoomGroup.getChildren().add(aux);
        zoomGroup.getChildren().add(transportador);
        zoom(0.15);

    }

    @FXML
    private void modPerfilAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModificarPerfil.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Modificar Perfil");
        stage.initModality(Modality.APPLICATION_MODAL);
        ModPerfil controladorModPerfil = loader.getController();

        stage.setScene(scene);
        controladorModPerfil.setUsuario(usuario);
        controladorModPerfil.setResultados(aciertos, fallos);
        controladorModPerfil.setController(this);
        controladorModPerfil.setStage(stage);
        stage.showAndWait();

    }

    @FXML
    private void cerrarSesionAction(ActionEvent event) throws IOException {

        if (stageOpen.get()) {
            stage.close();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Mapa.fxml"));
        Parent root = loader.load();
        Stage stage = ((Stage) aciertosLab.getScene().getWindow());
        Scene scene = new Scene(root);
        stage.setTitle("Mapa");

        stage.setScene(scene);
    }

    @FXML
    private void probListAction(ActionEvent event) throws IOException {
        if (stageOpen.get()) {
            stage.close();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ElegirProblema.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Elegir problemas");

        ElegirProblema controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setController(this);
        controladorTest.setStage(stage);
        stage.show();
        stageOpen.set(true);

    }

    @FXML
    private void probAleatorioAction(ActionEvent event) throws IOException {
        if (stageOpen.get()) {
            stage.close();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventanaResponder.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Resolucion de problemas");

        VentanaResponder controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setRandomnes(true);
        controladorTest.setController(this);
        controladorTest.setStage(stageActual);
        stage.show();
        stageOpen.set(true);
//        controladorTest.prueba();
//        controladorTest.prueba2;

    }

    @FXML
    private void resultadosAction(ActionEvent event) throws IOException {
        if (stageOpen.get()) {
            stage.close();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Resultados.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Elegir problemas");

        Resultados controladorResultados = loader.getController();
        
        controladorResultados.setUsuario(usuario);
        controladorResultados.setController(this);
        controladorResultados.setStage(stage);

        stage.setScene(scene);
        stage.show();
        stageOpen.set(true);
    }

    @FXML
    private void mousePressed(MouseEvent event) {
        if (crearPuntero) {

            circlePainting = new Circle(1);
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
            zoomGroup.getChildren().add(circlePainting);
            circlePainting.setStrokeWidth(4);
            circlePainting.setRadius((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 35);

//            zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
//                circlePainting.setRadius((zoom_slider.getMax() - (zoom_slider.getValue() - 0.15)) * 35);
//            });
            circlePainting.setOnContextMenuRequested(e -> contextMenu(e, "punto"));
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
            linePainting.setOnContextMenuRequested(e -> contextMenu(e, "linea"));
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
            circlePainting.setOnContextMenuRequested(e -> contextMenu(e, "circle"));
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

        if (crearTexto) {
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
                textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: " + (zoom_slider.getMax() - (zoom_slider.getValue() - 0.1)) * 80 + ";");
                zoom_slider.valueProperty().addListener((obs, oldV, newV) -> {
                    textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: " + (zoom_slider.getMax() - (zoom_slider.getValue() - 0.1)) * 80 + ";");
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

        if (crearArco) {
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

        if (crearTexto) {
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
                linePainting = new Line(e.getX(), 0, e.getX(), 5500);
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

    void setUsuario(User user) {
        usuario = user;
        imgUsuario.setImage(usuario.getAvatar());
        usuarioButton.setText(usuario.getNickName());
    }

    void setResultados(int a, int f) {
        aciertos = a;
        fallos = f;
        aciertosLab.setText(Integer.toString(aciertos));
        fallosLab.setText(Integer.toString(fallos));

    }

    @FXML
    private void verProblemasAction(ActionEvent event) {
        stage.requestFocus();
    }
    
    void setStage(Stage aux){
        stageActual = aux;
        
        if (stageOpen.getValue()) {
            stageActual.setOnCloseRequest(e -> {
                stage.close();
            });
        }
    }

    void closeProblemas(){
        stageOpen.set(false);
    }
}
