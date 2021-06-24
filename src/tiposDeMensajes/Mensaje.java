package tiposDeMensajes;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	
	protected int tipo;
	protected String origen;
	protected String destino;
	
	public int getTipo() {
		return tipo;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public String getDestino() {
		return destino;
	}
}
