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
                alert(data);
                document.getElementById("closeAppointmentButton").click();
            })
            .catch(error => console.error("Error:", error));
    });
});
