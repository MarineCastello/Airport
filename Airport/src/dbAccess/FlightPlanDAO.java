package dbAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.FlightPlan;
import entity.Plane;
import entity.Airline;
import entity.Airport;
import entity.Factory;
import entity.FlightDuration;

public class FlightPlanDAO {
	private DBAccess dbaccess = null;
	private static FlightPlanDAO instance = null;

	/**
	 * Retourne une instance de FlightPlanDAO
	 * 
	 * @return instance de la classe
	 * @throws MyDBException
	 */
	public static FlightPlanDAO getInstance() throws MyDBException {
		if (instance == null) {
			instance = new FlightPlanDAO();
		}
		return instance;
	}

	/**
	 * Constructeur par défaut Initialisation de la connexion à la base de
	 * données
	 * 
	 * @throws MyDBException
	 */
	private FlightPlanDAO() throws MyDBException {
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
	 * Permet d'insérer un nouvel enregistrement dans la table FlightPlan
	 * 
	 * @param m
	 *            FlightPlan à insérer
	 * @throws MyDBException
	 */
	public void insertFlightPlan(FlightPlan fp) throws MyDBException {
		// ajout de l'avion avec le bon id_FlightPlan et le bon id_FlightPlan
		PreparedStatement preparedStmt = null;

		getConnection();
		
		// récupération de l'id du plane 
		Integer idPlane = PlaneDAO.getInstance().selectIdPlane(fp.getPlane().getPlaneName());		
		
		// récupération de l'id de la flight duration
		Integer idFlightDuration = FlightDurationDAO.getInstance().selectIdFlightDuration(fp.getDuration().getAirportDeparture(), fp.getDuration().getAirportArrival());
		
		// insert du flight plan dans la BDD 
		String requete = "INSERT INTO flight_plan (fk_id_plane, fk_id_flight_duration, departure_time) VALUES (?,?,?);";
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idPlane);
			preparedStmt.setInt(2, idFlightDuration);
			preparedStmt.setDate(3, fp.getDepartureTime());
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
	 * Permet d'afficher toute la table FlightPlan
	 * 
	 * @throws MyDBException
	 */
	public List<FlightPlan> selectAll() throws MyDBException {
		ResultSet resultats = null;
		List<FlightPlan> lstFlightPlans = new ArrayList<FlightPlan>();
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
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
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportDeparture = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportArrival = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportDeparture, airportArrival, resultats.getInt("duration"));
				FlightPlan a = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
				lstFlightPlans.add(a);
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

		return lstFlightPlans;
	}
}
