<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/CSS" href="vueFrontPageUserCSS.css"/>
<title>Recherche Ultime</title>
</head>
<body>
	<div id="container">
		<form action="ControlerServlet" method="post" id="formulaire">
			<div class="champsForm">
				<div class="divForm">Aeroport de depart</div><input type="text" name="DEPARTAIRPORT" placeholder="Aeroport de depart" class="textBoxForm"/>
			</div>
			<div class="champsForm">
				<div class="divForm">Aeroport d'arrivee</div><input type="text" name="ARRIVALAIRPORT" placeholder="Aeroport d'arrivee" class="textBoxForm"/>
			</div>
			<div class="champsForm">
				<div class="divForm">Matricule de l'avion</div><input type="text" name="PLANEID" placeholder="Matricule de l'avion" class="textBoxForm"/>
			</div>
			<div class="champsForm">
				<div class="divForm">Avions en vol uniquement</div><input type="checkbox" name="CHECKBOX" id="checkBoxPlane"/>
			</div>
			<div class="champsForm">
			<input type="submit" value="Rechercher" id="researchButton"/>
			</div>
		</form>
	</div>
	
</body>
</html>