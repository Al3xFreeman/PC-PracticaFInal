package tiposDeMensajes.mensajesServidor;

import tiposDeMensajes.Mensaje;

public class Mensaje_Confirmacion_Cerrar_Conexion extends Mensaje {

	public Mensaje_Confirmacion_Cerrar_Conexion(String o) {
		tipo = 4;
		origen = o;
	}
	
}
