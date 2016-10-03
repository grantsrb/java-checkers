
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Game {
  private int playerCount;
  private int playerTurn;
  private int id;

  public Game(int pPlayerCount) {
    this.playerCount = pPlayerCount;
    this.playerTurn = 1;
    this.save();
    for (int i = 0; i < 8; i++) {
      for(int j = i%2; j < 8; j+=2) {
        if (i <=2)
          Checker newChecker = new Checker(1, i, j, this.id);
        else if (i >= 3 && i <= 4)
          Checker newChecker = new Checker(0, i, j, this.id);
        else
          Checker newChecker = new Checker(2, i, j, this.id);
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and Methods

  public int getId() {
    return this.id;
  }

  public int getPlayerCount() {
    return this.playerCount;
  }

//TODO sandro and caleb
  public List<Integer> getUserIds() {

  }

  public List<User> getUsers() {

  }
  //====

  public int[][] getBoard() {
    return this.board;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// validator Methods

  public boolean generalMoveIsAvailable(int pRow, int pCol) {

  }

  public boolean generalCaptureIsAvailable(int pRow, int pCol) {

  }

  public boolean legalMove(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {

  }

  public boolean specificMoveIsAvailable(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {

  }

  public boolean specificCaptureIsAvailable(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {

  }

  /////////////////////////////////////////////////////////////////////////////
  /// gamePlay Methods

  public void movePiece(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {

  }

  public void capturePiece(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {

  }

  public void makeKing(int pRow, int pCol) {

  }

  /////////////////////////////////////////////////////////////////////////////
  /// database Methods

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO games (playerCount, playerTurn) VALUES (:playerCount, :playerTurn)", true)
        .addParameter("playerCount", this.playerCount)
        .addParameter("playerTurn", this.playerTurn)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM games WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
