package cl.proyectg.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import cl.proyectg.utils.Archivos;
import cl.proyectg.utils.Comandos;

/**
 * Servicio que permite hacer varias cosas dependiendo del mensaje la estructura esta definida como.
 * opcion|valor1|valorn... 
 * @author nkey
 *
 */

public class Init {

	
	private static ServerSocket servidor;

	public static void main(String[] args) throws IOException {
		
		 int puerto = 0;
		if(args.length>0)
		{
			for(String argumentos:args)
				puerto = Integer.parseInt(args[0]);
		}
		else
			puerto=5555;
		
		System.out.println("Iniciando servicio en el puerto "+puerto);
		servidor = new ServerSocket(puerto);

		while (true)
			new IniciarServicio(servidor.accept()).run();

	}

	public void stop() throws IOException {
		servidor.close();
	}

	public static class IniciarServicio extends Thread {
		private ServerSocket servicio;
		private Socket cliente;
		private InputStream inputStream;
		private OutputStream outputStream;

		public IniciarServicio(Socket socket) {
			this.cliente = socket;
		}

		public void run() {
			try {
				inputStream = cliente.getInputStream();
				outputStream = cliente.getOutputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				
				
				Object entrada;
				while ((entrada = objectInputStream.readObject()) != null) {
					
					Map<String,Object> mensaje = (Map<String,Object>) entrada;
					
					Archivos archivo = new Archivos();
					cl.proyectg.cliente.acciones.Archivos objeto = new cl.proyectg.cliente.acciones.Archivos();
					Comandos cmd = new Comandos();
					
					switch((String)mensaje.get("tarea")) {
					case "leer":
						objeto = archivo.leerArchivo((cl.proyectg.cliente.acciones.Archivos)mensaje.get("objeto"));
						objectOutputStream.writeObject(mensaje.get("objeto"));
						break;
					case "escribir":
						objeto = archivo.guardarArchivo((cl.proyectg.cliente.acciones.Archivos)mensaje.get("objeto"));
						objectOutputStream.writeObject(mensaje.get("objeto"));
						break;
					case "ejecutar":
						String resultado = cmd.executar((String)mensaje.get("comando"));
						mensaje.put("resultado",resultado);
						objectOutputStream.writeObject(mensaje);
						break;
					default:
						objectOutputStream.writeObject(mensaje.put("resultado","No se ejecuto, ningun proceso."));
						break;
					}
					
				}
				
				objectInputStream.close();
				objectOutputStream.close();
				cliente.close();

			}catch(IOException e1) {
				System.out.println("No hay mas datos");
			}catch (Exception e) {
				System.out.println("El servicio ha fallado al inicializar.");
				e.printStackTrace();
			}
		}
	}
}
