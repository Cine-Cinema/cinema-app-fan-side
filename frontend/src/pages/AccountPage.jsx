import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import apiRequest from "../services/api";
import { logout } from "../services/authService";

function AccountPage() {

    const navigate = useNavigate();

    const [fan, setFan] = useState(null);   //when request for the fan account, store the response in setFan

    const [fullName, setFullName] = useState("");
    const [phone, setPhone] = useState("");

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    // Get logged-in fan details
    useEffect(() => {
        async function fetchAccount() {
            try {
                const response = await apiRequest("/api/fans/me");
                setFan(response);
                setFullName(response.fullName);
                setPhone(response.phone);

            } catch (err) {
                setError(err.message || "Unable to load account");

            } finally {
                setLoading(false);

            }
        }

        fetchAccount();

    }, []);

    // Update account details
    async function handleUpdate(event) {

        event.preventDefault();

        setMessage("");
        setError("");
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
            setFullName(updatedFan.fullName);
            setPhone(updatedFan.phone);
            setMessage("Account updated successfully!");

        } catch (err) {
            setError(err.message || "Unable to update account");

        } finally {
            setSaving(false);

        }
    }

    // Logout
    function handleLogout() {
        logout();   // Removes the stored JWT token
        navigate("/login");       // Redirects the fan to the login page

    }

    if (loading) {
        return <p>Loading account...</p>;
    }

    if (error && !fan) {
        return <p className="error-message">{error}</p>;
    }

    return (
        <div className="account-page">
            <div className="account-container">
                <h1>My Account</h1>

                <p>Manage your personal information</p>

                <form onSubmit={handleUpdate}>
                    <div>
                        <label>Email</label>

                        <input
                            type="email"
                            value={fan.email}
                            disabled
                        />
                    </div>

                    <div>
                        <label>Full Name</label>

                        <input
                            type="text"
                            value={fullName}
                            onChange={(event) =>
                                setFullName(event.target.value)
                            }
                            required
                        />
                    </div>

                    <div>
                        <label>Phone Number</label>

                        <input
                            type="text"
                            value={phone}
                            onChange={(event) =>
                                setPhone(event.target.value)
                            }
                            required
                        />
                    </div>

                    {message && (
                        <p className="success-message">{message}</p>
                    )}

                    {error && (
                        <p className="error-message">{error}</p>
                    )}

                    <button type="submit" disabled={saving}>
                        {saving ? "Saving..." : "Save Changes"}
                    </button>

                </form>

                <button
                    type="button"
                    onClick={handleLogout}
                    className="logout-button"
                >
                    Logout
                </button>

            </div>
        </div>
    );
}

export default AccountPage;