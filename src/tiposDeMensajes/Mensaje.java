package tiposDeMensajes;

public abstract class Mensaje {
	
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
