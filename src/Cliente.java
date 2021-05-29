import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	private String _nombreUsuario;
	private int _ip;
	
	//Añadir Socket y flujos?
	private static Socket socket; 
	
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente();
		
		cliente.inicioSesion();
		
		
		try {
			Socket socket = new Socket("a", 9999);
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	

	private void inicioSesion() {
		Scanner sc= new Scanner(System.in); //System.in is a standard input stream  
		
		System.out.println("Introduzca su nombre de usuario: ");
		_nombreUsuario = sc.nextLine();
		
		
				
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
