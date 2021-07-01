package tiposDeMensajes.mensajesCliente;

import logic.Usuario;
import tiposDeMensajes.Mensaje;

public class Mensaje_Conexion extends Mensaje {

	public Mensaje_Conexion(Usuario u) {
		tipo = 0;
		user = u;
	}

}
