INSERT INTO currency (code, name, symbol) 
SELECT * FROM 
(VALUES (?,?,?)) WHERE NOT EXISTS 
(SELECT * FROM currency  WHERE name =?);