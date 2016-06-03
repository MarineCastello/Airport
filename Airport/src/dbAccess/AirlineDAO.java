package dbAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Airline;
import entity.Airline;
import entity.Factory;

public class AirlineDAO {
	private DBAccess dbaccess = null;
	private static AirlineDAO instance = null;

	/**
	 * Retourne une instance de AirlineDAO
	 * 
	 * @return instance de la classe
	 * @throws MyDBException
	 */
	public static AirlineDAO getInstance() throws MyDBException {
		if (instance == null) {
			instance = new AirlineDAO();
		}
		return instance;
	}

	/**
	 * Constructeur par d�faut Initialisation de la connexion � la base de
	 * donn�es
	 * 
	 * @throws MyDBException
	 */
	private AirlineDAO() throws MyDBException {
		super();
		dbaccess = DBAccess.getInstance();
	}

	/**
	 * Connexion � la base de donn�es
	 * 
	 * @throws MyDBException
	 */
	public void getConnection() throws MyDBException {
		try {
			dbaccess.connect();
		} catch (SQLException e) {
			throw new MyDBException("Connexion � la base de donnees impossible");
		}
	}

	/**
	 * Fermeture de la connexion
	 * 
	 * @throws MyDBException
	 */
	public void close() throws MyDBException {
		dbaccess.close();
	}

	/**
	 * Permet d'ins�rer un nouvel enregistrement dans la table Airline
	 * 
	 * @param m
	 *            Airline � ins�rer
	 * @return nombre d'enregistrements impact�s
	 * @throws MyDBException
	 */
	public Integer insertAirline(Airline a) throws MyDBException {
		Integer nbMaj = 0;

		// ajout de l'avion avec le bon id_airline et le bon id_Airline
		String requete = "INSERT INTO Airline (airline_name) VALUES ('" + a.getAirlineName() + ")";
		Statement stmt = null;

		getConnection();
		try {
			stmt = dbaccess.getConnection().createStatement();
			nbMaj = stmt.executeUpdate(requete);
		} catch (SQLException e) {
			throw new MyDBException("Probleme lors de la creation ou de l'ex�cution de la requete d'insertion");
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors du close du statement de l'insertion");
		}
		close();

		return nbMaj;
	}

	/**
	 * Permet d'afficher toute la table Airline
	 * 
	 * @throws MyDBException
	 */
	public List<Airline> selectAll() throws MyDBException {
		ResultSet resultats = null;
		List<Airline> lstAirlines = new ArrayList<Airline>();
		String requete = "SELECT * FROM Airline;";
		Statement stmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultats = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			while (resultats.next()) {
				Airline a = Factory.createAirline(resultats.getString("airline_name"));
				lstAirlines.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return lstAirlines;
	}

	public Integer selectIdAirline(String airline) throws MyDBException {
		// r�cup�ration de l'idAirline
		Integer idAirline = 0;
		ResultSet resultat = null;
		String requete = "SELECT id_airline FROM airline WHERE airline_name = " + airline + ";";
		Statement stmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultat = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			if (resultat.next()) {
				idAirline = resultat.getInt("id_airline");
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return idAirline;
	}
}
