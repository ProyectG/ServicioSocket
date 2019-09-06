package cl.proyectg.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Archivos {

	public String guardarArchivo(String archivo, String ubicacion)
	{
		BufferedWriter lapiz;
		try {
			lapiz = new BufferedWriter(new FileWriter(ubicacion));
			lapiz.write(archivo);
			lapiz.close();
			System.out.println("Pude guardar el archivo, uff, cuanta informacion.");
			return "Archivo guardado correctamente.";
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo"+e);
			return "Hubo un problema al guardar el archivo";
		}
	}
	
	public String leerArchivo(String ubicacion)
	{
		
		return "demo";
	}

}
