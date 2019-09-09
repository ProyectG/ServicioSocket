package cl.proyectg.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	
	public javaSocketObject.File leerArchivo(javaSocketObject.File objeto) throws IOException
	{
		System.out.println("Leyendo archivo." +objeto.getUbicacionArchivo()+objeto.getNombreArchivo());
		String cadena,archivo = "";
        FileReader file = new FileReader(objeto.getUbicacionArchivo()+objeto.getNombreArchivo());
        BufferedReader lector = new BufferedReader(file);
        while((cadena = lector.readLine())!=null) {
        	   archivo = archivo + cadena +"\n";
        }
        objeto.setContenidoArchivo(archivo);
        
        if(objeto.isMd5())
        	objeto.setResultadoMD5(obtenerMD5(objeto.getUbicacionArchivo()+objeto.getNombreArchivo()));
        
        if(objeto.isTamaño())
        	objeto.setResultadoTamaño(obtenerTamaño(objeto.getUbicacionArchivo()+objeto.getNombreArchivo()));
        
        lector.close();
		return objeto;
	}
	
	public String obtenerMD5(String ubicacion) throws IOException
	{
		String md5="";
		String cmd = "md5sum "+ubicacion;
	    Process pb = Runtime.getRuntime().exec(cmd);
	    
	    String line;
	    BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
	    while ((line = input.readLine()) != null) {
	        md5=line.split(" ")[0];
	    }
		
		return md5;
	}
	
	public String obtenerTamaño(String ubicacion) throws IOException
	{
		String tamaño="";
		String cmd = "stat -c%s "+ubicacion;
	    Process pb = Runtime.getRuntime().exec(cmd);
	    
	    String line;
	    BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
	    while ((line = input.readLine()) != null) {
	        tamaño=line;
	    }
		return tamaño;
	}

}
