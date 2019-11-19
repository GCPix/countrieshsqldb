INSERT INTO language (iso639_1,iso639_2, name, nativeName) 
SELECT * FROM 
(VALUES (?,?,?,?)) WHERE NOT EXISTS 
(SELECT * FROM language  WHERE name =?);