package Comun;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.ListIterator;

import javafx.scene.control.TextArea;

public class Biblioteca{
	private ArrayList<Libro> libros;
	private ArrayList<Usuario> usuarios;
	
	//Constructores
	public Biblioteca() {
		libros = new ArrayList<Libro>();
		usuarios = new ArrayList<Usuario>();
	}
	public Biblioteca(ArrayList<Libro> libros) {
		this.libros = libros;
		this.usuarios = new ArrayList<Usuario>();
	}
	public Biblioteca(ArrayList<Libro> libros, ArrayList<Usuario> usuarios) {
		this.libros = libros;
		this.usuarios = usuarios;
	}

	//Getter & Setter
	public void setLibros(ArrayList<Libro> listaLibros) {
		this.libros = listaLibros;
	}
	public ArrayList<Libro> getListaLibros(){
		return libros;
	}
	public void setUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.usuarios = listaUsuarios;
	}
	public ArrayList<Usuario> getUsuarios(){
		return usuarios;
	}
	
	//METODOS LIBROS
	public Libro buscarLibro(String nombreLibro) {	//Busca libro, cualquier copia
		if(libros != null && !libros.isEmpty()) {
			ListIterator<Libro> iterador = libros.listIterator();
			while(iterador.hasNext()) {
				Libro libroItr = iterador.next();
				if(libroItr.getTitulo().equalsIgnoreCase(nombreLibro)) {
					return libroItr;
				}
			}
		}
		
		return null;
	}
	
	public boolean buscarLibro(int codNum) {		//Para generacion de codigo unico
		if(libros != null && !libros.isEmpty()) {
			ListIterator<Libro> iterador = libros.listIterator();
			while(iterador.hasNext()) {
				Libro actual = iterador.next();
				String[] codAlfaNumerico = actual.getCode().split("-");
				int num = Integer.parseInt(codAlfaNumerico[1]);
				if(num == codNum) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Libro buscarLibro(String codTipo, String codNum) {
		if(libros != null && !libros.isEmpty()) {
			String codigo = codTipo + "-" + codNum;
			ListIterator<Libro> iterador = libros.listIterator();
			while(iterador.hasNext()) {
				Libro libroItr = iterador.next();
				if(libroItr.getCode().equals(codigo)) {
					return libroItr;
				}
			}
		}
		return null;
	}
	
	public void modificarLibro(String codTipo, String codNum, String campo, Object dato) {
		if(buscarLibro(codTipo,codNum) != null) {
			buscarLibro(codTipo,codNum).modificarLibro(campo, dato);
		}
	}
	
	public boolean agregarLibro(Libro nuevoLibro) {
		String[] codigo = nuevoLibro.getCode().split("-");
		if(buscarLibro(codigo[0],codigo[1]) == null) {
			libros.add(nuevoLibro);
			return true;
		}
		return false;
	}
	
	public boolean eliminarLibro(String codTipo, String codNum) {	//Elimina libro especifico
		String codigo = codTipo + "-" + codNum;
		Libro buscado = buscarLibro(codTipo,codNum);
			if(buscado != null && buscado.getCode().equals(codigo)) {
				libros.remove(buscado);
				return true;
			}
		return false;
	}
	
	public ArrayList<String> listaLibros(String codAlfa, String titulo){
		if(libros != null & !libros.isEmpty()) {
			ArrayList<String> listLibros = new ArrayList<String>();
			ListIterator<Libro> itrLibros = libros.listIterator();
			while(itrLibros.hasNext()) {
				Libro actual = itrLibros.next();
				String datos = new String();
				
				if(codAlfa != null && !codAlfa.isEmpty() && titulo != null) {
					if(actual.getCode().contains(codAlfa) && actual.getTitulo().contains(titulo)) {
						datos += actual.getCode() + " " + actual.getTitulo();
						listLibros.add(datos);
					}
				}
				else if(codAlfa != null && !codAlfa.isEmpty() && titulo == null){
					if(actual.getCode().contains(codAlfa)) {
						datos += actual.getCode() + " " + actual.getTitulo();
						listLibros.add(datos);						
					}
				}
				else if((codAlfa == null || codAlfa.isEmpty()) && titulo != null) {
					if(actual.getTitulo().contains(titulo)) {
						datos += actual.getCode() + " " + actual.getTitulo();
						listLibros.add(datos);
					}
				}
			}
			return listLibros;
		}
		
		return null;
	}
	
	public ArrayList<String> listaLibros(String nombre){
		if(libros != null && !libros.isEmpty()) {
			ArrayList<String> listLibros = new ArrayList<String>();
			ListIterator<Libro> itrLibros = libros.listIterator();
			while(itrLibros.hasNext()) {
				Libro actual = itrLibros.next();
				if(actual.getTitulo().contains(nombre))
					listLibros.add(actual.infoLibro());
			}
		}
		return null;
	}
	
	public ArrayList<String> listaLibros(){
		if(libros != null && !libros.isEmpty()) {
			ArrayList<String> listLibros = new ArrayList<String>(libros.size());
			ListIterator<Libro> itrLibros = libros.listIterator();
			while(itrLibros.hasNext()) {
				Libro actual = itrLibros.next();
				listLibros.add(actual.infoLibro());
			}
		}
		return null;
	}
	
	public void reporteLibros(String fileOut) throws IOException {
		ArrayList<String> lista = listaLibros();
		if(lista != null && !lista.isEmpty()) {
			ListIterator<String> iterador = lista.listIterator();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));
			while(iterador.hasNext()) {
				bw.write(iterador.next());
			}
			bw.close();
		}
	}
	
	public void reporteLibros(TextArea texto) {
		ArrayList<String> lista = listaLibros();
		if(lista != null && lista.isEmpty()) {
			ListIterator<String> iterador = lista.listIterator();
			while(iterador.hasNext()) {
				texto.appendText(iterador.next());
			}
		}
	}
	
	//METODOS USUARIOS
	
	public boolean agregarUsuario(Usuario nuevoUsuario) {
		if(buscarUsuario(nuevoUsuario.getRut()) == null) {
			usuarios.add(nuevoUsuario);
			return true;
		}
		return false;
	}
	
	public ArrayList<Usuario> buscarUsuarios(String nombre, String apellido) {
		if(usuarios != null && !usuarios.isEmpty()) {
			ArrayList<Usuario> users = new ArrayList<Usuario>();
			ListIterator<Usuario> iterador = usuarios.listIterator();
			while(iterador.hasNext()) {
				Usuario usuarioItr = (Usuario)iterador.next();
				if(!nombre.isEmpty() && apellido.isEmpty()) {
					if(usuarioItr.getNombres().contains(nombre)) {
						users.add(usuarioItr);
					}
				}
				else if(nombre.isEmpty() && !apellido.isEmpty()) {
					if(usuarioItr.getApellidos().contains(apellido)) {
						users.add(usuarioItr);
					}
				}
				else if(!nombre.isEmpty() && !apellido.isEmpty()){
					if(usuarioItr.getNombres().contains(nombre) && usuarioItr.getApellidos().contains(apellido)) {
						users.add(usuarioItr);
					}
				}
			}
			
			
			return users;
		}
		return null;
	}
	
	public Usuario buscarUsuario(String rutBuscado) {
		if(usuarios != null && !usuarios.isEmpty()) {
			ListIterator<Usuario> iterador = usuarios.listIterator();
			while(iterador.hasNext()) {
				Usuario usuarioItr = (Usuario)iterador.next();
				if(usuarioItr.getRut().equals(rutBuscado)) {
					return usuarioItr;
				}
			}
		}
		return null;
	}
	
	public void modificarUsuario(String rut, String campo, Object dato) {
		if(buscarUsuario(rut) != null) {
			buscarUsuario(rut).modificarUsuario(campo,dato);
		}
	}
	
	public boolean eliminarUsuario(String rutEliminar) {
		Usuario buscado = buscarUsuario(rutEliminar);
		if(buscado != null && buscado.getRut().equals(rutEliminar)) {
			usuarios.remove(buscado);
			return true;
		}
		return false;
	}
	
	public ArrayList<String> listaUsuarios(){
		if(usuarios != null) {
			ArrayList<String> listUsuarios = new ArrayList<String>(usuarios.size());
			ListIterator<Usuario> itrUsers = usuarios.listIterator();
			while(itrUsers.hasNext()) {
				listUsuarios.add(itrUsers.next().infoUsuario());
			}
			return listUsuarios;
		}
		return null;
	}
	
	public void reporteUsuarios(String fileOut) throws IOException {
		ArrayList<String> usuarios = listaUsuarios();
		if(usuarios != null && !usuarios.isEmpty()) { 
			ListIterator<String> iterador = usuarios.listIterator();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));
			while(iterador.hasNext()) {
				bw.write(iterador.next());
			}
			bw.close();
		}
	}
	
	public void reporteUsuarios(TextArea texto)  {
		ArrayList<String> lista = listaUsuarios();
		if(lista != null && !lista.isEmpty()) {
			ListIterator<String> iterador = lista.listIterator();
			while(iterador.hasNext()) {
				texto.appendText(iterador.next() + "\n");
			}
		}
	}
}