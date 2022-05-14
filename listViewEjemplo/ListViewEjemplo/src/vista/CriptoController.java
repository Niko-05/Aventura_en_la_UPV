/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * FXML Controller class
 *
 * @author Vicente
 */
public class CriptoController implements Initializable {

    @FXML
    private CheckMenuItem bitcoin;
    @FXML
    private CheckMenuItem ether;
    @FXML
    private CheckMenuItem litecoin;
    @FXML
    private ListView<WalletEntry> lista;
    @FXML
    private Label cripto;
    @FXML
    private Button down;
    @FXML
    private Button up;
    
    private String coin = "";
    
    private int resultado = 0;
    private int venta = 0;
    
    private ObservableList<WalletEntry> dato = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ArrayList<WalletEntry> misdatos = new ArrayList<WalletEntry>();
        dato = FXCollections.observableArrayList(misdatos);
        lista.setItems(dato);
        
    }    

    @FXML
    private void bc(ActionEvent event) {
        if(bitcoin.isSelected()){
            cripto.setText("Bitcoin");
            ether.setSelected(false);
            litecoin.setSelected(false);
            coin = "Bitcoin";
        }
    }

    @FXML
    private void et(ActionEvent event) {
        if(ether.isSelected()){
            cripto.setText("Ether");
            bitcoin.setSelected(false);
            litecoin.setSelected(false);
            coin = "Ether";
        }
    }

    @FXML
    private void lc(ActionEvent event) {
        if(litecoin.isSelected()){
            cripto.setText("LiteCoin");
            ether.setSelected(false);
            bitcoin.setSelected(false);
            coin = "LateCoin";
        }
    }

    @FXML
    private void comprar(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Comprar");
        dialog.setHeaderText("Comprar " + coin);
        dialog.setContentText("Introduce la cantidad: ");
        
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            resultado = Integer.valueOf(result.get());
        }
        
       if(dato.contains()){
       
       }
       else{
           dato.add(new WalletEntry(coin, resultado));
       } 
    }

    @FXML
    private void vender(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Vender");
        dialog.setHeaderText("Vender " + coin);
        dialog.setContentText("Introduce la cantidad: ");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            venta = Integer.valueOf(result.get());
        }
        int i = dato.getSelection().getSelectedIndex();
        if(dato.get(i).getCantidad() > venta){
            int dif = dato.get(i).getCantidad() - venta;
            dato.get(i).setCantidad(dif);
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en venta de " + coin);
            alert.setContentText("No hay " + venta + " en el wallet");
            alert.showAndWait();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    
    
    
    public class WalletEntry {
    private final StringProperty Crypto = new SimpleStringProperty();
    private final IntegerProperty Cantidad = new SimpleIntegerProperty();
    public WalletEntry(String crypt, Integer q){
    Crypto.setValue(crypt);
    Cantidad.set(q);
    }
    public final StringProperty CryptoProperty() {
        return this.Crypto;
    }
    public final String getCrypto() {
        return this.CryptoProperty().get();
    }
    public final void setCrypto(final java.lang.String crypt) {
        this.CryptoProperty().set(crypt);
    }
    public final IntegerProperty CantidadProperty() {
        return this.Cantidad;
    }
    public final Integer getCantidad() {
        return this.CantidadProperty().get();
    }
    public final void setCantidad(final java.lang.Integer q) {
        this.CantidadProperty().set(q);
    }
    public String toString() {
        return String.format("%-10s%-5s%-4d",this.getCrypto(),"", this.getCantidad());
    }
   } 

    
}
