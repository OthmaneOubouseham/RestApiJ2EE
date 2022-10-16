-- -----------------------------------------------------
-- FILLING TABLES
-- -----------------------------------------------------



-- -----------------------------------------------------
-- Deleting all Tables
-- -----------------------------------------------------
DELETE FROM comment;
DELETE FROM rate;
DELETE FROM book;
DELETE FROM film;
DELETE FROM videogame;
DELETE FROM multimedia;
DELETE FROM user;


-- -----------------------------------------------------
-- Filling Table web_services.user
-- -----------------------------------------------------
INSERT INTO user (ID_user, pseudo, password, email, date_creation) VALUES
('1', 'JohnDOE', PASSWORD('azerty'), 'jDOE@gmail.com', '2019-11-12 00:00:00'),
('2', 'JaneDOE', PASSWORD('qwerty'), 'jDOE@gmail.com', '2019-11-12 00:00:00'),
('3', 'Siphona', PASSWORD('aqzszde'), 'adibou@efrei.net', '2019-11-12 00:00:00'),
('4', 'Samo', PASSWORD('aqzszde'), 'jaenSamo@efrei.net', '2019-11-12 00:00:00'),
('5', 'Kenobi', PASSWORD('12345'), 'republic@gmail.com', '2017-08-15 00:00:00'),
('6', 'Grievous', PASSWORD('12345'), 'csi@gmail.com', '2017-08-15 00:00:00'),
('7', 'Skywalker', PASSWORD('12345'), 'empire@gmail.com', '2014-08-15 00:00:00'),
('8', 'Yoda', PASSWORD('12345'), 'republic@gmail.com', '2014-08-15 00:00:00'),
('9', 'Windu', PASSWORD('12345'), 'republic@gmail.com', '2013-12-11 00:00:00'),
('10', 'Ki-Adi Mundi', PASSWORD('12345'), 'republic@gmail.com', '2017-11-25 00:00:00'),
('11', 'Fisto', PASSWORD('12345'), 'republic@gmail.com', '2016-10-15 00:00:00'),
('12', 'Secura', PASSWORD('12345'), 'republic@gmail.com', '2017-09-16 00:00:00'),
('13', 'Offee', PASSWORD('12345'), 'republic@gmail.com', '2015-08-15 00:00:00'),
('14', 'Unduli', PASSWORD('12345'), 'republic@gmail.com', '2011-07-15 00:00:00'),
('15', 'Palpatine', PASSWORD('12345'), 'senate@gmail.com', '2012-06-02 00:00:00'),
('16', 'Ameda', PASSWORD('12345'), 'senate@gmail.com', '2013-05-30 00:00:00'),
('17', 'Organa', PASSWORD('12345'), 'senate@gmail.com', '2016-04-30 00:00:00'),
('18', 'Amidala', PASSWORD('12345'), 'senate@gmail.com', '2014-03-25 00:00:00'),
('19', 'Maul', PASSWORD('12345'), 'csi@gmail.com', '2009-02-01 00:00:00'),
('20', 'Tyrannus', PASSWORD('12345'), 'csi@gmail.com', '2017-01-20 00:00:00');




-- -----------------------------------------------------
-- Filling Table web_services.multimedia
-- -----------------------------------------------------
INSERT INTO multimedia (ID_multimedia, title, language, genre, category, status, date_status, date_upload, date_release, ID_uploader, description) VALUES
('1', 'Lord of the Flies', 'en', 'Fiction', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1954-09-17', '1',
'The book takes place in the midst of an unspecified war. Some of the marooned characters are ordinary students, while others arrive as a musical choir under an established leader. With the exception of Sam and Eric and the choirboys, they appear never to have encountered each other before. The book portrays their descent into savagery; left to themselves on a paradisiacal island, far from modern civilization, the well-educated boys regress to a primitive state.'),
('2', 'Holy Bible', 'he', 'Fiction', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '01-01-01', '1',
'Story of how Emmanuel-Jesus joined the Avenger\'s forces in order to face the Emperor\'s rule of terror'),
('3', 'Pokedex', 'en', 'Dictionnary', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1996-10-10', '1',
'Brief presentation of each living pokemon'),
('4', 'Star Wars : Episode IV - A New Hope', 'en', 'Space-Opera', '2', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1977-05-25', '2',
'It is a period of civil war. Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire. During the battle, Rebel spies managed to steal secret plans to the Empire\'s ultimate weapon, the DEATH STAR, an armored space station with enough power to destroy an entire planet. Pursued by the Empire\'s sinister agents, Princess Leia races home aboard her starship, custodian of the stolen plans that can save her people and restore freedom to the galaxy..'),
('5', 'Star Wars : Episode V - The Empire Strikes Back', 'en', 'Space-Opera', '2', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1980-05-17', '2',
'It is a dark time for the Rebellion. Although the Death Star has been destroyed, Imperial troops have driven the Rebel forces from their hidden base and pursued them across the galaxy.Evading the dreaded Imperial Starfleet, a group of freedom fighters led by Luke Skywalker has established a new secret base on the remote ice world of Hoth. The evil lord Darth Vader, obsessed with finding young Skywalker, has dispatched thousands of remote probes into the far reaches of space....'),
('6', 'Star Wars : Episode VI - Return of the Jedi', 'en', 'Space-Opera', '2', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1983-05-25', '2',
'Luke Skywalker has returned to his home planet of Tatooine in an attempt to rescue his friend Han Solo from the clutches of the vile gangster Jabba the Hutt. Little does Luke know that the GALACTIC EMPIRE has secretly begun construction on a new armored space station even more powerful than the first dreaded Death Star. When completed, this ultimate weapon will spell certain doom for the small band of rebels struggling to restore freedom to the galaxy...'),
('7', 'The Elder Scrolls V : Skyrim', 'en', 'RPG', '3', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2011-11-11', '3',
'Skyrim reimagines the open-world fantasy epic, bringing to life a complete virtual world open for you to explore any way you choose. Play any type of character you can imagine, and do whatever you want; the legendary freedom of choice, storytelling, and adventure of The Elder Scrolls is realized like never before. Choose from hundreds of weapons, spells, and abilities. As Dragonborn, learn the dragon\'s secrets and harness their power for yourself'),
('8', 'Pikmin', 'en', 'Real-Time Strategy', '3', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2001-12-01', '3',
'Stranded on an unknown planet, Captain Olimar must enlist the help of these native Pikmin to rebuild his spaceship before the life-support system runs out. In the meantime, you\'ll have to fend off attackers and solve various puzzles. To produce additional multicolored Pikmin you must defeat enemies and carry them back to the Pikmin nests called onions. But beware : watching giant predators gobble your Pikmin might make you angrier than you\'d expect.'),
('9', 'Metal Gear Solid 3 : Snake Eater', 'en', 'Infiltration', '3', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2004-11-17', '3',
'A special elite tactical soldier is summoned to penetrate deep in the heart of enemy territory and obtain intel about "Metal Gear," a prototype weapon with nuclear capabilities. This is your mission and you must infiltrate alone. Interactive environments where traps catch enemies and prey. Rely on camouflage, combat, hunting and instincts to survive. Set in the 1960s where politics and war are shaping real world history.');



-- -----------------------------------------------------
-- Filling Table web_services.book
-- -----------------------------------------------------
INSERT INTO `book` (ID_book, author, publisher, ID_multimedia) VALUES
('1', 'William Golding', 'Gallimard Jeunesse', '1'),
('2', 'Emmanuel Jesus', 'Vaticano Libraris', '2'),
('3', 'Ash Ketchum', 'Pokebook', '3');



-- -----------------------------------------------------
-- Filling Table web_services.film
-- -----------------------------------------------------
INSERT INTO `film` (ID_film, director, productor, mainCast, duration, ID_multimedia) VALUES
('1', 'George Lucas', 'George Lucas', 'Mark Hamill', '121',  '4'),
('2', 'Irvin Kershner', 'George Lucas', 'Mark Hamill', '124', '5'),
('3', 'Ash Ketchum', 'Richard Marquand', 'Mark Hamill', '121', '6');



-- -----------------------------------------------------
-- Filling Table web_services.videogame
-- -----------------------------------------------------
INSERT INTO `videogame` (ID_videogame, developer, publisher, ID_multimedia) VALUES
('1', 'Bethesda Game Studios', 'Bethesda Softworks', '7'),
('2', 'Nintendo', 'Nintendo', '8'),
('3', 'Kojima Productions', 'Konami', '9');



-- -----------------------------------------------------
-- Filling Table web_services.comment
-- -----------------------------------------------------
INSERT INTO `comment` (ID_comment, value, ID_user, ID_multimedia) VALUES
('1', 'Truly a masterpiece', '1', '7'),
('2', 'And there are some who think that the prequels are better...', '1', '5'),
('3', 'Certainly the best one (we\'re speaking of he Phantom Menace, right ?)', '3', '5'),
('4', 'Truly a nice game, but where is the ending ?', '2', '9');


-- -----------------------------------------------------
-- Filling Table web_services.rate
-- -----------------------------------------------------
INSERT INTO `rate` (ID_rate, ID_user, ID_multimedia, value) VALUES
('1', '1', '4', '20'),
('2', '1', '5', '12'),
('3', '1', '6', '03'),
('4', '1', '7', '08'),
('5', '1', '8', '13'),
('6', '1', '9', '17'),
('7','2', '1', '09'),
('8', '2', '2', '05'),
('9', '2', '3', '11'),
('10', '2', '7', '17'),
('11', '2', '8', '20'),
('12', '2', '9', '20'),
('13', '3', '1', '07'),
('14', '3', '2', '20'),
('15', '3', '3', '19'),
('16', '3', '4', '14'),
('17', '3', '5', '06'),
('18', '3', '6', '09'),
('19', '4', '1', '13'),
('20', '4', '2', '20'),
('21', '4', '3', '18'),
('22', '4', '4', '17'),
('23', '4', '5', '09'),
('24', '4', '6', '05'),
('25', '4', '7', '11'),
('26', '4', '8', '13'),
('27', '4', '9', '15'),
('28', '5', '1', '14'),
('29', '5', '2', '16'),
('30', '5', '3', '18'),
('31', '5', '4', '17'),
('32', '5', '5', '19'),
('33', '5', '6', '16'),
('34', '5', '7', '13'),
('35', '5', '8', '14'),
('36', '5', '9', '19'),
('37', '6', '1', '12'),
('38', '6', '2', '16'),
('39', '6', '3', '13'),
('40', '6', '4', '12'),
('41', '6', '5', '20'),
('42', '6', '6', '04'),
('43', '6', '7', '09'),
('44', '6', '8', '15'),
('45', '6', '9', '19');