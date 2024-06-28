

let username = prompt("Choisissez un nom d'utilisateur");
const ws = new WebSocket("ws://localhost:8080/chatRedux/chat")

let messages = document.getElementById('messages');
let usernames = document.getElementById('usernames');
let message = document.getElementById('message');
let user;

ws.onopen = function (event){
	 let data = transformConnectToJson(username);
	 chat(data);
}

ws.onmessage = function (event){
	try{
		let chatMessage = JSON.parse(event.data);
		if(chatMessage.typeMessage == "CONNECT"){
			user = chatMessage.username;
			getPseudo(user);
		}else{
			addMessage(chatMessage);
		}
	}catch(error){
		if(event.data == "newList"){
			usernames.innerHTML ="";
		}
		else{
			addUsername(event.data);
		}
	}
}

function getPseudo(pseudo){
	document.getElementById('pseudo').innerHTML = "Vous etes connecté avec le pseudo "+pseudo;
}

ws.onclose = function(event){
	console.log("connexion fermée : "+event.reason);
}

document.getElementById('send').addEventListener('click', ()=>{
	let data = transformChatToJson(user, message.value);
	chat(data);
	addMessageMe();
	message.value="";
});

document.getElementById('disconnect').addEventListener('click', ()=>{
	disconnect(user);
});


function addMessageMe(){
	messages.innerHTML += user+"> "+message.value+"<br>";
}

function disconnect(userChat){
	let data = transformDisconnectToJson(userChat);
	chat(data);
}

function chat(data){
	ws.send(data);
}

function transformDisconnectToJson(user){
	return JSON.stringify({
		username : user,
		content : "",
		typeMessage : "DISCONNECT"
	})
}


function transformChatToJson(userChat, content){
	return JSON.stringify({
		username : userChat,
		content : content,
		typeMessage : "CHAT"
	})
}

function transformConnectToJson(userChat){
	return JSON.stringify({
		username : userChat,
		content : "",
		typeMessage : "CONNECT"
	})
}

function addMessage(chatMessage){
	console.log(chatMessage)
	messages.innerHTML += chatMessage.username+"> "+chatMessage.content+"<br>"
}

function addUsername(username){
	usernames.innerHTML += username+"<br>";
}