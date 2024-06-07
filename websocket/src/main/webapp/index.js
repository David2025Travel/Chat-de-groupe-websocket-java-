
// demande le nom de l'utilisateur
let username = prompt("Entrez un nom d'utilisateur");
let userList = document.getElementById('userList');
let trueUsername ;
let h3 = document.querySelector("h3");

const ws = new WebSocket("ws://localhost:8080/websocket/chat");

function afficherHeure(){
    let date = new Date();
    let heure = date.getHours();
    let minute = date.getMinutes();
    let seconde = date.getSeconds();

    if(heure<10){
        heure = "0"+heure;
    }
    if(minute <10 ){
        minute= "0"+minute;
    }
    if(seconde <10){
        seconde ="0"+seconde;
    }
    return heure+":"+minute+":"+seconde;
}
ws.onopen = function (event){
    let userConnect = new User(username, "est connecté ... ("+afficherHeure()+")", "CONNECT");
    sendMessage(userConnect);
}
function sendMessage(user){
    ws.send(JSON.stringify(user));
}

function removeUserDisconnect(username){
    userList.querySelectorAll("li").forEach(element =>{
        if(element.textContent == username){
            element.remove();
        }
    })
}

ws.onmessage = function (event){
    try {
        let userChat = JSON.parse(event.data);
        if(userChat.typeMessage == "CONNECT"){
            trueUsername = userChat.username;
            h3.textContent="Your username is : "+trueUsername+" in this chat";
        } else if(userChat.typeMessage == "userDisconnect"){
            removeUserDisconnect(userChat.username);
        } else{
            receiveMessage(userChat.username, userChat.boby);
        }
        
    } catch (e) {
       addUser(event.data);
    }
}

document.getElementById('sendButton').addEventListener('click', function() {
    var messageInput = document.getElementById('messageInput');
    var messageList = document.getElementById('messageList');

    if (messageInput.value.trim() !== "") {
        var listItem = document.createElement('li');
        listItem.className = 'message-item sent-message';
        listItem.textContent = messageInput.value;
        messageList.appendChild(listItem);
        let chat = new User(trueUsername, messageInput.value, "CHAT");
        sendMessage(chat);
        messageInput.value = '';
        messageList.scrollTop = messageList.scrollHeight; // Scroll to the bottom of the message list
    }
});

document.getElementById('disconnectButton').addEventListener('click', function() {
    let confirmDisconnect = confirm('Êtes-vous sûr de vouloir vous déconnecter ?');
    if (confirmDisconnect) {
        let chatEnd = new User(trueUsername, "c'est deconnecté ... ("+afficherHeure()+")", "DISCONNECT");
        sendMessage(chatEnd);
    }
});

function receiveMessage(username, boby) {
    let messageList = document.getElementById('messageList');
    let listItem = document.createElement('li');
    listItem.className = 'message-item received-message';
    listItem.textContent = username+": "+boby;
    messageList.appendChild(listItem);
    messageList.scrollTop = messageList.scrollHeight; // Scroll to the bottom of the message list
}

function addUser(username){
    // je dois supprimer les li deja present et les remplace ensuite
    let listItem = document.createElement('li');
    listItem.className = 'list-group-item';
    listItem.textContent = username;
    userList.appendChild(listItem);
}

function deleteListUserConnected(){
    userList.querySelectorAll("li").forEach(li => li.remove());
}
class User {
    constructor(username,boby,typeMessage){
        this.username = username;
        this.boby=boby;
        this.typeMessage = typeMessage;
    }
    ;
}



