package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class BazaDanych 
{
	static Connection conn;
	static boolean idDbConnected = false;
	
	
	public static void wczytajPracownikowWAktywnosci(int idAktywnosci)
	{
		//int id, int idAktywnosci, int idWaluty,int idPrac, String imie, String nazwisko, String waluta, float stawkaPracownika, float stawkaFirmy
		int id, idPrac;
		String imie, nazwisko,  waluta;
		float stawkaPracownika, stawkaFirmy;
		try
		{
			String query1 = " select id, Aktywnosci.idAktywnosci, Pracownicy.idPrac, imie, nazwisko, stawkaPracownika, stawkaFirmy, waluta from PracownicyWAktywnosci";
			String query2 = " join Pracownicy on Pracownicy.idPrac = PracownicyWAktywnosci.idPrac";
			String query3 = " join Aktywnosci on Aktywnosci.idAktywnosci = PracownicyWAktywnosci.idAktywnosci";
			String query4 = " where Aktywnosci.idAktywnosci = '"+idAktywnosci+"'";

			String query = query1+query2+query3+query4;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				id = rs.getInt("id");
				idPrac = rs.getInt("idPrac");
				
				imie = rs.getString("imie");
				nazwisko = rs.getString("nazwisko");
				waluta = rs.getString("waluta");
				
				stawkaPracownika = rs.getFloat("stawkaPracownika");
				stawkaFirmy = rs.getFloat("stawkaFirmy");
				
				Main.listaPracownikowWAktywnosci.add(new PracownikWAktywnosci(id, idAktywnosci,idPrac,imie,nazwisko, waluta, stawkaPracownika, stawkaFirmy));
			}
			rs.close();
			stmt.close();
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy pobieraniu danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
		}
	}
	
	public static void wczytajAktywnosci(int idDnia)
	{
		int idAktywnosci, idDniaPracy;
		String nazwaAktywnosci, opis, godzinaOd, godzinaDo;
		try
		{
			String query1 =" select idAktywnosci, idDniaPracy, nazwaAktywnosci, opis, godzinaOd, godzinaDo from Aktywnosci";
			String query2 =" join DniPracy on DniPracy.idDnia= Aktywnosci.idDniaPracy";
			String query3 =" where DniPracy.idDnia = '"+idDnia+"'";
			String query = query1+query2+query3; 
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				idAktywnosci = rs.getInt("idAktywnosci");
				idDniaPracy = rs.getInt("idDniaPracy");
				
				godzinaOd = rs.getString("godzinaOd");
				godzinaDo = rs.getString("godzinaDo");
				nazwaAktywnosci = rs.getString("nazwaAktywnosci");
				opis = rs.getString("opis");
				
				Main.listaAktywnosci.add(new Aktywnosc(idAktywnosci, idDniaPracy, nazwaAktywnosci, opis, godzinaOd, godzinaDo));
			}
			rs.close();
			stmt.close();
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy pobieraniu danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
		}
	}
	
	public static void wczytajDniPracy(int idMiesiaca, int idRoku)
	{
		int idDnia, dzien, rok;
		String miesiac;
		try
		{
			String query1 =" select idDnia, dzien,miesiac,rok from DniPracy";
			String query2 =" join Miesiace on Miesiace.idMiesiaca = DniPracy.idMiesiaca";
			String query3 =" join Lata on Lata.idRoku = DniPracy.idRoku";
			String query4 =" where DniPracy.idMiesiaca ='"+idMiesiaca+"'";
			String query5 =" and  DniPracy.idRoku = '"+idRoku+"'";
			String query = query1+query2+query3+query4+query5; // Select
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				idDnia = rs.getInt("idDnia");
				dzien = rs.getInt("dzien");
				miesiac = rs.getString("miesiac");
				rok = rs.getInt("rok");
				
				Main.listaDniPracy.add(new DzienPracy(idDnia,dzien,miesiac,rok));
			}
			rs.close();
			stmt.close();
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy pobieraniu danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
		}
	}
	
	public static void wczytajLataMiesiace() throws SQLException
	{
		int idRoku, rok, idMiesiaca;
		String miesiac;
		try
		{
			String query = "SELECT * FROM Lata"; // Select
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				idRoku = rs.getInt("idRoku");
				rok = rs.getInt("rok");
				Main.listaLat.add(new Rok(idRoku,rok));
			}
			rs.close();
			stmt.close();
			
			query = "SELECT * FROM Miesiace"; // Select
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				idMiesiaca = rs.getInt("idMiesiaca");
				miesiac = rs.getString("miesiac");
				Main.listaMiesiecy.add(new Miesiac(idMiesiaca,miesiac));
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy pobieraniu danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
		}
	}
	
	public static void wczytajPracownikow() throws SQLException 
	{
		String imie, nazwisko, dataUrodzenia, pesel, adres, miasto, kodPocztowy, numerTelefonu;
		int  idPrac;
		try
		{
			String query = "SELECT * FROM Pracownicy"; // Select
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				idPrac = rs.getInt("idPrac");
				imie = rs.getString("imie");
				nazwisko = rs.getString("nazwisko");
				dataUrodzenia = rs.getString("dataUrodzenia");
				pesel= rs.getString("pesel");
				adres = rs.getString("adres");
				miasto  = rs.getString("miasto");
				kodPocztowy = rs.getString("kodPocztowy");
				numerTelefonu = rs.getString("numerTelefonu");
				
				if(imie == null) imie = "brak danych";
				if(nazwisko == null) nazwisko = "brak danych";
				if(dataUrodzenia == null) dataUrodzenia = "brak danych";
				if(pesel == null) pesel = "brak danych";
				if(adres == null) adres = "brak danych";
				if(miasto == null) miasto = "brak danych";
				if(kodPocztowy == null) kodPocztowy = "brak danych";
				if(numerTelefonu == null) numerTelefonu = "brak danych";
				Main.listaPracownikow.add(new Pracownik(idPrac,imie, nazwisko, dataUrodzenia, pesel, adres, miasto, kodPocztowy, numerTelefonu));
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy pobieraniu danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
		}
	}
	
	
	public static void aktualizujAktywnosci(int idAktywnosci, String opis, String godzinaOd, String godzinaDo)
	{
		try
		{
			String query1 = "update Aktywnosci SET opis='"+opis+"' "+"WHERE idAktywnosci='"+idAktywnosci+"';";
			String query2 = "update Aktywnosci SET godzinaOd='"+godzinaOd+"' "+"WHERE idAktywnosci='"+idAktywnosci+"';";
			String query3 = "update Aktywnosci SET godzinaDo='"+godzinaDo+"' "+"WHERE idAktywnosci='"+idAktywnosci+"';";
		      
		    Statement stmt = conn.createStatement();
		    String query = query1 + query2 + query3;
		    stmt.executeUpdate(query);
			stmt.close();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy aktualizowaniu danych.");
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public static void aktualizujPracownikow(int id, String imie, String nazwisko, String numerTelefonu, String adres, String miasto,String kodPocztowy,String dataUrodzenia,String pesel)
	{
		try
		{
			
			String query1 = "update Pracownicy SET imie='"+imie+"' "+"WHERE idPrac='"+id+"';";
			String query2 = "update Pracownicy SET nazwisko='"+nazwisko+"' "+"WHERE idPrac='"+id+"';";
			String query3 = "update Pracownicy SET numerTelefonu='"+numerTelefonu+"' "+"WHERE idPrac='"+id+"';";
			String query4 = "update Pracownicy SET adres='"+adres+"' "+"WHERE idPrac='"+id+"';";
			String query5 = "update Pracownicy SET miasto='"+miasto+"' "+"WHERE idPrac='"+id+"';";
			String query6 = "update Pracownicy SET kodPocztowy='"+kodPocztowy+"' "+"WHERE idPrac='"+id+"';";
			String query7 = "update Pracownicy SET dataUrodzenia='"+dataUrodzenia+"' "+"WHERE idPrac='"+id+"';";
			String query8 = "update Pracownicy SET pesel='"+pesel+"' "+"WHERE idPrac='"+id+"';";
		      
		    Statement stmt = conn.createStatement();
		    String query = query1 + query2 + query3 + query4 + query5 + query6 + query7 + query8;
		    stmt.executeUpdate(query);
			stmt.close();
			JOptionPane.showMessageDialog(null, "Dane zosta�y zaktualizowane.");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy aktualizowaniu danych.");
			System.err.println(e);
			e.printStackTrace();
		}
	}
	public static void insert() throws SQLException 
	{
		try
		{
			
		}
		catch(Exception e)

		{
			JOptionPane.showMessageDialog(null, "Nie uda�o si� zapisa� danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
		}
		/*
		wiekString = Integer.toString(wiek);
		String query = "INSERT INTO Pracownicy " +
           				"VALUES ('"+imie+"', '"+nazwisko+"', '"+wiekString+"')";
		lastId=lastInsertedId(query);*/

	}
	public static int getLastId(String nazwaID, String tabela) throws SQLException
	{
		int lastId=0;
		try
		{
			String query = "SELECT "+nazwaID+" FROM "+tabela; // Select
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				lastId = rs.getInt(nazwaID);
			}
			rs.close();
			stmt.close();
			return lastId;
		}
		catch(SQLException e)
		{
			System.out.println("Nie uda�o si� pobra� id. Brak po��czenia z baz�");
			System.err.println(e);
			return -1;
		}
	}
	public static int lastInsertedId(String query) 
	{
		int id=-1;
		try 
		{	
		    Statement stmt = conn.createStatement();
		    stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		    ResultSet rs = stmt.getGeneratedKeys();
		    if (rs.next()) id=rs.getInt(1);
		    rs.close();
		    stmt.close();
		    return id;
		} 
		catch (Exception e) 
		{
			System.out.println("Nie uda�o si� pobra� id. Brak po��czenia z baz�");
			System.err.println(e);
			return -1;
		}
		
	}
	public static boolean dbConnection() throws SQLException, ClassNotFoundException
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //Rejestracja sterownika
			String connectionURL = "jdbc:sqlserver://217.113.235.83:1433;user=zatrans_db_user;password=%NeVer_mind.52;useUnicode=true;characterEncoding=utf8;databaseName=dbTest;"; //Po��czenie z baz�
			conn = DriverManager.getConnection(connectionURL);
			return true;
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy pobieraniu danych. Sprawd� po��czenie internetowe");
			System.err.println(e);
			return false;
		}
		
	}

	public static void aktualizujPracownikaWAktywnosci(int idAktywnosci, int idPrac, float stawkaPracownika, float stawkaFirmy, String waluta) 
	{
		try
		{
			String query1 = "update PracownicyWAktywnosci SET stawkaPracownika='"+stawkaPracownika+"' "+"WHERE idAktywnosci='"+idAktywnosci+"' and idPrac = '"+idPrac+"';";
			String query2 = "update PracownicyWAktywnosci SET stawkaFirmy='"+stawkaFirmy+"' "+"WHERE idAktywnosci='"+idAktywnosci+"' and idPrac = '"+idPrac+"';";
			String query3 = "update PracownicyWAktywnosci SET waluta='"+waluta+"' "+"WHERE idAktywnosci='"+idAktywnosci+"' and idPrac = '"+idPrac+"';";
		      
		    Statement stmt = conn.createStatement();
		    String query = query1 + query2 + query3;
		    stmt.executeUpdate(query);
			stmt.close();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "B��d przy aktualizowaniu danych.");
			System.err.println(e);
			e.printStackTrace();
		}
	}

	public static int isDayInDatabase(int wybranyDzien, int wybranyMiesiac, int wybranyRok) 
	{
		int isSomething = 0;
		try
		{
			String query1 = " select dzien, Miesiace.idMiesiaca, rok from DniPracy";
			String query2 = " join Miesiace on Miesiace.idMiesiaca = DniPracy.idMiesiaca";
		    String query3 = " join Lata on Lata.idRoku = DniPracy.idRoku";
			String query4 = " where rok = '"+wybranyRok+"'";
			String query5 = " and Miesiace.idMiesiaca = '"+wybranyMiesiac+"'";
			String query6 = " and dzien = '"+wybranyDzien+"'";
		      
		    Statement stmt = conn.createStatement();
		    String query = query1 + query2 + query3 + query4 + query5 + query6;
		    ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				isSomething = 1;
			}
			rs.close();
			stmt.close();
			
			return isSomething;
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Brak po��czenia z baz� danych.");
			System.err.println(e);
			e.printStackTrace();
			return -1;
		}
		
	}
}
