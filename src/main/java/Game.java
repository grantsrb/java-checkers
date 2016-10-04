import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Game {
  private int playerCount;
  private int playerTurn;
  private List<Checker> checkers = new ArrayList<>();
  private int id;

  public Game(int pPlayerCount) {
    this.playerCount = pPlayerCount;
    this.playerTurn = 1;
    this.save();
    for (int i = 0; i < 8; i++) {
      for(int j = (i+1)%2; j < 8; j+=2) {
        if (i <=2) {
          Checker newChecker = new Checker(1, i, j, this.id);
          newChecker.save();
          checkers.add(newChecker);
        } else if(i >= 5) {
          Checker newChecker = new Checker(2, i, j, this.id);
          newChecker.save();
          checkers.add(newChecker);
        }
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public int getPlayerCount() {
    return this.playerCount;
  }

  public int getPlayerTurn() {
    return this.playerTurn;
  }

  public List<Integer> getUserIds() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT userId FROM users_games WHERE gameId=:gameId")
        .addParameter("gameId", this.id)
        .executeAndFetch(Integer.class);
    }
  }

  public void attachUser(int pUserId) {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("INSERT INTO users_games (gameId, userId) VALUES (:gameId, :userId)")
        .addParameter("gameId", this.id)
        .addParameter("userId", pUserId)
        .executeUpdate();
    }
  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    for (Integer userId : this.getUserIds())
      users.add(User.find(userId));
    return users;
  }

  public List<Checker> getCheckers() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM checkers WHERE gameId=:gameId")
        .addParameter("gameId", this.id)
        .executeAndFetch(Checker.class);
    }
  }

  public void addChecker(Checker pChecker) { // made for testing
    this.checkers.add(pChecker);
  }

  /////////////////////////////////////////////////////////////////////////////
  /// validator Methods

  public Checker getCheckerInSpace(int pRowFinish, int pColFinish) {
    for (Checker checker : this.checkers) {
      if (checker.getRowPosition() == pRowFinish && checker.getColumnPosition() == pColFinish)
        return checker;
    }
    return null;
  }

  public boolean moveIsOffBoard(Checker pChecker, int pRowFinish, int pColFinish) {
    if(pRowFinish < 0 || pRowFinish > 7 || pColFinish > 7 || pColFinish < 0)
      return true;
    else
      return false;
  }

  public boolean moveIsWrongDirection(Checker pChecker, int pRowFinish, int pColFinish) {
    if(pChecker.getType() == 1 && pRowFinish <= pChecker.getRowPosition())
      return true;
    else if (pChecker.getType() == 2 && pRowFinish >= pChecker.getRowPosition())
      return true;
    else
      return false;
  }

  public boolean moveIsSameColumnOrRow(Checker pChecker, int pRowFinish, int pColFinish) {
    if(pChecker.getColumnPosition() == pColFinish)
      return true;
    else if (pRowFinish == pChecker.getRowPosition())
      return true;
    else
      return false;
  }

  public boolean moveIsOnNullSpace(Checker pChecker, int pRowFinish, int pColFinish) {
    for (int i = 0; i < 8; i++) {
      for(int j = i%2; j < 8; j+=2) {
        if (pRowFinish == i && pColFinish == j)
          return true;
      }
    }
    return false;
  }

  // Checks if the board space is legal without factoring in availability or distance
  public boolean spaceIsLegal(Checker pChecker, int pRowFinish, int pColFinish) {
    if(this.moveIsOffBoard(pChecker, pRowFinish, pColFinish))
      return false;
    else if(this.moveIsSameColumnOrRow(pChecker, pRowFinish, pColFinish))
      return false;
    else if (this.moveIsWrongDirection(pChecker, pRowFinish, pColFinish))
      return false;
    else if (this.moveIsOnNullSpace(pChecker, pRowFinish, pColFinish))
      return false;
    else
      return true;
  }

  // Checks for distance validity in regular move
  public boolean isLegalMove(Checker pChecker, int pRowFinish, int pColFinish) {
    if (!this.spaceIsLegal(pChecker, pRowFinish, pColFinish))
      return false;
    else if (1 < Math.abs(pChecker.getRowPosition() - pRowFinish))
      return false;
    else if (1 < Math.abs(pChecker.getColumnPosition() - pColFinish))
      return false;
    else
      return true;
  }

  // Checks for distance validity in capturing move
  public boolean isLegalCapture(Checker pChecker, int pRowFinish, int pColFinish) {
    if (!this.spaceIsLegal(pChecker, pRowFinish, pColFinish))
      return false;
    else if (1 == Math.abs(pChecker.getRowPosition() - pRowFinish) || 2 < Math.abs(pChecker.getRowPosition() - pRowFinish))
      return false;
    else if (1 == Math.abs(pChecker.getColumnPosition() - pColFinish) || 2 < Math.abs(pChecker.getColumnPosition() - pColFinish))
      return false;
    else
      return true;
  }

  public boolean specificMoveIsValid(Checker pChecker, int pRowFinish, int pColFinish) {
    if (!this.isLegalMove(pChecker, pRowFinish, pColFinish))
      return false;
    else if (this.getCheckerInSpace(pRowFinish, pColFinish) != null)
      return false;
    else
      return true;
  }

  public Checker getAdjacentOpponentChecker(Checker pChecker, int pRowFinish, int pColFinish) {
    int rowSign = pRowFinish - pChecker.getRowPosition();
    rowSign = rowSign/Math.abs(rowSign);
    int colSign = pColFinish - pChecker.getColumnPosition();
    colSign = colSign/Math.abs(colSign);
    Checker capturedChecker = this.getCheckerInSpace(pChecker.getRowPosition() + rowSign, pChecker.getColumnPosition() + colSign);
    if (capturedChecker != null && capturedChecker.getType() != pChecker.getType() && 2 != Math.abs(capturedChecker.getType()-pChecker.getType()))
      return capturedChecker;
    else
      return null;
  }

  public boolean specificCaptureIsValid(Checker pChecker, int pRowFinish, int pColFinish) {
    if (!this.isLegalCapture(pChecker, pRowFinish, pColFinish))
      return false;
    else if (this.getCheckerInSpace(pRowFinish, pColFinish) != null)
      return false;
    else if (this.getAdjacentOpponentChecker(pChecker, pRowFinish, pColFinish) != null)
      return true;
    else
      return false;
  }

  public boolean generalCaptureIsAvailable(Checker pChecker) {
    for (int i = -2; i <= 2; i+=4) {
      for (int j = -2; j <= 2; j+=4) {
        if(this.specificCaptureIsValid(pChecker, pChecker.getRowPosition()+i, pChecker.getColumnPosition()+j))
          return true;
      }
    }
    return false;
  }

  public boolean generalMoveIsAvailable(Checker pChecker) {
    for (int i = -1; i <= 1; i+=2) {
      for (int j = -1; j <= 1; j+=2) {
        if(this.specificMoveIsValid(pChecker, pChecker.getRowPosition()+i, pChecker.getColumnPosition()+j))
          return true;
      }
    }
    return false;
  }

  public boolean gameIsOver() {
    for(Checker checker : this.checkers) {
      if(checker.getType() == this.playerTurn || 2 == Math.abs(checker.getType() - this.playerTurn)) {
        if (this.generalMoveIsAvailable(checker)) {
          return false;
        } else if (this.generalCaptureIsAvailable(checker)) {
          return false;
        }
      }
    }
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////
  // gamePlay Methods

  public void movePiece(Checker pChecker, int pRowFinish, int pColFinish) {
    if(this.specificMoveIsValid(pChecker, pRowFinish, pColFinish)) {
      pChecker.updatePosition(pRowFinish, pColFinish);
      this.playerTurn = this.playerTurn%2 + 1;
    }
  }

  public void capturePiece(Checker pChecker, int pRowFinish, int pColFinish) {
    if(this.specificCaptureIsValid(pChecker, pRowFinish, pColFinish)) {
      Checker capturedChecker = this.getAdjacentOpponentChecker(pChecker, pRowFinish, pColFinish);
      capturedChecker.delete();
      pChecker.updatePosition(pRowFinish, pColFinish);
      this.playerTurn = this.playerTurn%2 + 1;
      this.checkers = this.getCheckers();
    }
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
    for (Checker checker : this.checkers)
      checker.delete();
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM games WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherGame) {
    if(!(otherGame instanceof Game)) {
      return false;
    } else {
      Game newGame = (Game) otherGame;
      return this.id == newGame.getId() &&
             this.playerCount == newGame.getPlayerCount() &&
             this.playerTurn == newGame.getPlayerTurn();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// static Methods

  public static Game findById(int pId) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM games WHERE id=:id")
        .addParameter("id", pId)
        .executeAndFetchFirst(Game.class);
    }
  }

  public static List<Game> all() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM games")
        .executeAndFetch(Game.class);
    }
  }

}
