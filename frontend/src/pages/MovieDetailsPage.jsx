import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getMovieDetails } from "../services/movieService";
import "./MovieDetailsPage.css";

function MovieDetailsPage() {

    // Get the movie ID from the URL
    const { movieId } = useParams();

    // Store movie information
    const [movie, setMovie] = useState(null);

    // Store the selected date
    const [selectedDate, setSelectedDate] = useState("");

    // Store loading and error states
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // Generate the next 30 dates, starting from today
    const dates = Array.from({ length: 7 }, (_, index) => {
        const date = new Date();
        date.setHours(0, 0, 0, 0);
        date.setDate(date.getDate() + index);

        return date;
    });

    // Convert a JavaScript Date into YYYY-MM-DD
    const formatDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");

        return `${year}-${month}-${day}`;
    };

    // Format the date for displaying in the date selector
    const displayDate = (date) => {
        return new Intl.DateTimeFormat("en", {
            weekday: "short",
            day: "numeric",
            month: "short",
        }).format(date);
    };

    // Fetch movie details from the backend
    useEffect(() => {

        const fetchMovieDetails = async () => {
            try {
                setLoading(true);
                setError("");

                const data = await getMovieDetails(movieId);

                setMovie(data);

            } catch (err) {
                console.error("Error fetching movie details:", err);
                setError("Unable to load movie details. Please try again.");

            } finally {
                setLoading(false);
            }
        };

        fetchMovieDetails();

    }, [movieId]);

    // Select today's date when the page loads
    useEffect(() => {
        setSelectedDate(formatDate(new Date()));
    }, []);

    // Filter showtimes according to the selected date
    const selectedShowtimes = movie?.showtimes?.filter(
        (showtime) => showtime.showDate === selectedDate
    ) || [];

    // Display loading message
    if (loading) {
        return <div className="movie-details-message">Loading movie details...</div>;
    }

    // Display error message
    if (error) {
        return <div className="movie-details-message">{error}</div>;
    }

    // Display message if movie is unavailable
    if (!movie) {
        return <div className="movie-details-message">Movie not found.</div>;
    }

    return (
        <div className="movie-details-page">

            {/* Movie Information */}
            <section className="movie-details-container">

                <img
                    src={movie.posterUrl}
                    alt={movie.title}
                    className="movie-details-poster"
                />

                <div className="movie-details-info">
                    <h1>{movie.title}</h1>
                    <p>{movie.description}</p>
                </div>

            </section>


            {/* Date Selector */}
            <section className="date-selection-section">
                <h2>Select a Date</h2>
                <div className="date-selector">
                    {dates.map((date) => {
                        const dateValue = formatDate(date);
                        return (
                            <button
                                key={dateValue}
                                className={`date-button ${
                                    selectedDate === dateValue ? "selected" : ""
                                }`}
                                onClick={() => setSelectedDate(dateValue)}
                            >
                                <span>{displayDate(date)}</span>
                            </button>
                        );
                    })}
                </div>
            </section>

            {/* Showtime Selection */}
            <section className="showtime-section">
                <h2>Available Showtimes</h2>
                {selectedShowtimes.length > 0 ? (
                    <div className="showtimes-list">
                        {selectedShowtimes.map((showtime) => (
                            <button
                                key={showtime.showtimeId}
                                className="showtime-button"
                            >
                                <span>
                                    {showtime.showTime.slice(0, 5)}
                                </span>
                                <span>
                                    Rs. {showtime.price}
                                </span>
                                <small>
                                    {showtime.cinemaName}
                                </small>
                            </button>
                        ))}
                    </div>
                ) : (
                    <p className="unavailable-message">
                        Oops! This movie is not available for this date.
                    </p>
                )}
            </section>
        </div>
    );
}

export default MovieDetailsPage;