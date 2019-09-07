package cl.proyectg.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import cl.proyectg.utils.Archivos;
import javaSocketObject.Test;

/**
 * Servicio que permite hacer varias cosas dependiendo del mensaje la estructura esta definida como.
 * opcion|valor1|valorn... 
 * @author nkey
 *
 */

public class Init {

	private static ServerSocket servidor;

	public static void main(String[] args) throws IOException {

		servidor = new ServerSocket(5555);

		while (true)
			new IniciarServicio(servidor.accept()).run();

	}

	public void stop() throws IOException {
		servidor.close();
	}

	public static class IniciarServicio extends Thread {
		private int puerto;
		private ServerSocket servicio;
		private Socket cliente;
		private PrintWriter out;
		private BufferedReader in;
		private InputStream inputStream;
		private OutputStream outputStream;

		public IniciarServicio(Socket socket) {
			this.cliente = socket;
		}

		public void run() {
			try {
				System.out.println("Hola en que te puedo ayudar...");

				//out = new PrintWriter(cliente.getOutputStream(), true);
				//in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				
				inputStream = cliente.getInputStream();
				outputStream = cliente.getOutputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				
				Object entrada;
				while ((entrada = objectInputStream.readObject()) != null) {
					
					Map<String,Object> mensaje = (Map<String,Object>) entrada;
					System.out.println("Mensaje recibido :" + mensaje.toString() + " de " + cliente.toString());
					
					Test test = (Test) mensaje.get("llave");
					
					System.out.println("Data :"+test.getMensaje());
					System.out.println("Data :"+test.getNumero());
					
					test.setMensaje("Quakerman");
					
					objectOutputStream.writeObject(test);
					
					//String[] parametros = entrada.split("\\|");
					
					
					//for(String data:parametros)
					//	System.out.println(data);
					
//					if("addFile".equalsIgnoreCase(parametros[0]))
//					{
//						try {
//							System.out.println("Interesante, quieres guardar un archivo, intentare ayudarte.");
//							Archivos archivo = new Archivos();
//							out.println(archivo.guardarArchivo(parametros[1], parametros[2]));
//						}catch(Exception e)
//						{
//							out.println("Amigo hubo un problema te envio la informacion :"+e);
//						}
//					}
					
//					if("getFile".equalsIgnoreCase(parametros[0]))
//					{
//						try {
//							System.out.println("Deseas recuperar un archivo, veamos si existe, comenzare a buscar.");
//							Archivos archivo = new Archivos();
//							out.println(archivo.leerArchivo(parametros[1]));
//						}catch(Exception e)
//						{
//							out.println("Ups, creo que no pude leer el archivo que buscabas, te envio el error :"+e);
//						}
//					}
					
					
//					if ("stop".equalsIgnoreCase((String)entrada)) {
//						//out.println(entrada);
//						System.out.println("Deteniendo servicio.");
//						System.out.println("Termine mi trabajo, a la camita.");
//						break;
//					}

					//out.println("No se lo que quieres hacer, enviame una peticion correcta.");
				}
				
				//out.println("Gracias por usarme, ¡saludos!.");

				//in.close();
				//out.close();
				objectInputStream.close();
				objectOutputStream.close();
				cliente.close();

			}catch(IOException e1) {
				System.out.println("No hay mas datos");
				//out.println("Gracias por usarme, ¡Saludos!");
			}catch (Exception e) {
				System.out.println("El servicio ha fallado al inicializar.");
				e.printStackTrace();
			}
		}
	}
}