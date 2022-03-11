package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import modelo.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VistaListaControlador implements Initializable {
	
	
    @FXML private ListView<Persona> vistadeListafxID;
    @FXML private TextField textFieldfxID;
    @FXML private TextField textFieldApellidofxID;

    @FXML private Button BAddfxID;
    @FXML private Button BBorrarfxID;
    @FXML
    private Button bBajar;
    @FXML
    private Button bSubir;
    @FXML
    private Label labelSELECTED;
    @FXML
    private Button bModificar;
    
    @FXML void addAccion(ActionEvent event) {
        // añade a la colección si los campos no son vacíos y no contienen únicamente blancos
         if ((!textFieldfxID.getText().isEmpty())
        	&& (textFieldfxID.getText().trim().length()!=0)
        	&& (!textFieldApellidofxID.getText().isEmpty())
        	&& (textFieldApellidofxID.getText().trim().length()!=0))
         { 
           datos.add(new Persona(textFieldfxID.getText(),textFieldApellidofxID.getText()));
           textFieldfxID.clear();
           textFieldApellidofxID.clear();
           textFieldfxID.requestFocus();  //cambio del foco al textfield.
        	 
         } 
    }

    @FXML void borrarAccion(ActionEvent event) {
    	int i = vistadeListafxID.getSelectionModel().getSelectedIndex();
    	if (i>=0) datos.remove(i);
    }
	
	private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
            vistadeListafxID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            
                // en el código de inicialización del controlador
                //vistadeListafxID.setCellFactory((c)->{ return new MiCelda(); });
               

		// TODO Auto-generated method stub
		ArrayList<Persona> misdatos = new ArrayList<Persona>();
		misdatos.add(new Persona("Pepe", "García"));
		misdatos.add(new Persona("María", "Pérez"));
		datos = FXCollections.observableArrayList(misdatos);
		vistadeListafxID.setItems(datos); // vinculaci�n entre la vista y el modelo
                
		//datos.add(new Persona("Juan","sanchez"));
		
		
		// inhabilitar botones al arrancar.
		BAddfxID.setDisable(true);
		BBorrarfxID.setDisable(true);
                bBajar.setDisable(true);
                bSubir.setDisable(true);
		// oyente de foco para el textfield.
		textFieldfxID.focusedProperty().addListener((ObservableValue<? extends Boolean> arg2, Boolean antiguo, Boolean nuevo) -> {
                    // TODO Auto-generated method stub
                    if (textFieldfxID.isFocused()) {
                        BAddfxID.setDisable(false);
                        BBorrarfxID.setDisable(true);
                        bBajar.setDisable(true);
                        bSubir.setDisable(true); 
                   }
                });
		
                textFieldApellidofxID.focusedProperty().addListener((ObservableValue<? extends Boolean> arg2, Boolean antiguo, Boolean nuevo) -> {
                    // TODO Auto-generated method stub
                    if (textFieldApellidofxID.isFocused()) {
                        BAddfxID.setDisable(false);
                        BBorrarfxID.setDisable(true);
                        bBajar.setDisable(true);
                        bSubir.setDisable(true);
                    }
                });
                
		// oyente de foco para el listView
		vistadeListafxID.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (vistadeListafxID.isFocused()) {
                        BBorrarfxID.setDisable(false);
                        BAddfxID.setDisable(true);
                        bBajar.setDisable(false);
                        bSubir.setDisable(false);
                        int i = vistadeListafxID.getSelectionModel().getSelectedIndex();
                        Persona aux = datos.get(i);
                        labelSELECTED.setText(aux.toString());
                    //    labelSELECTED.setText(datos.get(vistadeListafxID.getSelectionModel().getSelectedIndex()).toString);
                    }
                });
                
               vistadeListafxID.getSelectionModel().selectedIndexProperty().addListener( (o, oldVal, newVal) -> {
                    if (newVal.intValue() == -1)
                    labelSELECTED.setText("none");
                    else
                    labelSELECTED.setText(datos.get(newVal.intValue()).toString());
                    });
                    labelSELECTED.setText("none");

		
	}

    @FXML
    private void bajarAction(ActionEvent event) {
        int i = vistadeListafxID.getSelectionModel().getSelectedIndex();
        Persona aux = datos.get(i);
        Persona aux2 = datos.get(i+1);
        datos.set(i,aux2);
        datos.set(i+1,aux);
        vistadeListafxID.getSelectionModel().select(i+1);
    }

    @FXML
    private void botonSUBIR(ActionEvent event) {
        int i = vistadeListafxID.getSelectionModel().getSelectedIndex();
        Persona aux = datos.get(i);
        Persona aux2 = datos.get(i-1);
        datos.set(i,aux2);
        datos.set(i-1,aux);
        vistadeListafxID.getSelectionModel().select(i-1);
    }

    @FXML
    private void modificarAccion(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/Ventana2.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Demo vista de lista de personas");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
	

//    class MiCelda extends ListCell<Persona>{
//
//        @Override
//        protected void updateItem(Persona t, boolean bln) {
//            super.updateItem(t, bln); //To change body of generated methods, choose Tools | Templates.
//            if(t==null || bln){
//                setText(null);
//            } else {
//                setText(t.getNombre() + " " + t.getApellidos());
//            }
//        }
    
//    }

        
        
}
