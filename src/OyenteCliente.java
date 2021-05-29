import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import tiposDeMensajes.Mensaje;

public class OyenteCliente extends Thread {
	//	Proporciona concurrencia para las sesiones de cada usuario con el servidor
	
	
	//	Run se limita a hacer:
	//		- lecturas del flujo de entrada correspondiente
	//		- realizar las acciones oportunas
	//		- devolver los resultados en forma de mensajes 
	//		  que serán enviados al usuario o usuarios involucrados.
	
	Socket socket;
	
	public OyenteCliente(Socket s) {
		socket = s;
	}
	
	@Override
	public void run() {
				
		try {
			ObjectInputStream fin = null;
			fin = new ObjectInputStream(socket.getInputStream());
			
			while (true) {
				
				Mensaje m = (Mensaje) fin.readObject();
				
				switch(m.getTipo()) {
				case 0:
					//Mensaje Conexion
					
					break;
				case 1:
					//Mensaje Lista Usuarios
					
					break;
				case 2:
					//Mensaje Cerrar Conexion
					
					break;
				case 3:
					//Mensaje Pedir Fichero
					
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
