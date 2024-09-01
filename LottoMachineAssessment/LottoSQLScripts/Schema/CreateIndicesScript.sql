CREATE INDEX IF NOT EXISTS idx_userId ON Tickets(UserId);
CREATE INDEX IF NOT EXISTS idx_placedTime ON Tickets(TimePlaced);
CREATE INDEX IF NOT EXISTS idx_loyaltyProgramId ON Tickets(LoyaltyProgramId);