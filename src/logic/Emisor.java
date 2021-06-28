package logic;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor  extends Thread {

	private String ip;
	private int puerto;
	private Cliente cliente;
	private String fichero;
	
	private ServerSocket serverSocket;

	
	public Emisor(String ip, int puerto, Cliente c, String f) {
		this.ip = ip;
		this.puerto = puerto;
		cliente = c;
		fichero = f;
	}

	public void run() {
		
		
		try {
			//Crear el ServerCocket
			serverSocket = new ServerSocket(puerto);
			
			//aceptar la conexion
			Socket clientSocket = serverSocket.accept();

			//Acceder al flujo de salida
			ObjectOutputStream fout;
			fout = new ObjectOutputStream(clientSocket.getOutputStream());
			
			//Enviar el fichero
			fout.writeObject(cliente.getFichero(fichero));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
