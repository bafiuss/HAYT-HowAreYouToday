var socket = new SockJS('/chat');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    stompClient.subscribe('/topic/messages', function(message) {
        var msg = JSON.parse(message.body);
        addMessageToChat(msg);
    });
});

function sendMessage() {
    var input = document.getElementById("messageInput");
    var messageContent = input.value.trim();

    if (messageContent !== "") {
        var msg = {
            senderId: senderId,
            receiverId: receiverId,
            content: messageContent,
            timestamp: new Date().toISOString()
        };

        stompClient.send("/app/sendMessage", {}, JSON.stringify(msg));
        input.value = "";
    }
}

function addMessageToChat(msg) {
    var chatBox = document.getElementById("chatMessages");
    var div = document.createElement("div");

    if (msg.receiverId == receiverId) {
        div.classList.add("row", "justify-content-start", "mb-4");
    } else {
        div.classList.add("row", "justify-content-end", "text-right", "mb-4");
    }

    div.innerHTML = `
        <div class="col-auto">
            <div class="card" style="${msg.receiverId != receiverId ? 'background-color: #e9ecef' : ''}">
                <div class="card-body py-2 px-3">
                    <p class="mb-1">${msg.content}</p>
                    <div class="d-flex align-items-center ${msg.receiverId != receiverId ? 'justify-content-end' : ''} text-sm opacity-6">
                        <small style="color: #67748E">${new Date().toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}</small>
                    </div>
                </div>
            </div>
        </div>
    `;

    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}
