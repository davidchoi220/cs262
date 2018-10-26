-- Get the number of Game records.
SELECT *
  FROM Game
  ;

-- Get the player records.
SELECT * 
  FROM Player
  ;

-- Get all the users with Calvin email addresses.
SELECT *
  FROM Player
 WHERE emailAddress LIKE '%calvin%'
 ;

-- Get the highest score ever recorded.
  SELECT score
    FROM PlayerGame
ORDER BY score DESC
   LIMIT 1
   ;

-- Get the cross-product of all the tables.
SELECT *
  FROM Player, PlayerGame, Game
  ;

SELECT *
  FROM PlayerProperty
  ;

--1.a 
SELECT * 
  FROM Game
  ORDER BY time DESC 
  ;
--1.b
SELECT *
  FROM Game
  WHERE DATE_PART('day', NOW() - time) < 7
  ;
--1.c
SELECT *
  FROM Player
  WHERE name is not NULL;
--1.d
SELECT ID
  FROM Players
  WHERE Score > 2000
  ;
--1.e
SELECT *
  FROM Player
  WHERE emailAddress LIKE '%gmail'
  ;
--2.a
SELECT score 
  FROM PlayerGame
  INNER JOIN Player
  ON PlayerGame.playerID = Player.ID
  WHERE name = 'The King'
  ;
--2.b
SELECT name 
  FROM Player
  INNER JOIN Game
  ON Player.ID = PlayerGame.PlayerID INNER JOIN Game
  ON Game.ID = PlayerGame.gameID
  AND score = (SELECT max(score) FROM PlayerGame WHERE gameID = 
	(SELECT ID FROM Game WHERE TIME = '2006-06-28 13:20:00'))
--2.c
--It is to check if those two are not the same person. In order to do that, makes a comparison to check that ones ID is greater than the other one.
--2.d
--You would join a table with itself when there are players with the same name. By joining the table, you can look at their name and their ID to differentiate each players with the same name.
  
  