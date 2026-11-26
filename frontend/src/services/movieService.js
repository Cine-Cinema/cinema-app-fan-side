// const API_BASE_URL =
//     import.meta.env.VITE_API_URL;
//
// export async function searchMovies(title = "", date = "") {
//     const params = new URLSearchParams();
//
//     if (title.trim()) {
//         params.append("title", title.trim());
//     }
//
//     if (date) {
//         params.append("date", date);
//     }
//
//     const queryString = params.toString();
//
//     const url = `${API_BASE_URL}/api/movies/search${
//         queryString ? `?${queryString}` : ""
//     }`;
//
//     const response = await fetch(url);
//
//     if (!response.ok) {
//         throw new Error(`Movie search failed: ${response.status}`);
//     }
//
//     return await response.json();
// }

const API_BASE_URL =
    import.meta.env.VITE_API_URL || "http://localhost:8080";


// Search movies by title and/or date
export async function searchMovies(title = "", date = "", signal) {

    const params = new URLSearchParams();

    if (title.trim()) {
        params.append("title", title.trim());
    }

    if (date) {
        params.append("date", date);
    }

    const queryString = params.toString();

    const url = `${API_BASE_URL}/api/movies/search${
        queryString ? `?${queryString}` : ""
    }`;

    const response = await fetch(url, { signal });

    // Read the response as text first
    const responseText = await response.text();

    if (!response.ok) {
        throw new Error(
            `Request failed (${response.status}): ${responseText}`
        );
    }

    try {
        return JSON.parse(responseText);
    } catch {
        throw new Error(
            `The server did not return JSON. Response: ${responseText.substring(0, 300)}`
        );
    }
}


// Get details of a specific movie
export async function getMovieDetails(movieId) {

    const url = `${API_BASE_URL}/api/movies/${movieId}`;

    const response = await fetch(url);

    // Read the response as text first
    const responseText = await response.text();

    if (!response.ok) {
        throw new Error(
            `Request failed (${response.status}): ${responseText}`
        );
    }

    try {
        return JSON.parse(responseText);
    } catch {
        throw new Error(
            `The server did not return JSON. Response: ${responseText.substring(0, 300)}`
        );
    }
}