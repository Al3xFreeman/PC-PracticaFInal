package tiposDeMensajes;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;

import logic.Usuario;

public abstract class Mensaje implements Serializable {
	
	protected int tipo;
	protected String origen;
	protected String destino;
	protected String fichero;
	protected File file;
	
	protected ArrayList<File> archivos;
	
	protected Usuario user;
	
	protected String ip;
	protected int puerto;
	
	protected String contenido;
	
	public void setArchivos(ArrayList<File> a) {
		archivos = a;
	}
	
	public ArrayList<File> getArchivos() {
		return archivos;
	}
	
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
	
	public Usuario getUsuario() {
		return user;
	}
	
	public void setUsuario(Usuario u) {
		user = u;
	}
	
	
}
