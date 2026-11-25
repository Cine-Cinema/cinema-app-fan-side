import { Routes, Route, Navigate } from "react-router-dom";

import AuthPage from "./pages/AuthPage.jsx";
import AccountPage from "./pages/AccountPage";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {

  return (

      <Routes>
        {/* Login and registration */}
        <Route path="/login" element={<AuthPage />} />

        {/* Protected account page */}
        <Route element={<ProtectedRoute />}>
          <Route path="/account" element={<AccountPage />} />
        </Route>

        {/* Default route */}
        <Route path="/" element={<Navigate to="/login" replace />} />
      </Routes>

  );
}

export default App;