import apiRequest from "./api";

// Login an existing fan
export async function login(email, password) {

    const response = await apiRequest("/api/auth/login", {
        method: "POST",

        body: JSON.stringify({
            email,
            password,
        }),
    });

    // Save JWT token after successful login
    sessionStorage.setItem("token", response.token);

    return response;
}

// Register a new fan
export async function registerFan(fullName, email, password, phone) {

    return await apiRequest("/api/auth/register", {
        method: "POST",
        body: JSON.stringify({
            fullName,
            email,
            password,
            phone
        }),
    });
}

// Logout fan
export function logout() {
    sessionStorage.removeItem("token");
}

// Check whether user is logged in
export function isLoggedIn() {
    return Boolean(sessionStorage.getItem("token"));
}