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
import javafx.util.Duration;
import model.Navegacion;
import model.User;
import org.controlsfx.control.Notifications;

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
public class ModPerfil implements Initializable {
    
    private User usuario;
    private int fallos;
    private int aciertos;
    private FileChooser selector = new FileChooser();
    private Tooltip t;
    private String passCode = "*******************";
    private static final String ERRCOINCIDENCIA = "Las contrase??as no coinciden";
    private static final String ERRACTUAL = "La contrase??a actual no coincide";
    private static final String ERRNUEVA = "Contrase??a no valida";
    
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
    private Label contrase??aLab;
    @FXML
    private Label errContrase??aLab;
    @FXML
    private Label nacimientoLab;
    private Stage stageActual;
    private MapaLoged controllerLoged;
    @FXML
    private ImageView correoCondiciones;
    @FXML
    private ImageView contrase??aCondiciones;
    @FXML
    private ImageView nacimientoCondiciones;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        t = new Tooltip("El correo electr??nico requiere un formato v??lido (correo@example.com)");
        Tooltip.install(correoCondiciones, t);
        
        
        t = new Tooltip("Se requieren entre 8 y 20 caracteres, al menos una letra en may??sculas y una min??scula, alg??n d??gito y alg??n car??cter especial (!@#$%&*()-+=)");
        Tooltip.install(contrase??aCondiciones, t);
        
        t = new Tooltip("El usuario tiene que ser mayor de 16 a??os");
        Tooltip.install(nacimientoCondiciones, t);
        
//        try {
//            usuario = Navegacion.getSingletonNavegacion().loginUser("prueba", "123456aA!");
//        } catch (NavegacionDAOException ex) {
//            Logger.getLogger(ModPerfil.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        errContrase??aLab.setVisible(false);
        errNomLab.setVisible(false);
        errCorreoLab.setVisible(false);
        
        
        // TODO
    }    

    @FXML
    private void buttonBack(ActionEvent event) throws IOException {
        ((Stage) errContrase??aLab.getScene().getWindow()).close();
        controllerLoged.closeProblemas();
        controllerLoged.setUsuario(usuario);
    }
    
    @FXML
    private void avatarDefaultAction(ActionEvent event) throws NavegacionDAOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("??Quiere cambiar su avatar a: Avatar Default?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result2 = alerta.showAndWait();
        if (result2.isPresent() && result2.get() == ButtonType.OK) {
            usuario.setAvatar(new Image("/icons/default.png"));
            avatarField.setImage(new Image("/icons/default.png"));
            controllerLoged.setUsuario(usuario);
        }
    }

    @FXML
    private void avatar1Action(ActionEvent event) throws NavegacionDAOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("??Quiere cambiar su avatar a: Avatar 1?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result2 = alerta.showAndWait();
        if (result2.isPresent() && result2.get() == ButtonType.OK) {
            usuario.setAvatar(new Image("/icons/avatar1.png"));
            avatarField.setImage(new Image("/icons/avatar1.png"));
            controllerLoged.setUsuario(usuario);
        }
    }

    @FXML
    private void avatar2Action(ActionEvent event) throws NavegacionDAOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("??Quiere cambiar su avatar a: Avatar 2?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result2 = alerta.showAndWait();
        if (result2.isPresent() && result2.get() == ButtonType.OK) {
            usuario.setAvatar(new Image("/icons/avatar2.png"));
            avatarField.setImage(new Image("/icons/avatar2.png"));
            controllerLoged.setUsuario(usuario);
        }
    }

    @FXML
    private void avatar3Action(ActionEvent event) throws NavegacionDAOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("??Quiere cambiar su avatar a: Avatar 3?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result2 = alerta.showAndWait();
        if (result2.isPresent() && result2.get() == ButtonType.OK) {
            usuario.setAvatar(new Image("/icons/avatar3.png"));
            avatarField.setImage(new Image("/icons/avatar3.png"));
            controllerLoged.setUsuario(usuario);
        }
    }

    @FXML
    private void avatar4Action(ActionEvent event) throws NavegacionDAOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.initStyle(StageStyle.UTILITY);
        alerta.setTitle("Confirmacion");
        alerta.setHeaderText("??Quiere cambiar su avatar a: Avatar 4?");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        Optional<ButtonType> result2 = alerta.showAndWait();
        if (result2.isPresent() && result2.get() == ButtonType.OK) {
            usuario.setAvatar(new Image("/icons/avatar4.png"));
            avatarField.setImage(new Image("/icons/avatar4.png"));
            controllerLoged.setUsuario(usuario);
        }
    }

    @FXML
    private void avatarImportAction(ActionEvent event) throws NavegacionDAOException {
        selector.setTitle("Avatar Selector");
        selector.setInitialDirectory(new File(System.getProperty("user.home")));
        selector.getExtensionFilters().clear();
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes ", "*.png", "*.jpg", "*.gif"));
        File file = selector.showOpenDialog(null);
        if (file != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.initStyle(StageStyle.UTILITY);
            alerta.setTitle("Confirmacion");
            alerta.setHeaderText("??Quiere cambiar su avatar a la imagen importada?");
            alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
            Optional<ButtonType> result2 = alerta.showAndWait();
            if (result2.isPresent() && result2.get() == ButtonType.OK) {
                avatarField.setImage(new Image(file.toURI().toString()));
                avatarField.resize(50, 50);
                usuario.setAvatar(new Image(file.toURI().toString()));
                controllerLoged.setUsuario(usuario);
            }
        }

    }

    @FXML
    private void modCorreoAction(ActionEvent event) throws NavegacionDAOException {
        errCorreoLab.setVisible(false);
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
                alerta.setHeaderText("??Seguro que quiere cambiar el correo?");
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
    private void modContrase??aAction(ActionEvent event) throws NavegacionDAOException {
        errContrase??aLab.setVisible(false);
        
        errContrase??aLab.setVisible(false);
        
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Contrase??as");
        dialog.setHeaderText("Introduce las contrase??as");

        Label label1 = new Label("Contrase??a actual: ");
        Label label2 = new Label("Contrase??a nueva: ");
        Label label3 = new Label("Repita contrase??a: ");
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
                            errContrase??aLab.setText(ERRCOINCIDENCIA);
                            errContrase??aLab.setVisible(true);
                        }
                    } else {
                        errContrase??aLab.setText(ERRACTUAL);
                        errContrase??aLab.setVisible(true);
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
            alerta.setHeaderText("??Seguro que quiere cambiar la contrase??a?");
            alerta.getDialogPane().getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
            Optional<ButtonType> result2 = alerta.showAndWait();
            if (result2.isPresent() && result2.get() == ButtonType.OK) {
                if (User.checkPassword(result.get())) {
                    contrase??aLab.setText(passCode.substring(20 - result.get().length()));
                    usuario.setPassword(result.get());
                    Notifications notificationBuilder = Notifications.create()
                            .title("Confirmaci??n")
                            .text("Se ha registrado el usuario correctamente")
                            .graphic(null)
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.showInformation();
                } else {
                    errContrase??aLab.setText(ERRNUEVA);
                    errContrase??aLab.setVisible(true);
                }

            }

        }
        
        
        
    }

    @FXML
    private void modFechaAction(ActionEvent event) throws NavegacionDAOException {

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Cumplea??os");
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
            alerta.setHeaderText("??Seguro quiere cambiar la fecha de nacimiento?");
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
        contrase??aLab.setText(passCode.substring(20 - usuario.getPassword().length()));
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
            stageActual.setResizable(true);
            controllerLoged.setUsuario(usuario);
        });
    }
    
    void setController(MapaLoged contr) {
        controllerLoged = contr;
    }
}
