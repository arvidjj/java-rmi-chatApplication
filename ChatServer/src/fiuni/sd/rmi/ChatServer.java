package fiuni.sd.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import utils.Configurator;
import utils.Loggeador;

public class ChatServer {
	
    public static void main(String[] args) {
    	Configurator configuracion = new Configurator("src/config/config.properties");
    	Loggeador log = new Loggeador();; //logger
    	
        try {
            Registry registry = LocateRegistry.createRegistry(configuracion.getIntProperty("puerto"));

            IChatServer chatServer = new ChatServerImpl();
            
            System.out.println("Servidor corriendo...");
            log.loggear("Servidor corriendo...");
            
            registry.rebind("ChatServer", chatServer);
            System.out.println("Objeto ChatServer bindeado en el registro.");
            log.loggear("Objeto ChatServer bindeado en el registro.");
            
        } catch (Exception e) {
        	log.loggearError(e.getMessage());
        }
    }
}
