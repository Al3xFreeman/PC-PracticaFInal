package tiposDeMensajes.mensajesServidor;

import tiposDeMensajes.Mensaje;

public class Mensaje_Confirmacion_Usuarios_Registrados extends Mensaje {

	public Mensaje_Confirmacion_Usuarios_Registrados(String lista) {
		tipo = 7;
		contenido = lista;
	}
	
}
