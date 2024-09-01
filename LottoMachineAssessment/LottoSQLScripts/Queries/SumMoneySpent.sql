-- Select the sum of the money spent by a user over a certain time frame
SELECT SUM(Amount) AS TotalSpent
FROM Tickets
WHERE UserId = @userId AND TimePlaced BETWEEN @startTime AND @endTime;