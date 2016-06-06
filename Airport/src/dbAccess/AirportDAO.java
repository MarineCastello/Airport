package dbAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Airport;
import entity.Factory;

public class AirportDAO {
	private DBAccess dbaccess = null;
	private static AirportDAO instance = null;

	/**
	 * Retourne une instance de AirportDAO
	 * 
	 * @return instance de la classe
	 * @throws MyDBException
	 */
	public static AirportDAO getInstance() throws MyDBException {
		if (instance == null) {
			instance = new AirportDAO();
		}
		return instance;
	}

	/**
	 * Constructeur par défaut Initialisation de la connexion à la base de
	 * données
	 * 
	 * @throws MyDBException
	 */
	private AirportDAO() throws MyDBException {
		super();
		dbaccess = DBAccess.getInstance();
	}

	/**
	 * Connexion à la base de données
	 * 
	 * @throws MyDBException
	 */
	public void getConnection() throws MyDBException {
		try {
			dbaccess.connect();
		} catch (SQLException e) {
			throw new MyDBException("Connexion à la base de donnees impossible");
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
	 * Permet d'insérer un nouvel enregistrement dans la table Airport
	 * 
	 * @param m
	 *            Airport à insérer
	 * @return nombre d'enregistrements impactés
	 * @throws MyDBException
	 */
	public void insertAirport(Airport a) throws MyDBException {
		String requete = "INSERT INTO airport (airport_name, city, country, time_zone) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStmt = null;

		getConnection();
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, a.getAirportName());
			preparedStmt.setString(2, a.getCity());
			preparedStmt.setString(3, a.getCountry());
			preparedStmt.setInt(4, a.getTimezone());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			throw new MyDBException("Probleme lors de la creation ou de l'exécution de la requete d'insertion");
		}
		try {
			preparedStmt.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors du close du statement de l'insertion");
		}
		close();
	}

	/**
	 * Permet d'afficher toute la table Airport
	 * 
	 * @throws MyDBException
	 */
	public List<Airport> selectAll() throws MyDBException {
		ResultSet resultats = null;
		List<Airport> lstAirports = new ArrayList<Airport>();
		String requete = "SELECT * FROM airport;";
		PreparedStatement preparedStmt = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			while (resultats.next()) {
				Airport a = Factory.createAirport(resultats.getString("airport_name"), resultats.getString("city"),
						resultats.getString("country"), resultats.getInt("time_zone"));
				lstAirports.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return lstAirports;
	}

	public Integer selectIdAirport(String airport) throws MyDBException {
		// récupération de l'idAirport
		Integer idAirport = 0;
		ResultSet resultat = null;
		String requete = "SELECT id_airport FROM airport WHERE airport_name = ?;";
		PreparedStatement preparedStmt = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, airport);
			resultat = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				idAirport = resultat.getInt("id_airport");
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return idAirport;
	}
	
	public Integer selectTimeZoneAirport(String airport) throws MyDBException {
		// récupération de l'idAirport
		Integer timezone = 0;
		ResultSet resultat = null;
		String requete = "SELECT time_zone FROM airport WHERE airport_name = ?;";
		PreparedStatement preparedStmt = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, airport);
			resultat = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				timezone = resultat.getInt("id_airport");
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return timezone;
	}
}
