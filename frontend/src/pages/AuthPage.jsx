import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { login, registerFan } from "../services/authService";

import "./AuthPage.css";

function AuthPage() {

    const navigate = useNavigate();

    // Controls which tab is active
    const [activeTab, setActiveTab] = useState("register");

    // Form fields
    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phone, setPhone] = useState("")

    // Loading and messages
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    // Switch between login and registration
    function handleTabChange(tab) {
        setActiveTab(tab);

        setError("");
        setSuccess("");
    }

    // Handle form submission
    async function handleSubmit(event) {

        event.preventDefault();

        setError("");
        setSuccess("");
        setLoading(true);

        try {

            if (activeTab === "login") {
                await login(email, password);
                navigate("/movies");

            } else {
                await registerFan(fullName, email, password, phone);

                setSuccess("Account created successfully! Please sign in.");

                setActiveTab("login");

                setFullName("");
                setPassword("");

            }

        } catch (err) {
            setError(err.message || "Something went wrong.");

        } finally {
            setLoading(false);

        }
    }

    const isRegister = activeTab === "register";

    return (
        <div className="auth-page">

            {/* Header */}
            <header className="auth-header">
                <div className="header-content">
                    <div className="brand">
                        <div className="brand-icon">
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
                    </div>
                    <span className="portal-text">
                        Cinema portal
                    </span>
                </div>
            </header>

            {/* Main content */}
            <main className="auth-main">

                {/* Left promotional section */}
                <section className="hero-section">

                    <p className="hero-eyebrow">
                        <span></span>
                        YOUR NIGHT AT THE MOVIES
                    </p>

                    <h1>
                        Big stories.
                        <br />
                        <span>Your seat.</span>
                    </h1>

                    <p className="hero-description">
                        Discover what’s playing, choose the perfect seats together,
                        and confirm your cinema night in a few simple steps.
                    </p>

                    <div className="feature-list">
                        <div className="feature-item">
                            <div className="feature-icon">▤</div>
                            <span>Curated releases</span>
                        </div>

                        <div className="feature-item">
                            <div className="feature-icon">▣</div>
                            <span>Pick together</span>
                        </div>

                        <div className="feature-item">
                            <div className="feature-icon">♧</div>
                            <span>Instant booking</span>
                        </div>
                    </div>
                </section>

                {/* Authentication card */}
                <section className="auth-card">
                    {/* Tabs */}
                    <div className="auth-tabs">
                        <button
                            type="button"
                            className={!isRegister ? "active" : ""}
                            onClick={() => handleTabChange("login")}
                        >
                            Sign In
                        </button>

                        <button
                            type="button"
                            className={isRegister ? "active" : ""}
                            onClick={() => handleTabChange("register")}
                        >
                            Create Account
                        </button>
                    </div>

                    {/* Heading */}
                    <div className="form-heading">
                        <h2>
                            {isRegister ? "Join the front row" : "Welcome back"}
                        </h2>

                        <p>
                            {isRegister
                                ? "Create your free fan account in less than a minute."
                                : "Sign in to browse showtimes and reserve your seats."
                            }
                        </p>
                    </div>

                    {/* Form */}
                    <form onSubmit={handleSubmit}>
                        {isRegister && (
                            <>
                                <div className="form-group">
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

                                <div className="form-group">
                                    <label htmlFor="phone">
                                        Phone
                                    </label>

                                    <input
                                        id="phone"
                                        type="tel"
                                        value={phone}
                                        onChange={(event) =>
                                            setPhone(event.target.value)
                                        }
                                        required
                                    />
                                </div>
                            </>
                        )}

                        <div className="form-group">
                            <label htmlFor="email">
                                Email address
                            </label>

                            <input
                                id="email"
                                type="email"
                                value={email}
                                onChange={(event) =>
                                    setEmail(event.target.value)
                                }
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="password">
                                Password
                            </label>

                            <input
                                id="password"
                                type="password"
                                value={password}
                                onChange={(event) =>
                                    setPassword(event.target.value)
                                }
                                required
                            />
                        </div>

                        {error && (
                            <p className="form-error">{error}</p>
                        )}

                        {success && (
                            <p className="form-success">{success}</p>
                        )}


                        <button
                            type="submit"
                            className="submit-button"
                            disabled={loading}
                        >

                            {loading
                                ? "Please wait..."
                                : isRegister
                                    ? "Create account  →"
                                    : "Sign in  →"
                            }

                        </button>
                    </form>
                </section>
            </main>
        </div>
    );
}

export default AuthPage;