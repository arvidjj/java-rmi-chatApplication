package fiuni.sd.clientgui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import fiuni.sd.rmi.ChatClient;
import utils.Loggeador;

public class ChatClientGUI3 {

	private JFrame frame;
	private JTextField enviarTexto;
	private JTextField nombreInput;
	private JList<String> listaUsuarios;
	private JTextArea chatTexto;
	private JButton enviarButton;
	private JButton conectarButton;
	
	private ChatClient chatClient;
	private String nombre;
	private boolean estaConectado;
	
	private Loggeador log; //logger
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
	        System.err.println("Uso: java fiuni.sd.clientgui.ChatClientGUI3 rmiHostName rmiPort");
	        System.exit(1);
	    }

	    String rmiHostName = args[0];
	    int rmiPort = Integer.parseInt(args[1]);
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 ChatClientGUI3 window = new ChatClientGUI3(rmiHostName, rmiPort);
					window.frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClientGUI3(String rmiHostName, int rmiPort) {
		Loggeador log = new Loggeador(); //logger

		initialize();
		try {
			chatClient = new ChatClient(this, rmiHostName, rmiPort);
		} catch (RemoteException e) {
			log.loggearError(e.getMessage());
		} catch (NotBoundException e) {
			log.loggearError(e.getMessage());
		}
		estaConectado = false;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 649, 514);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		enviarTexto = new JTextField();
		enviarTexto.setBounds(20, 434, 362, 20);
		frame.getContentPane().add(enviarTexto);
		enviarTexto.setColumns(10);
		
		enviarButton = new JButton("Send");
		enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String mensaje = enviarTexto.getText();
                if (estaConectado && mensaje.length()>0) {
                    try {
                        chatClient.enviarMensaje(mensaje, nombre);
                        enviarTexto.setText("");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
		enviarButton.setBounds(392, 433, 89, 23);
		frame.getContentPane().add(enviarButton);
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		listaUsuarios = new JList<>(listModel);
		listaUsuarios.setBounds(434, 60, 190, 346);
		frame.getContentPane().add(listaUsuarios);
		
		conectarButton = new JButton("Conectar");
		
		conectarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nombre = nombreInput.getText();
                try {
                	if (!estaConectado) {
                		chatClient.conectar(nombre);
                        estaConectado = true;
                        mostrarAvisoEnGui("Te has conectado a la sala como: " + nombre);
                        
                        disableInputs();
                        conectarButton.setText("Desconectar");
                        
                        List<String> listaClientes = chatClient.obtenerListaClientesConectados();
                        actualizarUsuarios(listaClientes);
                	}
                    else {
                    	chatClient.desconectar();
                    	estaConectado = false;
                    	mostrarAvisoEnGui("Te has desconectado de la sala");
            			enableInputs();
                    	conectarButton.setText("Conectar");
                    }
                } catch (RemoteException | NotBoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
		
		conectarButton.setBounds(533, 26, 93, 23);
		frame.getContentPane().add(conectarButton);
		
		nombreInput = new JTextField();
		nombreInput.setBounds(434, 27, 96, 20);
		frame.getContentPane().add(nombreInput);
		nombreInput.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 11, 404, 395);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		chatTexto = new JTextArea();
		chatTexto.setEditable(false);
		chatTexto.setBounds(0, 0, 404, 395);
		panel.add(chatTexto);
	}
	
	public void actualizarUsuarios(List<String> listaRecibida) {
	    SwingUtilities.invokeLater(() -> {
	        DefaultListModel<String> listModel = new DefaultListModel<>();
	        for (String user : listaRecibida) {
	            listModel.addElement(user);
	        }
	        listaUsuarios.setModel(listModel); // Assuming listaUsuarios is your JList
	    });
	}

	
	public void mostrarAvisoEnGui(String mensaje) {
		chatTexto.append(mensaje + "\n");
    }
	public void mostrarMensajeEnGUI(String usuario, String mensaje) {
		chatTexto.append("["+obtenerFechaYHora()+ "] " + usuario + ": " + mensaje + "\n");
    }
	public void disableInputs() {
		nombreInput.setEnabled(false);
		enviarButton.setEnabled(true);
    }
	public void enableInputs() {
		nombreInput.setEnabled(true);
		enviarButton.setEnabled(false);
    }
	public String obtenerFechaYHora() {
		////OBTENER FECHA Y HORA
	        LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        return currentDateTime.format(formatter);
		}
}
