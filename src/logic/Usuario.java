package logic;
import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
	private int _id;
	private int _ip;
	private String _nombre;
	private ArrayList<Fichero> _archivos;
	
	public Usuario(String nombre) {
		_nombre = nombre;
	}
	
	public String toString() {
		return "Usuario:" + _nombre;
	}
}
