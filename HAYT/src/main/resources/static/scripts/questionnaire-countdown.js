document.addEventListener("DOMContentLoaded", function() {
    let secondsLeft = countdown;

    function updateCountdown() {
        if (secondsLeft > 0) {
            let now = new Date().getTime();
            let futureTime = now + secondsLeft * 1000;

            function refreshCountdown() {
                let currentTime = new Date().getTime();
                let timeDiff = futureTime - currentTime;

                if (timeDiff > 0) {
                    let days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
                    let hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                    let minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
                    let seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);

                    document.getElementById("countdown").innerText =
                        `Next questionnaire in: ${days}d ${hours}h ${minutes}m ${seconds}s`;

                    document.getElementById("questionnaireLink").classList.add("disabled");

                    setTimeout(refreshCountdown, 1000);
                } else {
                    document.getElementById("countdown").innerText = "";
                    document.getElementById("questionnaireLink").classList.remove("disabled");
                }
            }

            refreshCountdown();
        } else {
            document.getElementById("countdown").innerText = "";
            document.getElementById("questionnaireLink").classList.remove("disabled");
        }
    }

    updateCountdown();
});