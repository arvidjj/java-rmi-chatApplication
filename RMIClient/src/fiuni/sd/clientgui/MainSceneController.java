package fiuni.sd.clientgui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import fiuni.sd.rmi.ChatClient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.TextArea;

public class MainSceneController {
	@FXML
	private TextField inputMensaje;
	@FXML
	private Button enviarButton;
	@FXML
	private TextField nombreInput;
	@FXML
	private Button conectarButton;
	@FXML
	private ListView<String> listaUsers;
	@FXML
	private ScrollPane mensajesScroll;
	@FXML
	private TextArea textAreaChat;
	@FXML
	private Button emojiButton;

	private ChatClient chatClient;
	private String nombre;
	private boolean estaConectado;
	
	public MainSceneController() throws RemoteException, NotBoundException {
		 chatClient = new ChatClient(this);
		 estaConectado = false;
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void enviarMensaje(ActionEvent event) throws RemoteException {
		String mensaje = inputMensaje.getText();
		if (mensaje.length()>0) {
			chatClient.enviarMensaje(mensaje, nombre);
		    inputMensaje.setText("");
		}
	    
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void conectarAlServer(ActionEvent event) throws RemoteException, NotBoundException {		
		if (!estaConectado) {
			nombre = nombreInput.getText();
		    chatClient.conectar(nombre);
		    estaConectado = true;
		    mostrarAvisoEnGui("Te has conectado a la sala como: " + nombre);
		    
		    disableInputs();
		    conectarButton.setText("Desconectar");
		    
		    List<String> listaClientes = chatClient.obtenerListaClientesConectados();
		    actualizarUsuarios(listaClientes);
		} else {
			chatClient.desconectar();
			estaConectado = false;
			mostrarAvisoEnGui("Te has desconectado de la sala");
			enableInputs();
			conectarButton.setText("Conectar");
		}
	    
	}
	
	public void actualizarUsuarios(List<String> listaUsuarios) {
		listaUsers.setItems(FXCollections.observableArrayList(listaUsuarios));
    }
	public void mostrarAvisoEnGui(String mensaje) {
		textAreaChat.appendText(mensaje + "\n");
    }
	public void mostrarMensajeEnGUI(String usuario, String mensaje) {
		textAreaChat.appendText("["+obtenerFechaYHora()+ "] " + usuario + ": " + mensaje + "\n");
    }
	public void disableInputs() {
		nombreInput.setDisable(true);
		enviarButton.setDisable(false);
    }
	public void enableInputs() {
		nombreInput.setDisable(false);
		enviarButton.setDisable(true);
    }
	public String obtenerFechaYHora() {
		////OBTENER FECHA Y HORA
	        LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        return currentDateTime.format(formatter);
		}
}
