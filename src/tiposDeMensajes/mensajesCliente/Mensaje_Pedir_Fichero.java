package tiposDeMensajes.mensajesCliente;

import tiposDeMensajes.Mensaje;

public class Mensaje_Pedir_Fichero extends Mensaje {

	private String _usuario;
	
	public Mensaje_Pedir_Fichero(String u, String f) {
		tipo = 3;
		//origen = o;
//		destino = d;
		_usuario = u;
		fichero = f;
	}
	
	public String getUsuario() {
		return _usuario;
	}

}
