package tiposDeMensajes;

import java.io.File;
import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	
	protected int tipo;
	protected String origen;
	protected String destino;
	protected String fichero;
	protected File file;
	
	protected String ip;
	protected int puerto;
	
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
	
	public File getFile() {
		return file;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public String getIp() {
		return ip;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	
}
