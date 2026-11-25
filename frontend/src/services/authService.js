import apiRequest from "./api";

// Login fan
export async function login(email, password) {

    const response = await apiRequest("/api/auth/login", {
        method: "POST",

        body: JSON.stringify({
            email,
            password,
        }),
    });

    // Save JWT token
    sessionStorage.setItem("token", response.token);

    return response;
}

// Logout fan
export function logout() {
    sessionStorage.removeItem("token");
}

// Check whether user is logged in
export function isLoggedIn() {
    return Boolean(sessionStorage.getItem("token"));
}