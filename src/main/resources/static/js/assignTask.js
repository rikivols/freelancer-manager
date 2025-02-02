
function showDropdown(taskId) {
    console.log('freelancer-' + taskId);
    document.getElementById('freelancer-' + taskId).style.display = 'none';
    document.getElementById('dropdown-' + taskId).style.display = 'block';
}

function hideDropdown(taskId) {
    document.getElementById('freelancer-' + taskId).style.display = 'block';
    document.getElementById('dropdown-' + taskId).style.display = 'none';
}
function assignTask(taskId, freelancerId, suffix) {
    fetch('/rest/task/assignTask', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": "Bearer TOKEN-PUBLIC"
        },
        body: JSON.stringify({
            taskId: taskId,
            freelancerId: freelancerId
        }),
    })
        .then(response => response.json())
        .then(response => {
            let freelancerName = response.freelancerId !== -1 ? response.freelancerName: 'unassigned';
            document.querySelector('#freelancer-' + taskId + '-' + suffix + ' a').innerText = freelancerName;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}
