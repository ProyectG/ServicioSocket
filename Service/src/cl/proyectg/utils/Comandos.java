package cl.proyectg.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Comandos {
	
	public String executar(String cmd) throws IOException
	{
		Process pb = Runtime.getRuntime().exec(cmd);
		String resultado="";
		String line;
		BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
		while ((line = input.readLine()) != null) {
			resultado = line;
		}
		input.close();
		System.out.println("Resultado [["+resultado+"]]");
		return resultado;
	}

}
