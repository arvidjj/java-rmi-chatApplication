package fiuni.sd.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.function.Consumer;

import fiuni.sd.clientgui.MainSceneController;
import fiuni.sd.rmi.IChatServer;
import utils.Configurator;
import utils.Loggeador;

public class ChatClient {
    private IChatServer chatServer; //Interfaz remota del servidor
    private IClientCallback clientCallback; //Callback del cliente
    private MainSceneController guiController;
    private Configurator configuracion = new Configurator("src/config/config.properties");
    private final Loggeador log; //logger
    
    public ChatClient(MainSceneController guiController) throws RemoteException, NotBoundException {
    	
    	this.log = new Loggeador();
		this.guiController = guiController;
    	clientCallback = new ClientCallbackImpl(guiController);
    }

    public void enviarMensaje(String mensaje, String username) throws RemoteException {
    	chatServer.enviarMensaje(mensaje, username);
    }
    
    public void conectar(String username) throws RemoteException, NotBoundException {
    	Registry registry = LocateRegistry.getRegistry(configuracion.getProperty("direccion"), configuracion.getIntProperty("puerto"));
		chatServer = (IChatServer) registry.lookup("ChatServer");
		
		System.out.println("conectando...");
		log.loggear(username + " conectando al servidor...");
    	chatServer.conectar(username, clientCallback);
    }
    public void desconectar() throws RemoteException, NotBoundException {
		
		System.out.println("desconectando...");
		log.loggear("Desconectando del servidor...");
    	chatServer.desconectar(clientCallback);
    }
    
    public List<String> obtenerListaClientesConectados() throws RemoteException {
        List<String> listaClientes = chatServer.obtenerListaClientesConectados();
        return listaClientes;
    }
}
