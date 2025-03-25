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

    if (description === "") {
        errorDescription.textContent = "Description is required";
        errorDescription.style.color = "red";
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

        const officeStartTime = new Date(selectedDate);
        officeStartTime.setHours(9, 0, 0, 0);
        const officeEndTime = new Date(selectedDate);
        officeEndTime.setHours(18, 0, 0, 0);

        if (selectedDate < officeStartTime || selectedDate > officeEndTime) {
            errorDate.textContent = "Appointments must be scheduled between 9:00 AM and 6:00 PM";
            errorDate.style.color = "red";
            isValid = false;
        }

        getAppointmentsForPsychotherapist().then(appointments => {
            const gapInMillis = 30 * 60 * 1000;
            for (let appointment of appointments) {
                const appointmentTime = new Date(appointment.dateTime);
                if (Math.abs(appointmentTime - selectedDate) < gapInMillis) {
                    errorDate.textContent = "The selected time is too close to another appointment. Please select a different time";
                    errorDate.style.color = "red";
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                const form = document.getElementById("appointmentForm");
                const patientId = form.dataset.patientId;

                const appointmentData = {
                    title: title,
                    dateTime: dateTime,
                    description: description,
                    patientId: patientId,
                    psychotherapistId: form.dataset.psychotherapistId
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

                        document.getElementById("appointmentTitle").value = "";
                        document.getElementById("appointmentDate").value = "";
                        document.getElementById("appointmentDescription").value = "";

                        const successAlert = document.getElementById("successAlert");
                        successAlert.style.display = "block";
                    })
                    .catch(error => console.error("Error:", error));
            }
        });
    }

    if (!isValid) return;
});



function getAppointmentsForPsychotherapist() {
    const patientId = document.getElementById("appointmentForm").dataset.patientId;

    return fetch('/psychotherapist-appointments', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error("Error fetching appointments:", error);
            return [];
        });
}


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