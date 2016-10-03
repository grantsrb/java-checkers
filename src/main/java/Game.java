import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Game {
  private int playerCount;
  private int playerTurn;
  private List<Checker> checkers;
  private int id;

  public Game(int pPlayerCount) {
    this.playerCount = pPlayerCount;
    this.playerTurn = 1;
    this.save();
    for (int i = 0; i < 8; i++) {
      for(int j = i%2; j < 8; j+=2) {
        if (i <=2) {
          Checker newChecker = new Checker(1, i, j, this.id);
          checkers.add(newChecker);
        } else if (i >= 3 && i <= 4) {
          Checker newChecker = new Checker(0, i, j, this.id);
          checkers.add(newChecker);
        } else {
          Checker newChecker = new Checker(2, i, j, this.id);
          checkers.add(newChecker);
        }
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

  /////////////////////////////////////////////////////////////////////////////
  /// validator Methods

  public boolean isLegal(Checker pChecker, int pRowFinish, int pColFinish) {
    if(pRowFinish < 0 || pRowFinish > 7 || pColFinish > 7 || pColFinish < 0) {
      return false;
    } else if(pChecker.getColumnPosition() == pColFinish || pRowFinish == pChecker.getRowPosition()) {
      return false;
    }else if(pChecker.getType() == 1 && pRowFinish < pChecker.getRowPosition()) {
      return false;
    } else if (pChecker.getType() == 2 && pRowFinish > pChecker.getRowPosition()) {
      return false;
    } else {
      for (int i = 0; i < 8; i++) {
        for(int j = (i+1)%2; j < 8; j+=2) {
          if (pRowFinish == i && pColFinish == j)
            return false;
        }
      }
    }
    return true;
  }

  public boolean isLegalMove(Checker pChecker, int pRowFinish, int pColFinish) {
    if (!this.isLegal(pChecker, pRowFinish, pColFinish)) {
      return false;
    } else if (1 < Math.abs(pChecker.getRowPosition() - pRowFinish)) {
      return false;
    } else if (1 < Math.abs(pChecker.getColumnPosition() - pColFinish)) {
      return false;
    }
    return true;
  }

  public boolean isLegalCapture(Checker pChecker, int pRowFinish, int pColFinish) {
    if (!this.isLegal(pChecker, pRowFinish, pColFinish)) {
      return false;
    } else if (1 == Math.abs(pChecker.getRowPosition() - pRowFinish) || 2 < Math.abs(pChecker.getRowPosition() - pRowFinish)) {
      return false;
    } else if (1 == Math.abs(pChecker.getColumnPosition() - pColFinish) || 2 < Math.abs(pChecker.getColumnPosition() - pColFinish)) {
      return false;
    }
    return true;
  }

  // public boolean specificMoveIsAvailable(Checker pChecker, int pRowFinish, int pColFinish) {
  //   if (!this.isLegalMove(pChecker, pRowFinish, pColFinish))
  //     return false;
  //   for (Checker checker : this.checkers) {
  //     if (checker.getRowPosition == pRowFinish && checker.getColumnPosition() == pColFinish)
  //       return false;
  //   }
  //   return true;
  // }

  // public boolean specificCaptureIsAvailable(Checker pChecker, int pRowFinish, int pColFinish) {
  //
  // }
  //
  // public boolean generalCaptureIsAvailable(Checker pChecker) {
  //
  // }
  //
  // public boolean generalMoveIsAvailable(Checker pChecker) {
  //
  // }

  ///////////////////////////////////////////////////////////////////////////
  // gamePlay Methods

  // public void movePiece(Checker pChecker, int pRowFinish, int pColFinish) {
  //
  // }
  //
  // public void capturePiece(Checker pChecker, int pRowFinish, int pColFinish) {
  //
  // }

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

}
