import { useEffect, useState } from "react";
import { searchMovies } from "../services/movieService";
import "./MoviesPage.css";
import { Link } from "react-router-dom";

function MoviesPage() {
    const [title, setTitle] = useState("");
    const [date, setDate] = useState("");

    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    // Search movies when the title or date changes.
    // Wait 300ms after the user stops typing before searching.
    useEffect(() => {
        const controller = new AbortController();

        const delaySearch = setTimeout(async () => {
            try {
                setLoading(true);
                setError("");

                const results = await searchMovies(
                    title,
                    date,
                    controller.signal
                );

                setMovies(Array.isArray(results) ? results : []);
            } catch (err) {
                if (err.name !== "AbortError") {
                    console.error("Error searching movies:", err);
                    setError(err.message);
                    setMovies([]);
                }
            } finally {
                if (!controller.signal.aborted) {
                    setLoading(false);
                }
            }
        }, 300);

        return () => {
            clearTimeout(delaySearch);
            controller.abort();
        };
    }, [title, date]);

    // Search Movies button
    const handleSearch = async (event) => {
        event.preventDefault();

        try {
            setLoading(true);
            setError("");

            const results = await searchMovies(title, date);

            setMovies(Array.isArray(results) ? results : []);
        } catch (err) {
            console.error("Error searching movies:", err);
            setError(err.message);
            setMovies([]);
        } finally {
            setLoading(false);
        }
    };

    const handleClear = () => {
        setTitle("");
        setDate("");
    };

    return (
        <div className="movies-page">

            {/* Navigation bar */}
            <header className="account-header">
                <div className="account-header-content">

                    <Link to="/movies" className="account-brand">
                        <div className="account-brand-icon">
                            <svg
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                strokeWidth="1.8"
                            >
                                <rect x="5" y="3" width="14" height="18" rx="2" />
                                <path d="M9 3v18M15 3v18M5 8h4M15 8h4M5 16h4M15 16h4" />
                            </svg>
                        </div>
                        <span>Movio</span>
                    </Link>

                    <nav className="account-navigation">
                        <Link to="/movies" className="active">
                            Movies
                        </Link>

                        <Link to="/account" className="user-profile">
                            My Account
                        </Link>
                    </nav>

                </div>
            </header>

            {/* Hero section */}
            <section className="movies-hero">
                <div className="hero-content">
                    <p className="hero-subtitle">
                        YOUR MOVIE EXPERIENCE STARTS HERE
                    </p>

                    <h1>
                        Find Your Next
                        <span> Favourite Movie</span>
                    </h1>

                    <p className="hero-description">
                        Explore movies, discover showtimes and plan
                        your next cinema experience.
                    </p>
                </div>
            </section>

            {/* Search section */}
            <main className="movies-container">

                <section className="search-section">
                    <div className="section-heading">
                        <h2>Explore Movies</h2>
                        <p>Find movies by title or showing date.</p>
                    </div>

                    <form
                        className="movie-search-form"
                        onSubmit={handleSearch}
                    >
                        <div className="search-field">
                            <label htmlFor="movie-title">
                                Movie Title
                            </label>

                            <input
                                id="movie-title"
                                type="text"
                                placeholder="Search for a movie..."
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                            />
                        </div>

                        <button
                            type="submit"
                            className="search-button"
                            disabled={loading}
                        >
                            {loading ? "Searching..." : "Search Movies"}
                        </button>

                        <button
                            type="button"
                            className="clear-button"
                            onClick={handleClear}
                        >
                            Clear
                        </button>
                    </form>
                </section>

                {/* Movie results */}
                <section className="results-section">
                    <div className="results-heading">
                        <h2>Now Showing</h2>
                        <span>
                            {movies.length} {movies.length === 1 ? "movie" : "movies"}
                        </span>
                    </div>

                    {loading && (
                        <div className="message-box">
                            Loading movies...
                        </div>
                    )}

                    {!loading && error && (
                        <div className="message-box error-message">
                            {error}
                        </div>
                    )}

                    {!loading && !error && movies.length === 0 && (
                        <div className="message-box">
                            <h3>No movies found</h3>
                            <p>
                                Try searching with a different title or date.
                            </p>
                        </div>
                    )}

                    {!loading && !error && movies.length > 0 && (
                        <div className="movies-grid">
                            {movies.map((movie) => (
                                <Link
                                    to={`/movies/${movie.movieId}`}
                                    className="movie-card"
                                    key={movie.movieId}
                                >
                                    <div className="movie-poster">
                                        <img
                                            src={
                                                movie.posterUrl ||
                                                "https://placehold.co/400x550?text=No+Poster"
                                            }
                                            alt={movie.title}
                                            onError={(e) => {
                                                e.currentTarget.src =
                                                    "https://placehold.co/400x550?text=No+Poster";
                                            }}
                                        />
                                    </div>

                                    <div className="movie-details">
                                        <h3>{movie.title}</h3>

                                        <p className="movie-description">
                                            {movie.description ||
                                                "No description available."}
                                        </p>

                                        <span className="view-details">View Details →</span>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    )}
                </section>
            </main>

            {/* Footer */}
            <footer className="movies-footer">
                <p>© 2026 Movio. All rights reserved.</p>
            </footer>

        </div>
    );
}

export default MoviesPage;