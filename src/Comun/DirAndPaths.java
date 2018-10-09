package Comun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import Exception.ExHandler;

public class DirAndPaths {
	ExHandler EX = new ExHandler();
	Metodos met = new Metodos();
	
	private boolean debug = true;
	private boolean debug2 = false;
	private String path_Main 	= "c://POO";	
	private String path_Persona =path_Main+"//Persona.txt";
	private String path_SQL		=path_Main+"//SqlData.txt";
	private String path_Log 	=path_Main+"//Log.txt";
	private String path_Libros 	=path_Main+"//Libro.txt";
	private String pathInsumos = path_Main+"//Insumos.txt";
	private String pathRentas = path_Main+"//Rentas.txt";
	private String pathOperador = path_Main+"//Operador.txt";
	private String sqlDB ;
	
	public boolean intensiveDebug () {
		return debug2;
	}
	public String getPathRenta() {
		return this.pathRentas;
	}
	
	public boolean debug() { return this.debug; }
	public String getPathInsumos() {
		return this.pathInsumos;
	}
	public String getPathProgram() {	
		return this.path_Main;
	}
	public String getPathPersona() {
		return this.path_Persona;
	}	
	public String getPathSql() {
		return this.path_SQL;
	}
	public String getPathLog() {
		return this.path_Log;
	}
	public String getPathLibro() {
		return this.path_Libros;
	}

	public String getDB(String s) {
		if (this.debug) {met.ConLOG("IN GetDB from DirAndPaths called from "+ s +" : Started geting db saved");}
		File file = new File(this.path_SQL); 
	    Scanner sc;
		try {
			sc = new Scanner(file);
		
		int i=1;  
			    while (sc.hasNextLine()) {
			    	if (i == 4) {
			    		this.sqlDB = sc.nextLine();
			    		if (this.debug) {met.ConLOG(" - DB name loaded : "+this.sqlDB); met.ConLOG("");}
			    	}else {
			    	sc.nextLine();
			    	}
			    	i++;
			    }
			    
	    sc.close();
		} catch (FileNotFoundException e) {
			EX.ShowException(e);
		} 
	    return this.sqlDB;
	    /*
	     * TODO: add file verification and error handling
	     */
	}
	public String getOperador() {
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(this.pathOperador);
			br = new BufferedReader(fr);
			String operador = br.readLine();
			br.close();
			return operador;
			
		}catch (Exception ex) {
			return "null"; 
		}
		
	}
}
