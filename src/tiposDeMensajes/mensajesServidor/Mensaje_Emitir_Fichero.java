package tiposDeMensajes.mensajesServidor;

import java.io.File;

import tiposDeMensajes.Mensaje;

public class Mensaje_Emitir_Fichero extends Mensaje {

	public Mensaje_Emitir_Fichero(String o, File f) {
		tipo = 2;
		origen = o;
		file = f;
	}
	
}
