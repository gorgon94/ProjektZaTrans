package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WybierzDzienException extends Exception
{
	private static final long serialVersionUID = 1L;

	public WybierzDzienException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Proszę wybrać dzień.");
		alert.showAndWait();
	}
}
