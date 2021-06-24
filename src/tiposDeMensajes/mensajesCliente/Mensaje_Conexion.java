package tiposDeMensajes.mensajesCliente;

import tiposDeMensajes.Mensaje;

public class Mensaje_Conexion extends Mensaje {

	public Mensaje_Conexion(String o, String d) {
		tipo = 0;
		origen = o;
		destino = d;
	}

}
