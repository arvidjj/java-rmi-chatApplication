package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Loggeador {
	
	private static final Logger loggeador = LogManager.getLogger(Loggeador.class.getName());  //logger
	
	public void loggear(String mensaje) {
		loggeador.info("("+ obtenerFechaYHora() + ") " +mensaje);
	}
	public void loggearError(String mensaje) {
		loggeador.error("("+ obtenerFechaYHora() + ") " +mensaje);
	}
	public String obtenerFechaYHora() {
	////OBTENER FECHA Y HORA
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
        return currentDateTime.format(formatter);
	}
}
