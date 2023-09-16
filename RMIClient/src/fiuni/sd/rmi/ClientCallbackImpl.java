package fiuni.sd.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import fiuni.sd.clientgui.MainSceneController;
import javafx.application.Platform;

public class ClientCallbackImpl extends UnicastRemoteObject implements IClientCallback {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainSceneController guiController;

	public ClientCallbackImpl(MainSceneController guiController) throws RemoteException {
        super();
        this.guiController = guiController;
    }

    @Override
    public String recibirMensaje(String usuario, String mensaje) throws RemoteException {
    	System.out.println("Mensaje recibido: " + mensaje);
    	Platform.runLater(() -> {
    		guiController.mostrarMensajeEnGUI(usuario, mensaje);
        });
    	return mensaje;
    }
    
    @Override
    public void actualizarListaClientes(List<String> listaClientes) throws RemoteException {
    	System.out.println("actualizando usuarios:");
        Platform.runLater(() -> {
        	guiController.actualizarUsuarios(listaClientes);
        });
    }

}
