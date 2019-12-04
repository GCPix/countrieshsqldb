INSERT INTO country_currency (country_id, currency_id) 
SELECT * FROM
(VALUES(?,?)) 
WHERE NOT EXISTS 
(SELECT * FROM country_currency WHERE country_id = ? AND currency_id = ?);