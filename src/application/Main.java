package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import Exception.ExHandler;
import Comun.Setup;
import Comun.Metodos;

/**
 * 
 * @author Nicolas
 *
 *
 * Este programa corre con base de datos MYSQL externa, para comunicar hacia tal database se requiere
 * del uso del puerto 3306 por defecto, desconosco si a la hora de entrega arreglamos ese tema cambiando el puerto 
 * o con otra solucion. porfavor tener en cuenta.
 *
 *
 */

public class Main extends Application {
	private boolean debug = true;
	Metodos met = new Metodos();
	@Override
	public void start(Stage primaryStage) {
		startUp();
		
		
		try {
			Setup set = new Setup();
			set.CheckSetup();				//Se revisan las carpetas del programa y sus archivos
			set.DescargaSQL();			// se descarga la base de datos para hacer uso de archivos ( requerimiento entrega 1 ).
		} catch (Exception e) {
			met.ConLOG("IN Main : error upon trying to setup");
			e.printStackTrace();
			
		}finally { met.ConLOG("Setup ended"); }
	

		ExHandler ExH = new ExHandler();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception ex) {
				met.ConLOG("Error en main al iniciar la ventana");
				ExH.ShowException(ex);
			}
	}
	
	public void closeLogin(Stage primaryStage) {
		primaryStage.close();
	}
	
	private void startUp() {
		met.ConLOG("");
		met.ConLOG("----------------- Iniciando Programa ---------------------- ");
		met.ConLOG("");
		Setup();
	}
	
	private void Setup()  {
		boolean j = false ;
		if (debug) {met.ConLOG("IN Setup / Main : starting");}
		File carpetaRoot = new File("c://POO");
		if (!carpetaRoot.exists()) {
		try {
		j= new File("c://POO").mkdir();
		}catch(Exception ex) {
			if (debug) {	met.ConLOG("IN Setup / SetupCarpeta : Error Crear Carpeta");	}
			ex.printStackTrace();
		}
		if (j) {
			met.ConLOG("IN Setup / SetupCarpeta : Carpeta creada");
				try {
					File sqlData = 		new File("c://POO//SqlData.txt"	);	sqlData.createNewFile();
					File Log =		 	new File("c://POO//Log.txt"		);		Log.createNewFile();
					File Personas = 	new File("c://POO//Persona.txt"	);		Personas.createNewFile();
				} catch (IOException e) {
					met.ConLOG("Error in main!");
					e.printStackTrace();
				}
			
			}
		}
		if (debug) {met.ConLOG("IN Setup / Main : Ending");}

	}
	

}
