INSERT INTO regionalblock(acronym, name)
SELECT * FROM 
(VALUES (?,?)) WHERE NOT EXISTS
(SELECT * FROM regionalblock where name = ?);