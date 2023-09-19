package fiuni.sd.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Loggeador;

public class ChatServerImpl extends UnicastRemoteObject implements IChatServer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, IClientCallback> clientesConectados;
	 private final Loggeador log;
	 
    public ChatServerImpl() throws RemoteException {
		super();
		this.log = new Loggeador();
        clientesConectados = new HashMap<>();
    }

	@Override
	public void conectar(String nombreUsuario, IClientCallback callback) throws RemoteException {
		if (!clientesConectados.containsValue(callback)) {
			clientesConectados.put(nombreUsuario, callback);
			System.out.println("Cliente con nombre: " + nombreUsuario + " se ha conectado");
			log.loggear("Cliente con nombre: " + nombreUsuario + " se ha conectado");
			actualizarListaClientesEnClientes();
	    }
	}
	@Override
	public void desconectar(IClientCallback usuario) throws RemoteException {
		clientesConectados.values().remove(usuario);
		
		System.out.println("Cliente se ha desconectado");
		log.loggear("Cliente se ha desconectado");
        actualizarListaClientesEnClientes();
	}

	@Override
	public void enviarMensaje(String mensaje, String nombreUsuario) throws RemoteException {
		ServerMensajeThread hiloEnviador = new ServerMensajeThread(clientesConectados, mensaje, nombreUsuario);
		hiloEnviador.start();
	}

	@Override
	public List<String> obtenerListaClientesConectados() throws RemoteException {
		List<String> listaClientes = new ArrayList<>(clientesConectados.keySet());
		
		return listaClientes;
	}
	
	@Override
	public void actualizarListaClientesEnClientes() throws RemoteException {
		List<String> listaClientes = new ArrayList<>(clientesConectados.keySet());
		
		System.out.println("Enviando lista de clientes a los clientes...");
		log.loggear("Enviando lista de clientes a los clientes...");
        for (IClientCallback client : clientesConectados.values()) {
            client.actualizarListaClientes(listaClientes);
        }
	}
	
	}


 
 
