package fiuni.sd.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            IChatServer chatServer = new ChatServerImpl();
            
            System.out.println("Servidor corriendo...");
            
            registry.rebind("ChatServer", chatServer);
            System.out.println("Objeto ChatServer bindeado en el registro.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
