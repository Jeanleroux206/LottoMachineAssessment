-- Users table
CREATE TABLE IF NOT EXISTS Users (
    UserId INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);

-- Tickets table
CREATE TABLE IF NOT EXISTS Tickets (
    TicketId INT PRIMARY KEY,
    UserId INT,
    TimePlaced TIMESTAMP,
    Amount DECIMAL(10, 2),
    Numbers VARCHAR(255),
    LoyaltyProgramId INT,
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (LoyaltyProgramId) REFERENCES LoyaltyPrograms(LoyaltyProgramId)
);

-- Loyalty Programs table
CREATE TABLE IF NOT EXISTS LoyaltyPrograms (
    LoyaltyProgramId INT PRIMARY KEY,
    ProgramName VARCHAR(100) NOT NULL
);

-- User Loyalty Programs table
CREATE TABLE IF NOT EXISTS UserLoyaltyPrograms (
    UserId INT,
    LoyaltyProgramId INT,
    PRIMARY KEY (UserId, LoyaltyProgramId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (LoyaltyProgramId) REFERENCES LoyaltyPrograms(LoyaltyProgramId)
);