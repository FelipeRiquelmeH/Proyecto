package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.util.concurrent.ThreadLocalRandom;
import Comun.Biblioteca;
import Comun.Libro;
import Comun.Usuario;

public class AddController{
	private Biblioteca biblioteca;
	
	/* Variables de la pestaña Usuario*/
	/**********************************/
	@FXML private TextField usrNombre;
	@FXML private TextField usrApellido;
	@FXML private TextField usrRut;
	@FXML private TextField usrMail;
	@FXML private RadioButton sexoH,sexoM,sexoNA;
	@FXML private TextArea userInfo;
	/**********************************/
	
	/* Variables de la pestaña Libro*/
	/********************************/
	@FXML private Label lblAlfa;
	@FXML private Label lblNum;
	@FXML private TextField txtTitulo;
	@FXML private TextField txtAutor;
	@FXML private Label lblTema;
	@FXML private TextArea libroInfo;
	@FXML private ComboBox<String> cbTema;
	@FXML private ComboBox<String> cbEstado;
	@FXML private ComboBox<String> cbDisp; //Importancia del libro (Por ej: libros pregrado, tesis, etc.)
	/********************************/
	
	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
/*********************************************************************/
	
	/*** METODOS DE LA PESTAÑA USUARIO ***/
	public Usuario nuevoUsuario() {
		Usuario newUser = new Usuario();
		newUser.setNombres(usrNombre.getText());
		newUser.setApellidos(usrApellido.getText());
		newUser.setRut(usrRut.getText());
		newUser.setMail(usrMail.getText());
		if(sexoH.isSelected()) {
			newUser.setSexo('H');
		}
		else if(sexoM.isSelected()) {
			newUser.setSexo('M');
		}
		else if(sexoNA.isSelected()){
			newUser.setSexo('O');
		}
		
		return newUser;
	}
	
	public boolean setUsuario() {
		Usuario newUser = nuevoUsuario();
		if(biblioteca.agregarUsuario(newUser)) {
			newUser.mostrarInfo(userInfo);
			return true;
		}
		
		return false;
	}
	
	public boolean datosUsuario() {
		if((!usrNombre.getText().isEmpty()) && (!usrApellido.getText().isEmpty()) && (!usrRut.getText().isEmpty())){
			if(sexoM.isSelected() || sexoH.isSelected() || sexoNA.isSelected()) {
				return true;
			}
		}
		
		return false;
	}
	
	public void agregarUsuario(ActionEvent event) throws Exception {
		if(datosUsuario()) {
			if(setUsuario()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Exito");
				alert.setHeaderText("El usuario ha sido añadido correctamente!");
				alert.showAndWait();
				userInfo.clear();
				biblioteca.reporteUsuarios(userInfo);
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Hubo un error al añadir el usuario");
				alert.setContentText("Porfavor asegurese que todos los datos necesarios hayan sido ingresados correctamente.");
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Hubo un error al añadir el usuario");
			alert.setContentText("Porfavor complete todos los campos necesarios.");
			alert.showAndWait();
		}
	}
	
/*********************************************************************/
	
	/*** METODOS DE LA PESTAÑA LIBRO ***/
	public void updCodeTema(ActionEvent event) throws Exception {
		lblAlfa.setText(cbTema.getSelectionModel().getSelectedItem());
		updTema();
		lblNum.setText(generateNumCode());
	}
	
	public void updTema() {
		if(lblAlfa.getText().equals("MATE")) {
			lblTema.setText("Matematicas");
		}
		else if(lblAlfa.getText().equals("FISI")) {
			lblTema.setText("Fisica");
		}
		else if(lblAlfa.getText().equals("QUIM")) {
			lblTema.setText("Quimica");
		}
		else if(lblAlfa.getText().equals("LENG")) {
			lblTema.setText("Lenguaje");
		}
		else if(lblAlfa.getText().equals("INGL")) {
			lblTema.setText("Ingles");
		}
		else if(lblAlfa.getText().equals("INFO")) {
			lblTema.setText("Informatica");
		}
		else if(lblAlfa.getText().equals("MECA")) {
			lblTema.setText("Mecanica");
		}
		else if(lblAlfa.getText().equals("ELEC")) {
			lblTema.setText("Electronica");
		}
		else if(lblAlfa.getText().equals("ECON")) {
			lblTema.setText("Economia");
		}
		else if(lblAlfa.getText().equals("OTRO")) {
			lblTema.setText("Otro");
		}
	}
	
	/***/
	
	public String generateNumCode(/*Biblioteca biblioteca*/) {
		String numCode = new String();
		do {
			numCode = "";
			for(int i=0;i<4;i++) {
				numCode += Integer.toString(ThreadLocalRandom.current().nextInt(0, 10));
			}
		}while(biblioteca.buscarLibro(Integer.parseInt(numCode)));
		
		return numCode;
	}
	
	public Libro nuevoLibro() {
		String code = lblAlfa.getText() + "-" + lblNum.getText();
		Libro nuLibro = new Libro(code);
		nuLibro.setTitulo(txtTitulo.getText());
		nuLibro.setAutor(txtAutor.getText());
		nuLibro.setTema(lblTema.getText());
		if(cbDisp.getValue().equals("Pregrado")) {
			nuLibro.setDias(5);
		}
		else if(cbDisp.getValue().equals("Tesis")) {
			nuLibro.setDias(2);
		}
		else if(cbDisp.getValue().equals("Postgrado")) {
			nuLibro.setDias(3);
		}
		else if(cbDisp.getValue().equals("Otro")) {
			nuLibro.setDias(7);
		}
		//----
		if(cbEstado.getValue().equals("Nuevo")) {
			nuLibro.setEstado("Nuevo");
		}
		else if(cbEstado.getValue().equals("Buen Estado")) {
			nuLibro.setEstado("Buen Estado");
		}
		else {
			nuLibro.setEstado("Decente");
		}
		
		return nuLibro;
	}
	
	public boolean setLibro() {
		Libro nuLibro = nuevoLibro();
		if(biblioteca.agregarLibro(nuLibro)) {
			nuLibro.mostrarInfo(libroInfo);
			return true;
		}
		return false;
	}
	
	public boolean datosLibro() {
		if(!txtTitulo.getText().isEmpty() && cbTema.getValue() != null && cbEstado.getValue() != null && cbDisp.getValue() != null) {
		   	return true;
		}
		
		return false;
	}
	
	public void agregarLibro(ActionEvent event) throws Exception {
		libroInfo.clear();
		if(datosLibro()) {
			if(setLibro()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Exito");
				alert.setHeaderText("El libro ha sido añadido correctamente!");
				alert.showAndWait();
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Hubo un error al añadir el libro");
				alert.setContentText("Porfavor asegurese que los datos hayan sido ingresados correctamente.");
				alert.showAndWait();
			}			
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Datos no ingresados");
			alert.setContentText("Porfavor complete todos los campos necesarios.");
			alert.showAndWait();
		}
	}	
}