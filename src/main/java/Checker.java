import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Checker {
  private int type;
  private int columnPosition;
  private int rowPosition;
  private int gameId;
  private int id;

  public Checker (int type, int rowPosition, int columnPosition, int gameId) {
    this.type = type;
    this.rowPosition = rowPosition;
    this.columnPosition = columnPosition;
    this.gameId = gameId;
  }

  public int getType() {
    return this.type;
  }

  public int getRowPosition() {
    return this.rowPosition;
  }

  public int getColumnPosition() {
    return this.columnPosition;
  }

  public int getGameId() {
    return this.gameId;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkers (type, rowPosition, columnPosition, gameId) VALUES (:type, :rowPosition, :columnPosition, :gameId)";
      this.id = (int) con.createQuery(sql, true)
                         .addParameter("type", this.type)
                         .addParameter("rowPosition", this.rowPosition)
                         .addParameter("columnPosition", this.columnPosition)
                         .addParameter("gameId", this.gameId)
                         .executeUpdate()
                         .getKey();
    }
  }

  public static List<Checker> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM checkers";
      return con.createQuery(sql)
                .executeAndFetch(Checker.class);
    }
  }

  public static Checker find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM checkers WHERE id = :id";
      return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Checker.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM checkers WHERE id = :id";
      con.createQuery(sql)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  public void updatePosition(int rowPosition, int columnPosition) {
    this.rowPosition = rowPosition;
    this.columnPosition = columnPosition;
    if(this.type == 1 && this.rowPosition == 7) {
      this.type = 3;
    }
    if(this.type == 2 && this.rowPosition == 0) {
      this.type = 4;
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE checkers SET rowPosition = :rowPosition, columnPosition = :columnPosition, type = :type WHERE id = :id";
      con.createQuery(sql)
        .addParameter("rowPosition", this.rowPosition)
        .addParameter("columnPosition", this.columnPosition)
        .addParameter("type", this.type)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherChecker) {
    if (!(otherChecker instanceof Checker)) {
      return false;
    } else {
      Checker newChecker = (Checker) otherChecker;
      return this.getType() == newChecker.getType() &&
      this.getRowPosition() == newChecker.getRowPosition() &&
      this.getColumnPosition() == newChecker.getColumnPosition() &&
      this.getGameId() == newChecker.getGameId() &&
      this.getId() == newChecker.getId();
    }
  }
}
