package tiposDeMensajes.mensajesServidor;

import java.util.ArrayList;

import logic.Usuario;
import tiposDeMensajes.Mensaje;

public class Mensaje_Confirmacion_Lista_Usuarios extends Mensaje {
	private ArrayList<Usuario> listaUsuarios;
	
	public Mensaje_Confirmacion_Lista_Usuarios(ArrayList<Usuario> l) {
		tipo = 1;
		listaUsuarios = l;
	}
	
	public ArrayList<Usuario> getUsuarios() {
		return listaUsuarios;
	}
}
