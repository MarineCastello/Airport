package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dbAccess.FlightPlanDAO;
import dbAccess.MyDBException;
import entity.Airport;
import entity.Factory;
import entity.FlightDuration;
import entity.FlightPlan;
import entity.Plane;

public class FlightPlanService {

	// D�finition comme singleton
	private static FlightPlanService instance = null;

	public static FlightPlanService getInstance() {
		if (instance == null) {
			instance = new FlightPlanService();
		}
		return instance;
	}

	/**
	 * Ajout d'un nouveau plan de vol
	 * 
	 * @param planeName
	 *            nom de l'avion concern� par le plan de vol
	 * @param airportDepartureName
	 *            nom de l'a�roport de d�part
	 * @param airportArrivalName
	 *            nom de l'a�roport d'arriv�e
	 * @param departureTime
	 *            heure de d�part
	 * @throws MyDBException
	 */
	public void insertFlightPlan(String planeName, String airportDepartureName, String airportArrivalName,
			Date departureTime) throws MyDBException {
		Plane p = Factory.createPlane(planeName);
		Airport aDepart = Factory.createAirport(airportDepartureName);
		Airport aArrivee = Factory.createAirport(airportArrivalName);
		FlightDuration fd = Factory.createFlightDuration(aDepart, aArrivee);
		FlightPlan fp = Factory.createFlightPlan(p, fd, departureTime);
		FlightPlanDAO.getInstance().insertFlightPlan(fp);
	}

}
