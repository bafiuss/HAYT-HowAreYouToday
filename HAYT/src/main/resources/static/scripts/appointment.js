document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("saveAppointmentButton").addEventListener("click", function () {
        const form = document.getElementById("appointmentForm");
        const title = document.getElementById("appointmentTitle").value;
        const dateTime = document.getElementById("appointmentDate").value;
        const description = document.getElementById("appointmentDescription").value;
        const patientId = form.dataset.patientId;
        const psychotherapistId = form.dataset.psychotherapistId;

        const appointmentData = {
            title: title,
            dateTime: dateTime,
            description: description,
            patientId: patientId,
            psychotherapistId: psychotherapistId
        };

        fetch("/save-appointment", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(appointmentData)
        })
            .then(response => response.text())
            .then(data => {
                document.getElementById("closeAppointmentButton").click();
                highlightAppointmentDay(dateTime);

                const successAlert = document.getElementById("successAlert");
                successAlert.style.display = "block";

            })
            .catch(error => console.error("Error:", error));
    });
});


function highlightAppointmentDay(dateString) {
    const appointmentDate = new Date(dateString);
    const year = appointmentDate.getFullYear();
    const month = appointmentDate.getMonth();
    const day = appointmentDate.getDate();

    document.querySelectorAll(".day").forEach(dayCell => {
        if (parseInt(dayCell.textContent) === day && currentDate.getMonth() === month && currentDate.getFullYear() === year) {
            dayCell.classList.add("appointment-day");
        }
    });
}