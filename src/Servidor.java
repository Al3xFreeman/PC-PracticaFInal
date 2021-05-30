import java.awt.List;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.sql.rowset.spi.SyncResolver;

public class Servidor {

	private static Socket socket;
	
	private int port;
	private String ip = "1.1.1.1";
	
	//Estructuras de Usuarios
	private ArrayList<Usuario> usuarios;
	
	private ServerSocket serverSocket;
	
	
	public Servidor(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {

		Servidor servidor = new Servidor(Integer.parseInt(args[0]));
			
		try {
			Socket clientSocket;
			servidor.serverSocket = new ServerSocket(999);
			while (true) {
				
				System.out.println("Esperando a un usuario");
				
				clientSocket = servidor.serverSocket.accept();
				clientSocket.getInputStream();
				
				(new OyenteCliente(clientSocket, servidor)).start();
				(new OyenteServidor(clientSocket)).start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	public synchronized void cargaUsuario(Usuario u) {
		usuarios.add(u);
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
