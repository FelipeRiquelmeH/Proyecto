package Controllers;

import application.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import Comun.Metodos;
import ConnectionHandler.SqlConection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class MainController implements Initializable{
	Stage stage1 = null;
	@FXML private ProgressBar pbar_Progress = new ProgressBar(0);
	@FXML private TextField txtID,txtPass;
	@FXML private TextField Txt_IP,Txt_Port,Txt_User,Txt_Password,Txt_DB;
	@FXML private CheckBox Clk_Utf;
	@FXML private Button btn_Guardar;
	private Boolean estadoTest = false;
	


	public void stop(){
	    System.out.println("Stage is closing");
	}
	
	Main command = new Main();

	public void Login(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/Main.fxml"));
		command.closeLogin((Stage)txtID.getScene().getWindow());
		Stage stage = new Stage();
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              System.out.println("Stage is closing");
	              MenuController menu = loader.getController();
	              menu.exit();
	          }
	      });   
		stage.show();
		
		MenuController menu = loader.getController();
		menu.initialize();
	}
	
	public void login(ActionEvent event)  {
		
			SqlConection sql = new SqlConection();
			
		try {
			if(sql.LookUser(txtID.getText(),txtPass.getText())) {
				
				try {
				Parent root = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"));
				command.closeLogin((Stage)txtID.getScene().getWindow());
				Stage stage = new Stage();
				stage.setResizable(false);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
				stage.show();
				}catch (Exception Ex) {
					System.out.println(Ex);
				}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	public void SQLSetup(ActionEvent event) throws Exception {
		String[] data = new String[6];
		data[0] =  Txt_IP.getText();
		data[1] = Txt_Port.getText();
		data[2] = Txt_User.getText();
		data[3] = Txt_Password.getText();
		data[4] = Txt_DB.getText();
		data[5] = "?&characterEncoding=UTF-8";
		String url;
		boolean dbSelected = false;

		
		
		System.out.println("Iniciando Test");
		Connection con;
		if (Txt_DB.getText().equals("")) {
			url = "jdbc:mysql://"+data[0]+":"+data[1]+data[5];
		}else {
			url = "jdbc:mysql://"+data[0]+":"+data[1]+"/"+data[4]+data[5];
			dbSelected = true;
		}

		try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url,data[2],data[3]);
		Statement st = con.createStatement();
		
		if (dbSelected) {
			
		}else {
		ResultSet rs = st.executeQuery("show databases");
		ElegirDB(rs );	
		}
		
		con.close();	
		this.estadoTest = true;
		this.pbar_Progress.setProgress(1);
		
		}catch (Exception ex) {
			System.out.println("Test ended in exception");
			this.estadoTest = false;
			
			System.out.println(ex);
			
			this.pbar_Progress.setProgress(0);
		}	


	}
	
	public void ElegirDB(ResultSet rs) throws SQLException {
		int i = 0;
		while(rs.next()) {
			i++;
		}
		rs.first();
		String [] choices = new String [i-1];
		i=0;
		
		while(rs.next()) {
			choices[i]=rs.getString("Database");

			System.out.println(choices[i]);
			i++;
		}
		ChoiceDialog<String> dialog = new ChoiceDialog<>("Data bases", choices);
		//201.241.83.19
		
		
		dialog.setTitle("DB requerida");
		dialog.setHeaderText("Porfavor selecciona una DB en la cual trabajaras");
		dialog.setContentText("Elige tu DB:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your choice: " + result.get());
		    Txt_DB.setText(result.get());
		}
		

	}
	
	public void SqlGuardar() throws IOException {
		Metodos Guardado = new Metodos();
		String IP = Txt_IP.getText();
		String port = Txt_Port.getText();
		String user = Txt_User.getText();
		String pass = Txt_Password.getText();
		String db = Txt_DB.getText();
		String url = "jdbc:mysql://"+IP+":"+port+"/"+db+"?&characterEncoding=UTF-8";
		if (this.estadoTest) {
		Guardado.GuardarSqlConData(url,user,pass,db);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Se han guardado los datos");
		alert.setHeaderText("Exelente has completado el primer paso !");
		alert.showAndWait();
		Stage stage = (Stage) btn_Guardar.getScene().getWindow();
	    stage.close();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Hubo un error con respecto a la SQL");
			alert.setContentText("Porfavor asegurate que la conneccion haya sido exitosa para continuar  "
					+ "Si no tienes los datos ahora podras ingresarlos mas tarde");
			alert.showAndWait();
		}
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
}
