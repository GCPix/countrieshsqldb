INSERT INTO border (country_id, country_border_id) 
SELECT * FROM
(VALUES(?,?)) 
WHERE NOT EXISTS 
(SELECT * FROM border WHERE country_id = ? AND country_border_id = ?);