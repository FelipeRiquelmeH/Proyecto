package Exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class ExHandler {

	
	
	
	public void ShowException(Exception ex) {
/**
 * @author Nicolas
 * Ventana popup para facilitar el trace de los errores encontrados.
 * TODO:  Terminar de modelarla para una mejor presentacion
 */
		Alert alert = new Alert(AlertType.ERROR);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		Label Excep = new Label();
		String exception = sw.toString();
        Excep.setText(exception);
        GridPane expContent = new GridPane();
        expContent.add ( Excep ,0 , 0);
        alert.getDialogPane().setExpandableContent(expContent);	        
        alert.setTitle("Exception Dialog"); 	
		alert.showAndWait();
	}
}
