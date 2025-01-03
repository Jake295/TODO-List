const taskList = document.getElementById('task-list');
const form = document.getElementById('new-task-form');
const titleInput = document.getElementById('task-title');
const noteInput = document.getElementById('task-note');
const dueDateInput = document.getElementById('task-due-date');

// Fetch existing tasks on page load
fetch('/api/tasks')
    .then(response => response.json())
    .then(tasks => {
        tasks.forEach(task => {
            const li = document.createElement('li');
            li.classList.add('task-item');

            // Checkbox for completion
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.checked = task.complete;
            checkbox.addEventListener('change', () => {
                updateTaskCompletion(task.id, checkbox.checked);
            });

            // Title/Note Display Mode Elements
            const titleNoteSpan = document.createElement('span');
            titleNoteSpan.classList.add('task-title-note');

            const titleEl = document.createElement('span');
            titleEl.classList.add('task-title');
            titleEl.textContent = task.title;

            const noteEl = document.createElement('span');
            noteEl.classList.add('task-note');
            if (task.note && task.note.trim() !== '') {
                noteEl.textContent = `: ${task.note}`;
            }

            titleNoteSpan.appendChild(titleEl);
            if (task.note && task.note.trim() !== '') {
                titleNoteSpan.appendChild(noteEl);
            }

            // Due date display
            const dueDateSpan = document.createElement('span');
            dueDateSpan.classList.add('task-due-date');
            dueDateSpan.textContent = task.due_date ? `Due: ${task.due_date}` : '';

            // Edit mode input fields
            const titleInputEl = document.createElement('input');
            titleInputEl.type = 'text';
            titleInputEl.value = task.title;
            titleInputEl.classList.add('edit-title');
            titleInputEl.style.display = 'none';

            const noteInputEl = document.createElement('input');
            noteInputEl.type = 'text';
            noteInputEl.value = task.note || '';
            noteInputEl.classList.add('edit-note');
            noteInputEl.style.display = 'none';

            const dueDateInputEl = document.createElement('input');
            dueDateInputEl.type = 'date';
            dueDateInputEl.value = task.due_date || '';
            dueDateInputEl.classList.add('edit-due-date');
            dueDateInputEl.style.display = 'none';

            // Actions (Edit & Delete buttons)
            const actionsSpan = document.createElement('span');
            actionsSpan.classList.add('task-actions');

            const editButton = document.createElement('button');
            editButton.textContent = 'Edit';
            editButton.classList.add('edit-button');

            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.classList.add('delete-button');
            deleteButton.addEventListener('click', () => {
                deleteTask(task.id);
            });

            const saveButton = document.createElement('button');
            saveButton.textContent = 'Save';
            saveButton.style.display = 'none';

            const cancelButton = document.createElement('button');
            cancelButton.textContent = 'Cancel';
            cancelButton.style.display = 'none';

            actionsSpan.appendChild(editButton);
            actionsSpan.appendChild(deleteButton);
            actionsSpan.appendChild(saveButton);
            actionsSpan.appendChild(cancelButton);

            // Mode toggle functions
            function showDisplayMode() {
                // Display mode
                titleEl.style.display = '';
                noteEl.style.display = task.note && task.note.trim() !== '' ? '' : 'none';
                dueDateSpan.style.display = task.due_date ? '' : 'none';

                titleInputEl.style.display = 'none';
                noteInputEl.style.display = 'none';
                dueDateInputEl.style.display = 'none';

                editButton.style.display = '';
                deleteButton.style.display = '';
                saveButton.style.display = 'none';
                cancelButton.style.display = 'none';
            }

            function showEditMode() {
                // Edit mode
                titleEl.style.display = 'none';
                noteEl.style.display = 'none';
                dueDateSpan.style.display = 'none';

                titleInputEl.style.display = '';
                noteInputEl.style.display = '';
                dueDateInputEl.style.display = '';

                editButton.style.display = 'none';
                deleteButton.style.display = 'none';
                saveButton.style.display = '';
                cancelButton.style.display = '';
            }

            editButton.addEventListener('click', () => {
                showEditMode();
            });

            saveButton.addEventListener('click', () => {
                const updatedTask = {
                    title: titleInputEl.value,
                    note: noteInputEl.value,
                    due_date: dueDateInputEl.value ? dueDateInputEl.value : null,
                    complete: checkbox.checked
                };
                fetch(`/api/tasks/${task.id}`, {
                    method: 'PUT',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(updatedTask)
                })
                    .then(response => {
                        if (response.ok) {
                            // Update the displayed text
                            task.title = updatedTask.title;
                            task.note = updatedTask.note;
                            task.due_date = updatedTask.due_date;

                            titleEl.textContent = task.title;
                            if (task.note && task.note.trim() !== '') {
                                noteEl.textContent = `: ${task.note}`;
                                noteEl.style.display = '';
                            } else {
                                noteEl.textContent = '';
                                noteEl.style.display = 'none';
                            }
                            dueDateSpan.textContent = task.due_date ? `Due: ${task.due_date}` : '';
                            showDisplayMode();
                        } else {
                            console.error('Failed to update task');
                        }
                    });
            });

            cancelButton.addEventListener('click', () => {
                titleInputEl.value = task.title;
                noteInputEl.value = task.note || '';
                dueDateInputEl.value = task.due_date || '';
                showDisplayMode();
            });

            // Initially display mode
            showDisplayMode();

            li.appendChild(checkbox);
            li.appendChild(titleNoteSpan);
            li.appendChild(dueDateSpan);
            li.appendChild(titleInputEl);
            li.appendChild(noteInputEl);
            li.appendChild(dueDateInputEl);
            li.appendChild(actionsSpan);

            taskList.appendChild(li);
        });
    });

// Add a new task
form.addEventListener('submit', event => {
    event.preventDefault();
    const newTask = {
        title: titleInput.value,
        note: noteInput.value,
        due_date: dueDateInput.value ? dueDateInput.value : null,
        complete: false
    };
    fetch('/api/new-task', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(newTask)
    })
        .then(() => {
            location.reload();
        });
});

function updateTaskCompletion(id, isComplete) {
    fetch(`/api/tasks/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ complete: isComplete })
    })
        .then(response => {
            if (!response.ok) {
                console.error('Failed to update task completion');
            }
        });
}

function deleteTask(id) {
    fetch(`/api/del/${id}`, {
        method: 'DELETE'
    })
        .then(() => {
            location.reload();
        });
}