import java.awt.List;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	private static Socket socket;
	
	private int port;
	private String ip = "1.1.1.1";
	
	//Estructuras de Usuarios
	private ArrayList<Usuario> usuarios;
	
	private ServerSocket serverSocket;
	
	
	public Servidor(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {

		Servidor servidor = new Servidor(Integer.parseInt(args[0]));
			
		try {
			Socket s;
			while (true) {
				
				s = servidor.serverSocket.accept();
				
				(new OyenteCliente(s)).start();
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	private synchronized void cargaUsuario() {
		
	}
	

	
/*
		ObjectOutputStream objectOutput;
		try {
			
			objectOutput = new ObjectOutputStream(socket.getOutputStream());

			ArrayList<Fichero> listF = new ArrayList<>();

			listF.add(new Fichero());

			for (Fichero f : listF) {
				objectOutput.writeObject(f);
				objectOutput.flush();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
 */
}
