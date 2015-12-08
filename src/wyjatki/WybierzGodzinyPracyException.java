package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WybierzGodzinyPracyException extends Exception
{
	private static final long serialVersionUID = 1L;

	public WybierzGodzinyPracyException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Wybierz godziny pracy");
		alert.showAndWait();
	}

}
