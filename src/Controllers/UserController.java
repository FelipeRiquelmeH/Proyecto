package Controllers;

import Comun.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserController {
	private Usuario usuario;
	@FXML private TextField usrNombre, usrApellido, usrMail;
	@FXML private RadioButton sexoH, sexoM, sexoNA;
	@FXML private TextArea userInfo;
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		userInfo.setWrapText(true);
		usuario.mostrarInfo(userInfo);
	}
	
	public void guardarCambios(ActionEvent event) {
		if(!usrNombre.getText().isEmpty()) {
			usuario.setNombres(usrNombre.getText());
		}
		if(!usrApellido.getText().isEmpty()) {
			usuario.setApellidos(usrApellido.getText());
		}
		if(!usrMail.getText().isEmpty()) {
			usuario.setMail(usrMail.getText());
		}
		if(sexoH.isSelected()) {
			usuario.setSexo('H');
		}
		else if(sexoM.isSelected()) {
			usuario.setSexo('M');
		}
		else if(sexoNA.isSelected()) {
			usuario.setSexo('O');
		}
		userInfo.clear();
		usuario.mostrarInfo(userInfo);
	}
}
