package ConnectionHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import Comun.DirAndPaths;
import Comun.Libro;
import Comun.Metodos;
import Comun.Setup;
import Comun.Usuario;
import Exception.ExHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Nicolas
 **/


public class SqlConection {
	
	Metodos met = new Metodos();
	ExHandler EXH = new ExHandler(); 
	
	private String url="",username="",password="";	
	private Connection con; 
	public Statement st;
	private String tablaEstudiantes = "Estudiante";
	private String dbLibros = "Libro";
	private String tablaUsuarios = "Persona";
	private String tablaLogin = "DataLogin";
	private String tablaInsumos = "Insumo";
	
	//
	DirAndPaths dir = new DirAndPaths();
	private String dbName = dir.getDB("SqlCon-initStringdbName");
	
	// -------------------------------------------------------------- //
	
	public boolean LoadConData(String s) throws IOException {
		if (dir.debug()) {met.ConLOG("IN SqlConection / LoadConData : "+ s + " started a load ConData");met.ConLOG("");}

		String filePath = dir.getPathSql();		
		BufferedReader reader = null;
		String [] linea = new String[5];
		int i = 0;
		
		File file = new File(filePath);
		if (file.length()==0) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ConfigSQL.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.showAndWait();
				}catch(Exception ex) {
				met.ConLOG("Err un sqlcon");
				 ex.printStackTrace();
				 
				}			
		}
		met.ConLOG("FILE FOUND: proceding to read");
			try {
				reader = new BufferedReader(new FileReader(filePath));
				String line = reader.readLine();
				
				while ((line != null) && (i<3  )) {
					if (dir.intensiveDebug()) {met.ConLOG("read line for sqlData: ",line);}
					linea[i] = line;
					line = reader.readLine();
					i++;
				}
				met.ConLOG("");
		         if (!linea[0].isEmpty()) {this.url 		= linea[0];}
		         if (!linea[1].isEmpty()) {this.username 	= linea[1];}
		         if (!linea[2].isEmpty()) {this.password 	= linea[2];}
			}catch (Exception e) {
				met.ConLOG("Error en sqlCon al intentar leer el archivo :"+filePath);
				e.printStackTrace();
				return false;
			}finally {
				reader.close();
			}
		
	
		if (dir.debug()) {
			met.ConLOG(" - url      Loaded : "+this.url);
			met.ConLOG(" - Username Loaded : "+this.username);
			met.ConLOG(" - Password Loaded : "+this.password);
			met.ConLOG("");
		}
		
	  return true;
		
	}
	
	
	
	public void DescargarPersonas() throws SQLException, IOException {

		CreateConnection("DescargarPersonas");
		String dataBase = this.dbName;
		accionSql ("use "+dataBase);
		String query = "Select * from " + tablaEstudiantes;
		ResultSet rs = preguntaSql(query);
		
		String mail,nombre,apellido,rut,sexo;
		PrintWriter writer = new PrintWriter(dir.getPathPersona());
		while (rs.next()) 
		{
			nombre = rs.getString("Nombre");
			apellido = rs.getString("Apellido");
			rut = rs.getString("Rut");
			sexo = rs.getString("Sexo");
			mail = rs.getString("Mail");
			writer.println(rut+";"+nombre+";"+apellido+";"+sexo+";"+mail);
		}
		CloseConnection();	writer.close();
		if(dir.debug()) {met.ConLOG("in: SqlCon / DescargaPersonas : Exito  ");}
	}
	
	
	public void DescargarLibro() throws SQLException, IOException {

		CreateConnection("DescargarLibros");
		String dataBase = this.dbName;
		accionSql ("use "+dataBase);
		String query = "Select * from " + dbLibros;
		ResultSet rs = preguntaSql(query);
		
		String id,nombre,autor,tema,tipo,estado;
		PrintWriter writer = new PrintWriter(dir.getPathLibro());
		while (rs.next()) 
		{
			id = rs.getString("idAlfaNumerico");
			nombre = rs.getString("nombre");
			autor = rs.getString("autor");
			tema= rs.getString("tema");
			tipo = rs.getString("tipoObjeto");
			estado = rs.getString("estado");
			
			writer.println(id+";"+nombre+";"+autor+";"+tema+";"+estado);
		}
		CloseConnection();	writer.close();
		if(dir.debug()) {met.ConLOG("in: SqlCon / DescargaLibros : Exito  ");}
	}
	
	@Deprecated
	public void ActualizarSQLUsuarios(ArrayList<Usuario> usuarios) {
		String tabla = "Estudiante";
		CreateConnection("Actualizar SQL");
		for (int i = 0; i<usuarios.size();i++) {
		Usuario user = usuarios.get(i);
		accionSql("UPDATE "+tabla+" SET (rut,nombre,apellido,mail,sexo) = ('"+user.getRut()+"','"+user.getNombres()+"','"+user.getApellidos()+"','"+user.getMail()+"','"+user.getSexo()+"');");
		}
		
		CloseConnection();
	}
	
	public void subirRentas(ArrayList<String> rentas  ) {
		String tabla = "Renta";
		CreateConnection("Actualizar RentaSQL");
		
		for ( int i=0;i<rentas.size();i++ ) {
			String [] rentaSplited = rentas.get(i).split(";");
			
		
			accionSql("INSERT INTO "+tabla+" (rutOperador,rutCliente,fecha,ObjetoRenta,Rentado) VALUES ('"+rentaSplited[0]+"','"+rentaSplited[1]+  "','"+ rentaSplited[2] +"','"+ rentaSplited[3] +"','SI');");
			
			
		}
		
		
		
		CloseConnection();
	}
	
	public void DescargaRentas() throws FileNotFoundException, SQLException {
	
		CreateConnection("DescargarRentas");
		String dataBase = this.dbName;
		accionSql ("use "+dataBase);
		String query = "Select * from Renta";

		/*
		 * TODO: not finished just **working** reed re-work
		 */

		ResultSet rs = preguntaSql(query);
		
		String rutO,rutC,fecha,libro,rentado;
		PrintWriter writer = new PrintWriter(dir.getPathRenta());
		while (rs.next()) 
		{
			rutO = rs.getString("rutOperador");
			rutC = rs.getString("rutCliente");
			fecha = rs.getString("Fecha");
			libro = rs.getString("ObjetoRenta");
			rentado = rs.getString("Rentado");

			
			writer.println(rutO+";"+rutC+";"+fecha+";"+libro+";"+rentado);
		}
		CloseConnection();	writer.close();
		if(dir.debug()) {met.ConLOG("in: SqlCon / DescargaLibros : Exito  ");}
		
	}
	
	//-------------------------------------------------------------------------------------------------------------
	/*
	 * ActualizarSQLLibros es un metodo que como dice su nombre actualiza los libros con la base de datos
	 * primero busca que libros no estan en la sql para poder agregarlos
	 * luego busca cuales no estan en memoria para quitarlos entendiendo que si no estan en memoria se borraron 
	 * y por ultimo avtualiza datos que puedan haber sido cambiados 
	 * 
	 */
	public void ActualizarSQLLibros(ArrayList<Libro> libros) {
		String tabla = "Libro";
		CreateConnection("Actualizar SQL");
		
		
		String id;
		boolean status = false;
		
		/*
		 * Quita los que estan en la sql que no esten en memoria
		 */
		ResultSet LibsRs = preguntaSql("select * FROM "+tabla);
		try {
			
			while (LibsRs.next()) {
				id = LibsRs.getString("idAlfaNumerico");
				
				for (int i=0;i<libros.size();i++) {
						if ( id.equals(libros.get(i).getCode()) ) {
							status = true;
						}
				}
				if (!status) {
					accionSql("DELETE FROM "+tabla+" WHERE idAlfaNumerico = '"+ id +"';");
				}
				status = false;
			}
			
			LibsRs.close();
			
			/*
			 * Agrega los que esten en memoria si no estan en la sql
			 */
			
			for (int i = 0;i<libros.size();i++) {
				boolean status2 = false;
				ResultSet agregaLib = preguntaSql("select * FROM "+tabla+" WHERE idAlfaNumerico = '"+libros.get(i).getCode()+"';");
				id = libros.get(i).getCode();
				
				while (agregaLib.next()) {
					if (agregaLib.getString("idAlfaNumerico").equals(id)) {
						status2 = true;
					}	
				}
				
				if ( !status2 ) {
					met.ConLOG("Procediendo a a gregar a en la sql a");
					accionSql(" INSERT INTO "+tabla+" (idAlfaNumerico,nombre,autor,tema,Estado)  VALUES ('"   
							+libros.get(i).getCode()+"','"+libros.get(i).getTitulo()+"','"+libros.get(i).getAutor()
							+"','"+libros.get(i).getTema()+"','"+libros.get(i).getEstado()+"');"	);
				}
				agregaLib.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * 
		 * Se actualizan los datos en la sql
		 * 
		 */
		for (int i = 0; i<libros.size();i++) {
			
		Libro libro = libros.get(i);
		
		
		accionSql("UPDATE "+tabla+" SET autor = '"+libro.getAutor() + "' ,"
				+ "idAlfaNumerico = '"+libro.getCode()+"',"
						+ "nombre = '"+libro.getTitulo()+"',"
								+ "tema = '"+libro.getTema()+"',"
										+ "Estado = '"+libro.getEstado()+"'"
												+ "WHERE idAlfaNumerico = '"+libro.getCode()+"';"  );
		}		
		CloseConnection();
	}
	
	
	
	
	public void DescargarInsumo() throws SQLException, IOException {

		CreateConnection("DescargarLibros");
		String dataBase = this.dbName;
		accionSql ("use "+dataBase);
		String query = "Select * from " + tablaInsumos;

		/*
		 * TODO: not finished just **working** reed re-work
		 */

		ResultSet rs = preguntaSql(query);
		
		String id,nombre,modelo;
		PrintWriter writer = new PrintWriter(dir.getPathInsumos());
		while (rs.next()) 
		{
			id = rs.getString("idAlfaNumerico");
			nombre = rs.getString("nombre");
			modelo = rs.getString("modelo");

			
			writer.println(id+", "+nombre+","+modelo);
		}
		CloseConnection();	writer.close();
		if(dir.debug()) {met.ConLOG("in: SqlCon / DescargaLibros : Exito  ");}
	}
	
	public boolean LookUser(String user,String password) throws SQLException, IOException {

		CreateConnection("DescargarLibros");
		String dataBase = this.dbName;
		String pass;
		accionSql ("use "+dataBase);
		String query = "Select * from " + tablaUsuarios+" INNER JOIN  "+tablaLogin + " ON "+tablaUsuarios+".rut ="+tablaLogin+".Persona_rut "
				+ "WHERE rut ='"+user+"'";
		ResultSet rs = preguntaSql(query);
		
		if (rs.first()) {
		pass = rs.getString("password");
		if (pass.equals(password)) {
			return true;
			}
		}
		return false;
	}
	
	public boolean TestSqlCon() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (this.url.isEmpty() || this.username.isEmpty() || this.password.isEmpty()) { LoadConData("TestSqlCon"); }
			this.con = DriverManager.getConnection (this.url,this.username,this.password);
			st = this.con.createStatement();
			this.con.close();
			return true;
		}catch (Exception ex) {
			EXH.ShowException(ex);
			return false;
		}

	}
	
	/*
	 * TODO: Take out those trycatch from below and add them where we call those functions
	 */
	
	public boolean CreateConnection (String s){
		if (dir.debug()) { met.ConLOG ("IN CreateCon from SqlCon asked by °",s,"° starting Conection to DB");met.ConLOG("");}
		boolean dataOK = false;
		try {
			dataOK = LoadConData("CreateCon");
			} catch (IOException ex) {		
				EXH.ShowException(ex);
				return false;
			}
		if (dataOK) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				this.con = DriverManager.getConnection (this.url,this.username,this.password);
				st = this.con.createStatement();
				}catch(Exception ex){
					EXH.ShowException(ex);
					return false;
				}
		}
		return true;
	}
	
	public void CloseConnection() {
		try {
		this.con.close();
		}catch (Exception ex) {
			EXH.ShowException(ex);
		}
	}
	public ResultSet preguntaSql (String s) {
		try {
		return st.executeQuery(s);
		}catch (Exception ex) {
			EXH.ShowException(ex);
			return null;
		}
	}
	public int accionSql (String s){
		try {
			return st.executeUpdate(s);
		} catch (SQLException ex) {
			//EXH.ShowException(ex);
			return 0;
		}		
	}
	
	
	/*
	 * Metodo para crear las tablas en una db mysql si esque no existen 
	 * 
	 * 
	 */
	
	private void CreateDB() {
		try {
		CreateConnection("CreateDB");		
			
		
		}catch (Exception ex) {
			ex.printStackTrace(); // TODO : usar un handler u otro metodo 
		}finally {
			CloseConnection();
		}
	}
	

}

