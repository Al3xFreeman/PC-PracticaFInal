package tiposDeMensajes.mensajesServidor;

import java.io.Serializable;
import java.util.ArrayList;

import logic.Usuario;
import tiposDeMensajes.Mensaje;

public class Mensaje_Confirmacion_Lista_Usuarios extends Mensaje implements Serializable {
	private ArrayList<String> listaUsuarios;
	
	public Mensaje_Confirmacion_Lista_Usuarios(ArrayList<String> l) {
		tipo = 1;
		listaUsuarios = l;
	}
	
	public ArrayList<String> getUsuarios() {
		return listaUsuarios;
	}
}
