INSERT INTO country_language (country_id, language_id) 
SELECT * FROM
(VALUES(?,?)) 
WHERE NOT EXISTS 
(SELECT * FROM country_language WHERE country_id = ? AND language_id = ?);