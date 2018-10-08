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
	
	/*
	 * Busca cualquier copia del libro, usando el titulo recibido por parametro.
	 * Retorna una copia si la encontro.
	 */
	@Deprecated 
	/* Este metodo no es usado en el programa, recordatorio de eliminar si no genera problemas
	 * posteriores.
	 */
	public Libro buscarLibro(String nombreLibro) {
		if(libros != null && !libros.isEmpty()) {
			ListIterator<Libro> iterador = libros.listIterator(); //Para recorrer la lista
			while(iterador.hasNext()) {
				Libro libroItr = iterador.next();
				if(libroItr.getTitulo().equalsIgnoreCase(nombreLibro)) {
					return libroItr;
				}
			}
		}
		
		return null;
	}
	
	/* Busca si el codigo generado ya ha sido ocupado por otro libro,
	 * recibe el codigo separado en parte alfabetica y en parte numerica, siendo
	 * la ultima generada aleatoriamente. Retorna si encuentra coincidencias.
	 */
	public boolean buscarLibro(int codNum, String codAlfa) {
		if(libros != null && !libros.isEmpty()) {
			ListIterator<Libro> iterador = libros.listIterator();
			while(iterador.hasNext()) {
				Libro actual = iterador.next();
				if(actual.getCode() != null) {
					//Se separa el codigo del libro analizado para hacer coincidir con los
					//argumentos de la función.
					String[] codAlfaNumerico = actual.getCode().split("-");
					int num = Integer.parseInt(codAlfaNumerico[1]);
					if(num == codNum && codAlfa.equals(codAlfaNumerico[0])) {
						return true;
					}					
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Busca un libro especifico por codigo recibido en strings separados.
	 * Recibe el codigo del libro que se quiere buscar.
	 * Retorna el libro si lo encuentra o null en caso contrario.
	 */
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
	
	/*
	 * Agrega un libro a la biblioteca, primero busca si un libro con el mismo codigo existe,
	 * si no existe, lo agrega a la biblioteca y retorna true, de lo contrario, retorna false.
	 * Recibe el nuevo libro a agregar.
	 */
	public boolean agregarLibro(Libro nuevoLibro) {
		String[] codigo = nuevoLibro.getCode().split("-");
		if(buscarLibro(codigo[0],codigo[1]) == null) {
			libros.add(nuevoLibro);
			return true;
		}
		return false;
	}
	
	/*
	 * Elimina un libro de la biblioteca, si es que existe en ésta. Busca el libro especifico 
	 * y si lo encuentra, lo elimina de la biblioteca.
	 * Recibe el codigo del libro especifico en 2 strings.
	 */
	public boolean eliminarLibro(String codTipo, String codNum) {
		String codigo = codTipo + "-" + codNum;
		Libro buscado = buscarLibro(codTipo,codNum);
			if(buscado != null && buscado.getCode().equals(codigo)) {
				libros.remove(buscado);
				return true;
			}
		return false;
	}
	
	/*
	 * Crea una arraylist de copias de un libro con los datos de los libros obtenidos.
	 * Recibe la parte alfabetica del codigo que indica el tema y parte del titulo del libro.
	 * Retorna la arraylist creada, con datos si encontro o vacia si no encontro libros.
	 */
	public ArrayList<String> listaLibros(String codAlfa, String titulo){
		if(libros != null & !libros.isEmpty()) {
			ArrayList<String> listLibros = new ArrayList<String>();
			ListIterator<Libro> itrLibros = libros.listIterator();
			while(itrLibros.hasNext()) {
				Libro actual = itrLibros.next();
				String datos = new String();
				
				/*
				 * Casos de busqueda:
				 *Si el tema existe y es valido y hay un titulo,
				 *agrega todas las coincidencias de tema y titulo.
				 */
				if(codAlfa != null && !codAlfa.isEmpty() && titulo != null) {
					if(actual.getCode().contains(codAlfa) && actual.getTitulo().contains(titulo)) {
						datos += actual.getCode() + " " + actual.getTitulo();
						listLibros.add(datos);
					}
				}
				/*
				 * Si el tema existe y es valido pero no hay titulo,
				 * agrega todos los libros con tema coincidente.
				 */
				else if(codAlfa != null && !codAlfa.isEmpty() && titulo == null){
					if(actual.getCode().contains(codAlfa)) {
						datos += actual.getCode() + " " + actual.getTitulo();
						listLibros.add(datos);						
					}
				}
				/*
				 * Si el codigo no existe o no es valido pero hay un titulo,
				 * agrega todas las coincidencias con el titulo.
				 */
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
	
	/*
	 * Crea una arraylist con los datos de los libros que contienen
	 * el string nombre en su titulo. Retorna la arraylist si existen libros o null si no
	 * existen libros en la biblioteca
	 */
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
	
	/*
	 * Crea y retorna una arraylist de string con los datos de todos los libros de la biblioteca.
	 */
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
	
	/*
	 * Escribe los datos de todos los libros de la biblioteca en un archivo ".txt".
	 * Recibe la dirección del archivo.
	 */
	@Deprecated
	/*
	 * Metodo no usado en el programa, recordatorio de eliminar si no causa problemas
	 * posteriores.
	 */
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
	
	/*
	 * Recibe un area de texto de ventana y la llena con la informacion de los libros
	 * de la biblioteca.
	 */
	@Deprecated
	/*
	 * Metodo no es usado en el proyecto, recordatorio de eliminar si no causa problemas
	 * posteriores.
	 */
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
	
	/*
	 * Agrega un usuario a la biblioteca, si es que este no esta ya registrado en ésta.
	 * Recibe el usuario a ingresar y retorna true si lo agrega y false en caso contrario.
	 */
	public boolean agregarUsuario(Usuario nuevoUsuario) {
		if(buscarUsuario(nuevoUsuario.getRut()) == null) {
			usuarios.add(nuevoUsuario);
			return true;
		}
		return false;
	}
	
	/*
	 * Crea y retorna una arraylist de usuarios con coincidencias de nombre y/o apellido.
	 * Recibe nombre y apellido a buscar.
	 */
	public ArrayList<Usuario> buscarUsuarios(String nombre, String apellido) {
		if(usuarios != null && !usuarios.isEmpty()) {
			ArrayList<Usuario> users = new ArrayList<Usuario>();
			ListIterator<Usuario> iterador = usuarios.listIterator();
			while(iterador.hasNext()) {
				Usuario usuarioItr = (Usuario)iterador.next();
				/*
				 * Casos de busqueda:
				 * Si existe el nombre y no el apellido, ve si el usuario contiene
				 * parte del string nombre en su nombre.
				 */
				if(!nombre.isEmpty() && apellido.isEmpty()) {
					if(usuarioItr.getNombres().contains(nombre)) {
						users.add(usuarioItr);
					}
				}
				/*
				 * Si existe apellido y no el nombre, ve si el usuario contiene
				 * parte del string apellido en su apellido.
				 */
				else if(nombre.isEmpty() && !apellido.isEmpty()) {
					if(usuarioItr.getApellidos().contains(apellido)) {
						users.add(usuarioItr);
					}
				}
				/*
				 * Si ambos campos existen busca si el usuario contiene parte de ambos
				 * strings dentro de su nombre y apellido.
				 */
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
	
	/*
	 * Busca un usuario dentro de la biblioteca.
	 * Recibe el rut del usuario buscado y retorna el usuario si lo encuentra,
	 * de lo contrario retorna null.
	 */
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
	
	/*
	 * Elimina un usuario de la biblioteca.
	 * Recibe el rut del usuario y retorna true si lo elimina o false en caso contrario.
	 */
	public boolean eliminarUsuario(String rutEliminar) {
		Usuario buscado = buscarUsuario(rutEliminar);
		if(buscado != null && buscado.getRut().equals(rutEliminar)) {
			usuarios.remove(buscado);
			return true;
		}
		return false;
	}
	
	/*
	 * Crea y retorna un ArrayList de strings con la información de todos los usuarios.
	 */
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
	
	/*
	 * Escribe los datos de todos los usuarios de la biblioteca en un archivo.
	 * Recibe la dirección del archivo.
	 */
	@Deprecated
	/*
	 * Metodo no usado en el programa, recordatorio de eliminar si no causa problemas
	 * posteriores.
	 */
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
	
	/*
	 * Recibe un area de texto de ventana y lo llena con la información de todos
	 * los usuarios de la biblioteca.
	 */
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