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
import tiposDeMensajes.mensajesServidor.Mensaje_Actualiza_Archivos_Confirmacion;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Cerrar_Conexion;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Conexion;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Lista_Usuarios;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Usuarios_Registrados;
import tiposDeMensajes.mensajesServidor.Mensaje_Emitir_Fichero;
import tiposDeMensajes.mensajesServidor.Mensaje_Fallo;
import tiposDeMensajes.mensajesServidor.Mensaje_Preparado_ServidorCliente;

public class OyenteCliente extends Thread {

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
					//servidor.cargaUsuario(new Usuario(m.getOrigen(), fin, fout));
					m.getUsuario().setfIn(fin);
					m.getUsuario().setfOut(fout);
					servidor.cargaUsuario(m.getUsuario());
					System.out.println("Cliente \"" + m.getUsuario().getNombre() + "\" conectado\n");
					
					fout.writeObject(new Mensaje_Confirmacion_Conexion("algo"));
					break;
				case 1:
					//Mensaje Lista Usuarios
					String msgContents = servidor.listaUsuarios();
					
					System.out.println("\"" + m.getOrigen() + "\" ha pedido la lista de usuarios");
					
					fout.writeObject(new Mensaje_Confirmacion_Lista_Usuarios(msgContents));
					
					break;
				case 2:
					//Mensaje Cerrar Conexion
					exit = true;
					servidor.desconectaUsuario(m.getOrigen());
					
					System.out.println("Cliente \"" + m.getOrigen() + "\" desconectado\n");
					
					fout.writeObject(new Mensaje_Confirmacion_Cerrar_Conexion(m.getOrigen()));
					fout.flush();
					break;
				case 3:
					//Mensaje Pedir Fichero
					
					System.out.println("El cliente \"" + m.getOrigen() + "\" ha pedido el archivo \"" + m.getFichero() + "\"\n");
					
					Usuario usuarioPropietario = servidor.buscaUsuarioFichero(m.getFichero());
					if(usuarioPropietario == null) {
						System.out.println("\"" + m.getOrigen() + "\" ha pedido un archivo \"" + m.getFichero() + "\" que no está disponible");
						fout.writeObject(new Mensaje_Fallo("El archivo no está disponible"));
						break;
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
					System.out.println("Avisando a \"" + m.getOrigen() + "\" de que el servidor ya está listo en el pueto " + Integer.toString(m.getPuerto()) );
					
					break;
					
				case 5:
					//Mensaje Actualiza archivos
					//Llega el usuario con los archivos ya actualizados	 
					m.getArchivos();
					m.getUsuario();
					
					System.out.println("Usuario \"" + m.getUsuario().getNombre() + "\" está actualizando los ficheros");
					System.out.println("Archivos de \"" + m.getUsuario().getNombre() + "\": " + m.getArchivos().toString());
					servidor.actualizaArchivos(m.getUsuario().getNombre(), m.getArchivos());
					
					fout.writeObject(new Mensaje_Actualiza_Archivos_Confirmacion());

					break;
				/*case 6:
					//Mensaje Registra Usuario
					
					break;
				*/
				case 7: 
					//Mensaje Usuarios Registrados
					fout.writeObject(new Mensaje_Confirmacion_Usuarios_Registrados(servidor.getUsuariosRegistrados()));
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
