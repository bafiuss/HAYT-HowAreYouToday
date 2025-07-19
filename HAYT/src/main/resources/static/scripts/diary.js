document.getElementById("saveThoughtButton").addEventListener("click", async () => {
    const titleInput = document.getElementById("pageTitle");
    const contentInput = document.getElementById("pageContent");
    const moodInput = document.querySelector(".form-select");

    const diaryEntry = {
        title: titleInput.value,
        content: contentInput.value,
        mood: moodInput.value
    };

    const response = await fetch("/api/diary", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(diaryEntry)
    });

    if (response.ok) {
        const newThought = await response.json();

        titleInput.value = "";
        contentInput.value = "";
        moodInput.value = "";

        const modalElement = document.getElementById("newPageModal");
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();

        addThoughtToList(newThought);

    } else {
        alert("Errore durante il salvataggio.");
    }
});

function addThoughtToList(thought) {
    const thoughtsContainer = document.getElementById("thoughtsContainer");
    const newPageBox = thoughtsContainer.querySelector(".col-xl-3");

    const thoughtId = `thought-${thought.id}`;
    const truncatedContent = truncateText(thought.content, 30);
    const formattedDate = formatDate(thought.createdAt);

    const thoughtHTML = `
        <div class="col-xl-3 col-md-6 mb-4" id="${thoughtId}">
            <div class="card card-blog card-plain border-0">
                <div class="position-relative">
                    <a class="d-block shadow-xl border-radius-xl">
                        <img src="/images/patient/diary-thought.jpg" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl rounded-top">
                    </a>
                </div>
                <div class="card-body">
                    <p class="text-gradient text-dark mb-2 text-sm" style="color: #344767;">${formattedDate}</p>
                    <h5 style="color: #344767;">
                        I feel <strong>${thought.mood}</strong>
                    </h5>
                    <p class="mb-4 text-sm" style="color: #8392AB;">${truncatedContent}</p>
                    <div class="d-flex align-items-center justify-content-between">
                        <button type="button"
                                class="button-24 view-thought-button"
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
            document.getElementById('thoughtMood').value = mood;
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
            document.getElementById('thoughtMood').value = mood;
        });
    });
});
