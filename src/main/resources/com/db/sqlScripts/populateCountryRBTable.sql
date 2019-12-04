INSERT INTO country_regionalblock (country_id, regionalblock_id) 
SELECT * FROM
(VALUES(?,?)) 
WHERE NOT EXISTS 
(SELECT * FROM country_regionalblock WHERE country_id = ? AND regionalblock_id = ?);