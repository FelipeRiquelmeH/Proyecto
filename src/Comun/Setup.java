package Comun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ConnectionHandler.SqlConection;
import Exception.ExHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Setup {

	private DirAndPaths dir = new DirAndPaths();
	private Metodos metodos = new Metodos();
	private ExHandler ExH = new ExHandler();


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
				String[] words=line	.split(";");//	splits the string based on string
												//	el orden en personas es rut nombres apellidos sexo y mail ;
				
				user.setRut(words[0]);
				user.setNombres( words[1] );
				user.setApellidos(words[2]);
				user.setSexo(words[3].charAt(0) );
				user.setRentas(CargaRentas(user.getRut()));
				Usuarios.add(user);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Usuarios;
	}
	
	public ArrayList <Rental> CargaRentas(String rut){
		ArrayList<Rental>rentas = new ArrayList<Rental>();
		BufferedReader readerRenta;
		
		if (new File (dir.getPathRenta()).exists()){
			try {
				readerRenta = new BufferedReader(new FileReader (dir.getPathRenta()));
				String linea = readerRenta.readLine();
				ArrayList<Libro> libros = CargaLibros();
				

				while (linea != null) {	
					String[] words = linea .split(";");
					for (int i = 0; i < libros.size();i++) {
						if (libros.get(i).getCode().equals(words[3]) && rut.equals(words[1])  ) {
							Rental renta = new Rental(libros.get(i));
							renta.setRut(rut);
							renta.setFechaRenta(ZonedDateTime.parse(words[2], DateTimeFormatter.ISO_ZONED_DATE_TIME));
							rentas.add(renta);
						}
					}
					
					
					linea = readerRenta.readLine();
					
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}
		
		
		}
		
		return rentas;
		
		
		
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
					libro.setDias(5); //Valor predefinido por mientras
					libros.add(libro);
					linea = readerLibro.readLine();
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
			SQL.DescargaRentas();
				

		} catch (SQLException | IOException e) {
			if (dir.debug()) { 	metodos.ConLOG("IN Setup / DescargarPersonas : exception trying to DescargarPersonas()"); 	} 
			ExH.ShowException(e);
		}
		
	}
	
}
