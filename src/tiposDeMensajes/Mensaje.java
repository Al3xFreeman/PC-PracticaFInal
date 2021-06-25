package tiposDeMensajes;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	
	protected int tipo;
	protected String origen;
	protected String destino;
	protected String fichero;
	
	protected String contenido;
	
	public int getTipo() {
		return tipo;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public String getFichero() {
		return fichero;
	}
	
	public String getContenido() {
		return contenido;
	}
}
