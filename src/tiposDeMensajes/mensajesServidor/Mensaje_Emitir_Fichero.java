package tiposDeMensajes.mensajesServidor;

import tiposDeMensajes.Mensaje;

public class Mensaje_Emitir_Fichero extends Mensaje {

	public Mensaje_Emitir_Fichero(String o, String f) {
		tipo = 2;
		origen = o;
		fichero = f;
	}
	
}
