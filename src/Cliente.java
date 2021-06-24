import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tiposDeMensajes.Mensaje;
import tiposDeMensajes.mensajesCliente.Mensaje_Conexion;

public class Cliente {

	private String _nombreUsuario;
	private int _ip;
	
	//Añadir Socket y flujos?
	private static Socket socket; 
		
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente();
		cliente.inicioSesion();
		
		try {
			Socket socket = new Socket("localhost", 1);
			
			(new OyenteServidor(socket)).start();
			
			OutputStream output = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
          
            objectOutputStream.writeObject(new Mensaje_Conexion(cliente._nombreUsuario, "servidor"));
            
            System.out.println("Mensaje de conexion enviado");
            
            			
			Scanner sc= new Scanner(System.in); //System.in is a standard input stream  
			
			System.out.println("Introduzca la orden que quiera\n 1: Algo.\n 2: Otra cosa.\n 3: Salir. ");
			String in = sc.nextLine();
		
			
			
			//(new OyenteServidor(socket))
			
			
			
		} catch (UnknownHostException | ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	

	private String inicioSesion() {
		Scanner sc= new Scanner(System.in); //System.in is a standard input stream  
		
		System.out.println("Introduzca su nombre de usuario: ");
		_nombreUsuario = sc.nextLine();
		
		return _nombreUsuario;
				
	}
	
	
	/*
	ObjectInputStream objectInput;
	try {
		
		objectInput = new ObjectInputStream(socket.getInputStream());
		
		Fichero f = (Fichero) objectInput.readObject();
		
		
		
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	}
	*/
}
