package tiposDeMensajes.mensajesServidor;

import tiposDeMensajes.Mensaje;

public class Mensaje_Fallo extends Mensaje {

	public Mensaje_Fallo(String c) {
		
		tipo = 8;
		this.contenido = c;
	}
}
