var skillCounter = 0;

function addSkill() {
    var div = document.createElement('div');
    div.className = "skill";

    div.innerHTML = `
        <div>
            <label for="skillName-${skillCounter}">Skill Name</label>
            <input class="w3-input" type="text" id="skillName-${skillCounter}" name="skillForms[${skillCounter}].name" >
        </div>
        <div>
            <label for="yearsOfExperience-${skillCounter}">Years of Experience</label>
            <input class="w3-input" type="number" id="yearsOfExperience-${skillCounter}" name="skillForms[${skillCounter}].yearsOfExperience" >
        </div>
        <div>
            <label for="notes-${skillCounter}">Notes</label>
            <textarea class="w3-input" id="notes-${skillCounter}" name="skillForms[${skillCounter}].notes"></textarea>
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

// makes the remove button to work for modify freelancer
window.onload = function() {
    var skillForms = document.querySelectorAll('.skill');
    skillForms.forEach(function(skillForm) {
        var removeButton = skillForm.querySelector('.remove-skill');
        removeButton.addEventListener('click', function() {
            skillForm.remove();
        });
    });
}