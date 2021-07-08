package logic;

import java.io.File;
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
	private Cliente cliente;
	
	public Receptor(String ip, int puerto, Cliente c) {

		this.ip = ip;
		this.puerto = puerto;
		cliente = c;
		
	}
	
	public void run() {
		

		try {
			//Crear Socket
			//System.out.println(puerto);
			socket = new Socket("localhost", puerto);
			
			//Acceder al flujo de entrada
			ObjectInputStream fin;
			fin = new ObjectInputStream(socket.getInputStream());
			
			//Recibir información
			File f = (File) fin.readObject();
			
			System.out.println("Ruta de origen de \"" + f.getName() + "\": " + f.getPath());

			cliente.addFile(cliente, f);
			
			System.out.println("Archivo \"" + f.getName() + "\" añadido!");
			
			socket.close();
			
			//Mensaje LIBERA PUERTO?
			
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
