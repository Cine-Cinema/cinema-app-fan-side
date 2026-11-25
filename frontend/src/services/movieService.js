const API_BASE_URL =
    import.meta.env.VITE_API_URL;

export async function searchMovies(title = "", date = "") {
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

    const response = await fetch(url);

    if (!response.ok) {
        throw new Error(`Movie search failed: ${response.status}`);
    }

    return await response.json();
}