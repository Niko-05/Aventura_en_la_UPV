/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import static java.lang.Boolean.FALSE;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.control.Tooltip;
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
import javafx.stage.StageStyle;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * 22/05/2022
 * @author:
 * Marcial Carreras Arencibia
 * Nicolas montoliu zarza
 * Vicente Morell Amat
 * 
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
    private ContextMenu menuContext = new ContextMenu();
    private Tooltip t;

    @FXML
    private ImageView transportador;
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
    @FXML
    private ChoiceBox<Integer> grosorChoice;
    @FXML
    private HBox boxArriba;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
        
        grosorChoice.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        grosorChoice.getSelectionModel().select(9);
        
//        ((Stage)grosorChoice.getScene().getWindow()).setOnCloseRequest(e -> {
//            System.out.println("se cierra");
//            if (stageOpen.get()) {
//                stage.close();
//            }
//            if (aciertos > 0 || fallos > 0) {
//                System.out.println("");
//                try {
//                    usuario.addSession(new Session(LocalDateTime.now(), aciertos, fallos));
//                } catch (NavegacionDAOException ex) {
//                }
//            }
//        });
        
        
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
        
//        stageActual.setOnCloseRequest(e -> {
//            stage.close();
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

        
            
//        stageActual.widthProperty().
        ventanaPrincipal.widthProperty().addListener((obs, oldV, newV) -> {
            botonesBox.setSpacing((double) newV / 25);
            hboxClear.setPrefWidth((double) newV);
            hboxabajo3.setPrefWidth((double) newV / 3);
            hboxabajo3.setSpacing((double) newV / 25);
            map_scrollpane.setPrefWidth((double) newV);
        });
        hboxClear.setPrefHeight(10);
        boxArriba.setPrefHeight(10);
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


    }

    @FXML
    private void limpiarAction(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("¿Quiere borrar todos los elementos del mapa?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result = alerta.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Node aux = cartaNautica;
            while (zoomGroup.getChildren().size() > 0) {
                zoomGroup.getChildren().remove(0);
            }
            transportador.setTranslateX(500);
            transportador.setTranslateY(200);
            zoomGroup.getChildren().add(aux);
            zoom(0.15);
        }   
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
        stage.setResizable(false);
        controladorModPerfil.setUsuario(usuario);
        controladorModPerfil.setResultados(aciertos, fallos);
        controladorModPerfil.setController(this);
        controladorModPerfil.setStage(stage);
        stage.showAndWait();

    }

    @FXML
    private void cerrarSesionAction(ActionEvent event) throws IOException, NavegacionDAOException {

        if (stageOpen.get()) {
            stage.close();
        }

        if (aciertos > 0 || fallos > 0) {
            usuario.addSession(new Session(LocalDateTime.now(), aciertos, fallos));
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
        controladorTest.setStageMapa(stageActual);
        stage.show();
        stageOpen.set(true);

    }

    @FXML
    private void probAleatorioAction(ActionEvent event) throws IOException {
        if (stageOpen.get()) {
            stage.close();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Responder.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Resolucion de problemas");

        Responder controladorTest = loader.getController();

        stage.setScene(scene);
        controladorTest.setUsuario(usuario);
        controladorTest.setResultados(aciertos, fallos);
        controladorTest.setRandomnes(true);
        controladorTest.setController(this);
        controladorTest.setStage(stageActual);
        controladorTest.setStageMapa(stageActual);
        stage.setResizable(false);
        stage.show();
        stageOpen.set(true);

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
        stage.setTitle("Resultados");

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
        if (menuContext.isShowing()) {
            menuContext.hide();
        }
        if (eliminarButton.isSelected() && !(event.getSource() instanceof ImageView)) {
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
//        menuContext.autoHideProperty().set(true);
        menuContext.setHideOnEscape(true);
        menuContext.setAutoHide(true);
        menuContext.focusedProperty().addListener((obs, oldV, newV) -> {
             menuContext.hide();
        });

        e.consume();
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
    
    void setStageProblemas(Stage str) {
        stage = str;

    }

    @FXML
    private void verProblemasAction(ActionEvent event) {
        stage.requestFocus();
    }

    void setStage(Stage aux) {
        stageActual = aux;
        
        stageActual.setOnCloseRequest(e -> {
            if (stageOpen.get()) {
                stage.close();
            }
            if (aciertos > 0 || fallos > 0) {
                try {
                    usuario.addSession(new Session(LocalDateTime.now(), aciertos, fallos));
                } catch (NavegacionDAOException ex) {
                }
            }
        });
    }

    void closeProblemas(){
        stageOpen.set(false);
    }
    
    private void cambiarColor(Object e) {
        if (e instanceof Circle) {
            circlePainting.setFill(Color.TRANSPARENT);
            if (((Circle) e).getFill().equals(Color.TRANSPARENT)) {
                ((Circle) e).setStroke(pickerColor.getValue());
                colorButton.setSelected(false);

            } else {
                ((Circle) e).setFill(pickerColor.getValue());
                colorButton.setSelected(false);
            }
            
        }
        
        if (e instanceof Line) {
            ((Circle) e).setStroke(pickerColor.getValue());
            colorButton.setSelected(false);
        }
        
        if (e instanceof Text) {
            System.out.println(pickerColor.getValue().toString().substring(0).toUpperCase());
            ((Text) e).setStyle("-fx-text-fill: " + pickerColor.getValue().toString().substring(2,8) + ";");
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
    
    }
    
    private void crearPuntero(MouseEvent event){
        circlePainting = new Circle(1);
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
            zoomGroup.getChildren().add(circlePainting);
            circlePainting.setFill(pickerColor.getValue());
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
            circlePainting.setCenterX(event.getX());
            circlePainting.setCenterY(event.getY());
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

        linePainting = new Line(event.getX(), event.getY(), event.getX(), event.getY());
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
        texto.setLayoutX(event.getX());
        texto.setLayoutY(event.getY());
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
