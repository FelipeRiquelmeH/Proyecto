package Controllers;

import Comun.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class BookController {
	private Libro libro;
	@FXML private TextField bkTitulo, bkAutor;
	@FXML private TextArea bookInfo;
	@FXML private ComboBox<String> cbEstado;
	@FXML private ComboBox<String> cbDisp;
	
	public void setLibro(Libro libro) {
		this.libro = libro;
		libro.mostrarInfo(bookInfo);
	}
	
	public void guardarCambios(ActionEvent event) {
		if(!bkTitulo.getText().isEmpty()) {
			libro.setTitulo(bkTitulo.getText());
		}
		if(!bkAutor.getText().isEmpty()) {
			libro.setAutor(bkAutor.getText());
		}
		if(cbDisp.getSelectionModel().getSelectedItem() != null) {
			if(cbDisp.getValue().equals("Pregrado")) {
				libro.setDias(5);
			}
			else if(cbDisp.getValue().equals("Tesis")) {
				libro.setDias(2);
			}
			else if(cbDisp.getValue().equals("Postgrado")) {
				libro.setDias(3);
			}
			else if(cbDisp.getValue().equals("Otro")) {
				libro.setDias(7);
			}
		}
		if(cbEstado.getSelectionModel().getSelectedItem() != null) {
			if(cbEstado.getValue().equals("Nuevo")) {
				libro.setEstado("Nuevo");
			}
			else if(cbEstado.getValue().equals("Buen Estado")) {
				libro.setEstado("Buen Estado");
			}
			else {
				libro.setEstado("Decente");
			}
		}
		bookInfo.clear();
		libro.mostrarInfo(bookInfo);
	}
}
