import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class EasyAI {
  private Game currentGame;
  private String name;
  private int id;

  private class MoveValue{
    public String player;
    public float moveValue;

    public MoveValue(String name) {
      this.player = name;
      this.moveValue = 0f;
    }
  }

  public EasyAI(String pname, int pboardId) {
    this.name = pname;
    this.currentGame = Game.findById(pboardId);
  }

  /////////////////////////////////////////////////////////////////////////////
  /// special Methods

  public Checker copyChecker(Checker pchecker) {
    Checker checkerCopy = new Checker(pchecker.getType(), pchecker.getRowPosition(), pchecker.getColumnPosition(), pchecker.getGameId());
    return checkerCopy;
  }

  public List<Checker> copyCheckers(List<Checker> pcheckers) {
    List<Checker> checkerCopies = new ArrayList<>();
    for (int i = 0; i < pcheckers.size(); i++) {
      checkerCopies.add(this.copyChecker(pcheckers.get(i)));
    }
    return checkerCopies;
  }

  public void recursiveMoveEvaluation(List<Checker> pcheckers, int moveCount, MoveValue paiMoveValue, MoveValue popponentMoveValue) {
    float moveValue;
    if(moveCount > 0) {
      for (int k = 0; k < pcheckers.size(); k++) {
        List<Checker> originalCheckers = this.copyCheckers(pcheckers);
        this.currentGame.setCheckersList(originalCheckers);
        Checker currentChecker = originalCheckers.get(k);
        if(currentChecker.getType() % 2 == this.currentGame.getPlayerTurn() % 2) {
          if(this.currentGame.generalMoveIsAvailable(currentChecker)) {
            for (int i = -1; i <= 1; i+=2) {
              for (int j = -1; j <= 1; j+=2) {
                boolean success = this.currentGame.virtualMovePiece(currentChecker, currentChecker.getRowPosition()+i, currentChecker.getColumnPosition()+j);
                if(success)
                  this.recursiveMoveEvaluation(originalCheckers, moveCount-1, paiMoveValue, popponentMoveValue);
                originalCheckers = this.copyCheckers(pcheckers);
                currentChecker = originalCheckers.get(k);
              }
            }
          }
        }
        if(this.currentGame.generalCaptureIsAvailable(currentChecker)) {
          for (int i = -2; i <= 2; i+=4) {
            for (int j = -2; j <= 2; j+=4) {
              boolean success = this.currentGame.virtualCapturePiece(currentChecker, currentChecker.getRowPosition()+i, currentChecker.getColumnPosition()+j);
              if(success)
                this.recursiveMoveEvaluation(originalCheckers, moveCount-1, paiMoveValue, popponentMoveValue);
              originalCheckers = this.copyCheckers(pcheckers);
              currentChecker = originalCheckers.get(k);
            }
          }
        }
      }
    } else if (moveCount == 1) {
      if (this.evaluateMove(pcheckers) > popponentMoveValue.moveValue)
        popponentMoveValue.moveValue = this.evaluateMove(pcheckers);
    } else {
      if (this.evaluateMove(pcheckers) > paiMoveValue.moveValue)
        paiMoveValue.moveValue = this.evaluateMove(pcheckers);
    }
  }

  public void generateMove(List<Checker> pcheckers) {
    int maxEvaluationRow = 0;
    int maxEvaluationColumn = 0;
    Checker maxEvaluationChecker = this.copyChecker(pcheckers.get(0));
    int evaluation = 0;
    int evaluationRow = 0;
    int evaluationColumn = 0;
    float maxDifference = 0f;
    MoveValue aiMoveValue = new MoveValue("ai");
    MoveValue opponentMoveValue = new MoveValue("opponent");
    for (int k = 0; k < pcheckers.size(); k++) {
      List<Checker> originalCheckers = this.copyCheckers(pcheckers);
      this.currentGame.setCheckersList(originalCheckers);
      Checker currentChecker = originalCheckers.get(k);
      if(currentChecker.getType() % 2 == this.currentGame.getPlayerTurn() % 2) {
        if(this.currentGame.generalMoveIsAvailable(currentChecker)) {
          for (int i = -1; i <= 1; i+=2) {
            for (int j = -1; j <= 1; j+=2) {
              evaluationRow = currentChecker.getRowPosition()+i;
              evaluationColumn = currentChecker.getColumnPosition()+j;
              boolean success = this.currentGame.virtualMovePiece(currentChecker, currentChecker.getRowPosition()+i, currentChecker.getColumnPosition()+j);
              if(success)
                this.recursiveMoveEvaluation(originalCheckers, 4, aiMoveValue, opponentMoveValue);
              if ((aiMoveValue.moveValue - opponentMoveValue.moveValue) > maxDifference) {
                maxDifference = aiMoveValue.moveValue - opponentMoveValue.moveValue;
                maxEvaluationRow = evaluationRow;
                maxEvaluationColumn = evaluationColumn;
                maxEvaluationChecker = pcheckers.get(k);
              }
              originalCheckers = this.copyCheckers(pcheckers);
              currentChecker = originalCheckers.get(k);
            }
          }
        }
      }
      if(this.currentGame.generalCaptureIsAvailable(currentChecker)) {
        for (int i = -2; i <= 2; i+=4) {
          for (int j = -2; j <= 2; j+=4) {
            evaluationRow = currentChecker.getRowPosition()+i;
            evaluationColumn = currentChecker.getColumnPosition()+j;
            boolean success = this.currentGame.virtualCapturePiece(currentChecker, currentChecker.getRowPosition()+i, currentChecker.getColumnPosition()+j);
            if(success)
              this.recursiveMoveEvaluation(originalCheckers, 4, aiMoveValue, opponentMoveValue);
            if ((aiMoveValue.moveValue - opponentMoveValue.moveValue) > maxDifference) {
              maxDifference = aiMoveValue.moveValue - opponentMoveValue.moveValue;
              maxEvaluationRow = evaluationRow;
              maxEvaluationColumn = evaluationColumn;
              maxEvaluationChecker = pcheckers.get(k);
            }
            originalCheckers = this.copyCheckers(pcheckers);
            currentChecker = originalCheckers.get(k);
          }
        }
      }
    }
    this.currentGame.movePiece(maxEvaluationChecker, maxEvaluationRow, maxEvaluationColumn);
    this.currentGame.capturePiece(maxEvaluationChecker, maxEvaluationRow, maxEvaluationColumn);
    if(this.currentGame.getPlayerTurn() != 1) {
      this.currentGame.updatePlayerTurn();
    }
  }

  public float evaluatePieceRatio(List<Checker> pcheckers) {
    float aiCheckerCount = 0f;
    float playerCheckerCount = 0f;
    for(int i = 0; i < pcheckers.size(); i++) {
      if(pcheckers.get(i).getType() % 2 == 1) {
        aiCheckerCount++;
      } else {
        playerCheckerCount++;
      }
    }
    return aiCheckerCount/playerCheckerCount;
  }

  public float evaluateMove(List<Checker> pcheckers) {
    float pieceRatio = this.evaluatePieceRatio(pcheckers);
    float moveRating = 100*pieceRatio;
    return moveRating;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Game getCurrentGame() {
    return this.currentGame;
  }

  // public void attachGame(int pgameId) {
  //   Integer attachedGameId;
  //   try (Connection con = DB.sql2o.open()) {
  //     attachedGameId = con.createQuery("SELECT gameId FROM easyAis_games WHERE aiId=:id AND gameId=:gameId")
  //       .addParameter("id", this.id)
  //       .addParameter("gameId", pgameId)
  //       .executeAndFetchFirst(Integer.class);
  //     if(attachedGameId == null) {
  //       con.createQuery("INSERT INTO easyAis_games (aiId, gameId) VALUES (:aiId, :gameId)")
  //         .addParameter("aiId", this.id)
  //         .addParameter("gameId", pgameId)
  //         .executeUpdate();
  //     }
  //   }
  //   this.currentGame = Game.findById(pgameId);
  // }

  // public List<Integer> getGameIds() {
  //   try (Connection con = DB.sql2o.open()) {
  //     return con.createQuery("SELECT gameId FROM easyAis_games WHERE aiId=:id")
  //       .addParameter("id", this.id)
  //       .executeAndFetch(Integer.class);
  //   }
  // }
  //
  // public List<Game> getGames() {
  //   List<Integer> gameIds = this.getGameIds();
  //   List<Game> games = new ArrayList<>();
  //   for (int i = 0; i < gameIds.size(); i++) {
  //     games.add(Game.findById(gameIds.get(i)));
  //   }
  //   return games;
  // }

  /////////////////////////////////////////////////////////////////////////////
  /// database Methods

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO easyais (name) VALUES (:name)", true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  // @Override
  // public boolean equals(Object otherAI) {
  //   if(!(otherAI instanceof EasyAI)) {
  //     return false;
  //   } else {
  //     EasyAI newAI = (EasyAI) otherAI;
  //     return this.id == newAI.getId() &&
  //            this.name == newAI.getName();
  //   }
  // }

  /////////////////////////////////////////////////////////////////////////////
  /// static Methods

  // public static EasyAI findById(int pId, int pgameId) {
  //   Game game;
  //   EasyAI ai;
  //   try (Connection con = DB.sql2o.open()) {
  //     ai = con.createQuery("SELECT * FROM easyais WHERE id=:id")
  //       .addParameter("id", pId)
  //       .executeAndFetchFirst(EasyAI.class);
  //     game = con.createQuery("SELECT * FROM games WHERE id=:id")
  //       .addParameter("id", pgameId)
  //       .executeAndFetchFirst(Game.class);
  //   }
  //   game.populateCheckers();
  //   ai.updateGame(game);
  //   return ai;
  // }

}
