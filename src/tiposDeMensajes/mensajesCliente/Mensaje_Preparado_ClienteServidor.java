package tiposDeMensajes.mensajesCliente;

import tiposDeMensajes.Mensaje;

public class Mensaje_Preparado_ClienteServidor extends Mensaje {

	public Mensaje_Preparado_ClienteServidor(String o, String d) {
		tipo = 4;
		origen = o;
		destino = d;
	}

}
