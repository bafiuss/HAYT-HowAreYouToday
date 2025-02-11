const onlyLettersPattern = /^[A-Za-z]+$/;
const emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
const passwordPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;

const firstNameError = "First name must contain only letters";
const lastNameError = "Last name must contain only letters";
const emailError = "Email entered not in correct format ";
const passwordError = "Password must contain at least one number, one uppercase and lowercase letter, and at least 8 or more characters";
const confirmPasswordError = "Passwords do not match";

function validateFormElem(formElem, pattern, span, message) {
    if (formElem.value.match(pattern)) {
        formElem.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }
    formElem.classList.add("error");
    span.innerHTML = message;
    span.style.color = "red";
    return false;
}

function validateFirstName() {
    let valid = true;
    let form = document.getElementById("signupForm");
    let spanName = document.getElementById("errorFirstName");

    if (!validateFormElem(form.firstName, onlyLettersPattern, spanName, firstNameError))
        valid = false;

    return valid;
}

function validateLastName() {
    let valid = true;
    let form = document.getElementById("signupForm");
    let spanName = document.getElementById("errorLastName");

    if (!validateFormElem(form.lastName, onlyLettersPattern, spanName, lastNameError))
        valid = false;

    return valid;
}

function validateEmail() {
    let valid = true;
    let form = document.getElementById("signupForm");
    let spanName = document.getElementById("errorEmail");

    if (!validateFormElem(form.email, emailPattern, spanName, emailError))
        valid = false;

    return valid;
}

function validatePassword() {
    let valid = true;
    let form = document.getElementById("signupForm");
    let spanName = document.getElementById("errorPassword");

    if (!validateFormElem(form.password, passwordPattern, spanName, passwordError))
        valid = false;

    return valid;
}

function passwordMatching() {

    let form = document.getElementById("signupForm");
    let spanPassword = document.getElementById("matchError");

    let psw1 = form.password.value;
    let psw2 = form.confirmPassword.value;


    if (psw1 != psw2) {
        spanPassword.classList.add("error");
        spanPassword.innerHTML = confirmPasswordError;
        spanPassword.style.color = "red";
        return false;
    }

    spanPassword.classList.remove("error");
    spanPassword.style.color = "black";
    spanPassword.innerHTML = "";
    return true;

}

function checkSignUp(obj) {
    let check = true;

    if (!validateFirstName()) check = false;
    if (!validateLastName()) check = false;
    if (!validateEmail()) check = false;
    if (!validatePassword()) check = false;
    if (!passwordMatching()) check = false;


    return check;
}