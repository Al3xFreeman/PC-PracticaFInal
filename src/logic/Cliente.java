package logic;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

import tiposDeMensajes.Mensaje;
import tiposDeMensajes.mensajesCliente.Mensaje_Actualiza_Archivos;
import tiposDeMensajes.mensajesCliente.Mensaje_Cerrar_Conexion;
import tiposDeMensajes.mensajesCliente.Mensaje_Conexion;
import tiposDeMensajes.mensajesCliente.Mensaje_Lista_Usuarios;
import tiposDeMensajes.mensajesCliente.Mensaje_Pedir_Fichero;
import tiposDeMensajes.mensajesCliente.Mensaje_Preparado_ClienteServidor;
import tiposDeMensajes.mensajesCliente.Mensaje_Usuarios_Registrados;

public class Cliente {

	String path;
	private String _nombreUsuario;
	private int _ip;
	
	private boolean[] puertosLibres;
	
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
			
			puertosLibres = new boolean[65535];
			Arrays.fill(puertosLibres, Boolean.FALSE);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int puertoLibre() { //Devuelve el primer puerto libre disponible
		for(int i = 2; i < puertosLibres.length; i++) {
			if(!puertosLibres[i]) {
				puertosLibres[i] = true;
				return i;
			}
		}
		
		return -1; //No hay puertos libres
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
          
			Usuario u = new Usuario(cliente._nombreUsuario);
			
			cliente.objectOutputStream.writeObject(new Mensaje_Conexion(u));
            
            System.out.println("Mensaje de conexion enviado");
            
            boolean exit = false;
            Scanner sc = new Scanner(System.in);
            String in;
    		while(!exit) {
    			 //System.in is a standard input stream  
    			
    			System.out.println("Introduzca la orden que quiera\n "
    					+ "1: Consultar lista usuarios.\n "
    					+ "2: Pedir fichero.\n "
    					+ "3: Actualizar ficheros disponibles.\n "
    					+ "4: Registrar nuevo usuario.\n "
    					+ "5: Lista Usuarios registrados.\n "
    					+ "6: a.\n "
    					+ "7: Salir. ");
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
    				//Actualiza Archivos
    				File dir = new File(cliente._nombreUsuario);
    				u.vaciaArchivos();
    				u.getFiles(dir);
    				
    				Mensaje_Actualiza_Archivos m = new Mensaje_Actualiza_Archivos(u, u.getArchivos());
    				m.setUsuario(u);
    				
    				cliente.objectOutputStream.writeObject(m);
    				
    				break;
    			case "4":
    				//Registra Usuario
    				
    				
    				break;
    			case "5":
    				//Usuarios registrados
    				cliente.objectOutputStream.writeObject(new Mensaje_Usuarios_Registrados());

    				break;
    			
    			case "6":
    				//Ejecuta varias acciones
    				//Sirve para probar a ejecutar multitud de comandos en serie
    				//Para comprobar que la paralielizaci´pn funciona bien
    				
    			case "7":
    				exit = true;
    				cliente.objectOutputStream.writeObject(new Mensaje_Cerrar_Conexion(cliente._nombreUsuario, "servidor"));

    				break;
    				
    			default:
    				System.err.println("Opción no válida.");
    			}
    			
    			cliente.objectOutputStream.reset();
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
		path = "_nombreUsuario/";
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

	public void addFile(Cliente c, File f) {
		//Añade el archivo al ssitema de archivos
		File target = new File(c.getName() + File.separator + f.getName());
		
		try {
			Files.copy(f.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
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
