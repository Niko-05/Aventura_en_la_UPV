/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class ModPerfil implements Initializable {
    
    private User usuario;
    private int fallos;
    private int aciertos;
    private FileChooser selector = new FileChooser();
    private Tooltip t;
    private String passCode = "*******************";
    private static final String ERRCOINCIDENCIA = "Las contraseñas no coinciden";
    private static final String ERRACTUAL = "La contraseña actual no coincide";
    private static final String ERRNUEVA = "Contraseña no valida";
    
    @FXML
    private ImageView avatarField;
    @FXML
    private Label usuarioLab;
    @FXML
    private Label errNomLab;
    @FXML
    private Label correoLab;
    @FXML
    private Label errCorreoLab;
    @FXML
    private Label contraseñaLab;
    @FXML
    private Label errContraseñaLab;
    @FXML
    private Label nacimientoLab;
    private Stage stageActual;
    private MapaLoged controllerLoged;
    @FXML
    private ImageView correoCondiciones;
    @FXML
    private ImageView contraseñaCondiciones;
    @FXML
    private ImageView nacimientoCondiciones;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        t = new Tooltip("El correo electrónico requiere un formato válido (correo@example.com)");
        Tooltip.install(correoCondiciones, t);
        
        
        t = new Tooltip("Se requieren entre 8 y 20 caracteres, al menos una letra en mayúsculas y una minúscula, algún dígito y algún carácter especial (!@#$%&*()-+=)");
        Tooltip.install(contraseñaCondiciones, t);
        
        t = new Tooltip("El usuario tiene que ser mayor de 16 años");
        Tooltip.install(nacimientoCondiciones, t);
        
//        try {
//            usuario = Navegacion.getSingletonNavegacion().loginUser("prueba", "123456aA!");
//        } catch (NavegacionDAOException ex) {
//            Logger.getLogger(ModPerfil.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        errContraseñaLab.setVisible(false);
        errNomLab.setVisible(false);
        errCorreoLab.setVisible(false);
        
        
        // TODO
    }    

    @FXML
    private void buttonBack(ActionEvent event) throws IOException {
        ((Stage) errContraseñaLab.getScene().getWindow()).close();
        controllerLoged.closeProblemas();
        controllerLoged.setUsuario(usuario);
    }
    
    
    @FXML
    private void avatarDefaultAction(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/default.png"));
        avatarField.setImage(new Image("/icons/default.png"));
        controllerLoged.setUsuario(usuario);
    }

    @FXML
    private void avatar1Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar1.png"));
        avatarField.setImage(new Image("/icons/avatar1.png"));
        controllerLoged.setUsuario(usuario);
    }

    @FXML
    private void avatar2Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar2.png"));
        avatarField.setImage(new Image("/icons/avatar2.png"));
        controllerLoged.setUsuario(usuario);
    }

    @FXML
    private void avatar3Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar3.png"));
        avatarField.setImage(new Image("/icons/avatar3.png"));
        controllerLoged.setUsuario(usuario);
    }

    @FXML
    private void avatar4Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar4.png"));
        avatarField.setImage(new Image("/icons/avatar4.png"));
        controllerLoged.setUsuario(usuario);
    }

    @FXML
    private void avatarImportAction(ActionEvent event) throws NavegacionDAOException {
        selector.setTitle("Avatar Selector");
        selector.setInitialDirectory(new File(System.getProperty("user.home")));
        selector.getExtensionFilters().clear();
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes ", "*.png","*.jpg","*.gif"));
        File file = selector.showOpenDialog(null);
        if(file != null){
            avatarField.setImage(new Image(file.toURI().toString()));
            avatarField.resize(50, 50);
            usuario.setAvatar(new Image(file.toURI().toString()));
            controllerLoged.setUsuario(usuario);
        }
        
    }

    @FXML
    private void modCorreoAction(ActionEvent event) throws NavegacionDAOException {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Modifica");
        textInput.setHeaderText("Introduce el nuevo correo");
//        textInput.getDialogPane().setContentText("Introduce el nuevo correo");
        textInput.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<String> result = textInput.showAndWait();
        TextField input = textInput.getEditor();

        if (result.isPresent() && input.getText().trim().length() > 0) {

            
            if (User.checkEmail(input.getText())) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.initStyle(StageStyle.UTILITY);
                alerta.setTitle("Confirmacion");
                alerta.setHeaderText("¿Quiere borrar todos los elementos del mapa?");
                alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
                Optional<ButtonType> result2 = alerta.showAndWait();
                if (result2.isPresent() && result2.get() == ButtonType.OK) {
                    usuario.setEmail(input.getText());
                    correoLab.setText(input.getText());
                }
            } else {
                errCorreoLab.setVisible(true);
            }

        }
    }

    @FXML
    private void modContraseñaAction(ActionEvent event) throws NavegacionDAOException {
        
        errContraseñaLab.setVisible(false);
        
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Contraseñas");
        dialog.setHeaderText("Introduce las contraseñas");

        Label label1 = new Label("Contraseña actual: ");
        Label label2 = new Label("Contraseña nueva: ");
        Label label3 = new Label("Repita contraseña: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(text3, 2, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {
                if (b == buttonTypeOk) {
                    if (usuario.getPassword().equals(text1.getText())) {
                        if (text2.getText().equals(text3.getText())) {
                            return text2.getText();
                        } else {
                            errContraseñaLab.setText(ERRCOINCIDENCIA);
                            errContraseñaLab.setVisible(true);
                        }
                    } else {
                        errContraseñaLab.setText(ERRACTUAL);
                        errContraseñaLab.setVisible(true);
                    }
                    return null;
                }
                return null;
            }
        });

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.initStyle(StageStyle.UTILITY);
            alerta.setTitle("Confirmacion");
            alerta.setHeaderText("¿Quiere borrar todos los elementos del mapa?");
            alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
            Optional<ButtonType> result2 = alerta.showAndWait();
            if (result2.isPresent() && result2.get() == ButtonType.OK) {
                if (User.checkPassword(result.get())) {
                    contraseñaLab.setText(result.get());
                    usuario.setPassword(result.get());
                } else {
                    errContraseñaLab.setText(ERRNUEVA);
                    errContraseñaLab.setVisible(true);
                }

            }

        }
        
        
        
    }

    @FXML
    private void modFechaAction(ActionEvent event) throws NavegacionDAOException {

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Cumpleaños");
        dialog.setHeaderText("Elije tu fecha de nacimiento");


        DatePicker pickerDate = new DatePicker();
        pickerDate.setEditable(false);
        
        pickerDate.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now().minusYears(16);
                    setDisable(empty || date.compareTo(today) > 0);
                }
            };
        });

        VBox grid = new VBox();

        grid.getChildren().add(pickerDate);
        grid.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        

        dialog.setResultConverter(new Callback<ButtonType, LocalDate>() {
            @Override
            public LocalDate call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return pickerDate.getValue();
                }
                return null;
            }
        });

        Optional<LocalDate> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.initStyle(StageStyle.UTILITY);
            alerta.setTitle("Confirmacion");
            alerta.setHeaderText("¿Quiere borrar todos los elementos del mapa?");
            alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
            Optional<ButtonType> result2 = alerta.showAndWait();
            if (result2.isPresent() && result2.get() == ButtonType.OK) {
                DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                nacimientoLab.setText(result.get().format(formatters));
                usuario.setBirthdate(result.get());
            }

        }
    }

     void setUsuario(User user){
        usuario = user;
        
        usuarioLab.setText(usuario.getNickName());
        avatarField.setImage(usuario.getAvatar());
        contraseñaLab.setText(passCode.substring(20 - usuario.getPassword().length()));
        correoLab.setText(usuario.getEmail());
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        nacimientoLab.setText(usuario.getBirthdate().format(formatters));
    }
     public void setAvatar(Image avatar) {
        avatarField.setImage(avatar);
    }

    void setResultados(int a, int f) {
        aciertos = a;
        fallos = f;
    }
    
    void setStage(Stage aux){
        stageActual = aux;
        stageActual.setOnCloseRequest(e -> {
            controllerLoged.closeProblemas();
            controllerLoged.setUsuario(usuario);
        });
    }
    
    void setController(MapaLoged contr) {
        controllerLoged = contr;
    }
}
