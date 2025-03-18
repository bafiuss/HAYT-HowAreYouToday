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
    var chatCount = document.getElementById("charCount");
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
        chatCount.textContent = "0/255";
    }
}

let lastMessageDay = localStorage.getItem("lastMessageDay") || null;

function addMessageToChat(msg) {
    var chatBox = document.getElementById("chatMessages");
    var div = document.createElement("div");

    let messageDay = new Date(msg.timestamp).toLocaleDateString('en-US', {
        weekday: 'long'
    }).toUpperCase();

    if (lastMessageDay !== messageDay) {
        let dateDiv = document.createElement("div");
        dateDiv.classList.add("text-center", "my-3", "text-muted");
        dateDiv.innerHTML = `<b>${messageDay}</b>`;
        chatBox.appendChild(dateDiv);
        lastMessageDay = messageDay;
        localStorage.setItem("lastMessageDay", lastMessageDay);
    }

    if (msg.receiverId == receiverId) {
        div.classList.add("row", "justify-content-start", "mb-4");
    } else {
        div.classList.add("row", "justify-content-end", "text-right", "mb-4");
    }

    div.innerHTML = `
        <div class="col-auto">
            <div class="card" style="border: none; ${msg.receiverId != receiverId ? 'background-color: #e9ecef' : ''}">
                <div class="card-body py-2" style="max-width: 300px; word-wrap: break-word; white-space: normal; overflow: visible;">
                    <p class="m-1">${msg.content}</p>
                    <div class="d-flex align-items-center ${msg.receiverId != receiverId ? 'justify-content-end' : ''} text-sm opacity-6">
                        <small style="color: #67748E">
                            ${new Date(msg.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true })}
                        </small>
                    </div>
                </div>
            </div>
        </div>
    `;

    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}


    function updateCharacterCount() {
            let input = document.getElementById("messageInput");
            let charCount = document.getElementById("charCount");

            charCount.textContent = `${input.value.length}/255`;

            if (input.value.length >= 255) {
            input.value = input.value.substring(0, 255);
        }
    }
