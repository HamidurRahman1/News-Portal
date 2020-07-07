const enableLogin = () => {
  const loginForm = document.querySelector("#login_form");
  loginForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const formData = new FormData(loginForm);
    const formObj = {
      username: formData.get("log_email"),
      password: formData.get("log_pass"),
    };
    fetch("http://localhost:8080/blogs/api/v1/public/login", {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(formObj),
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (data) {
        console.log(data);
      });
  });
}

export {enableLogin};