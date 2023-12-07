const adminTopNavbar = document.getElementById("admin_top_navbar");
const navigation = document.getElementById("nav-home-tab");
const adminPanel = document.getElementById("admin_panel");
const addUser = document.getElementById("add");
const editUser = document.getElementById("edit_form");
const deleteUser = document.getElementById("delete_form");
const modalEdit = document.getElementById("modal_edit");
const modalDelete = document.getElementById("modal_delete");

const userURL = 'http://localhost:8080/api/user';
const addUserURL = 'http://localhost:8080/admin/add';
const editUserURL = 'http://localhost:8080/admin/edit';
const deleteUserURL = 'http://localhost:8080/admin/delete';
const listUsersURL = 'http://localhost:8080/admin/users';
const listRolesURL = 'http://localhost:8080/admin/roles';
let listUsers;
let listRoles;

const createTopNavbar = () => {
    fetch(userURL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(user => {
            adminTopNavbar.innerHTML = `
                  <b>${user.username}</b>
                  <span>
                  with roles: ${user.roles.map((role) => role.name.replace("ROLE_", "")).join(" ")}
                  </span> `
        })
}

createTopNavbar();

async function getUsersAndRoles() {
    let users = await fetch(listUsersURL);
    listUsers = await users.json();
    let roles = await fetch(listRolesURL);
    listRoles = await roles.json();
    createAdminPanel();
}

getUsersAndRoles();

const createAdminPanel = () => {
    let table = '';
    listUsers.forEach(user => {
        table += `
                                <tr>
                                    <td> ${user.id} </td>
                                    <td> ${user.name} </td>
                                    <td> ${user.lastname} </td>
                                    <td> ${user.age} </td>
                                    <td> ${user.username} </td>
                                    <td> ${user.roles.map((role) => role.name.replace("ROLE_", "")).join(" ")} </td>
                                    <td>
                                        <button class="btn btn-info" data-toggle="modal" data-target="#modal_edit" onclick='editUserForm(${user.id})'>Edit
                                        </button>
                                    </td>
                                    <td>
                                        <button class="btn btn-danger" data-toggle="modal" data-target="#modal_delete" onclick='deleteUserForm(${user.id})'>Delete
                                        </button>                                                                      
                                    </td>
                                </tr>
                        `
        adminPanel.innerHTML = table;
    })
}

addUser.addEventListener("submit", async (e) => {
    const addName = document.getElementById("name");
    const addLastname = document.getElementById("lastname");
    const addAge = document.getElementById("age");
    const addUsername = document.getElementById("username");
    const addPassword = document.getElementById("password");
    const roles = document.getElementById("roles");
    const addListRoles = [];

    e.preventDefault();

    for (let i = 0; i < roles.options.length; i++) {
        if (roles.options[i].selected) {
            addListRoles.push({
                id: roles.options[i].value,
                name: roles.options[i].text
            });
        }
    }

    await fetch(addUserURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: addName.value,
            lastname: addLastname.value,
            age: addAge.value,
            username: addUsername.value,
            password: addPassword.value,
            roles: addListRoles
        })
    })
        .then(() => {
            navigation.click();
            location.reload();
        });
})


function editUserForm(id) {
    const editId = document.getElementById("edit_id")
    const editIdHidden = document.getElementById("edit_id_hidden")
    const editName = document.getElementById("edit_name");
    const editLastname = document.getElementById("edit_lastname");
    const editAge = document.getElementById("edit_age");
    const editUsername = document.getElementById("edit_email");
    const editPassword = document.getElementById("edit_password");
    const editRoles = document.getElementById("edit_roles");

    let user = listUsers.find(user => user.id === id);

    editId.value = user.id;
    editIdHidden.value = user.id;
    editName.value = user.name;
    editLastname.value = user.lastname;
    editAge.value = user.age;
    editUsername.value = user.username;
    editPassword.value = user.password;
    editRoles.options.length = 0;
    listRoles.forEach(role => {
        let option = new Option();
        option.text = role.name;
        option.value = role.id;
        editRoles.add(option);
    });
}

function getEditUserAction() {
    editUser.addEventListener("submit", async (e) => {
        e.preventDefault();
        let formData = new FormData(e.target);
        let update = await fetch(editUserURL, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body:
                $("#edit_form").serialize()
        });
        listUsers[formData.get("id") - 1] = await update.json();
        createAdminPanel();
        // $(modalEdit).modal('hide');
    });
}

getEditUserAction();

function deleteUserForm(id) {
    const deleteId = document.getElementById("delete_id")
    const deleteIdHidden = document.getElementById("delete_id_hidden")
    const deleteName = document.getElementById("delete_name");
    const deleteLastname = document.getElementById("delete_lastname");
    const deleteAge = document.getElementById("delete_age");
    const deleteUsername = document.getElementById("delete_email");
    const deleteRoles = document.getElementById("delete_roles");

    let user = listUsers.find(user => user.id === id);

    deleteId.value = user.id;
    deleteIdHidden.value = user.id;
    deleteName.value = user.name;
    deleteLastname.value = user.lastname;
    deleteAge.value = user.age;
    deleteUsername.value = user.username;
    deleteRoles.options.length = 0;
    listRoles.forEach(role => {
        let option = new Option();
        option.text = role.name;
        option.value = role.id;
        deleteRoles.add(option);
    });
}

getDeleteUserAction();

function getDeleteUserAction() {
    deleteUser.addEventListener("submit", async (e) => {
        e.preventDefault();
        let formData = new FormData(e.target);
        await fetch(deleteUserURL, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            body: $("#delete_id_hidden").serialize()
        });
        listUsers.splice(listUsers.findIndex(user => Number(user.id) === Number(formData.get("id"))), 1);
        createAdminPanel();
        $(modalDelete).modal('hide');
    });
}









