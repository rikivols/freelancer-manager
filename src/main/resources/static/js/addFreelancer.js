var skillCounter = 0;

function addSkill() {
    var div = document.createElement('div');
    div.className = "skill";

    div.innerHTML = `
        <div>
            <label for="skillName">Skill Name</label>
            <input class="w3-input" type="text" id="skillName" name="skills[${skillCounter}].name" >
        </div>
        <div>
            <label for="yearsOfExperience">Years of Experience</label>
            <input class="w3-input" type="number" id="yearsOfExperience" name="skills[${skillCounter}].yearsOfExperience" >
        </div>
        <div>
            <label for="notes">Notes</label>
            <textarea class="w3-input" id="notes" name="skills[${skillCounter}].notes"></textarea>
        </div>
        <button class="remove-skill">X</button>
    `;

    div.querySelector('.remove-skill').addEventListener('click', function() {
        div.remove();
    });

    document.getElementById('skills').appendChild(div);
    skillCounter++;
}

// makes one skill to be expanded by default
window.onload = addSkill;
