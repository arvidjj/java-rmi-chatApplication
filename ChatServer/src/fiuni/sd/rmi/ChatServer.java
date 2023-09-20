package fiuni.sd.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import utils.Configurator;
import utils.Loggeador;

public class ChatServer {
	
    public static void main(String[] args) {
    	if (args.length != 1) {
            System.err.println("Uso: java fiuni.sd.rmi.ChatServer rmiPort");
            System.exit(1);
        }
    	
    	int rmiPort = Integer.parseInt(args[0]);
    	
    	Configurator configuracion = new Configurator("src/config/config.properties");
    	Loggeador log = new Loggeador();; //logger
    	
        try {
            //Registry registry = LocateRegistry.createRegistry(configuracion.getIntProperty("puerto"));
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            
            IChatServer chatServer = new ChatServerImpl();
            
            System.out.println("Servidor corriendo...");
            log.loggear("Servidor corriendo...");
            
            registry.rebind("ChatServer", chatServer);
            System.out.println("Objeto ChatServer bindeado en el registro, puerto: " + rmiPort);
            log.loggear("Objeto ChatServer bindeado en el registro, puerto: " + rmiPort);
            
        } catch (Exception e) {
        	log.loggearError(e.getMessage());
        }
    }
}
