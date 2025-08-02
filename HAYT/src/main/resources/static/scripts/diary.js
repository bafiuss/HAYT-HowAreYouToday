const saveButton = document.getElementById("saveThoughtButton");

if (saveButton) {
    saveButton.addEventListener("click", async () => {

        const titleInput = document.getElementById("pageTitle");
        const contentInput = document.getElementById("pageContent");
        const moodInput = document.querySelector(".form-select");

        const errorTitle = document.getElementById("errorThoughtTitle");
        const errorContent = document.getElementById("errorThoughtContent");
        const errorMood = document.getElementById("errorThoughtMood");

        errorTitle.textContent = "";
        errorContent.textContent = "";
        errorMood.textContent = "";

        let isValid = true;

        if (titleInput.value.trim() === "") {
            errorTitle.textContent = "Title is required";
            errorTitle.style.color = "red";
            isValid = false;
        }

        if (contentInput.value.trim() === "") {
            errorContent.textContent = "Description is required";
            errorContent.style.color = "red";
            isValid = false;
        }

        if (moodInput.value === "null" || moodInput.value.trim() === "") {
            errorMood.textContent = "Mood is required";
            errorMood.style.color = "red";
            isValid = false;
        }

        if (isValid) {

            const diaryEntry = {
                title: titleInput.value.trim(),
                content: contentInput.value.trim(),
                mood: moodInput.value
            };

            const response = await fetch("/api/diary", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(diaryEntry)
            });

            if (response.ok) {
                const newThought = await response.json();

                titleInput.value = "";
                contentInput.value = "";
                moodInput.value = "null";

                const modalElement = document.getElementById("newPageModal");
                const modalInstance = bootstrap.Modal.getInstance(modalElement);
                modalInstance.hide();

                addThoughtToList(newThought);

            } else {
                alert("Errore durante il salvataggio.");
            }
        }
    });
}


function addThoughtToList(thought) {
    const thoughtsContainer = document.getElementById("thoughtsContainer");
    const newPageBox = thoughtsContainer.querySelector(".col-xl-3");

    const thoughtId = `thought-${thought.id}`;
    const truncatedContent = truncateText(thought.content, 30);

    const date = new Date(thought.createdAt);
    const formattedDate = date.toLocaleDateString('it-IT');

    const formattedTime = date.toLocaleTimeString('en-US', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    });

    const capitalizedMood = thought.mood.charAt(0).toUpperCase() + thought.mood.slice(1).toLowerCase();

    const thoughtHTML = `
        <div class="col-xl-3 col-md-6 mb-4" id="${thoughtId}">
            <div class="card card-blog card-plain border-0 h-100 d-flex flex-column">
                <div class="position-relative">
                    <a class="d-block shadow-xl border-radius-xl">
                        <img src="/images/patient/diary-thought.jpg" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl rounded-top">
                    </a>
                </div>
                <div class="card-body d-flex flex-column flex-grow-1">
                    <div class="d-flex justify-content-between text-sm mb-2" style="color: #344767;">
                        <span>${formattedDate}</span>
                        <span>${formattedTime}</span>
                    </div>
                    <h5 style="color: #344767;">
                        <strong>${capitalizedMood}</strong>
                    </h5>
                    <p class="mb-4 text-sm" style="color: #8392AB;">${truncatedContent}</p>
                    <div class="d-flex align-items-center justify-content-between mt-auto">
                        <button type="button"
                                class="button-24 view-thought-button w-100"
                                data-bs-toggle="modal"
                                data-bs-target="#thoughtPageModal"
                                data-title="${thought.title}"
                                data-content="${thought.content}"
                                data-mood="${thought.mood}">
                            View
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;

    newPageBox.insertAdjacentHTML("afterend", thoughtHTML);

    const newButton = document.querySelector(`#${thoughtId} .view-thought-button`);
    if (newButton) {
        newButton.addEventListener('click', function () {
            const title = this.getAttribute('data-title');
            const content = this.getAttribute('data-content');
            const mood = this.getAttribute('data-mood');

            document.getElementById('thoughtTitle').value = title;
            document.getElementById('thoughtContent').value = content;
            document.getElementById('thoughtMood').value = mood.charAt(0).toUpperCase() + mood.slice(1).toLowerCase();

        });
    }
}

function truncateText(text, maxLength) {
    if (text.length <= maxLength) return text;
    return text.slice(0, maxLength).trim() + "...";
}

function formatDate(isoString) {
    const date = new Date(isoString);
    return date.toLocaleDateString('it-IT');
}

document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll('.view-thought-button');

    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const title = this.getAttribute('data-title');
            const content = this.getAttribute('data-content');
            const mood = this.getAttribute('data-mood');

            document.getElementById('thoughtTitle').value = title;
            document.getElementById('thoughtContent').value = content;
            document.getElementById('thoughtMood').value = mood.charAt(0).toUpperCase() + mood.slice(1).toLowerCase();
        });
    });
});
