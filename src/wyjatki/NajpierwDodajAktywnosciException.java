package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NajpierwDodajAktywnosciException extends Exception
{
	private static final long serialVersionUID = 1L;

	public NajpierwDodajAktywnosciException()
	{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Uwaga!");
			alert.setHeaderText(null);
			alert.setContentText("Najpierw dodaj aktywność");
			alert.showAndWait(); 
	}
}
