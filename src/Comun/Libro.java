package Comun;

import javafx.scene.control.TextArea;

public class Libro {
	private final String codigoLibro;
	private String titulo;
	private String autor;
	private String tema;
	private String estado;
	private boolean rentado;
	private int diasPermitidos;
	
	//Constructores
	public Libro(String codigo) {
		codigoLibro = codigo;
		titulo = null;
		autor = null;
		tema = null;
		estado = null;
		rentado = false;
		diasPermitidos = 0;
	}
	/*Necesita tener un codigo valido y especifico para no generar problemas al ser variable final*/
	
	public Libro(String codigo, String nombreLibro, String autor, String tema, String estado, int diasPermitidos) {
		this.codigoLibro = codigo;
		this.titulo = nombreLibro;
		this.autor = autor;
		this.tema = tema;
		this.estado = estado;
		this.rentado = false;
		this.diasPermitidos = diasPermitidos;
	}
	
	//Getter & Setter
	public String getCode() {
		return codigoLibro;
	}
	/*Al ser variable final, codigoLibro no es modificable a traves de metodos
	 * por lo que no tiene metodo setter, se mantiene con el valor con el que es
	 * inicializado en el constructor*/
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public boolean getRentado() {
		return rentado;
	}
	public void setRentado(boolean rentado) {
		this.rentado = rentado;
	}
	public int getDias() {
		return diasPermitidos;
	}
	public void setDias(int diasPermitidos) {
		this.diasPermitidos = diasPermitidos;
	}
	
	//Metodos
	public String infoLibro() {
		String info = "Tema: " + getTema() + "\n" + getCode() + " | " + getTitulo() + "\nAutor: " + getAutor() + 
				"\nDias de Renta Permitidos: " + getDias() + "\nEstado: " + getEstado() + "\nRentado: ";
		
		if(getRentado() == true) {
			info = info + "SI";
		}
		else {
			info = info + "NO";
		}
		
		info += "\n";
		return info;
	}
	
	public void mostrarInfo(TextArea texto) {
		texto.appendText(infoLibro()+"\n");
	}
	
	//Metodos de modificacion
	public void modificarLibro(String campo, Object dato) {
		if(campo.equalsIgnoreCase("titulo")) {
			this.setTitulo((String) dato);
		}
		else if(campo.equalsIgnoreCase("autor")) {
			this.setAutor((String) dato);
		}
		else if(campo.equalsIgnoreCase("tema")) {
			this.setTema((String) dato);
		}
		else if(campo.equalsIgnoreCase("estado")) {
			this.setEstado((String) dato);
		}
		else if(campo.equalsIgnoreCase("rentado")) {
			this.setRentado((Boolean) dato);
		}
		else if(campo.equalsIgnoreCase("dias")) {
			this.setDias((Integer) dato);
		}
	}
	
}