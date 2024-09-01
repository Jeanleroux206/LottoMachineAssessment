-- Select all the loyalty programs assigned to a user
SELECT lp.ProgramName
FROM UserLoyaltyPrograms ulp
JOIN LoyaltyPrograms lp ON ulp.LoyaltyProgramId = lp.LoyaltyProgramId
WHERE ulp.UserId = @userId;