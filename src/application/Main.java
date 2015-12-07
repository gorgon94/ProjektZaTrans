package application;

import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application
{
	static Stage window;
    AnchorPane rootLayout;
    @FXML
	Button bZaloguj;
    @FXML
	TextField TFLogin;
    @FXML
    TextField TFHaslo;
    
	static ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
	
	static ArrayList<Rok> listaLat = new ArrayList<Rok>();
	static ArrayList<Miesiac> listaMiesiecy = new ArrayList<Miesiac>();
	static ObservableList<DzienPracy> listaDniPracy = FXCollections.observableArrayList();
	
	static ArrayList<Aktywnosc> listaAktywnosci = new ArrayList<Aktywnosc>();
	static ArrayList<PracownikWAktywnosci> listaPracownikowWAktywnosci = new ArrayList<PracownikWAktywnosci>();
	
	public static void clear()
	{
		listaPracownikow.clear();
		listaLat.clear();
		listaMiesiecy.clear();
		listaDniPracy.clear();
		listaAktywnosci.clear();
		listaPracownikowWAktywnosci.clear();
		//listaGodzin.clear();
		//listaMinut.clear();
	}
	
	
    public void start(Stage primaryStage) throws Exception
    {
    	window = primaryStage;
    	scenaLogowania();
    }
    
    public void scenaLogowania()
    {
    	try
    	{
	    	// Create the custom dialog.
	    	Dialog<Pair<String, String>> dialog = new Dialog<>();
	    	dialog.setTitle("Program ZA-TRANS");
	    	dialog.setHeaderText("Zaloguj się do systemu");
	
	    	// Set the icon (must be included in the project).
	    	dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
	
	    	// Set the button types.
	    	ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
	    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
	
	    	// Create the username and password labels and fields.
	    	GridPane grid = new GridPane();
	    	grid.setHgap(10);
	    	grid.setVgap(10);
	    	grid.setPadding(new Insets(20, 150, 10, 10));
	
	    	TextField username = new TextField();
	    	username.setPromptText("Username");
	    	PasswordField password = new PasswordField();
	    	password.setPromptText("Password");
	
	    	grid.add(new Label("Username:"), 0, 0);
	    	grid.add(username, 1, 0);
	    	grid.add(new Label("Password:"), 0, 1);
	    	grid.add(password, 1, 1);
	
	    	// Enable/Disable login button depending on whether a username was entered.
	    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
	    	loginButton.setDisable(true);
	
	    	// Do some validation (using the Java 8 lambda syntax).
	    	username.textProperty().addListener((observable, oldValue, newValue) -> {
	    	    loginButton.setDisable(newValue.trim().isEmpty());
	    	});
	
	    	dialog.getDialogPane().setContent(grid);
	
	    	// Request focus on the username field by default.
	    	Platform.runLater(() -> username.requestFocus());
	
	    	// Convert the result to a username-password-pair when the login button is clicked.
	    	dialog.setResultConverter(dialogButton -> {
	    	    if (dialogButton == loginButtonType) {
	    	        return new Pair<>(username.getText(), password.getText());
	    	    }
	    	    return null;
	    	});
	
	    	Optional<Pair<String, String>> result = dialog.showAndWait();
	
	    	result.ifPresent(usernamePassword -> 
	    	{
	    		String login = "zatrans";
	        	String haslo = "123#";
	        	
	        	//String login = usernamePassword.getKey();
	        	//String haslo = usernamePassword.getValue();
	        	
	    		if ((login.equals("zatrans"))&&(haslo.equals("123#"))) 
	    		{
	    			logowanie();
	    		}
	    		else
	    		{
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Uwaga!");
	    			alert.setHeaderText(null);
	    			alert.setContentText("Podano błędny login lub hasło");
	    			alert.showAndWait();
	    			scenaLogowania();
	    		}

	    	});
    	}	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public static void main(String[] args)
    {
    	launch(args);
    }
    
    public void logowanie() 
	{
		try 
		{
			if (BazaDanych.dbConnection()==true) 
			{
				Parent root = FXMLLoader.load(getClass().getResource("scenaPoZalogowaniu.fxml"));
				window.centerOnScreen();
				window.setTitle("Program ZA-TRANS");
		    	window.setScene(new Scene(root,960,540));
		    	window.show();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Błąd przy łączeniu z bazą danych. Sprawdź połączenie internetowe");
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
	}
}
