package Comun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectionHandler.SqlConection;
import Exception.ExHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Setup {

DirAndPaths dir = new DirAndPaths();
Metodos metodos = new Metodos();
ExHandler ExH = new ExHandler();


	public void CheckSetup() {
		
		SetupCarpeta();
		SetupSql();
		
	}
	
	public void DescargaSQL() {
		DescargaDB();
		
	}
	
	public ArrayList<Usuario> CargarPersonas() {
		ArrayList<Usuario> Usuarios = new ArrayList<Usuario>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader( dir.getPathPersona() )); //"c://POO//Persona.txt"
			String line = reader.readLine();
			while (line != null) 
			{
				Usuario user = new Usuario();
				String[] words=line	.split(",");//	splits the string based on string
												//	el orden en personas es rut nombres apellidos sexo y mail ;
				
				user.setRut(words[0]);
				user.setNombres( words[1] );
				user.setApellidos(words[2]);
				user.setSexo(words[3].charAt(0) );
				Usuarios.add(user);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Usuarios;
	}
	
	public ArrayList<Libro> CargaLibros() {
		// writer.println(id+", "+nombre+","+autor+","+tema+","+estado);
		BufferedReader readerLibro;
		ArrayList<Libro> libros = new ArrayList<Libro>();
		if (new File (dir.getPathLibro()).exists()){
			try {
				// ID id Nombre autor Tema Estado
				readerLibro = new BufferedReader(new FileReader (dir.getPathLibro()));
				String linea = readerLibro.readLine();
				Libro libro ;
				while (linea != null) {
					
					String[] words = linea .split(";");
					libro = new Libro( words[0] );
					libro.setTitulo(words[1]);	
					libro.setAutor(words[2]);
					libro.setTema(words[3]);			
					libro.setEstado(words[4]);
					libros.add(libro);
					linea = readerLibro.readLine();
					System.out.println(libro.infoLibro());
				}
				
				readerLibro.close();
				
			}catch (Exception ex) {
				ExH.ShowException(ex);
				return libros;
			}
		}
		return libros;
		
	}
	
	public void SetupCarpeta() {
		boolean j = false ;
		if (dir.debug()) {metodos.ConLOG("IN Setup / SetupFolder : starting");}
		File carpetaRoot = new File(dir.getPathProgram());
		if (!carpetaRoot.exists()) {
		try {
		j= new File(this.dir.getPathProgram()).mkdir();
		}catch(Exception ex) {
			if (dir.debug()) {	metodos.ConLOG("IN Setup / SetupCarpeta : Error Crear Carpeta");	}
			ExH.ShowException(ex);
		}
		if (j && dir.debug()) {	metodos.ConLOG("IN Setup / SetupCarpeta : Carpeta creada");	}
		}

	}
	
	public void SetupSql() {
		File tmpDir = new File(dir.getPathSql());
		if (!tmpDir.exists()) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ConfigSQL.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.showAndWait();
				}catch(Exception ex) {
				  ExH.ShowException(ex);
				}
		}
	}
	
	
	
	public void DescargaDB() {

		SqlConection SQL = new SqlConection();
		try {
			
			SQL.DescargarPersonas();
			SQL.DescargarLibro();
			SQL.DescargarInsumo();


		} catch (SQLException | IOException e) {
			if (dir.debug()) { 	metodos.ConLOG("IN Setup / DescargarPersonas : exception trying to DescargarPersonas()"); 	} 
			ExH.ShowException(e);
		}
		
	}
	
}
