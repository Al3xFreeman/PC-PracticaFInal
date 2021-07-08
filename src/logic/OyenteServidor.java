package logic;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import tiposDeMensajes.Mensaje;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Lista_Usuarios;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Usuarios_Registrados;

public class OyenteServidor extends Thread {

	//	Escucha continuamente el canal de comunicaicón con el servidor
	//	En un hilo diferente
	
	Socket socket;
	Cliente cliente;
	
	public OyenteServidor(Socket s, Cliente c) {
		socket = s;
		cliente = c;
	}
	
	@Override
	public void run() {
		
		try {
			
			ObjectInputStream fin;
			fin = new ObjectInputStream(socket.getInputStream());
			boolean exit = false;
			String ip;
			int puerto;
			
			while (!exit) {
				
				Mensaje m = (Mensaje) fin.readObject();
				
				switch(m.getTipo()) {
				case 0:
					System.out.println("Conexión realizada con éxito");
					break;
				case 1:
					//Mensaje Confirmacion Lista Usuarios
					System.out.println(m.getContenido());
					break;
				case 2:
					//Mensaje Emitir Fichero
					String peticion = m.getOrigen();
					File file = m.getFile();
					
					ip = "192.168.0.1";
					puerto = cliente.puertoLibre();
					
					cliente.enviaMensajePreparadoClienteServidor(cliente, peticion, ip, puerto);
					
					System.out.println("Preparando servidor P2P para enviar a " + peticion + " el archivo " + file.getName());
					
					//Crear proceso EMISOR y esperar en accept la conexion
					Thread emisor = new Emisor(ip, puerto, cliente, file);
					
					try {
						
						emisor.start();
						emisor.join();
						
					} catch (InterruptedException e) { e.printStackTrace(); }
					
					
					
					break;
				case 3:
					//Mensaje Preparado ServidorCliente
					//llega ip y puerto del propietario del fichero
					ip = m.getIp();
					puerto = m.getPuerto();
					//Crear proceso RECEPTOR
					System.out.println("Preprando conexión para recibir el archivo");
					
					Thread receptor = new Receptor(ip, puerto, cliente);
					
					try {
						
						receptor.start();
						receptor.join();
						
					} catch (InterruptedException e) { e.printStackTrace(); }
					
					
					break;
				case 4:
					//Mensaje Confirmacion CerrarConexion
					exit = true;
					cliente.desconecta(cliente);
					
					System.out.println(m.getOrigen() + " desconectado con éxito");
					
					break;
					
				case 5:
					//Mensaje_Actualiza_Archivos_Confirmacion
					System.out.println(m.getContenido());
					
					break;
					
				case 7:
					//Mensaje_Confirmacion_Usuarios_Registrados
					System.out.println(m.getContenido());
					
					break;
				case 8:
					//Mensaje de fallo
					System.out.println(m.getContenido());
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
