package Comun;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ConnectionHandler.SqlConection;


public class Metodos {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//DirAndPaths dir = new DirAndPaths();
	
	public boolean validarRut(String vrut, String vverificador) 
    {
        boolean flag = false; 
        String rut = vrut.trim(); 

        String posibleVerificador = vverificador.trim(); 
        int cantidad = rut.length(); 
        int factor = 2; 
        int suma = 0; 
        String verificador = ""; 

        for(int i = cantidad; i > 0; i--) 
        { 
            if(factor > 7) 
            { 
                factor = 2; 
            } 
            suma += (Integer.parseInt(rut.substring((i-1), i)))*factor; 
            factor++; 
        } 
        verificador = String.valueOf(11 - suma%11); 
        if(verificador.equals(posibleVerificador)) 
        { 
            flag = true; 
        } 
        else 
        { 
            if((verificador.equals("10")) && (posibleVerificador.toLowerCase().equals("k"))) 
            { 
                flag = true; 
            } 
            else 
            { 
                if((verificador.equals("11") && posibleVerificador.equals("0"))) 
                { 
                    flag = true; 
                } 
                else 
                { 
                    flag = false; 
                } 
            } 
        } 
        return flag;         
    }
	
	public boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	} 
	public boolean IsNumVer(String s) {
		if ( s.equals("k")||s.equals("K") ) {
			return true;
		}else {
			return s != null && s.matches("[-+]?\\d*\\.?\\d+"); 
		}
	}
public String getTimeStamp() {
	
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String timeS = sdf.format(timestamp);
	return timeS;
}


	
public void ConLOG(String...s) {
	boolean appendmode = false;
	String linea="";
	if (!(new File("c://POO").exists())) { 		// dir.getPathProgram()).exists()
	}else {
		try {
		File file = new File("c://POO//Log.txt"); //dir.getPathLog()
		if (file.exists()) {appendmode = true;}
		FileWriter fr = new FileWriter(file,appendmode);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		
		if( !s[0].equals("") ) { String timeS = sdf.format(timestamp);
		linea = linea+  timeS+" | ";}
		for (int i = 0;i<s.length;i++) {
			linea = linea+s[i];
		}
		fr.write(linea+"\r\n");
		System.out.println(linea);
		
		fr.close();
		}catch(Exception ex) {
			System.out.println("Error en Metodos");
			ex.printStackTrace();
		}
	}
	
}
/**    ---------------------------------------------------------------------------------          **/
	
	public void EscribirArchivo(String path,ArrayList<String> data) {
		File file = new File(path);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			for ( int i = 0;i < data.size();i++ ) {
				fw.write(data.get(i)+"\r\n");
			
			}
			fw.close();
		}catch (Exception ex) {
			System.out.println("Error en escribir archivo");
			ex.printStackTrace();
		}
	}
	
	public void SacarDatos(Biblioteca biblioteca) {
		DirAndPaths dir = new DirAndPaths();
		SqlConection sqlsave = new SqlConection();
		ArrayList<Libro> libros = biblioteca.getListaLibros();
		ArrayList<Usuario> usuarios = biblioteca.getUsuarios();
		
		
				
				
		ArrayList<String> textoLibros = new ArrayList<String>();
		String titulo,autor,tema,codigo,estado;
		for (int i = 0;i<libros.size();i++) {
			Libro libroActual = libros.get(i);
			titulo = libroActual.getTitulo();
			autor = libroActual.getAutor();
			tema = libroActual.getTema();
			codigo = libroActual.getCode();
			estado = libroActual.getEstado();

			textoLibros.add(codigo+";"+titulo+";"+autor+";"+tema+";"+estado);
		}
		ArrayList<String> textoPersonas = new ArrayList<String>();
		ArrayList<Rental> rentas = new ArrayList<Rental>();
		String rut,nombre,apellido,mail;
		char sexo;
		for (int i=0;i<usuarios.size();i++) {
			Usuario usuarioActual = usuarios.get(i);
			rut = usuarioActual.getRut();
			nombre = usuarioActual.getNombres();
			apellido = usuarioActual.getApellidos();
			mail = usuarioActual.getMail();
			sexo = usuarioActual.getSexo();
			textoPersonas.add(rut+";"+nombre+";"+apellido+";"+sexo+";"+mail);
			
			for (int j = 0; j<usuarioActual.getRentas().size();j++) {
				rentas.add(usuarioActual.getRentas().get(j));	
			}
		}
		
		ArrayList<String>textoRentas = new ArrayList<String>();
		
		for (int j = 0; j<rentas.size();j++) {
			textoRentas.add( dir.getOperador()+";"+rentas.get(j).getRut()+";"+rentas.get(j).getFechaRenta()+";"+rentas.get(j).getLibro().getCode() +";" );
			
		}
		
		
		
		
		sqlsave.ActualizarSQLLibros(libros);
		sqlsave.subirRentas( textoRentas );
		//sqlsave.ActualizarSQLUsuarios(usuarios);
		EscribirArchivo (dir.getPathRenta(),textoRentas);
		EscribirArchivo (dir.getPathLibro(),textoLibros);
		EscribirArchivo (dir.getPathPersona(),textoPersonas);
		
	}
	public void GuardarSqlConData(String...data)  {
		
		File file = new File("c://POO//SqlData.txt");		//dir.getPathSql()
		
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            	for (int i = 0;i<data.length;i++) {
            		fr.write(data[i]+"\r\n");
            		
            	}
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
            	System.out.println(e);
            }
        }
	}	
	
}
