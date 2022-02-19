CREATE KEYSPACE IF NOT EXISTS timeTracker WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };
CREATE TABLE IF NOT EXISTS timeEvents (
id bigint,
userId text,
xCoordinate int,
yCoordinate int,
mouseClickTime timestamp,
insertedOn timestamp,
PRIMARY KEY(id)
);