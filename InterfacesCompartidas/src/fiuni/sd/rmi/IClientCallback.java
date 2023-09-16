package fiuni.sd.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClientCallback extends Remote {
	String recibirMensaje(String usuario, String mensaje) throws RemoteException;
	void actualizarListaClientes(List<String> listaClientes) throws RemoteException;
}
