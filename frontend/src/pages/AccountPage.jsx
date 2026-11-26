import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import apiRequest from "../services/api";
import { logout } from "../services/authService";

import "./AccountPage.css";

function AccountPage() {

    const navigate = useNavigate();

    // Fan information
    const [fan, setFan] = useState(null);

    const [fullName, setFullName] = useState("");
    const [phone, setPhone] = useState("");

    // Loading and messages
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);

    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    // Retrieve logged-in fan details
    useEffect(() => {

        async function fetchAccount() {

            try {
                const response = await apiRequest("/api/fans/me");

                setFan(response);

                setFullName(response.fullName ?? "");
                setPhone(response.phone ?? "");

            } catch (err) {
                setError(err.message || "Unable to load account details.");

            } finally {
                setLoading(false);

            }
        }

        fetchAccount();

    }, []);


    // Update fan details
    async function handleUpdate(event) {
        event.preventDefault();

        setError("");
        setMessage("");

        setSaving(true);

        try {
            const updatedFan = await apiRequest("/api/fans/me", {
                method: "PUT",
                body: JSON.stringify({
                    fullName,
                    phone,
                }),

            });

            setFan(updatedFan);

            setFullName(updatedFan.fullName ?? "");
            setPhone(updatedFan.phone ?? "");

            setMessage("Your account details have been updated.");

        } catch (err) {
            setError(err.message || "Unable to update account details.");

        } finally {
            setSaving(false);

        }
    }


    // Logout
    function handleLogout() {
        logout();
        navigate("/login");

    }


    // Loading state
    if (loading) {

        return (
            <div className="account-message">
                Loading your account...
            </div>
        );
    }

    // Error state
    if (!fan) {
        return (
            <div className="account-message">
                <p>{error || "Unable to load your account."}</p>

                <button onClick={handleLogout}>
                    Return to login
                </button>

            </div>
        );
    }


    return (
        <div className="account-page">
            {/* Header */}
            <header className="account-header">
                <div className="account-header-content">
                    <Link to="/account" className="account-brand">
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
                        <Link to="/movies">
                            Movies
                        </Link>

                        <div className="user-profile">
                            <span className="user-icon">♙</span>
                            <span>{fan.fullName}</span>
                        </div>
                    </nav>
                </div>
            </header>

            {/* Main account content */}
            <main className="account-main">

                {/* Page heading */}
                <div className="account-title-section">
                    <div>
                        <p className="account-eyebrow">
                            MY ACCOUNT
                        </p>
                        <h1>Profile &amp; bookings</h1>
                    </div>

                    <button
                        className="sign-out-button"
                        onClick={handleLogout}
                    >
                        Sign out
                    </button>

                </div>

                {/* Two-column layout */}
                <div className="account-content">
                    {/* Personal details */}
                    <section className="personal-card">
                        <h2>Personal details</h2>
                        <p className="card-description">
                            Keep your contact details up to date.
                        </p>

                        <form onSubmit={handleUpdate}>
                            {/* Full name */}
                            <div className="account-form-group">
                                <label htmlFor="fullName">
                                    Full name
                                </label>

                                <input
                                    id="fullName"
                                    type="text"
                                    value={fullName}
                                    onChange={(event) =>
                                        setFullName(event.target.value)
                                    }
                                    required
                                />

                            </div>

                            {/* Email */}
                            <div className="account-form-group">
                                <label htmlFor="email">
                                    Email address
                                </label>

                                <input
                                    id="email"
                                    type="email"
                                    value={fan.email ?? ""}
                                    disabled
                                />

                            </div>

                            {/* Phone */}
                            <div className="account-form-group">
                                <label htmlFor="phone">
                                    Phone number <span>(optional)</span>
                                </label>
                                <input
                                    id="phone"
                                    type="tel"
                                    value={phone}
                                    onChange={(event) =>
                                        setPhone(event.target.value)
                                    }
                                />
                            </div>

                            {/* Messages */}
                            {error && (
                                <p className="account-error">
                                    {error}
                                </p>
                            )}

                            {message && (
                                <p className="account-success">
                                    {message}
                                </p>
                            )}

                            {/* Save button */}
                            <button
                                type="submit"
                                className="save-button"
                                disabled={saving}
                            >

                                {saving ? "Saving..." : "Save changes"}

                            </button>
                        </form>
                    </section>

                    {/* Tickets section */}
                    <section className="tickets-section">
                        <h2>My tickets</h2>
                        <div className="empty-tickets-card">
                            <div className="ticket-icon"></div>

                            <h3>No tickets yet</h3>

                            <p>
                                Your confirmed cinema bookings will appear here.
                            </p>

                            <Link to="/movies" className="browse-movies-button">
                                Browse movies
                            </Link>

                        </div>
                    </section>
                </div>
            </main>
        </div>
    );
}

export default AccountPage;