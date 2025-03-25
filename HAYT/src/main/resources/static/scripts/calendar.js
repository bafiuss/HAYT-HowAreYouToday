let currentDate = new Date();

document.addEventListener("DOMContentLoaded", renderCalendar);

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
    let appointmentDays = new Set(appointments
        .filter(app => app.date.getFullYear() === year && app.date.getMonth() === month)
        .map(app => app.date.getDate()));

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
        }

        calendar.appendChild(dayCell);
    }
}

function prevMonth() {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderCalendar();
}

function nextMonth() {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderCalendar();
}
