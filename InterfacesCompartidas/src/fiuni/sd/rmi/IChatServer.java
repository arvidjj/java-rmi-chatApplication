package fiuni.sd.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChatServer extends Remote {
    void conectar(String userName, IClientCallback callback) throws RemoteException;
    void desconectar(IClientCallback usuario) throws RemoteException;
    void enviarMensaje(String mensaje, String nombreUsuario) throws RemoteException;
    List<String> obtenerListaClientesConectados() throws RemoteException;
    void actualizarListaClientesEnClientes() throws RemoteException;
}


