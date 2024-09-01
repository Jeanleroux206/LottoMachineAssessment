-- Select the sum of the money spent by a user over a certain time frame for each loyalty program
SELECT lp.ProgramName, SUM(t.Amount) AS TotalSpent
FROM Tickets t
JOIN LoyaltyPrograms lp ON t.LoyaltyProgramId = lp.LoyaltyProgramId
WHERE t.UserId = @userId AND t.TimePlaced BETWEEN @startTime AND @endTime
GROUP BY lp.ProgramName;