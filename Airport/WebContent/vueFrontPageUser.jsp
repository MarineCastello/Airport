<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/CSS" href="vueFrontPageUserCSS.css"/>
<script type="text/javascript" src="airportJS.js"></script>
<title>Recherche Ultime</title>
</head>
<body>
	<div id="container">
		<h1 id="title">Recherche Ultime</h1>
		<form action="ControlerServlet" method="post" id="formulaire">
			<div id="divDynamicList">
				<select name="DYNAMICLIST" id="dynamicList" onchange="selectChange();">
					<option value="0" class="elmtFilter">Choisir une action</option>
					<option value="1" class="elmtFilter">Avions disponibles dans un aéroport donné</option>
					<option value="2" class="elmtFilter">Avions en vol</option>
					<option value="3" class="elmtFilter">Recherche d'un avion</option>
				</select>
			</div>
			<div class="champsForm" id="departAirport">
				<div class="divForm">Aeroport de depart</div><input type="text" name="DEPARTAIRPORT" placeholder="Aeroport de depart" class="textBoxForm"/>
			</div>
			<div class="champsForm" id="arrivalAirport">
				<div class="divForm">Aeroport d'arrivee</div><input type="text" name="ARRIVALAIRPORT" placeholder="Aeroport d'arrivee" class="textBoxForm"/>
			</div>
			<div class="champsForm" id="planeID">
				<div class="divForm">Matricule de l'avion</div><input type="text" name="PLANEID" placeholder="Matricule de l'avion" class="textBoxForm"/>
			</div>
			<div id="researchButton">
			<input type="submit" value="Rechercher" class="button"/>
			</div>
		</form>
	</div>
	
</body>
</html>