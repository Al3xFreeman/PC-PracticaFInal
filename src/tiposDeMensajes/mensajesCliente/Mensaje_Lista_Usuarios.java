package tiposDeMensajes.mensajesCliente;

import tiposDeMensajes.Mensaje;

public class Mensaje_Lista_Usuarios extends Mensaje {

	public Mensaje_Lista_Usuarios(String o, String d) {
		tipo = 1;
		origen = o;
		destino = d;
	}

}
