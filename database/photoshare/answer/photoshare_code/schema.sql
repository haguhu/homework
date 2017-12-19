
drop database if exists photoshare;
CREATE DATABASE photoshare;
USE photoshare;

CREATE TABLE Users (
	user_id int AUTO_INCREMENT,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	dob date NOT NULL,
	hometown varchar(255),
	gender varchar(255),
	contribution int not null,
	PRIMARY KEY (user_id),
	UNIQUE (email)
);

CREATE TABLE Friends (
	user_id int,
	friend_id int,
	PRIMARY KEY (user_id, friend_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (friend_id) REFERENCES Users(user_id)
);

CREATE TABLE Albums (
	album_id int AUTO_INCREMENT,
	user_id int NOT NULL,
	name varchar(255),
	doc date,
	PRIMARY KEY (album_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Photos (
	photo_id int AUTO_INCREMENT,
	caption varchar(255),
	data longblob,
	album_id int NOT NULL,
	PRIMARY KEY (photo_id),
	FOREIGN KEY (album_id) REFERENCES Albums(album_id) ON DELETE CASCADE
);

CREATE VIEW User_Photos (user_id, photo_id) AS
	SELECT a.user_id, p.photo_id
	FROM Photos AS p, Albums AS a 
	WHERE p.album_id = a.album_id
;

CREATE TABLE Tags (
	word varchar(255),
	popularity int,
	PRIMARY KEY (word)
);

CREATE TABLE Associate (
	photo_id int,
	word varchar(255),
	PRIMARY KEY (photo_id, word),
	FOREIGN KEY (photo_id) REFERENCES Photos(photo_id) ON DELETE CASCADE,
	FOREIGN KEY (word) REFERENCES Tags(word)
);

CREATE TABLE Comment (
	comment_id int AUTO_INCREMENT,
	user_id int,
	photo_id int,
	txt varchar(255),
	doc date,
	PRIMARY KEY (comment_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (photo_id) REFERENCES Photos(photo_id) ON DELETE CASCADE
);

CREATE TABLE Likes (
	user_id int,
	photo_id int,
	PRIMARY KEY (user_id, photo_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (photo_id) REFERENCES Photos(photo_id) ON DELETE CASCADE
);

DELIMITER $
DROP TRIGGER IF EXISTS t_afterdelete_on_albums;
CREATE TRIGGER t_afterdelete_on_albums
AFTER DELETE ON Albums
FOR EACH ROW
BEGIN
      delete from Photos where album_id=old.album_id;
END$
DELIMITER ;

DELIMITER $
DROP TRIGGER IF EXISTS t_afterdelete_on_photos;
CREATE TRIGGER t_afterdelete_on_photos
AFTER DELETE ON Photos
FOR EACH ROW
BEGIN
      delete from Associate where photo_id=old.photo_id;
END$
DELIMITER ;


