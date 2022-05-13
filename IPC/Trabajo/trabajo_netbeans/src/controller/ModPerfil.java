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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            usuario = Navegacion.getSingletonNavegacion().loginUser("prueba", "123456aA!");
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(ModPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        errContraseñaLab.setVisible(false);
        errNomLab.setVisible(false);
        errCorreoLab.setVisible(false);
        
        usuarioLab.setText(usuario.getNickName());
        avatarField.setImage(usuario.getAvatar());
        contraseñaLab.setText(usuario.getPassword());
        correoLab.setText(usuario.getEmail());
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        nacimientoLab.setText(usuario.getBirthdate().format(formatters));
        // TODO
    }    

    @FXML
    private void buttonBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MapaLoged.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();
        stage.setHeight(prevHeight);
        stage.setWidth(prevWidth);
        stage.setTitle("Mapa");

        MapaLoged controladorPrin = loader.getController();
//                controladorPrin.setUsuario(usuario);

        stage.setScene(scene);
        controladorPrin.setUsuario(usuario);
        controladorPrin.setResultados(aciertos, fallos);
    }
    
    
    @FXML
    private void avatarDefaultAction(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/default.png"));
        avatarField.setImage(new Image("/icons/default.png"));
    }

    @FXML
    private void avatar1Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar1.png"));
        avatarField.setImage(new Image("/icons/avatar1.png"));
    }

    @FXML
    private void avatar2Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar2.png"));
        avatarField.setImage(new Image("/icons/avatar2.png"));
    }

    @FXML
    private void avatar3Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar3.png"));
        avatarField.setImage(new Image("/icons/avatar3.png"));
    }

    @FXML
    private void avatar4Action(ActionEvent event) throws NavegacionDAOException {
        usuario.setAvatar(new Image("/icons/avatar4.png"));
        avatarField.setImage(new Image("/icons/avatar4.png"));
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
        }
    }

    @FXML
    private void modCorreoAction(ActionEvent event) throws NavegacionDAOException {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Modifica");
        textInput.setHeaderText("Introduce el nuevo correo");
//        textInput.getDialogPane().setContentText("Introduce el nuevo correo");
        Optional<String> result = textInput.showAndWait();
        TextField input = textInput.getEditor();

        if (result.isPresent() && input.getText().trim().length() > 0) {
            if (User.checkEmail(input.getText())) {
                usuario.setEmail(input.getText());
                correoLab.setText(input.getText());
            } else {
                errCorreoLab.setVisible(true);
            }
        }
    }

    @FXML
    private void modContraseñaAction(ActionEvent event) throws NavegacionDAOException {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Modifica");
//        textInput.getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        textInput.setHeaderText("Introduce la nueva contraseña");
//        textInput.getDialogPane().setContentText("Introduce el nuevo correo");
        Optional<String> result = textInput.showAndWait();
        TextField input = textInput.getEditor();

        if (result.isPresent() && input.getText().trim().length() > 0) {
            if (User.checkEmail(input.getText())) {
                usuario.setPassword(input.getText());
                contraseñaLab.setText(input.getText());
            } else {
                errContraseñaLab.setVisible(true);
            }
        }
    }

    @FXML
    private void modFechaAction(ActionEvent event) {

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Cumpleaños");
        dialog.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
//        dialog.setResizable(true);

//        Label label1 = new Label("Name: ");
//        Label label2 = new Label("Phone: ");
//        TextField text1 = new TextField();
//        TextField text2 = new TextField();

        DatePicker pickerDate = new DatePicker();

        GridPane grid = new GridPane();
//        grid.add(label1, 1, 1);
//        grid.add(text1, 2, 1);
//        grid.add(label2, 1, 2);
//        grid.add(text2, 2, 2);
        grid.add(pickerDate,1,1);
        dialog.getDialogPane().setContent(grid);
        grid.getStylesheets().add(getClass().getResource("/model/estilo.css").toExternalForm());
        
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, LocalDate>() {
            @Override
            public LocalDate call(ButtonType b) {

                if (b == buttonTypeOk) {

                    return null;
                }

                return null;
            }
        });

        Optional<LocalDate> result = dialog.showAndWait();

        if (result.isPresent()) {

            nacimientoLab.setText(result.get().toString());
        }

    }
    
     void setUsuario(User user){
        usuario = user;
    }
     public void setAvatar(Image avatar) {
        avatarField.setImage(avatar);
    }

    void setResultados(int a, int f) {
        aciertos = a;
        fallos = f;
    }
    
}
