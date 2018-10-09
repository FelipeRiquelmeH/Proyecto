package Comun;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.Reference;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import javafx.scene.control.TextArea;

public class Usuario extends Persona {
	private String mail;
	private ArrayList<Rental> rentas;
	private int deuda;
	
	//Constructores
	public Usuario() {
		super();
		mail = null;
		rentas = new ArrayList<Rental>();
		deuda = 0;
	}
	
	public Usuario(String rut, String nombre, String apellido,char sexo, String mail) {
		super(rut,nombre,apellido,sexo);
		this.mail = mail;
		rentas = new ArrayList<Rental>();
		deuda = 0;
	}
	
	//Getter & Setter
	//Incluye los metodos de la superclase Persona
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public ArrayList<Rental> getRentas(){
		return rentas;
	}
	public void setRentas(ArrayList<Rental> rentas) {
		this.rentas = rentas;
	}
	public int getDeuda() {
		return deuda;
	}
	public void setDeuda(int deuda) {
		this.deuda = deuda;
	}

	
	//Metodos
	public void modificarUsuario(String campo, Object dato) {
		if(campo.equalsIgnoreCase("rut")) {
			this.setRut((String) dato);
		}
		if(campo.equalsIgnoreCase("nombres")) {
			this.setNombres((String) dato);
		}
		else if(campo.equalsIgnoreCase("apellidos")) {
			this.setApellidos((String)dato);
		}
		else if(campo.equalsIgnoreCase("sexo")) {
			this.setSexo((Character) dato);
		}
		else if(campo.equalsIgnoreCase("mail")) {
			this.setMail((String)dato);
		}
	}
	
	public Rental buscarRenta(Libro libro) {
		ListIterator<Rental> iterador = rentas.listIterator();
		while(iterador.hasNext()) {
			Rental actual = iterador.next();
			if(actual.getLibro().equals(libro)) {
				return actual;
			}
		}
		return null;
	}
	
	public boolean agregarRenta(Rental nuevaRenta) {
		if(buscarRenta(nuevaRenta.getLibro()) == null) {
			rentas.add(nuevaRenta);
			nuevaRenta.getLibro().setRentado(true);
			return true;
		}
		return false;
	}
	
	/**ARREGLAR ESTE METODO POR FAVOR JSKDHKS**/
	public int getDeuda(Rental renta) {
		int deuda = 0;
		LocalDate entrega = Instant.now().atZone(ZoneId.of("Etc/GMT+3")).toLocalDate();
		long dias = ChronoUnit.DAYS.between(renta.getFechaRenta().toLocalDate(),entrega);
		if(dias > renta.getLibro().getDias()) {
			deuda += (dias * 200);
			
		}
		
		return deuda;
	}

	public boolean eliminarRenta(Rental rentaElim) {
		Rental buscado = buscarRenta(rentaElim.getLibro());
		if(buscado != null && buscado.equals(rentaElim)) {
			setDeuda(getDeuda(buscado));
			rentas.remove(buscado);
			return true;
		}
		return false;
	}
	
	
	public String infoUsuario() {
		String info = infoPersona() + " " + getMail() + ", Cant. Rentas: " + getRentas().size();
		
		return info;
	}
	
	public String infoRentas(ArrayList<Rental> rentas) {
		String info = new String();
		if(rentas != null) {
			ListIterator<Rental> iterador = rentas.listIterator();
			while(iterador.hasNext()) {
				Rental actual = iterador.next();
				info += "\t" + actual.getLibro().getCode() + " | " + actual.getLibro().getTitulo() + " " 
						+ actual.getFechaRenta() + "\n";
			}			
		}
		
		return info;
	}
	
	public void mostrarInfo(TextArea texto) {
		texto.appendText(infoUsuario() + "\n");
	}
	public void mostrarInfo(TextArea texto, String rentas) {
		texto.appendText(infoUsuario() + "\n" + rentas + "\n");
	}
	
}
