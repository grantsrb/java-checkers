import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Game {
  private int playerCount;
  private int playerTurn;
  private boolean saved;
  private List<Checker> checkers = new ArrayList<>();
  private int id;

  public Game(int pPlayerCount) {
    this.playerCount = pPlayerCount;
    this.playerTurn = 2;
    this.saved = false;
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

  public boolean getSaved() {
    return this.saved;
  }

  public List<Integer> getUserIds() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT userId FROM users_games WHERE gameId=:gameId")
        .addParameter("gameId", this.id)
        .executeAndFetch(Integer.class);
    }
  }

  public void attachUser(int pUserId) {
    this.saved = true;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("INSERT INTO users_games (gameId, userId) VALUES (:gameId, :userId)")
        .addParameter("gameId", this.id)
        .addParameter("userId", pUserId)
        .executeUpdate();

      con.createQuery("INSERT INTO games (saved) VALUES (:saved)")
        .addParameter("saved", this.saved)
        .executeUpdate();
    }

  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    for (Integer userId : this.getUserIds())
      users.add(User.find(userId));
    return users;
  }

  public void populateCheckers() {
    this.checkers = this.getCheckers();
  }

  public List<Checker> getCheckers() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM checkers WHERE gameId=:gameId")
        .addParameter("gameId", this.id)
        .executeAndFetch(Checker.class);
    }
  }

  public void updatePlayerTurn() {
    this.playerTurn = this.playerTurn%2 + 1;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE games SET playerTurn=:playerTurn WHERE id=:id")
        .addParameter("playerTurn", this.playerTurn)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addChecker(Checker pChecker) { // made for testing
    this.checkers.add(pChecker);
  }

  /////////////////////////////////////////////////////////////////////////////
  /// validator Methods

  public Checker getCheckerInSpace(int pSpecifiedRow, int pSpecifiedColumn) {
    for (int i = 0; i < this.checkers.size(); i++) {
      Checker checker = this.checkers.get(i);
      if (checker.getRowPosition() == pSpecifiedRow && checker.getColumnPosition() == pSpecifiedColumn)
        return checker;
    }
    return null;
  }

  public boolean moveIsOffBoard(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if(pSpecifiedRow < 0 || pSpecifiedRow > 7 || pSpecifiedColumn > 7 || pSpecifiedColumn < 0)
      return true;
    else
      return false;
  }

  public boolean moveIsWrongDirection(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if(pChecker.getType() == 1 && pSpecifiedRow <= pChecker.getRowPosition())
      return true;
    else if (pChecker.getType() == 2 && pSpecifiedRow >= pChecker.getRowPosition())
      return true;
    else
      return false;
  }

  public boolean moveIsSameColumnOrRow(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if(pChecker.getColumnPosition() == pSpecifiedColumn)
      return true;
    else if (pSpecifiedRow == pChecker.getRowPosition())
      return true;
    else
      return false;
  }

  public boolean moveIsOnNullSpace(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    for (int i = 0; i < 8; i++) {
      for(int j = i%2; j < 8; j+=2) {
        if (pSpecifiedRow == i && pSpecifiedColumn == j)
          return true;
      }
    }
    return false;
  }

  // Checks if the board space is legal without factoring in availability or distance
  public boolean spaceIsLegal(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if(this.moveIsOffBoard(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if(this.moveIsSameColumnOrRow(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if (this.moveIsWrongDirection(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if (this.moveIsOnNullSpace(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else
      return true;
  }

  // Checks for distance validity in regular move
  public boolean isLegalMove(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if (!this.spaceIsLegal(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if (1 < Math.abs(pChecker.getRowPosition() - pSpecifiedRow))
      return false;
    else if (1 < Math.abs(pChecker.getColumnPosition() - pSpecifiedColumn))
      return false;
    else
      return true;
  }

  // Checks for distance validity in capturing move
  public boolean isLegalCapture(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if (!this.spaceIsLegal(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if (1 == Math.abs(pChecker.getRowPosition() - pSpecifiedRow) || 2 < Math.abs(pChecker.getRowPosition() - pSpecifiedRow))
      return false;
    else if (1 == Math.abs(pChecker.getColumnPosition() - pSpecifiedColumn) || 2 < Math.abs(pChecker.getColumnPosition() - pSpecifiedColumn))
      return false;
    else
      return true;
  }

  public boolean specificMoveIsValid(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if (!this.isLegalMove(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if (this.getCheckerInSpace(pSpecifiedRow, pSpecifiedColumn) != null)
      return false;
    else
      return true;
  }

  public Checker getAdjacentOpponentChecker(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    int rowSign = pSpecifiedRow - pChecker.getRowPosition();
    rowSign = rowSign/Math.abs(rowSign);
    int colSign = pSpecifiedColumn - pChecker.getColumnPosition();
    colSign = colSign/Math.abs(colSign);
    Checker capturedChecker = this.getCheckerInSpace(pChecker.getRowPosition() + rowSign, pChecker.getColumnPosition() + colSign);
    if (capturedChecker != null && capturedChecker.getType() != pChecker.getType() && 2 != Math.abs(capturedChecker.getType()-pChecker.getType()))
      return capturedChecker;
    else
      return null;
  }

  public boolean specificCaptureIsValid(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if (!this.isLegalCapture(pChecker, pSpecifiedRow, pSpecifiedColumn))
      return false;
    else if (this.getCheckerInSpace(pSpecifiedRow, pSpecifiedColumn) != null)
      return false;
    else if (this.getAdjacentOpponentChecker(pChecker, pSpecifiedRow, pSpecifiedColumn) != null)
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

  public void movePiece(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if(this.specificMoveIsValid(pChecker, pSpecifiedRow, pSpecifiedColumn)) {
      pChecker.updatePosition(pSpecifiedRow, pSpecifiedColumn);
      this.updatePlayerTurn();
    }
  }

  public boolean capturePiece(Checker pChecker, int pSpecifiedRow, int pSpecifiedColumn) {
    if(this.specificCaptureIsValid(pChecker, pSpecifiedRow, pSpecifiedColumn)) {
      Checker capturedChecker = this.getAdjacentOpponentChecker(pChecker, pSpecifiedRow, pSpecifiedColumn);
      capturedChecker.delete();
      pChecker.updatePosition(pSpecifiedRow, pSpecifiedColumn);
      this.checkers = this.getCheckers();
      if(pChecker.getType() == this.getPlayerTurn() || pChecker.getType() == this.getPlayerTurn() + 2)
        this.updatePlayerTurn();
      if(!this.generalCaptureIsAvailable(pChecker)) {
        return false;
      } else
        return true;
    }
    return false;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// database Methods

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO games (playerCount, playerTurn, saved) VALUES (:playerCount, :playerTurn, :saved)", true)
        .addParameter("playerCount", this.playerCount)
        .addParameter("playerTurn", this.playerTurn)
        .addParameter("saved", this.saved)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM checkers WHERE gameId=:gameId")
        .addParameter("gameId", this.id)
        .executeUpdate();
      con.createQuery("DELETE FROM games WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static void deleteUnsaved() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM games WHERE saved = false")
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
    Game game;
    try (Connection con = DB.sql2o.open()) {
      game = con.createQuery("SELECT * FROM games WHERE id=:id")
        .addParameter("id", pId)
        .executeAndFetchFirst(Game.class);
    }
    game.populateCheckers();
    return game;
  }

  public static List<Game> all() {
    List<Game> games;
    try (Connection con = DB.sql2o.open()) {
       games = con.createQuery("SELECT * FROM games")
        .executeAndFetch(Game.class);
    }
    for (int i = 0; i < games.size(); i++) {
      games.get(i).populateCheckers();
    }
    return games;
  }

}
