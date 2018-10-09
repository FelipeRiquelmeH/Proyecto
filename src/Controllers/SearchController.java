package Controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import Comun.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SearchController {
	private Biblioteca biblioteca;
	 /* Atributos de la pestaña Usuario */
	@FXML private TextField usrNombre;
	@FXML private TextField usrApellido;
	@FXML private TextField usrRut;
	@FXML private TextArea userInfo;
	@FXML private CheckBox selectAll, selectAllRentas;
	@FXML private Button btnEditUsuario;
	/**************************************************/
	
	/* Atributos de la pestaña Libro */
	@FXML private TextField bkNombre;
	@FXML private TextField bkAutor;
	@FXML private ComboBox<String> bkTema;
	@FXML private TextField bkCode;
	@FXML private TextArea bookInfo;
	@FXML private Button btnEditLibro;
	
	/**************************************************/
	
	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
		btnEditUsuario.setVisible(false);
		btnEditLibro.setVisible(false);
	}

/**************************************************************/
	
	public void mostrarReporte(ArrayList<Usuario> users) {
		ListIterator<Usuario> iterador = users.listIterator();
		while(iterador.hasNext()) {
			Usuario actual = iterador.next();
			actual.mostrarInfo(userInfo,actual.infoRentas(actual.getRentas()));
		}
	}
	
	public void buscarUsuario(ActionEvent event) {
		userInfo.clear();
		btnEditUsuario.setVisible(false);
		if(selectAllRentas.isSelected()) {
			mostrarReporte(biblioteca.getUsuarios());
		}
		else if(selectAll.isSelected()) {
			biblioteca.reporteUsuarios(userInfo);
		}
		else {
			if(usrRut.getText().isEmpty()) {			
				mostrarReporte(biblioteca.buscarUsuarios(usrNombre.getText(), usrApellido.getText()));
			}
			else {
				Usuario user = biblioteca.buscarUsuario(usrRut.getText());
				user.mostrarInfo(userInfo);
				userInfo.appendText(user.infoRentas(user.getRentas()));
				btnEditUsuario.setVisible(true);
			}	
		}
	}
	
	public void generarReporteUsuario(ActionEvent event) throws IOException {
		if(!userInfo.getText().isEmpty()) {
			String path = "C://POO/Reportes/Usuarios";
			new File(path).mkdirs();
			String fileOut = userInfo.getText();
			BufferedWriter writer;
			if(selectAllRentas.isSelected()) {
				writer = new BufferedWriter(new FileWriter(path + "/AllUsers&Rents.txt"));					
			}
			else if(selectAll.isSelected()) {
				writer = new BufferedWriter(new FileWriter(path + "/AllUsers.txt"));
			}
			else if(!usrRut.getText().isEmpty()) {
				writer = new BufferedWriter(new FileWriter(path + "/Reporte-" + usrRut.getText() + ".txt"));
			}
			else {
				writer = new BufferedWriter(new FileWriter(path + "/ReporteComun.txt"));
			}
			writer.write(fileOut);
			writer.close();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Exito");
			alert.setHeaderText("Reporte Generado exitosamente");
			alert.showAndWait();
		}
	}
	
	public void editarUsuario(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/EditUsuario.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		UserController controller = loader.getController();
		controller.setUsuario(biblioteca.buscarUsuario(usrRut.getText()));
	}
	
/*********************************************************************/
	
	/*** Metodos pestaña Libro ***/
	public void mostrarLibros() {
		ArrayList<String> datos = biblioteca.listaLibros(bkTema.getSelectionModel().getSelectedItem(), bkNombre.getText());
		if(datos != null && !datos.isEmpty()) {
			ListIterator<String> iterador = datos.listIterator();
			while(iterador.hasNext()) {
				bookInfo.appendText(iterador.next() + "\n");
			}
		}
	}
	
	public void buscarLibro(ActionEvent event) {
		bookInfo.clear();
		btnEditLibro.setVisible(false);
		if(!bkCode.getText().isEmpty()) {
			if(bkCode.getText().indexOf('-') != -1) {
				String[] alfaNum = bkCode.getText().split("-");
				Libro buscado =	biblioteca.buscarLibro(alfaNum[0], alfaNum[1]);
				buscado.mostrarInfo(bookInfo);
				btnEditLibro.setVisible(true);
			}
		}
		else{
			mostrarLibros();
		}
	}
	
	public void editarLibro(ActionEvent event) throws Exception {
		if(bkCode.getText().indexOf('-') != -1) {
			String[] alfaNum = bkCode.getText().split("-");
			Libro buscado =	biblioteca.buscarLibro(alfaNum[0], alfaNum[1]);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Views/EditLibro.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
			BookController controller = loader.getController();
			controller.setLibro(buscado);
		}
	}
	
	public void generarReporteLibro(ActionEvent event) throws IOException {
		if(!bookInfo.getText().isEmpty()) {
			String path = "C://POO/Reportes/Libros";
			new File(path).mkdirs();
			String fileOut = bookInfo.getText();
			BufferedWriter writer;
			if(!bkCode.getText().isEmpty()) {
				writer = new BufferedWriter(new FileWriter(path + "/Reporte-" + bkCode.getText() +  ".txt"));					
			}
			else if(!bkTema.getSelectionModel().getSelectedItem().isEmpty()) {
				writer = new BufferedWriter(new FileWriter(path + "/(Temporal) Themed [" + bkTema.getSelectionModel().getSelectedItem() + "].txt"));
			}
			else{
				writer = new BufferedWriter(new FileWriter(path + "/(Temporal) Themed " + bkNombre.getText() + ".txt"));
			}
			writer.write(fileOut);
			writer.close();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Exito");
			alert.setHeaderText("Reporte Generado exitosamente");
			alert.showAndWait();
		}
	}
}