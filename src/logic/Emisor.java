package logic;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor  extends Thread {

	private String ip;
	private int puerto;
	private Cliente cliente;
	private File file;
	
	private ServerSocket serverSocket;

	
	public Emisor(String ip, int puerto, Cliente c, File f) {
		this.ip = ip;
		this.puerto = puerto;
		cliente = c;
		file = f;
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
			fout.writeObject(file);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
