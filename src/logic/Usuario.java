package logic;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
	private int _id;
	private int _ip;
	private String _nombre;
	private ArrayList<Fichero> _archivos;
	private ObjectInputStream _fin;
	private ObjectOutputStream _fout;
	
	public Usuario(String nombre, ObjectInputStream fin, ObjectOutputStream fout) {
		_nombre = nombre;
		//_fin = fin;
		//_fout = fout;	
		
		File directory = new File(_nombre);
		if (! directory.exists()){
			directory.mkdir();
		        // If you require it to make the entire directory path including parents,
		        // use directory.mkdirs(); here instead.
		    }
		
	}
	
	public String getNombre() {
		return _nombre;
	}
	
	public String toString() {
		return "Usuario:" + _nombre;
	}
}
