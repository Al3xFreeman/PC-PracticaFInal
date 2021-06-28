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
import tiposDeMensajes.mensajesCliente.Mensaje_Preparado_ClienteServidor;

public class Cliente {

	private String _nombreUsuario;
	private int _ip;
	
	//Añadir Socket y flujos?
	private Socket socket; 
	
	public Socket getSocket() {
		return socket;
	}
	
	public Cliente(Socket s) {
		socket = s;
	}
	
	public Cliente() {
		try {
			socket = new Socket("localhost", 1);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public OutputStream output;
	public ObjectOutputStream objectOutputStream;
	
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente();
		cliente.inicioSesion();
		
		try {
			
			(new OyenteServidor(cliente.getSocket(), cliente)).start();
			
			cliente.output = cliente.getSocket().getOutputStream();
			cliente.objectOutputStream = new ObjectOutputStream(cliente.output);
          
            cliente.objectOutputStream.writeObject(new Mensaje_Conexion(cliente._nombreUsuario, "servidor"));
            
            System.out.println("Mensaje de conexion enviado");
            
            boolean exit = false;
            Scanner sc = new Scanner(System.in);
            String in;
    		while(!exit) {
    			 //System.in is a standard input stream  
    			
    			System.out.println("Introduzca la orden que quiera\n 1: Consultar lista usuarios.\n 2: Pedir fichero.\n 3: Salir. ");
    			 in = sc.nextLine();
    			
    			switch(in) {
    			case "1":
    				//Mensaje lista usuarios
    				cliente.objectOutputStream.writeObject(new Mensaje_Lista_Usuarios(cliente._nombreUsuario, "servidor"));

    				break;
    			case "2":
    				//Mensaje pedir fichero
    				System.out.println("Elija el fichero que quiera obtener:");
    				String fichero = sc.nextLine();
    				
    				cliente.objectOutputStream.writeObject(new Mensaje_Pedir_Fichero(cliente._nombreUsuario, fichero));

    				break;
    			case "3":
    				exit = true;
    				cliente.objectOutputStream.writeObject(new Mensaje_Cerrar_Conexion(cliente._nombreUsuario, "servidor"));

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



	public void desconecta(Cliente c) {
		try {
			c.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void enviaMensajePreparadoClienteServidor(Cliente cliente, String peticion, String ip, int puerto) {
		try {
			cliente.objectOutputStream.writeObject(new Mensaje_Preparado_ClienteServidor(peticion, ip, puerto));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Fichero getFichero(String fichero) {
		//Busca el fichero a devolver
		//En la carpeta llamada _nombreUsuario -> el nombre de fichero
		
		//Contruye el objeto de tipo Fichero
		return null;
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
