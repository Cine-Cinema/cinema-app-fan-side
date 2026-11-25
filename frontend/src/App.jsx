import { Routes, Route, Navigate } from "react-router-dom";

import AuthPage from "./pages/AuthPage";
import AccountPage from "./pages/AccountPage";

import ProtectedRoute from "./components/ProtectedRoute";
import MoviesPage from "./pages/MoviesPage.jsx";

function App() {

    return (

        <Routes>

            <Route path="/login" element={<AuthPage />} />

            <Route element={<ProtectedRoute />}>

                <Route path="/account" element={<AccountPage />} />

            </Route>

            <Route
                path="/"
                element={<Navigate to="/login" replace />}
            />

            {/* Temporary route until Movies page is implemented */}
            <Route
                path="/movies"
                element={<MoviesPage />}
            />

        </Routes>

    );
}

export default App;