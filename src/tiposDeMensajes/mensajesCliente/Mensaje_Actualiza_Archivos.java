package tiposDeMensajes.mensajesCliente;

import java.io.File;
import java.util.ArrayList;

import logic.Usuario;
import tiposDeMensajes.Mensaje;

public class Mensaje_Actualiza_Archivos extends Mensaje {

	public Mensaje_Actualiza_Archivos(Usuario u, ArrayList<File> files) {
		tipo = 5;
		user = u;
		archivos = files;
	}
	
}
