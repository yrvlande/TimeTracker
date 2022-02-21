CREATE TABLE IF NOT EXISTS userSessionDetails (
userId text,
businessDate date,
userLoggedIn timestamp,
userLoggedOut timestamp,
activeHours float,
insertedOn timestamp,
PRIMARY KEY(userId, businessDate )
)WITH CLUSTERING ORDER BY (businessDate ASC);