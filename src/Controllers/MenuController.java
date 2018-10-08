package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Comun.*;

public class MenuController{
	private Biblioteca biblioteca;
	
	public void initialize() {
		Setup setup = new Setup();
		biblioteca = new Biblioteca(setup.CargaLibros(), setup.CargarPersonas());
	}
	
	public void exit(ActionEvent event) throws Exception {
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
		
		stage.setOnHiding(new EventHandler<WindowEvent>() {

	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {

	                 @Override
	                 public void run() {
	                     System.out.println("Application Closed by click to Close Button(X)");
	                     System.exit(0);
	                 }
	             });
	         }
	     });

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
