package dbAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.FlightDuration;
import entity.Airport;
import entity.Factory;

public class FlightDurationDAO {
	private DBAccess dbaccess = null;
	private static FlightDurationDAO instance = null;

	/**
	 * Retourne une instance de FlightDurationDAO
	 * 
	 * @return instance de la classe
	 * @throws MyDBException
	 */
	public static FlightDurationDAO getInstance() throws MyDBException {
		if (instance == null) {
			instance = new FlightDurationDAO();
		}
		return instance;
	}

	/**
	 * Constructeur par défaut Initialisation de la connexion à la base de
	 * données
	 * 
	 * @throws MyDBException
	 */
	private FlightDurationDAO() throws MyDBException {
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
	 * Permet d'insérer un nouvel enregistrement dans la table FlightDuration
	 * 
	 * @param m
	 *            FlightDuration à insérer
	 * @throws MyDBException
	 */
	public void insertFlightDuration(FlightDuration fd) throws MyDBException {
		// récupération de l'id de l'aéroport de départ
		Integer idAirportDeparture = AirportDAO.getInstance().selectIdAirport(fd.getAirportDeparture().getAirportName());
		
		// récupération de l'id de l'aéroport d'arrivée
		Integer idAirportArrival = AirportDAO.getInstance().selectIdAirport(fd.getAirportArrival().getAirportName());
		
		// insert du flight duration
		String requete = "INSERT INTO flight_duration (fk_id_airport_departure, fk_id_airport_arrival, duration) VALUES (?,?,?);";
		PreparedStatement preparedStmt = null;

		getConnection();
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idAirportDeparture);
			preparedStmt.setInt(2, idAirportArrival);
			preparedStmt.setInt(3, fd.getDuration());
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
	 * Permet d'afficher toute la table FlightDuration
	 * 
	 * @throws MyDBException
	 */
	public List<FlightDuration> selectAll() throws MyDBException {
		ResultSet resultats = null;
		List<FlightDuration> lstFlightDurations = new ArrayList<FlightDuration>();
		String requete = "SELECT Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration "
					   + "FROM rechercheultime.flight_duration FD " 
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport "
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport;";
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
				Airport airportDeparture = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportArrival = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportDeparture, airportArrival, resultats.getInt("duration"));
				lstFlightDurations.add(fd);
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

		return lstFlightDurations;
	}

	public Integer selectIdFlightDuration(Airport airportDeparture, Airport airportArrival) throws MyDBException {
		// récupération de l'idAirportDeparture
		Integer idAirportDeparture = AirportDAO.getInstance().selectIdAirport(airportDeparture.getAirportName());
		
		// récupération de l'idAirportArrival
		Integer idAirportArrival = AirportDAO.getInstance().selectIdAirport(airportArrival.getAirportName());
		
		// récupération de l'idFlightDuration
		Integer idFlightDuration = 0;
		ResultSet resultat = null;
		String requete = "SELECT id_flight_duration FROM flight_duration WHERE fk_id_airport_departure = ? AND fk_id_airport_arrival = ?;";
		PreparedStatement preparedStmt = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idAirportDeparture);
			preparedStmt.setInt(2, idAirportArrival);
			resultat = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				idFlightDuration = resultat.getInt("id_FlightDuration");
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

		return idFlightDuration;
	}
}
