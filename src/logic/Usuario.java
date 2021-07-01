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
//	private ArrayList<Fichero> _archivos;
	private ArrayList<File> _archivos;
	private ObjectInputStream _fin;
	private ObjectOutputStream _fout;
	
	
	public Usuario(String nombre) {
		_nombre = nombre;
		_archivos = new ArrayList<File>();
		
		File dir = new File(_nombre);
		if (!dir.exists()) {
			dir.mkdir();
		} else { //Escanea los contenidos para añadirlos a la lista de archivos
			getFiles(dir);
		}
	}
	
	
	public Usuario(String nombre, ObjectInputStream fin, ObjectOutputStream fout) {
		this(nombre);

		_fin = fin;
		_fout = fout;
		
	}
	
	public void setArchivos(ArrayList<File> archivos) {
		_archivos = archivos;
	}
	
	public ArrayList<File> getArchivos() {
		return _archivos;
	}
	
	public void vaciaArchivos() {
		_archivos.clear();
	}
	
	public void getFiles(File dir) {
		File[] contenido = dir.listFiles();
		
		for(File f : contenido)
			if(f.isFile()) 
				_archivos.add(f);
			else if (f.isDirectory())
				getFiles(f);
				
	}
	
	public String getNombre() {
		return _nombre;
	}
	
	public ObjectOutputStream getfOut() {
		return _fout;
	}
	
	public void setfOut(ObjectOutputStream out) {
		_fout = out;
	}
	
	public ObjectInputStream getfIn() {
		return _fin;
	}
	
	public void setfIn(ObjectInputStream in) {
		_fin = in;
	}
	
	public String toString() {
		String out  = "";
		out += "Usuario:" + _nombre + " ||| Archivos disponibles --> ";
		for(File f : _archivos)
			out += f.getName() + " | ";
		
		out += "\n";
		
		return out;
	}
}
