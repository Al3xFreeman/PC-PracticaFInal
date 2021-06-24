import java.awt.List;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.sql.rowset.spi.SyncResolver;

public class Servidor {

	private static Socket socket;
	
	private int port;
	private String ip = "1.1.1.2";
	
	//Estructuras de Usuarios
	private ArrayList<Usuario> usuarios;
	
	private ServerSocket serverSocket;
	
	
	//private OutputStream output = serverSocket.get
	
	public Servidor(int port) {
		this.port = port;
		
		//Cambiar por funcion cargaUsuarios que cargue todos los usuarios registrados en un fichero
		usuarios = new ArrayList<>();
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
		
		usuarios.add(u);
		
		System.out.println(u.toString());
	}
	
	public synchronized void listaUsuarios() {
		
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
