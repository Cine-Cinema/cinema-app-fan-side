import { Routes, Route, Navigate } from "react-router-dom";

import AuthPage from "./pages/AuthPage";
import AccountPage from "./pages/AccountPage";

import ProtectedRoute from "./components/ProtectedRoute";
import MoviesPage from "./pages/MoviesPage.jsx";
import MovieDetailsPage from "./pages/MovieDetailsPage.jsx";

function App() {

    return (

        <Routes>
            <Route path="/login" element={<AuthPage />} />
            <Route element={<ProtectedRoute />}>
                <Route path="/account" element={<AccountPage />} />
                <Route path="/movies" element={<MoviesPage />} />
                <Route path="/movies/:movieId" element={<MovieDetailsPage />} />
            </Route>

            <Route
                path="/"
                element={<Navigate to="/login" replace />}
            />
        </Routes>
    );
}

export default App;