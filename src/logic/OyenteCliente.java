package logic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import tiposDeMensajes.Mensaje;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Cerrar_Conexion;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Conexion;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Lista_Usuarios;
import tiposDeMensajes.mensajesServidor.Mensaje_Emitir_Fichero;
import tiposDeMensajes.mensajesServidor.Mensaje_Preparado_ServidorCliente;

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
			
			boolean exit = false;
			
			while (!exit) {
				
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
					String msgContents = servidor.listaUsuarios();
					
					System.out.println("Usuario ha pedido la lista de usuarios");
					
					fout.writeObject(new Mensaje_Confirmacion_Lista_Usuarios(msgContents));
					
					break;
				case 2:
					//Mensaje Cerrar Conexion
					exit = true;
					servidor.desconectaUsuario(m.getOrigen());
					
					fout.writeObject(new Mensaje_Confirmacion_Cerrar_Conexion(m.getOrigen()));
					fout.flush();
					break;
				case 3:
					//Mensaje Pedir Fichero
					Usuario usuarioPropietario = servidor.buscaUsuarioFichero(m.getFichero());
					if(usuarioPropietario == null) {
						//Usuario o archivo no encontrado
					}
					
					String usuarioPeticion = m.getOrigen();
					String fichero = m.getFichero();
					
					File file = servidor.getFile(fichero);
					
					usuarioPropietario.getfOut().writeObject(new Mensaje_Emitir_Fichero(usuarioPeticion, file));
					
					//el usuario propietario actuará como servidor, abriendo un nuevo serverSocket
					//el usuario que ha pedido el fichero se conectará y el usuario propietario
					//procederá a mandarle el archivo.
					
					break;
				case 4:
					//Mensaje Preparado ClienteServidor
					//Buscar fout1, flujo del cliente al que hay que enviar la info
					//envio fout1 mensaje MENSAJE_PREPARADP_SERVIDORCLIENTE

					servidor.buscarCliente(m.getOrigen()).writeObject(new Mensaje_Preparado_ServidorCliente(m.getIp(), m.getPuerto()));
					
					
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
