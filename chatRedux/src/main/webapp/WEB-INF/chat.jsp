<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat</title>
<link type="text/css" rel="stylesheet" href="bootstrap.min.css"><link>
<script type="text/javascript" src="chat.js" async="true"></script>
</head>
<style>
body {
    font-family: 'Consolas', monospace;
    font-style: italic;
}

.card-body-scroll {
    max-height: 400px; /* Définissez ici la hauteur maximale souhaitée */
    overflow-y: auto; /* Active le défilement vertical lorsque le contenu dépasse */
}

.usernames {
	width: 230px;
	height: 410px;
	margin-top: 10px;
	padding-top: 5px;
	padding-left: 0%;
}

button {
	border-radius: 5px;
	margin: 3px;
}

.messages {
	height: 410px;
	margin-top: 10px;
}

 .body{
 	font-family: "consolas", "monospace";
    font-weight: "italic";
 }
</style>
<body>
	<div class="container">
		<div class="row mt-3">
			<div class="col">
				<div class="card messages">
					<div class="card-body card-body-scroll" id="messages"></div>
				</div>
			</div>
			<div class="col">
				<div class="card usernames">
					<div class="card-body" id="usernames"></div>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col">
				<input type="text" class="form-control" id="message" required="required">
			</div>
			<div class="col">
				<button id="send">Envoyer</button>
				<button id="disconnect">Se deconnecter</button>
			</div>
		</div>
		<div class="row mt-4">
			<p id="pseudo"></p>
			<p>
				<a href="data" id="dataPage">Cliquez ici</a> pour voir les données de la
				discussion
			</p>
		</div>
	</div>
</body>
</html>