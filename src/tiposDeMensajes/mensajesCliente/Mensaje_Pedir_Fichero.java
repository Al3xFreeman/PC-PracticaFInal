package tiposDeMensajes.mensajesCliente;

import tiposDeMensajes.Mensaje;

public class Mensaje_Pedir_Fichero extends Mensaje {

	public Mensaje_Pedir_Fichero(String o, String d) {
		tipo = 3;
		origen = o;
		destino = d;
	}
	
}
