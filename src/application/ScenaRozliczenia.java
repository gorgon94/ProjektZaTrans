package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScenaRozliczenia 
{
	public void bPowrotClicked()
	{
		Parent root;
		Scene scene;
		try 
		{
			root = FXMLLoader.load(getClass().getResource("scenaPoZalogowaniu.fxml"));
			scene = new Scene(root);
			Main.window.setScene(scene);
			
		} 
		catch (IOException e) {e.printStackTrace();}
	}
}
