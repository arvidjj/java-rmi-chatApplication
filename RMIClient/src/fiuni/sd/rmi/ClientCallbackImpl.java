package fiuni.sd.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import fiuni.sd.clientgui.ChatClientGUI3;

public class ClientCallbackImpl extends UnicastRemoteObject implements IClientCallback {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChatClientGUI3 gui;

	public ClientCallbackImpl(ChatClientGUI3 guiController) throws RemoteException {
        super();
        this.gui = guiController;
    }

    @Override
    public String recibirMensaje(String usuario, String mensaje) throws RemoteException {
    	System.out.println("Mensaje recibido: " + mensaje);
    	gui.mostrarMensajeEnGUI(usuario, mensaje);
    	return mensaje;
    }
    
    @Override
    public void actualizarListaClientes(List<String> listaClientes) throws RemoteException {
    	System.out.println("actualizando usuarios:");
        gui.actualizarUsuarios(listaClientes);
    }

}
