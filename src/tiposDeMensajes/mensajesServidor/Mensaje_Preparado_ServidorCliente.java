package tiposDeMensajes.mensajesServidor;

import tiposDeMensajes.Mensaje;

public class Mensaje_Preparado_ServidorCliente extends Mensaje {

	public Mensaje_Preparado_ServidorCliente (String ip, int puerto) {
		tipo = 3;
		this.ip = ip;
		this.puerto = puerto;
	}
	
}
