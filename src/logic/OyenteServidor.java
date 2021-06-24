package logic;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import tiposDeMensajes.Mensaje;
import tiposDeMensajes.mensajesServidor.Mensaje_Confirmacion_Lista_Usuarios;

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
			
			while (true) {
				
				Mensaje m = (Mensaje) fin.readObject();
				
				switch(m.getTipo()) {
				case 0:
					System.out.println("Conexión realizada con éxito");
					break;
				case 1:
					//Mensaje Confirmacion Lista Usuarios
					Mensaje_Confirmacion_Lista_Usuarios conf = (Mensaje_Confirmacion_Lista_Usuarios) m;
					System.out.println(conf.getUsuarios());
					break;
				case 2:
					//Mensaje Emitir Fichero
					
					break;
				case 3:
					//Mensaje Preparado ServidorCliente
					
					break;
				case 4:
					//Mensaje Confirmacion CerrarConexion
					
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
