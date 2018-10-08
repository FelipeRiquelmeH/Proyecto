package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import Comun.*;

public class MenuController{
	private Biblioteca biblioteca;
	@FXML private Button btnExit;
	@FXML private Button bAdd;
	@FXML private MenuItem libro;
	
	public void initialize() {
		Setup setup = new Setup();
		biblioteca = new Biblioteca(setup.CargaLibros(), setup.CargarPersonas());
	}
	


	public void exit() 
	{
		try {
		Metodos met = new Metodos();
		System.out.println("Se guardaron los cambios");
		met.SacarDatos(biblioteca);
		}catch (Exception ex) {
			System.out.println("RRRO");
			ex.printStackTrace();
		}
		
		Platform.exit();
		
	}


	public void agregar(ActionEvent event) throws Exception{	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/Agregar.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		AddController controller = loader.getController();
		controller.setBiblioteca(biblioteca);

	}
	
	public void adminRentas(ActionEvent event) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/Rental.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		RentalController controller = loader.getController();
		controller.setBiblioteca(biblioteca);
	}
	
	public void buscar(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/Buscar.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		SearchController controller = loader.getController();
		controller.setBiblioteca(biblioteca);
	}
	
	public void eliminar(ActionEvent event) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/Eliminar.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		DeleteController controller = loader.getController();
		controller.setBiblioteca(biblioteca);
	}
}
