package fiuni.sd.clientgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Loggeador;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatClientGUI extends Application {
    
	@Override
	public void start(Stage primaryStage) throws RemoteException, NotBoundException {
		Loggeador log = new Loggeador();; //logger
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Chat Client");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			log.loggearError(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}