package logic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Servidor {

	private static Socket socket;
	
	private int port;
	private String ip = "1.1.1.2";
	
	//Estructuras de Usuarios
	private ArrayList<Usuario> usuarios;
	private ArrayList<String> usuariosNombres; 
	private ArrayList<String> usuariosRegistrados;
	
	private ServerSocket serverSocket;
	
	private final Semaphore addUsuarioRegistrado = new Semaphore(1);
	private final Semaphore conectaUsuario = new Semaphore(1);
	private volatile boolean entraRegistrado = false;
	
	
	
	public Servidor(int port) {
		this.port = port;
		
		usuarios = new ArrayList<>();
		usuariosNombres = new ArrayList<>();
		
		usuariosRegistrados = new ArrayList<>();
		cargaUsuariosRegistrados();
		
		System.out.println(usuariosRegistrados.toString());
	}
	
	//Al iniciarse el servidor carga la lista de todos los usuarios registrados en el sistema
	private void cargaUsuariosRegistrados() {
		
		BufferedReader reader;
		
		try {
			
			reader = new BufferedReader(new FileReader("users.txt"));
			
			String line = reader.readLine();
			
			while (line != null) {
				usuariosRegistrados.add(line);
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Devuelve la lista completa de usuarios registrados.
	public String getUsuariosRegistrados() {
		return usuariosRegistrados.toString();
	}
	
	public static void main(String[] args) {

		Servidor servidor = new Servidor(Integer.parseInt(args[0]));
			
		try {
			Socket clientSocket;
			servidor.serverSocket = new ServerSocket(1);
			while (true) {
				
				System.out.println("Esperando a un usuario");
				
				clientSocket = servidor.serverSocket.accept();
				
				System.out.println("Conexión establecida con un cliente" + clientSocket);
				
				(new OyenteCliente(clientSocket, servidor)).start();
				
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	//Registra un usuario "usuario" a la lista de usuarios registrados.
	public void addUsuarioRegistrado(String usuario) {
		try
		{
			addUsuarioRegistrado.acquire();
			
			System.out.println("Registrando nuevo usuario: " + usuario);
			
			Thread.sleep(10000);
			
			String filename= "users.txt";
		    FileWriter fw = new FileWriter(filename, true); //the true will append the new data
		    fw.write(usuario + "\n"); //appends the string to the file
		    fw.close();
		    
		    addUsuarioRegistrado.release();
		}
		catch(IOException e)
		{
		    System.err.println("IOException: " + e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//añade un usuario "u" a la lista de usuarios conectados
	public void cargaUsuario(Usuario u) {
		
		//Lock para que solo se compruebe si está registrado o no uno a la vez
		while(entraRegistrado) {}
		entraRegistrado = true;
		boolean registrado = usuariosRegistrados.contains(u.getNombre());
		usuariosRegistrados.add(u.getNombre());
		entraRegistrado = false;
		
		if(!registrado) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addUsuarioRegistrado(u.getNombre());
		}

		
		//Semáforo para que solo se añada un usuario a la vez a la base de  datos
		try {
			conectaUsuario.acquire();
		} catch (InterruptedException e) { e.printStackTrace();	}
		
		if(!usuariosNombres.contains(u.getNombre())) {
			usuarios.add(u);
			usuariosNombres.add(u.getNombre());
			
			System.out.println(u.toString());
		} else {
			System.out.println("Usuario ya estaba conectado");
		}
		
		conectaUsuario.release();
	}
	
	//Devuelve la lista de usuarios conectados y sus respectivos ficheros disponibles.
	public String listaUsuarios() {		
		return usuarios.toString();
	}

	//Monitor para actualizar los archivos disponibles para descargar de un usuario.
	public synchronized void actualizaArchivos(String usuario, ArrayList<File> archivos) {
		//Probar a buscar el usuario con usuarios.indexOf(u)
		for(Usuario u : usuarios) {
			if(u.getNombre().equals(usuario))
				u.setArchivos(archivos);
		}
	}
	
	//Devuelve el usuario que contenga el fichero "fichero".
	public Usuario buscaUsuarioFichero(String fichero) {
		for(Usuario u : usuarios)
			for(File f : u.getArchivos())
				if(f.getName().equals(fichero))
					return u;
		
		return null;
	}

	//Desconecta el usuario con nombre "usuario".
	public synchronized void desconectaUsuario(String usuario) {
		Usuario desconectar = null;
		
		usuariosNombres.remove(usuario);
		
		for(Usuario u : usuarios) {
			if(u.getNombre().equals(usuario)) {
				desconectar = u;
				
			}
		}
		if (desconectar != null) {
			usuarios.remove(desconectar);
			System.out.println("Usuario " + usuario + " eliminado de la lista de usuarios conectados");
		} else {
			System.out.println("El usuario no se encuentra conectado");
		}
	}

	//Devuelve el Usuario con el nombre "user".
	public ObjectOutputStream buscarCliente(String user) {

		for(Usuario u : usuarios)
			if(u.getNombre().equals(user))
				return u.getfOut();
		
		return null;
		
	}

	
	//Devuelve el fichero con el nombre "fichero".
	public File getFile(String fichero) {
		for(Usuario u : usuarios)
			for(File f : u.getArchivos())
				if(f.getName().equals(fichero))
					return f;
		return null;
	}

}
