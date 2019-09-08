package cl.proyectg.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javaSocketObject.File;

/**
 * Utilitarios para tratar archivos.
 * @author nkey
 *
 */

public class Archivos {

	/**
	 * Guarda archivos con contenido que reciba el string.
	 * @param archivo
	 * @param ubicacion
	 * @return
	 */
	
	public File ejecucion(File tarea)
	{
		if("leer".equalsIgnoreCase(tarea.getTarea()))
		{ 
			String ubicacionArchivo = tarea.getUbicacionArchivo()+"/"+tarea.getNombreArchivo();
			tarea.setResultadoProceso(guardarArchivo(tarea.getContenidoArchivo(),ubicacionArchivo));
		}else if("escribir".equalsIgnoreCase(tarea.getTarea()))
		{
			
		}else
		{
			//Nada que hacer.
		}
		
		return tarea;
	}
	
	
	public String guardarArchivo(String archivo, String ubicacion)
	{
		BufferedWriter lapiz;
		try {
			lapiz = new BufferedWriter(new FileWriter(ubicacion));
			lapiz.write(archivo);
			lapiz.close();
			return "OK";
		} catch (IOException e) {
			return "NOK";
		}
	}
	
	
	
	
	public String leerArchivo(String ubicacion)
	{
		
		return "demo";
	}

}
