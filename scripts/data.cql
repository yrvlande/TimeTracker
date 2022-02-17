-- Create a keyspace
CREATE KEYSPACE IF NOT EXISTS timeTracker WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

-- Create a table
CREATE TABLE IF NOT EXISTS timeTracker.timeEvents (
userId text,
xCoordinate int,
yCoordinate int,
mouseClickTime timestamp
);

-- Insert some data
INSERT INTO timeTracker.timeEvents
(userId, xCoordinate, yCoordinate, mouseClickTime )
VALUES ('e1078064', 2, 2,  toTimeStamp(now()));
