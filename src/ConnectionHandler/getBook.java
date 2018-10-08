package ConnectionHandler;


import java.sql.*;
import ConnectionHandler.SqlConection;

public class getBook {
	private String Tabla = "Persona";
	private String columna = "";
	private SqlConection Con = new SqlConection();
	
	
	
	public ResultSet ConsultaIdText(String idText) {
		try {
		Con.CreateConnection(null);
		ResultSet rs = Con.preguntaSql("select * from "+Tabla+" where "+columna+" LIKE '%"+idText+"%';");
		Con.CloseConnection();
		
		return rs;
		}catch (Exception Ex) {	
			System.out.println(Ex);
			return null;
		}	
	}
	
	public ResultSet ConsultaTema(String texto_Tema) {
		try {
			Con.CreateConnection(null);
			ResultSet rs = Con.preguntaSql("select * from "+Tabla+" where "+columna+" LIKE '%"+texto_Tema+"%';");
			Con.CloseConnection();
			
			return rs;
			}catch (Exception Ex) {	
				System.out.println(Ex);
				return null;
			}
	}
	public ResultSet ConsultaTitulo(String texto_Titulo) {
		try {
			Con.CreateConnection(null);
			ResultSet rs = Con.preguntaSql("select * from "+Tabla+" where "+columna+" LIKE '%"+texto_Titulo+"%';");
			Con.CloseConnection();
			
			return rs;
			}catch (Exception Ex) {	
				System.out.println(Ex);
				return null;
			}
	}
	public ResultSet ConsultaAutor(String texto_Autor) {
		try {
			Con.CreateConnection(null);
			ResultSet rs = Con.preguntaSql("select * from "+Tabla+" where "+columna+" LIKE '%"+texto_Autor+"%';");
			Con.CloseConnection();
			
			return rs;
			}catch (Exception Ex) {	
				System.out.println(Ex);
				return null;
			}
	}
	public int Existencias(ResultSet rs) throws SQLException {
		/**
		 *  Se le entrega un ResultSet definido por java.sql
		 *  entrega un int con la cantidad de libros sin 
		 */
		int existencias = 0;
		while (rs.next()) {
			if ( rs.getBoolean("Estado") ) {
				existencias++;
			}
		}
		return existencias;
	}
	
	

}

