package logic;
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
import tiposDeMensajes.mensajesCliente.Mensaje_Cerrar_Conexion;
import tiposDeMensajes.mensajesCliente.Mensaje_Conexion;
import tiposDeMensajes.mensajesCliente.Mensaje_Lista_Usuarios;
import tiposDeMensajes.mensajesCliente.Mensaje_Pedir_Fichero;

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
			
			(new OyenteServidor(socket, cliente)).start();
			
			OutputStream output = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
          
            objectOutputStream.writeObject(new Mensaje_Conexion(cliente._nombreUsuario, "servidor"));
            
            System.out.println("Mensaje de conexion enviado");
            
            boolean exit = false;
            Scanner sc= new Scanner(System.in);
            String in;
    		while(!exit) {
    			 //System.in is a standard input stream  
    			
    			System.out.println("Introduzca la orden que quiera\n 1: Consultar lista usuarios.\n 2: Pedir fichero.\n 3: Salir. ");
    			 in = sc.nextLine();
    			
    			switch(in) {
    			case "1":
    				//Mensaje lista usuarios
    	            objectOutputStream.writeObject(new Mensaje_Lista_Usuarios(cliente._nombreUsuario, "servidor"));

    				break;
    			case "2":
    				//Mensaje pedir fichero
    				System.out.println("Elija el fichero que quiera obtener:");
    				String fichero = sc.nextLine();
    				
    	            objectOutputStream.writeObject(new Mensaje_Pedir_Fichero(cliente._nombreUsuario, fichero));

    				break;
    			case "3":
    				exit = true;
    	            objectOutputStream.writeObject(new Mensaje_Cerrar_Conexion(cliente._nombreUsuario, "servidor"));

    				break;
    				
    			default:
    				System.err.println("Opción no válida.");
    			}
    		}
			
			
			
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
