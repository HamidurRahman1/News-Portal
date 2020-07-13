import {signUpEndpoint} from "./api_handling/__public_endpoint.js";

const enableSignUp = () => {
    const signUpForm = document.querySelector("#signup_form");
    signUpForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(signUpForm);
        const formObj = {
            firstName : formData.get("userfirst"),
            lastName : formData.get("userlast"),
            username : formData.get("sign_email"),
            password : formData.get("sign_pass"),
            enabled : false
        };

        console.log(JSON.stringify(formObj));

        fetch("http://localhost:8080/blogs/api/v1/public/user/signup", {
            headers: {
                Accept: "application/json",  
                "Content-Type": "application/json", //l is sub and r is rec
              },
              method: "POST",
              body: JSON.stringify(formObj),
        })
        .then(function(response){return response.json()})
        .then(function(data){console.log(data)});
        console.log(formObj);
    })
};

export {enableSignUp};