package rest.service.util;

public class Constants {
	
	public class User{

		public static final String checkPasswordByID = "SELECT password = PASSWORD(?) FROM User WHERE id_user = ?";
		public static final String checkPasswordByPseudo = "SELECT id_user FROM User WHERE (pseudo = ? AND password = PASSWORD(?))";
		
		public static final String getAll = "SELECT * FROM User";
		public static final String getByID = "SELECT * FROM User WHERE id_user = ?";
		
		public static final String post = "INSERT INTO User(pseudo, password, email) VALUES(?, PASSWORD(?), ?)";

		public static final String putPasswordByID = "UPDATE User SET password = PASSWORD(?) WHERE id_user = ?";
		public static final String putEmailByID = "UPDATE User SET email = ? WHERE id_user = ?";

		public static final String deleteByID = "DELETE FROM User WHERE id_user = ?";

	}
	
	public class Rate{
		
		public static final String getAll = "SELECT * FROM Rate";
		public static final String getByID = "SELECT * FROM Rate WHERE id_rate = ?";
		public static final String getByMult = "SELECT * FROM Rate WHERE ID_multimedia=?";
		
		public static final String post = "INSERT INTO Rate(value, id_user, id_multimedia) VALUES(?, ?, ?)";
		
		public static final String putByID = "UPDATE Rate SET value = ?, date_creation = NOW() WHERE id_rate = ?";

		public static final String deleteByID = "DELETE FROM Rate WHERE id_rate = ?";

	}
	
	public class Comment{
		
		public static final String getAll = "SELECT * FROM Comment";
		public static final String getByID = "SELECT * FROM Comment WHERE id_comment = ?";
		public static final String getByMult = "SELECT * FROM Comment WHERE ID_multimedia = ?";
		
		public static final String post = "INSERT INTO Comment(value, id_user, id_multimedia) VALUES(?, ?, ?)";
		
		public static final String putByID = "UPDATE Comment SET value = ?, date_creation = NOW() WHERE id_comment = ?";

		public static final String deleteByID = "DELETE FROM Comment WHERE id_comment = ?";

	}
	

	public class Multimedia{
		public static final String getAll = "SELECT * FROM Multimedia";
		public static final String getByID = "SELECT * FROM Multimedia WHERE ID_multimedia = ?";
		public static final String getByTitle = "SELECT * FROM Multimedia WHERE title LIKE ?";
		
		public static final String getTypeByID = "SELECT category FROM Multimedia WHERE ID_multimedia = ?";
		public static final String getBookChildID = "SELECT id_book FROM book WHERE ID_multimedia = ?";
		public static final String getFilmChildID = "SELECT id_film FROM film WHERE ID_multimedia = ?";
		public static final String getVideoGameChildID = "SELECT id_videoGame FROM videoGame WHERE ID_multimedia = ?";
		
		public static final String post = "INSERT INTO multimedia (title, language, genre, category, status, ID_uploader, description, date_release) VALUES(?,?,?,?,?,?,?,?)";
		
		public static final String deleteByID = "DELETE FROM multimedia WHERE id_multimedia = ?";

	}
	

	public class Film{
		public static final String getAll = "SELECT * FROM Film_v";
		public static final String getByID = "SELECT * FROM Film_v WHERE (id_film = ?)";
		public static final String getByIDMultimedia = "SELECT * FROM Film_v WHERE (id_multimedia = ?)";
		
		public static final String getByName = "SELECT * FROM Film_v WHERE title LIKE ? ";
		
		public static final String post ="INSERT INTO Film (director, productor, mainCast, duration, ID_multimedia) VALUES(?,?,?,?,?)";
		
		public static final String putDescriptionByID = "UPDATE Film_v SET description = ? WHERE id_film = ?";
		public static final String putLangueByID = "UPDATE Film_v SET language = ? WHERE id_film = ?";
		public static final String putGenreByID = "UPDATE Film_v SET genre = ? Where id_film = ?";
		public static final String putStatusByID = "UPDATE Film_v SET status = ?, date_status = NOW() WHERE id_film = ?";
		
		public static final String putDirectorByID = "UPDATE Film_v SET director = ? WHERE id_film = ?";
		public static final String putProductorByID = "UPDATE Film_v SET productor = ? WHERE id_film = ?";
		public static final String putMainCastByID = "UPDATE film_v SET mainCast = ? Where id_film = ?";

	}


	public class Book{
		public static final String getAll = "SELECT * FROM Book_v";
		public static final String getByID = "SELECT * FROM Book_v WHERE (id_book = ?)";
		public static final String getByIDMultimedia = "SELECT * FROM Book_v WHERE (id_multimedia = ?)";
		
		public static final String getByName = "SELECT * FROM Book_v WHERE title LIKE ?";
		
		public static final String post = "INSERT INTO `book` (author, publisher, id_multimedia) VALUES(?,?,?)";

		public static final String putDescriptionByID = "UPDATE Book_v SET description = ? WHERE id_book = ?";
		public static final String putLangueByID = "UPDATE Book_v SET language = ? WHERE id_book = ?";
		public static final String putGenreByID = "UPDATE Book_v SET genre = ? Where id_book = ?";
		public static final String putStatusByID = "UPDATE Book_v SET status = ?, date_status = NOW() WHERE id_book = ?";
		
		public static final String putAuthorByID = "UPDATE Book_v SET author = ? WHERE id_book = ?";
		public static final String putTitleByID = "UPDATE Book_v SET title = ? WHERE id_book = ?";
		public static final String putPublisherByID = "UPDATE Book_v SET publisher = ? Where id_book = ?";

	}


	public class VideoGame{
		public static final String getAll = "SELECT * FROM VideoGame_v";
		public static final String getByID = "SELECT * FROM VideoGame_v WHERE id_videoGame = ?";
		public static final String getByIDMultimedia = "SELECT * FROM VideoGame_v WHERE (id_multimedia = ?)";
		
		public static final String getByName= "SELECT * FROM VideoGame_v WHERE title LIKE ?";
		
		public static final String post ="INSERT INTO VideoGame (developer, publisher, ID_multimedia) VALUES(?,?,?)";
		
		public static final String putDescriptionByID = "UPDATE VideoGame_v SET description = ? WHERE id_videoGame = ?";
		public static final String putLangueByID = "UPDATE VideoGame_v SET language = ? WHERE id_videoGame = ?";
		public static final String putGenreByID = "UPDATE VideoGame_v SET genre = ? Where id_videoGame = ?";
		public static final String putStatusByID = "UPDATE VideoGame_v SET status = ?, date_status = NOW() WHERE id_videoGame = ?";
		
		public static final String putDeveloperByID = "UPDATE VideoGame_v SET developer = ? WHERE id_videoGame = ?";
		public static final String putPublisherByID = "UPDATE VideoGame_v SET publisher = ? Where id_videoGame = ?";

	}
	
}
