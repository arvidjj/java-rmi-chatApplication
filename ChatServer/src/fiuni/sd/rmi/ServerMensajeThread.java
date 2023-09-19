package fiuni.sd.rmi;

import java.rmi.RemoteException;
import java.util.Map;

import utils.Loggeador;

public class ServerMensajeThread extends Thread {
	private Map<String, IClientCallback> clientesConectados;
	private String mensaje;
	private String nombreUsuario;
	private final Loggeador log;
	
	public ServerMensajeThread(Map<String, IClientCallback> clientes, String mensaje, String nombreUsuario){
		this.log = new Loggeador();
		this.clientesConectados = clientes;
		this.mensaje = mensaje;
		this.nombreUsuario = nombreUsuario;
	}
	
	public void run() {
		System.out.println("Hilo enviador creado.");
		System.out.println(nombreUsuario + ": " + mensaje);
		log.loggear(nombreUsuario + ": " + mensaje);
		for (IClientCallback client : clientesConectados.values()) {
            try {
				client.recibirMensaje(nombreUsuario, mensaje);
			} catch (RemoteException e) {
				log.loggearError(e.getMessage());
			}
        }
	}
	
	public static void main(String args[]){  
	}  
}
