document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".viewQuestionnaireButton").forEach(button => {
        button.addEventListener("click", function () {
            let questionnaireId = this.getAttribute("data-id");

            fetch(`/api/questionnaires/${questionnaireId}`)
                .then(response => response.json())
                .then(data => {

                    let fields = {
                        depressedMood: "depressedMoodBar",
                        lossOfInterest: "lossOfInterestBar",
                        appetiteChanges: "appetiteChangesBar",
                        insomnia: "insomniaBar",
                        psychomotorAgitation: "psychomotorAgitationBar",
                        fatigue: "fatigueBar",
                        worthlessness: "worthlessnessBar",
                        difficultyConcentratingDepression: "difficultyConcentratingDepressionBar",
                        excessiveWorry: "excessiveWorryBar",
                        difficultyControllingWorry: "difficultyControllingWorryBar",
                        restlessness: "restlessnessBar",
                        easyFatigue: "easyFatigueBar",
                        difficultyConcentratingAnxiety: "difficultyConcentratingAnxietyBar",
                        irritability: "irritabilityBar",
                        muscleTension: "muscleTensionBar",
                        sleepDisturbances: "sleepDisturbancesBar",
                        impactWorkStudy: "impactWorkStudyBar",
                        impactSocial : "impactSocialBar",
                        impactLeisure : "impactLeisureBar"
                    };

                    for (let key in fields) {
                        if (data[key] !== undefined) {
                            updateMoodBar(fields[key], data[key]);
                        }
                    }
                })
                .catch(error => console.error("Errore nel caricamento:", error));
        });
    });


    function updateMoodBar(barId, value) {
        let bar = document.querySelector(`#${barId}`);
        if (!bar) return;

        let percentage = value * 20;


        let barInner = bar.querySelector(".mood-bar-inner");
        if (!barInner) {
            barInner = document.createElement("div");
            barInner.classList.add("mood-bar-inner");
            bar.appendChild(barInner);
        }

        barInner.style.width = `${percentage}%`;
        barInner.style.backgroundColor = getColor(value);

        bar.querySelectorAll("span").forEach(line => line.remove());

        for (let i = 1; i < 5; i++) {
            let line = document.createElement("span");
            line.style.left = `${i * 20}%`;
            bar.appendChild(line);
        }
    }


    function getColor(value) {
        switch (value) {
            case 1: return "#A5D6A7";
            case 2: return "#C5E1A5";
            case 3: return "#FFF2A1";
            case 4: return "#FFCBA4";
            case 5: return "#FFB3B3";

            default: return "#ddd";
        }
    }

});
