INSERT INTO border (name) 
SELECT * FROM 
(VALUES (?)) WHERE NOT EXISTS 
(SELECT * FROM border  WHERE name =?);