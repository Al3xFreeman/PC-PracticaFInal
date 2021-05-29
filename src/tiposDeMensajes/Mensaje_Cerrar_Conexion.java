package tiposDeMensajes;

public class Mensaje_Cerrar_Conexion extends Mensaje {
	
	public Mensaje_Cerrar_Conexion(String o, String d) {
		tipo = 2;
		origen = o;
		destino = d;
	}
	
}
