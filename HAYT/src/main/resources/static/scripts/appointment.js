document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("saveAppointmentButton").addEventListener("click", function (event) {
        event.preventDefault();

        const title = document.getElementById("appointmentTitle").value.trim();
        const dateTime = document.getElementById("appointmentDate").value.trim();
        const description = document.getElementById("appointmentDescription").value.trim();

        const errorTitle = document.getElementById("errorAppointmentTitle");
        const errorDate = document.getElementById("errorAppointmentDate");
        const errorDescription = document.getElementById("errorAppointmentDescription");

        errorTitle.textContent = "";
        errorDate.textContent = "";
        errorDescription.textContent = "";

        let isValid = true;

        if (title === "") {
            errorTitle.textContent = "Title is required";
            errorTitle.style.color = "red";
            isValid = false;
        }

        if (dateTime === "") {
            errorDate.textContent = "Date and Time are required";
            errorDate.style.color = "red";
            isValid = false;
        } else {
            const selectedDate = new Date(dateTime);
            const now = new Date();
            if (selectedDate < now) {
                errorDate.textContent = "The date and time must be in the future";
                errorDate.style.color = "red";
                isValid = false;
            }
        }

        if (description === "") {
            errorDescription.textContent = "Description is required";
            errorDescription.style.color = "red";
            isValid = false;
        }

        if (!isValid) return;

        const form = document.getElementById("appointmentForm");
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