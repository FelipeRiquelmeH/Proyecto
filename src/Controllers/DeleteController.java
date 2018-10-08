package Controllers;

import Comun.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class DeleteController {
	private Biblioteca biblioteca;
	
	/* Atributos pestaña Usuario */
	@FXML private TextField usrRut;
	@FXML private TextArea userInfo;
	@FXML private Label usrDeuda;
	@FXML private Label usrAlert;
	@FXML private Label usrRentas;
	/**********************************/
	
	/* Atributos pestaña Libro */
	@FXML private TextField bkCode;
	@FXML private TextArea bookInfo;
	@FXML private Label bkAlert;
	
	/**************************************************/
	
	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
/*****************************************/
	public Usuario buscarUsuario(ActionEvent event) {
		userInfo.clear();
		if(!usrRut.getText().isEmpty()) {
			Usuario buscado = biblioteca.buscarUsuario(usrRut.getText());
			if(buscado != null) {
				usrDeuda.setText(Integer.toString(buscado.getDeuda()));
				usrRentas.setText(Integer.toString(buscado.getRentas().size()));
				if(buscado.getDeuda() == 0 && buscado.getRentas().size() == 0) {
					usrAlert.setText("El usuario: " + buscado.getRut() + "\n no presenta conflictos.");
					buscado.mostrarInfo(userInfo);
				}
				else {
					usrAlert.setText("El usuario: " + buscado.getNombres() + " " + buscado.getApellidos() + "\n presenta conflictos de ");
					if(buscado.getDeuda() != 0 && buscado.getRentas().size() == 0) {
						usrAlert.setText(usrAlert.getText() + "deuda!");
					}
					else if(buscado.getDeuda() == 0 && buscado.getRentas().size() != 0) {
						usrAlert.setText(usrAlert.getText() + "rentas no devueltas!");
					}
					else {
						usrAlert.setText(usrAlert.getText() + "deuda y rentas no devueltas!");
					}
					buscado.mostrarInfo(userInfo, buscado.infoRentas(buscado.getRentas()));
				}
			}
			return buscado;
		}
		
		return null;
	}
	
	public void eliminarUsuario(ActionEvent event) {
		Usuario elimUser = buscarUsuario(event);
		if(elimUser != null) {
			if(elimUser.getDeuda() == 0 && elimUser.getRentas().size() == 0) {
				if(biblioteca.eliminarUsuario(elimUser.getRut())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Exito");
					alert.setHeaderText("El usuario ha sido eliminado correctamente!");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Hubo un error al eliminar el usuario");
					alert.setContentText("Porfavor asegurese que los datos hayan sido ingresados correctamente.");
					alert.showAndWait();
				}				
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("El usuario presenta conflictos!");
				alert.setContentText("Porfavor asegurese que el usuario no tenga deudas ni rentas pendientes.");
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("El usuario buscado no existe!");
			alert.setContentText("Porfavor ingrese un rut valido/existente");
			alert.showAndWait();
		}
	}
	
/*******************************************************************/
	public Libro buscarLibro(ActionEvent event) {
		bookInfo.clear();
		if(!bkCode.getText().isEmpty()) {
			if(bkCode.getText().indexOf('-') != -1) {
				String[] alfaNum = bkCode.getText().split("-");
				Libro buscado =	biblioteca.buscarLibro(alfaNum[0], alfaNum[1]);
				if(buscado != null) {
					buscado.mostrarInfo(bookInfo);
					if(buscado.getRentado() == true) {
						bkAlert.setText("El libro se encuentra \nrentado!");
					}
					else {
						bkAlert.setText("El libro no presenta \nconflictos");
					}
					return buscado;
				}
			}
		}
		return null;
	}
	
	public void eliminarLibro(ActionEvent event) {
		Libro elimBook = buscarLibro(event);
		if(elimBook != null) {
			if(elimBook.getRentado() == false) {
				String[] code = elimBook.getCode().split("-");
				if(biblioteca.eliminarLibro(code[0],code[1])){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Exito");
					alert.setHeaderText("El libro ha sido eliminado correctamente!");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Hubo un error al eliminar el libro");
					alert.setContentText("Porfavor asegurese que el codigo sea correcto.");
					alert.showAndWait();
				}				
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("El libro se encuentra rentado!");
				alert.setContentText("Porfavor asegurese que el libro sea devuelto antes de eliminarlo.");
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("El libro buscado no existe!");
			alert.setContentText("Porfavor ingrese un codigo valido/existente");
			alert.showAndWait();
		}
	}
}
