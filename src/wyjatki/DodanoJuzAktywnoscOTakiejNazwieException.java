package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DodanoJuzAktywnoscOTakiejNazwieException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DodanoJuzAktywnoscOTakiejNazwieException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Dodano już aktywność o takiej samej nazwie. Wybierz inn� nazw�");
		alert.showAndWait();
	}

}
