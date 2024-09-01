-- Select a specific ticket and all information on it including who placed it
SELECT t.TicketId, t.TimePlaced, t.Amount, t.Numbers, u.Name, lp.ProgramName
FROM Tickets t
JOIN Users u ON t.UserId = u.UserId
LEFT JOIN LoyaltyPrograms lp ON t.LoyaltyProgramId = lp.LoyaltyProgramId
WHERE t.TicketId = @ticketId;