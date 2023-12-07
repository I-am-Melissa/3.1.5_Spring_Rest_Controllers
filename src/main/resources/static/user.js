const userInfoPage = document.getElementById("user_information_page");
const userTopNavbar = document.getElementById("user_top_navbar");
const userURL = 'http://localhost:8080/api/user';

let userPage = () => {
    fetch(userURL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(user => {
                userInfoPage.innerHTML = `
                                <tr>
                                    <td> ${user.id} </td>
                                    <td> ${user.name} </td>
                                    <td> ${user.lastname} </td>
                                    <td> ${user.age} </td>
                                    <td> ${user.username} </td>
                                    <td> ${user.roles.map((role) => role.name.replace("ROLE_", "")).join(" ")} </td>
                                </tr> `

                userTopNavbar.innerHTML = `
                  <b>${user.username}</b>
                  <span>
                  with roles: ${user.roles.map((role) => role.name.replace("ROLE_", "")).join(" ")}
                  </span> `
        })
}
userPage();
