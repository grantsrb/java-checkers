import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
  private String username;
  private String password;
  private int gamesWon;
  private int gamesLost;
  private int id;

  public static boolean loggedIn = false;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.gamesWon = 0;
    this.gamesLost = 0;
    loggedIn = true;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public int getGamesWon() {
    return this.gamesWon;
  }

  public int getGamesLost() {
    return this.gamesLost;
  }

  public int getId() {
    return this.id;
  }

  // public List<Game> getGames() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM games WHERE userid = :id";
  //     return con.createQuery(sql)
  //       .addParameter("id", this.id)
  //       .executeAndFetch(Game.class);
  //   }
  // }

  public static User login(String username, String password) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE username = :username AND password = :password";
      User user =  con.createQuery(sql)
        .addParameter("username", username)
        .addParameter("password", password)
        .executeAndFetchFirst(User.class);
      if(user == null) {
        throw new RuntimeException("Invalid username and/or password");
      }
      return user;
    }
  }

  public static boolean isLoggedIn(String username, String password) {
    try {
      User.login(username, password);
    } catch (RuntimeException exception) {
      return false;
    }
    loggedIn = true;
    return true;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (username, password, gameswon, gameslost) VALUES (:username, :password, :gameswon, :gameslost)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("username", this.username)
        .addParameter("password", this.password)
        .addParameter("gameswon", this.gamesWon)
        .addParameter("gameslost", this.gamesLost)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<User> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users";
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public void updateUsername(String username) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET username = :username WHERE id = :id";
      con.createQuery(sql)
        .addParameter("username", username)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updatePassword(String password) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET password = :password WHERE id = :id";
      con.createQuery(sql)
        .addParameter("password", password)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateGamesWon() {
    this.gamesWon++;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET gameswon = :gameswon WHERE id = :id";
      con.createQuery(sql)
        .addParameter("gameswon", this.gamesWon)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateGamesLost() {
    this.gamesLost++;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET gameslost = :gameslost WHERE id = :id";
      con.createQuery(sql)
        .addParameter("gameslost", this.gamesLost)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getUsername().equals(newUser.getUsername()) &&
      this.getPassword().equals(newUser.getPassword()) &&
      this.getGamesWon() == newUser.getGamesWon() &&
      this.getGamesLost() == newUser.getGamesLost() &&
      this.getId() == newUser.getId();
    }
  }
}
