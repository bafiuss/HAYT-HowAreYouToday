let currentDate = new Date();

document.addEventListener("DOMContentLoaded", () => {
    renderCalendar();
    setupSaveAppointmentButton();
});

async function fetchAppointments() {
    try {
        let response = await fetch("/patient-appointments");
        let appointments = await response.json();
        return appointments.map(app => ({
            title: app.title,
            date: new Date(app.dateTime),
            description: app.description,
            patientId: app.patientId,
            psychotherapistId: app.psychotherapistId
        }));
    } catch (error) {
        console.error("Error fetching appointments:", error);
        return [];
    }
}

async function renderCalendar() {
    const calendar = document.getElementById("calendar");
    const monthYear = document.getElementById("monthYear");
    calendar.innerHTML = "";

    let year = currentDate.getFullYear();
    let month = currentDate.getMonth();
    let today = new Date();

    monthYear.textContent = `Appointments - ${new Intl.DateTimeFormat("en-US", { month: "long", year: "numeric" }).format(currentDate)}`;

    const daysOfWeek = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
    daysOfWeek.forEach(day => {
        let dayNameCell = document.createElement("div");
        dayNameCell.classList.add("day-name");
        dayNameCell.textContent = day;
        calendar.appendChild(dayNameCell);
    });

    let firstDay = new Date(year, month, 1).getDay();
    firstDay = (firstDay === 0) ? 6 : firstDay - 1;
    let daysInMonth = new Date(year, month + 1, 0).getDate();

    let appointments = await fetchAppointments();
    let appointmentDays = new Map();

    appointments
        .filter(app => app.date.getFullYear() === year && app.date.getMonth() === month)
        .forEach(app => {
            let day = app.date.getDate();
            if (!appointmentDays.has(day)) {
                appointmentDays.set(day, []);
            }
            appointmentDays.get(day).push(app);
        });

    for (let i = 0; i < firstDay; i++) {
        let emptyCell = document.createElement("div");
        emptyCell.classList.add("day");
        emptyCell.style.visibility = "hidden";
        calendar.appendChild(emptyCell);
    }

    for (let i = 1; i <= daysInMonth; i++) {
        let dayCell = document.createElement("div");
        dayCell.classList.add("day");
        dayCell.textContent = i;

        let cellDate = new Date(year, month, i);

        if (cellDate.toDateString() === today.toDateString()) {
            dayCell.classList.add("current-day");
        } else if (cellDate < today) {
            dayCell.classList.add("past-day");
        }

        if (appointmentDays.has(i)) {
            dayCell.classList.add("appointment-day");

            dayCell.addEventListener("click", () => showAppointmentsModal(i, appointmentDays.get(i)));
        }

        calendar.appendChild(dayCell);
    }
}

function showAppointmentsModal(day, appointments) {
    const modalDate = document.getElementById("modalDate");
    const appointmentsList = document.getElementById("appointmentsList");

    function getDaySuffix(day) {
        if (day >= 11 && day <= 13) return "th";
        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }

    const suffix = getDaySuffix(day);

    const monthName = new Intl.DateTimeFormat("en-US", { month: "long" }).format(new Date());

    modalDate.textContent = `${day}${suffix} of ${monthName}`;

    appointments.sort((a, b) => a.date - b.date);

    appointmentsList.innerHTML = "";

    appointments.forEach(app => {
        const appointmentItem = document.createElement("div");
        appointmentItem.classList.add("appointment-item");

        appointmentItem.innerHTML = `
            <p><strong>Title:</strong> ${app.title}</p>
            <p><strong>Time:</strong> ${app.date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
            <p><strong>Description:</strong> ${app.description}</p>
            <hr>
        `;

        appointmentsList.appendChild(appointmentItem);
    });

    const appointmentsModal = new bootstrap.Modal(document.getElementById("appointmentsModal"));
    appointmentsModal.show();
}

function prevMonth() {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderCalendar();
}

function nextMonth() {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderCalendar();
}

function setupSaveAppointmentButton() {
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
                            renderCalendar();

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
}


function getAppointmentsForPsychotherapist() {
    const patientId = document.getElementById("appointmentForm").dataset.patientId;

    return fetch('/psychotherapist-associated-appointments', {
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