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
	private String peticion;
	
	private ServerSocket serverSocket;

	
	public Emisor(String ip, int puerto, Cliente c, File f, String pet) {
		this.ip = ip;
		this.puerto = puerto;
		cliente = c;
		file = f;
		peticion = pet;
	}

	public void run() {
		
		
		try {
			//Crear el ServerCocket
			
			//Añadir un delay artificial
			/*
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			serverSocket = new ServerSocket(puerto);
			cliente.enviaMensajePreparadoClienteServidor(cliente, peticion, ip, puerto);

			//System.out.println(puerto);
			//aceptar la conexion
			Socket clientSocket = serverSocket.accept();

			//Acceder al flujo de salida
			ObjectOutputStream fout;
			fout = new ObjectOutputStream(clientSocket.getOutputStream());
			
			//Enviar el fichero
			fout.writeObject(file);
			
			System.out.println("Archivo " + file.getName() + " enviado");
			
			fout.flush();
			serverSocket.close();
			
			cliente.liberaPuerto(puerto);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
