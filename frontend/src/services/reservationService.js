import apiRequest from "./api";

// Get the seats that are already reserved for a particular showtime
export async function getReservedSeats(showtimeId) {
    return await apiRequest(
        `/api/reservations/showtime/${showtimeId}/seats`,
        {
            method: "GET",
        }
    );
}

// Send a reservation request to the backend
export async function makeReservation(showtimeId, seatNumbers) {
    return await apiRequest("/api/reservations", {
        method: "POST",
        body: JSON.stringify({
            showtimeId: showtimeId,
            seatNumbers: seatNumbers,
        }),
    });
}