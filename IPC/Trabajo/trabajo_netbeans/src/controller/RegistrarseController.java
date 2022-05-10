/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Navegacion;
import model.User;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author marci
 */
public class RegistrarseController implements Initializable {

    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty validname;
    private BooleanProperty validage;

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField correoField;
    @FXML
    private Label errCorreoLab;
    @FXML
    private TextField nombreField;
    @FXML
    private Label errNomLab;
    @FXML
    private TextField contraField;
    @FXML
    private Label errConLab;
    @FXML
    private DatePicker fechaField;
    @FXML
    private Button buttonAvatar;
    @FXML
    private ImageView avatarField;
    @FXML
    private Button buttonAceptar;
    @FXML
    private Label errFechaLab;

    private User usuario;
    private boolean registered;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        avatarField.setPreserveRatio(false);
        avatarField.resize(50, 50);

        // Esconder mensajes de error
        errNomLab.setVisible(false);
        errConLab.setVisible(false);
        errFechaLab.setVisible(false);
        errCorreoLab.setVisible(false);

        // inicializacion propiedades binding
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();
        validname = new SimpleBooleanProperty();

        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        validname.setValue(Boolean.FALSE);

        // Bindings para boton aceptar
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(validname);
        buttonAceptar.disableProperty().bind(Bindings.not(validFields));

        // Control de errores correo
        correoField.focusedProperty().addListener((observable, oldV, newV) -> {
            if (!newV) {
                if (!User.checkEmail(correoField.getText())) {
                    errCorreoLab.setVisible(true);
                    validEmail.setValue(Boolean.FALSE);
                } else {
                    errCorreoLab.setVisible(false);
                    validEmail.setValue(Boolean.TRUE);
                }
            }
        });

        correoField.textProperty().addListener((observable, oldV, newV) -> {
            if (User.checkEmail(newV)) {
                validEmail.setValue(Boolean.TRUE);
                errCorreoLab.setVisible(false);
            } else {
                validEmail.setValue(Boolean.FALSE);
            }
        });

        // Control de errores nombre
        nombreField.focusedProperty().addListener((observable, oldV, newV) -> {
            if (!newV) {
                if (!User.checkNickName(nombreField.getText())) {
                    errNomLab.setVisible(true);
                    validname.setValue(Boolean.FALSE);
                } else {
                    errNomLab.setVisible(false);
                    validname.setValue(Boolean.TRUE);
                }
            }
        });

        nombreField.textProperty().addListener((observable, oldV, newV) -> {
            if (User.checkNickName(newV)) {
                validname.setValue(Boolean.TRUE);
                errNomLab.setVisible(false);
            } else {
                validname.setValue(Boolean.FALSE);
            }
        });

        // Control de errores contraseña
        contraField.focusedProperty().addListener((observable, oldV, newV) -> {
            errConLab.setVisible(false);
            if (!newV) {
                if (!User.checkPassword(contraField.getText())) {
                    errConLab.setVisible(true);
                    validPassword.setValue(Boolean.FALSE);
                } else {
                    errConLab.setVisible(false);
                    validPassword.setValue(Boolean.TRUE);
                }
            }
        });

        contraField.textProperty().addListener((observable, oldV, newV) -> {
            if (User.checkPassword(newV)) {
                validPassword.setValue(Boolean.TRUE);
                errConLab.setVisible(false);
            } else {
                validPassword.setValue(Boolean.FALSE);
            }
        });

        // Establecer limite de edad (16 años)
        fechaField.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now().minusYears(16);
                    setDisable(empty || date.compareTo(today) > 0);
                }
            };
        });

        fechaField.focusedProperty().addListener((observable, oldV, newV) -> {
            errFechaLab.setVisible(false);
        });
    }

    @FXML
    private void buttAvatarAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChoseAvatar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Modificar Persona");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        ChoseAvatarController controladorAvatar = loader.getController();
        if (controladorAvatar.getAvatar() != null) {
            avatarField.setImage(controladorAvatar.getAvatar());
        }
    }

    @FXML
    private void buttAceptarAction(ActionEvent event) throws NavegacionDAOException, IOException {
        //Prueba correo
        if (!User.checkEmail(correoField.getText())) {
            errNomLab.setVisible(true);
            validPassword.setValue(Boolean.FALSE);
        }
        // Prueba Contraseña
        if (!User.checkPassword(contraField.getText())) {
            errConLab.setVisible(true);
            validPassword.setValue(Boolean.FALSE);
        }
        //Prueba nombre
        if (!User.checkNickName(nombreField.getText())) {
            errNomLab.setVisible(true);
            validname.setValue(Boolean.FALSE);
        }
        //Prueba nombre no existente
        if (Navegacion.getSingletonNavegacion().exitsNickName(nombreField.getText())) {
            errNomLab.setText("nombre ya existente");
            errNomLab.setVisible(true);
        }
        // Prueba fecha
        if (fechaField.getValue() == null) {
            errFechaLab.setVisible(true);
        }

        //crea usuario
        if (validPassword.getValue() && validname.getValue() && validEmail.getValue() && (fechaField.getValue() != null)) {
            Navegacion.getSingletonNavegacion().registerUser(nombreField.getText(), contraField.getText(), contraField.getText(), avatarField.getImage(), fechaField.getValue());

            usuario = Navegacion.getSingletonNavegacion().loginUser(nombreField.getText(), contraField.getText());
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

            stage.setScene(scene);
            controladorPrin.setUsuario(usuario);
            new ConfRegistro().start();
        }
    }

    @FXML   // Boton back para volver a la pantalla de inicio
    private void buttonBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inicial.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Pestaña Inicial");
        stage.setScene(scene);
    }

    public void setAvatar(Image avatar) {
        avatarField.setImage(avatar);
    }
    
    class ConfRegistro extends Thread {
        @Override
        public void run() {
            Notifications notificationBuilder = Notifications.create()
                    .title("Confirmacion")
                    .text("Se ha registrado el usuario correctamente")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);

            Platform.runLater(() -> {

                notificationBuilder.showInformation();
            });
        }
    }
}
