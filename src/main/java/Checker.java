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

  public getType() {
    return this.type;
  }

  public getRowPosition() {
    return this.rowPosition;
  }

  public getColumnPosition() {
    return this.columnPosition;
  }

  public getGameId() {
    return this.gameId;
  }

  public getId() {
    return this.id;
  }

  public getIsKing() {
    return this
  }

  public void save() {
    try(Connection con = DB.sql2o) {
      String sql = "INSERT INTO checkers (gameId, type, rowPosition, columnPosition) VALUES (:gameId, :type, :rowPosition, :columnPosition)";
      con.createQuery(sql)
         .addParameter("gameId", gameId)
         .addParameter("type", type)
         .addParameter("rowPosition", rowPosition)
         .addParameter("columnPosition", columnPosition)
         .executeUpdate();
    }
  }
}
