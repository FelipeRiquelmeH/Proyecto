package Comun;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Rental {
	private Libro libro;
	private String rutAsociado;
	private ZonedDateTime fechaRenta;
	
	//Constructores
	public Rental(Libro libro) {
		this.libro = libro;
		rutAsociado = null;
		fechaRenta = null;
	}
	public Rental(Libro libro,String rut) {
		this.libro = libro;
		rutAsociado = rut;
		fechaRenta = Instant.now().atZone(ZoneId.of("America/Santiago"));
	}
	
	//Getter & Setter
	public ZonedDateTime getFechaRenta() {
		return fechaRenta;
	}
	public void setFechaRenta(ZonedDateTime fecha) {
		this.fechaRenta = fecha;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libroRentado) {
		this.libro = libroRentado; //For same object reference
	}
	public String getRut() {
		return rutAsociado;
	}
	public void setRut(String rut) {
		this.rutAsociado = rut;
	}
	
	//Metodos
	
	
}