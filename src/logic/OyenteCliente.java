package logic;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import tiposDeMensajes.Mensaje;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Conexion;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Lista_Usuarios;

public class OyenteCliente extends Thread {
	//	Proporciona concurrencia para las sesiones de cada usuario con el servidor
	
	
	//	Run se limita a hacer:
	//		- lecturas del flujo de entrada correspondiente
	//		- realizar las acciones oportunas
	//		- devolver los resultados en forma de mensajes 
	//		  que serán enviados al usuario o usuarios involucrados.
	
	Socket clientSocket;
	Servidor servidor;
	
	public OyenteCliente(Socket clientS, Servidor servInstance) {
		clientSocket = clientS;
		servidor = servInstance;
	}
		
	@Override
	public void run() {
				
		try {
			ObjectInputStream fin;
			fin = new ObjectInputStream(clientSocket.getInputStream());
			
			ObjectOutputStream fout;
			fout = new ObjectOutputStream(clientSocket.getOutputStream());
			
			while (true) {
				
				Mensaje m = (Mensaje) fin.readObject();
				
				switch(m.getTipo()) {
				case 0:
					//Mensaje Conexion
					servidor.cargaUsuario(new Usuario(m.getOrigen(), fin, fout));
					System.out.println("Cliente conectado\n");
					
					fout.writeObject(new Mensaje_Confirmacion_Conexion("algo"));
					
					break;
				case 1:
					//Mensaje Lista Usuarios
					ArrayList<Usuario> listaUsuarios = servidor.listaUsuarios();
					
					System.out.println("Usuario ha pedido la lista de usuarios");
					
					fout.writeObject(new Mensaje_Confirmacion_Lista_Usuarios(listaUsuarios));
					
					break;
				case 2:
					//Mensaje Cerrar Conexion
					
					break;
				case 3:
					//Mensaje Pedir Fichero
					Usuario usuarioPropietario = servidor.buscaFichero(m.getFichero());
					
					//el usuario propietario actuará como servidor, abriendo un nuevo serverSocket
					//el usuario que ha pedido el fichero se conectará y el usuario propietario
					//procederá a mandarle el archivo.
					
					break;
				case 4:
					//Mensaje Preparadp ClienteServidor
					
					break;
				default:
					System.out.println("¡Mensaje no válido!");
						
				}
				
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		 
		
	}
}
