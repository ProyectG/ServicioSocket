package cl.proyectg.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Utilitarios para tratar archivos. md5 tamaño leer escribir
 * 
 * @author nkey
 *
 */

public class Archivos {

	/**
	 * Guarda archivos con contenido que reciba el string.
	 * 
	 * @param archivo
	 * @param ubicacion
	 * @return
	 */

	public cl.proyectg.cliente.acciones.Archivos guardarArchivo(cl.proyectg.cliente.acciones.Archivos objeto) {

		if (objeto.getTipo() == 0) {
			try {
				objeto = escribirArchivoContenido(objeto);

				if (objeto.isMd5())
					objeto.setResultadoMD5(obtenerMD5(objeto.getUbicacionArchivo() + objeto.getNombreArchivo()));

				if (objeto.isTamaño())
					objeto.setResultadoTamaño(obtenerTamaño(objeto.getUbicacionArchivo() + objeto.getNombreArchivo()));

				objeto.setResultado("OK");
				return objeto;
			} catch (IOException e) {
				objeto.setContenidoArchivo(null);
				objeto.setResultado("NOK");
				return objeto;
			}
		} else if (objeto.getTipo() == 1) {
			try {

				objeto = escribirArchivoBytes(objeto);

				if (objeto.isMd5())
					objeto.setResultadoMD5(obtenerMD5(objeto.getUbicacionArchivo() + objeto.getNombreArchivo()));

				if (objeto.isTamaño())
					objeto.setResultadoTamaño(obtenerTamaño(objeto.getUbicacionArchivo() + objeto.getNombreArchivo()));

				objeto.setResultado("OK");
				return objeto;

			} catch (Exception e) {
				objeto.setArchivo(null);
				objeto.setResultado("NOK");
				return objeto;
			}
		} else {
			objeto.setResultado("NOK");
			return objeto;
		}

	}

	public cl.proyectg.cliente.acciones.Archivos escribirArchivoBytes(cl.proyectg.cliente.acciones.Archivos objeto)
			throws IOException {
		String ubicacion = objeto.getUbicacionArchivo() + objeto.getNombreArchivo();
		byte[] archivo = objeto.getArchivo();
		try (FileOutputStream fos = new FileOutputStream(ubicacion)) {
			fos.write(archivo);
		} catch (Exception e) {
			System.out.println("Error [[" + e + "]]");
		}

		objeto.setArchivo(null);
		return objeto;
	}

	public cl.proyectg.cliente.acciones.Archivos escribirArchivoContenido(cl.proyectg.cliente.acciones.Archivos objeto)
			throws IOException {
		String ubicacion = objeto.getUbicacionArchivo() + objeto.getNombreArchivo();
		BufferedWriter lapiz = new BufferedWriter(new FileWriter(ubicacion));
		lapiz.write(objeto.getContenidoArchivo());
		lapiz.close();
		objeto.setContenidoArchivo(null);
		return objeto;
	}

	public cl.proyectg.cliente.acciones.Archivos leerArchivo(cl.proyectg.cliente.acciones.Archivos objeto)
			throws IOException {

		if (objeto.getTipo() == 0) {
			String cadena, archivo = "";
			FileReader file = new FileReader(objeto.getUbicacionArchivo() + objeto.getNombreArchivo());
			BufferedReader lector = new BufferedReader(file);
			while ((cadena = lector.readLine()) != null) {
				archivo = archivo + cadena + "\n";
			}
			objeto.setContenidoArchivo(archivo);
			lector.close();
		} else if (objeto.getTipo() == 1) {
			byte[] archivoByte = leerArchivoAbytes(objeto.getUbicacionArchivo() + objeto.getNombreArchivo());
			objeto.setArchivo(archivoByte);
		}

		if (objeto.isMd5())
			objeto.setResultadoMD5(obtenerMD5(objeto.getUbicacionArchivo() + objeto.getNombreArchivo()));

		if (objeto.isTamaño())
			objeto.setResultadoTamaño(obtenerTamaño(objeto.getUbicacionArchivo() + objeto.getNombreArchivo()));

		objeto.setResultado("OK");

		return objeto;
	}

	public String obtenerMD5(String ubicacion) throws IOException {
		String md5 = "";
		String cmd = "md5sum " + ubicacion;
		Process pb = Runtime.getRuntime().exec(cmd);

		String line;
		BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
		while ((line = input.readLine()) != null) {
			md5 = line.split(" ")[0];
		}

		return md5;
	}

	public String obtenerTamaño(String ubicacion) throws IOException {
		String tamaño = "";
		String cmd = "stat -c%s " + ubicacion;
		Process pb = Runtime.getRuntime().exec(cmd);

		String line;
		BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
		while ((line = input.readLine()) != null) {
			tamaño = line;
		}
		input.close();
		return tamaño;
	}

	private static byte[] leerArchivoAbytes(String ubicacion) {
		File file = new File(ubicacion);
		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();

		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}

}
