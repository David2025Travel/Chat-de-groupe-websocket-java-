<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="bootstrap.min.css"><link>
<style>
    body {
    font-family: 'Consolas', monospace;
    font-style: italic;
}
    
</style>
<title>Data</title>
</head>
<body>
	<div class="container">
        <div class="row mt-2">
            <p>Salut ${sessionScope.username}, dans cette page sont affichées les données de la discussion</p>
            <p>Vous pouvez <a href="chat">retournez</a> afin de continuer votre discussion.</p>
        </div>
        <h2>Information courante</h2>
        <div class="row">
            <div class="col-3 text-center mx-1 bg-success">Utilisateur courant</div>
            <div class="col-5 text-center mx-1 bg-success">Transcription complete</div>
            <div class="col-3 text-center mx-1 bg-success">Utilisateurs actifs</div>
        </div>
        <div class="row">
            <!-- nameServlet -->
            <jsp:include page="/nameServlet"></jsp:include>
             <!-- transcriptionServlet -->
            <jsp:include page="/transcription"></jsp:include>
              <!-- UsernamesServlet -->
            <jsp:include page="/usernameServlet"></jsp:include>
        </div>
        <h2>Messages par utilisateur</h2>
        <!-- UserMessageServlet -->
        <jsp:include page="/userMessages"></jsp:include>
    </div>
</body>
</html>