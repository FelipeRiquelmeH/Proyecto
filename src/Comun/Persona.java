package Comun;

import javafx.scene.control.TextArea;

public class Persona {
	private String rut;
	private String nombres;
	private String apellidos;
	private char sexo;
	
	//Constructores
	public Persona() {
		rut = null;
		nombres = null;
		apellidos = null;
		sexo = ' ';
	}
	public Persona(String rut, String nombres, String apellidos, char sexo) {
		this.rut = rut;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.sexo = sexo;
	}
	
	//Getter & Setter
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	//Metodos
	public String infoPersona() {
		String info = getRut() + " " +  getNombres() + " " + getApellidos() + "[" 
				+ getSexo() + "]";
		
		return info;
	}
	
	public void mostrarInfo(TextArea texto) {
		texto.appendText(infoPersona());
	}
	
}
