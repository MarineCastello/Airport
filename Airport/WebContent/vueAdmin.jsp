<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="vueAdminCSS.css"/>
<title>Administrator Vue</title>
</head>
<body>
	<div id="container">
		<h1 id="title">Administrator</h1>
		<form action="ControlerServlet" method="post" id="formulaire">
			<div id="divDynamicList">
				<select name="DYNAMICLISTADMIN" id="dynamicList" onchange="selectChange();">
					<option value="0" class="elmtFilter">Choisir une action</option>
					<option value="1" class="elmtFilter">Ajouter un aéroport</option>
					<option value="2" class="elmtFilter">Ajouter un avion</option>
					<option value="3" class="elmtFilter">Ajouter une compagnie aérienne</option>
					<option value="4" class="elmtFilter">Ajouter un plan de vol</option>
				</select>
			</div>
			<div class="champsForm" id="addAirport">
				<div class="divForm">Airport Name</div><input type="text" name="AIRPORTNAME" placeholder="Airport Name" class="textBoxForm"/>
				<div class="divForm">City</div><input type="text" name="CITY" placeholder="City" class="textBoxForm"/>
				<div class="divForm">Country</div><input type="text" name="COUNTRY" placeholder="Country" class="textBoxForm"/>
				<div class="divForm">Time Zone</div><input type="text" name="TIMEZONE" placeholder="Time Zone" class="textBoxForm"/>
			</div>
			
			<hr class="divSeparator"/>
			
			<div class="champsForm" id="addPlane">
				<div class="divForm">Plane Name</div><input type="text" name="PLANENAME" placeholder="Plane Name" class="textBoxForm"/>
				<div class="divForm">Airline Name</div><input type="text" name="AIRLINENAME" placeholder="Airline" class="textBoxForm"/>
				<div class="divForm">Airport Name</div><input type="text" name="AIRPORTNAME" placeholder="Airport" class="textBoxForm"/>
				<div class="divForm">Available</div><input type="text" name="AVAILABLE" placeholder="Available [0-1]" class="textBoxForm"/>
			</div>
			
			<hr class="divSeparator"/>
			
			<div class="champsForm" id="addAirlineCompany">
				<div class="divForm">Airline Name</div><input type="text" name="AIRLINENAME" placeholder="Airline Name" class="textBoxForm"/>
			</div>
			
			<hr class="divSeparator"/>
			
			<div class="champsForm" id="addFlightPlan">
				<div class="divForm">Plane Name</div><input type="text" name="PLANENAME" placeholder="Plane Name" class="textBoxForm"/>
				<div class="divForm">Departure Airport</div><input type="text" name="DEPARTUREAIRPORT" placeholder="Departure Airport" class="textBoxForm"/>
				<div class="divForm">Arrival Airport</div><input type="text" name="ARRIVALAIRPORT" placeholder="Arrival Airport" class="textBoxForm"/>
				<div class="divForm">Departure Time</div><input type="text" name="DEPARTURETIME" placeholder="Departure Time" class="textBoxForm"/>
			</div>
			
			<hr class="divSeparator"/>
			
			<div class="champsForm" id="addFlightDuration">
				<div class="divForm">Departure Airport</div><input type="text" name="DEPARTUREAIRPORT" placeholder="Departure Airport" class="textBoxForm"/>
				<div class="divForm">Arrival Airport</div><input type="text" name="ARRIVALAIRPORT" placeholder="Arrival Airport" class="textBoxForm"/>
				<div class="divForm">Duration</div><input type="text" name="DURATION" placeholder="Duration" class="textBoxForm"/>
			</div>
			<div id="researchButton">
			<input type="submit" value="Ajouter" class="button"/>
			</div>
		</form>
	</div>
</body>
</html>