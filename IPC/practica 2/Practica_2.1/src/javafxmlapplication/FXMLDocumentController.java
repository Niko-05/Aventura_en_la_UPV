/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import static javafxmlapplication.Utils.*;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private Circle circulo;
    private double X_inicial;
    private double Y_inicial;
    @FXML
    private ToggleButton borderButton;
    @FXML
    private ColorPicker ballColor;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void moverbola(KeyEvent event) {
        KeyCode teclaPulsada = event.getCode();
        
        int column, row;
        switch(teclaPulsada){
            case UP:
                row = GridPane.getRowIndex(circulo);
                row = Utils.rowNorm(grid, row - 1);
                GridPane.setRowIndex(circulo, row);
                break;
                
            case DOWN:
                row = GridPane.getRowIndex(circulo);
                row = Utils.rowNorm(grid, row + 1);
                GridPane.setRowIndex(circulo, row);
                break;
                
            case RIGHT:
                column = GridPane.getColumnIndex(circulo);
                column = Utils.columnNorm(grid, column + 1);
                GridPane.setColumnIndex(circulo, column);
                break;
                
            case LEFT:
                column = GridPane.getColumnIndex(circulo);
                column = Utils.columnNorm(grid, column - 1);
                GridPane.setColumnIndex(circulo, column);
                break;
        }
        //GridPane.setConstraints(mibola, columncalc(migrid, event.getSceneX()), rowCalc(miGrid,event.getSceneY());
    }
    
    @FXML
    private void moverbolaRaton(MouseEvent event) {
        GridPane.setConstraints(circulo, columnCalc(grid, event.getSceneX()), rowCalc(grid,event.getSceneY()));
    }
    
    @FXML
    private void soltarBola(MouseEvent event) {
        moverbolaRaton(event);
        circulo.setTranslateX(0);
        circulo.setTranslateY(0);
    }

    @FXML
    private void arrastrarBola(MouseEvent event) {
        circulo.setTranslateX(event.getSceneX()-X_inicial);
        circulo.setTranslateY(event.getSceneY()-Y_inicial);
    }

    @FXML
    private void pincharBola(MouseEvent event) {
        X_inicial = event.getSceneX();
        Y_inicial= event.getSceneY();
    }
    
    /*public int rowNorm(GridPane grid, int row){
    int rowCount = grid.getRowCount();
    return(row + rowCount) % rowCount;
    }
    public int columNorm(GridPane grid, int column){
    int columnCount = grid.getColumnCount();
    return(column + columnCount) % columnCount;
    }*/

    // hacer un new de un circulo
    // ponerlo como hijo del grid (grid.getchildrens (add))


}
//sacar el tamaño de columna. Dividir x del raton entre el tamaño de columna quedarse con el entero - metodo "event.getSceneX();"
// metodo ""