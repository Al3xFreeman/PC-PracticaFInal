package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Receptor extends Thread {

	private String ip;
	private int puerto;
	private Socket socket;
	
	public Receptor(String ip, int puerto) {

		this.ip = ip;
		this.puerto = puerto;
		
	}
	
	public void run() {
		

		try {
			//Crear Socket
			socket = new Socket("localhost", 1);
			
			//Acceder al flujo de entrada
			InputStream in = socket.getInputStream();
			ObjectInputStream fin = new ObjectInputStream(in);
			
			//Recibir información
			Fichero f = (Fichero) fin.readObject();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
