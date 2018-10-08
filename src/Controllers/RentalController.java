package Controllers;


import Comun.Biblioteca;
import Comun.Libro;
import Comun.Rental;
import Comun.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RentalController {
	private Biblioteca biblioteca;
	/* Variables Libros/ Rentar Libro*/
	@FXML private TextField idLibro;
	@FXML private TextField rutAlumno;
	@FXML private TextArea infoLibro;
	@FXML private Label lblDias;
	@FXML private Label lblUsuario;
	/*******************************************/
	
	/* Variables Libros/ Retornar Libro  */
	@FXML private TextField retCode;
	@FXML private TextField retRut;
	@FXML private Label userDeuda;
	
	
	/*******************************************/
	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
/******************************************/
	
	/*** METODOS RENTAR LIBRO ***/
	
	public void clearAll() {
		infoLibro.clear();
		idLibro.clear();
		rutAlumno.clear();
		lblUsuario.setText("");
		lblDias.setText("'X'");
	}
	
	public Libro buscarLibro(ActionEvent event) throws Exception {
		if(idLibro.getText().indexOf('-') != -1) {
			String[] codigo = idLibro.getText().split("-");
			Libro buscado = biblioteca.buscarLibro(codigo[0],codigo[1]);
			if(buscado != null) {
				buscado.mostrarInfo(infoLibro);
				lblDias.setText(Integer.toString(buscado.getDias()));
			}
			
			return buscado;
		}
		return null;
	}
	
	public void verificar(ActionEvent event) throws Exception{
		if(biblioteca != null) {
			Usuario buscado = biblioteca.buscarUsuario(rutAlumno.getText());
			if(buscado != null) {
				if(buscado.getDeuda() > 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("El usuario registra deudas!");
					alert.setContentText("No puede rentar hasta quedar libre de deuda");
					alert.showAndWait();
				}
				else {
					lblUsuario.setText(buscado.getNombres() + "\n" + buscado.getApellidos());
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("El usuario buscado no existe");
				alert.setContentText("Ingrese rut valido o agregue el usuario a la lista");
				alert.showAndWait();
			}
		}
	}
	
	public void rentarLibro(ActionEvent event) throws Exception {
		infoLibro.clear();
		verificar(event);
		Usuario usuario = biblioteca.buscarUsuario(rutAlumno.getText());
		Libro libro = buscarLibro(event);
		if(libro != null && usuario != null) {
			Rental renta = new Rental(libro,usuario.getRut());
			if(usuario.getRentas().size() <= 3 ) {
				if(usuario.agregarRenta(renta)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Exito");
					alert.setHeaderText("El libro ha sido rentado correctamente!");
					alert.showAndWait();
					libro.mostrarInfo(infoLibro);
					clearAll();
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("No se ha podido agregar la renta");
					alert.showAndWait();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Excede numero de rentas permitidas!");
				alert.setContentText("No puede rentar mas de 3 libros");
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Datos omitidos o incorrectos");
			alert.setContentText("Por favor verifique los datos ingresados");
			alert.showAndWait();
		}
	}

/*****************************************************/
	
	/*** METODOS RETORNAR LIBRO ***/
	public Libro buscarLibro(String codigo) {
		if(!codigo.isEmpty() && codigo.indexOf('-') != -1 ) {
			String[] alfaNumerico = codigo.split("-");
			return biblioteca.buscarLibro(alfaNumerico[0], alfaNumerico[1]);
		}
		return null;
	}
	
	public void retornarLibro(ActionEvent event) {
		if(biblioteca != null && !retRut.getText().isEmpty() && !retCode.getText().isEmpty()) {
			Usuario usuario = biblioteca.buscarUsuario(retRut.getText());
			Libro retornado = buscarLibro(retCode.getText());
			if(retornado != null && usuario != null) {
				if(usuario.buscarRenta(retornado) != null) {
					usuario.getDeuda(usuario.buscarRenta(retornado));
					usuario.eliminarRenta(usuario.buscarRenta(retornado));
					userDeuda.setText(Integer.toString(usuario.getDeuda()));
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Exito");
					alert.setHeaderText("El libro ha sido retornado exitosamente!");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("No se pudo retornar el libro");
					alert.setContentText("El usuario no tiene rentado este libro!");
					alert.showAndWait();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No se encontro el usuario/libro");
				alert.setContentText("Verifique que los datos ingresados son correctos");
				alert.showAndWait();
			}
		}
	}
	
}
