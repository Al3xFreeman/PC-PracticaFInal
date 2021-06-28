package logic;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	private static Socket socket;
	
	private int port;
	private String ip = "1.1.1.2";
	
	//Estructuras de Usuarios
	private ArrayList<Usuario> usuarios;
	private ArrayList<String> usuariosNombres; 
	//meterle a cada usuario el fin y el fout para poder realizar el pedido de ficheros
	
	private ServerSocket serverSocket;
	
	
	//private OutputStream output = serverSocket.get
	
	public Servidor(int port) {
		this.port = port;
		
		//Cambiar por funcion cargaUsuarios que cargue todos los usuarios registrados en un fichero
		usuarios = new ArrayList<>();
		usuariosNombres = new ArrayList<>();
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

	public synchronized void cargaUsuario(Usuario u) {
		if(!usuariosNombres.contains(u.getNombre())) {
			usuarios.add(u);
			usuariosNombres.add(u.getNombre());
			
			System.out.println(u.toString());
		} else {
			System.out.println("Usuario ya estaba conectado");
		}
	}
	
	public synchronized String listaUsuarios() {		
		return usuarios.toString();
	}

	public Usuario buscaFichero(String fichero) {
		//Busca qué usuario tiene el fichero
		return null;
	}

	public void desconectaUsuario(String usuario) {
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

	public ObjectOutputStream buscarCliente(String origen) {
		//Devuelve el fOut del cliente
		
		return null;
		
	}

	
/*
		ObjectOutputStream objectOutput;
		try {
			
			objectOutput = new ObjectOutputStream(socket.getOutputStream());

			ArrayList<Fichero> listF = new ArrayList<>();

			listF.add(new Fichero());

			for (Fichero f : listF) {
				objectOutput.writeObject(f);
				objectOutput.flush();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
 */
}
