import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class EasyAI {
  private Game currentGame;
  private String name;
  private int id;
  public MoveValue testingVariable;

  private class MoveValue{
    public float moveValue;
    public int row;
    public int column;
    public float valueDifference;
    public Checker checkerToMove;

    public MoveValue() {
      this.moveValue = 0f;
      this.row = -1;
      this.column = -1;
      this.valueDifference = 0f;
      this.checkerToMove = null;
    }
  }

  public EasyAI(String pname, int pboardId) {
    this.name = pname;
    this.currentGame = Game.findById(pboardId);
    this.testingVariable = new MoveValue();
    this.testingVariable.row = 0;
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

  public Checker getCheckerByCoordinates(int prow, int pcol) {
    for (int i = 0; i < this.currentGame.getCheckersList().size(); i++) {
      Checker checker = this.currentGame.getCheckersList().get(i);
      if(checker.getRowPosition() == prow && checker.getColumnPosition() == pcol) {
        return checker;
      }
    }
    return null;
  }

  public void generateMoves(int range, List<Checker> pcheckers, int pcheckerIndex, MoveValue paiMoveValue, MoveValue popponentMoveValue, int pmoveCount) {
    List<Checker> originalCheckers = this.copyCheckers(pcheckers);
    this.currentGame.setCheckersList(originalCheckers);
    Checker currentChecker = originalCheckers.get(pcheckerIndex);
    for (int i = -range; i <= range; i+=2*range) {
      for (int j = -range; j <= range; j+=2*range) {
        boolean success;
        if(range == 1) {
          success = this.currentGame.virtualMovePiece(currentChecker, currentChecker.getRowPosition()+i, currentChecker.getColumnPosition()+j);
        } else {
          success = this.currentGame.virtualCapturePiece(currentChecker, currentChecker.getRowPosition()+i, currentChecker.getColumnPosition()+j);
        }
        if(success)
          this.recursiveMoveEvaluation(originalCheckers, pmoveCount-1, paiMoveValue, popponentMoveValue);
        if (pmoveCount == 5 && (paiMoveValue.moveValue - popponentMoveValue.moveValue) > paiMoveValue.valueDifference) {
          paiMoveValue.valueDifference = paiMoveValue.moveValue - popponentMoveValue.moveValue;
          paiMoveValue.row = pcheckers.get(pcheckerIndex).getRowPosition() + i;
          paiMoveValue.column = pcheckers.get(pcheckerIndex).getColumnPosition() + j;
          paiMoveValue.checkerToMove = pcheckers.get(pcheckerIndex);
        }
        originalCheckers = this.copyCheckers(pcheckers);
        currentChecker = originalCheckers.get(pcheckerIndex);
      }
    }
  }

  public void recursiveMoveEvaluation(List<Checker> pcheckers, int pmoveCount, MoveValue paiMoveValue, MoveValue popponentMoveValue) {
    if(pmoveCount > 0) {
      List<Checker> originalCheckers = this.copyCheckers(pcheckers);
      this.currentGame.setCheckersList(originalCheckers);
      for (int k = 0; k < pcheckers.size(); k++) {
        Checker currentChecker = originalCheckers.get(k);
        if(currentChecker.getType() % 2 == this.currentGame.getPlayerTurn() % 2) {
          if(this.currentGame.generalMoveIsAvailable(currentChecker)) {
            this.generateMoves(1,pcheckers, k, paiMoveValue, popponentMoveValue, pmoveCount);
          }
          if(this.currentGame.generalCaptureIsAvailable(currentChecker)) {
            this.generateMoves(2,pcheckers, k, paiMoveValue, popponentMoveValue, pmoveCount);
          }
        }
        if (this.evaluateMove(pcheckers) > popponentMoveValue.moveValue && pmoveCount == 1)
          popponentMoveValue.moveValue = this.evaluateMove(pcheckers);
      }
    } else {
      if (this.evaluateMove(pcheckers) > paiMoveValue.moveValue)
        paiMoveValue.moveValue = this.evaluateMove(pcheckers);
      System.out.println(testingVariable.row++);
    }
  }

  public void move() {
    this.currentGame.populateCheckers();
    List<Checker> checkers = this.currentGame.getCheckersList();
    MoveValue aiMoveValue = new MoveValue();
    MoveValue opponentMoveValue = new MoveValue();
    for (int k = 0; k < checkers.size(); k++) {
      Checker currentChecker = checkers.get(k);
      if(currentChecker.getType() % 2 == this.currentGame.getPlayerTurn() % 2) {
        if(this.currentGame.generalMoveIsAvailable(currentChecker)) {
          this.generateMoves(1, checkers, k, aiMoveValue, opponentMoveValue, 5);
        }
        if(this.currentGame.generalCaptureIsAvailable(currentChecker)) {
          this.generateMoves(2, checkers, k, aiMoveValue, opponentMoveValue, 5);
        }
      }
    }
    if(aiMoveValue.checkerToMove == null)
      this.generateRandomMove();
    else {
      this.currentGame.populateCheckers();
      System.out.println("AICheckerStats: " + aiMoveValue.checkerToMove.getRowPosition() + " " + aiMoveValue.checkerToMove.getColumnPosition());
      System.out.println("MoveToStats: " + aiMoveValue.row + " " + aiMoveValue.column);
      Checker chosenChecker = this.currentGame.getCheckerInSpace(aiMoveValue.checkerToMove.getRowPosition(), aiMoveValue.checkerToMove.getColumnPosition());
      this.currentGame.movePiece(chosenChecker, aiMoveValue.row, aiMoveValue.column);
      this.currentGame.capturePiece(chosenChecker, aiMoveValue.row, aiMoveValue.column);
    }
    if(this.currentGame.getPlayerTurn() == 1) {
      this.currentGame.updatePlayerTurn();
    }
  }

  public boolean generateRandomMove() {
    Random rand = new Random();
    this.currentGame.populateCheckers();
    List<Checker> pcheckers = this.currentGame.getCheckersList();
    boolean moveIsNotGenerated = true;
    System.out.println("random");
    while (moveIsNotGenerated) {
      int randRow = rand.nextInt(8);
      int randCol = rand.nextInt(8);
      Checker checker = this.getCheckerByCoordinates(randRow, randCol);
      if(checker != null && checker.getType() % 2 == 1) {
        if(this.currentGame.generalCaptureIsAvailable(checker)) {
          for (int i = -2; i <= 2; i+=4) {
            for (int j = -2; j <= 2; j+=4) {
              boolean success = this.currentGame.capturePiece(checker, checker.getRowPosition()+i, checker.getColumnPosition()+j);
              if (success) {
                return true;
              }
            }
          }
        } else if (this.currentGame.generalMoveIsAvailable(checker)) {
          for (int i = -1; i <= 1; i+=2) {
            for (int j = -1; j <= 1; j+=2) {
              if (this.currentGame.specificMoveIsValid(checker, checker.getRowPosition()+i, checker.getColumnPosition()+j)) {
                this.currentGame.movePiece(checker, checker.getRowPosition()+i, checker.getColumnPosition()+j);
                moveIsNotGenerated = false;
                return true;
              }
            }
          }
        }
      }
    }
    return true;
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

  public float evaluateCentralPositionRatio(List<Checker> pcheckers) {
    float aiCenterPieces = 0f;
    float playerCenterPieces = 0f;
    for(int i = 0; i < pcheckers.size(); i++) {
      Checker checker = pcheckers.get(i);
      if(checker.getRowPosition() >= 2 && checker.getRowPosition() <= 5 && checker.getColumnPosition() >= 2 &&  checker.getColumnPosition() <= 5 ) {
        if(checker.getType() % 2 == 1) {
          if(checker.getRowPosition() >= 3 && checker.getRowPosition() <= 4 && checker.getColumnPosition() >= 3 && checker.getColumnPosition() <= 4)
            aiCenterPieces += 2;
          else
            aiCenterPieces++;
        } else {
          if(checker.getRowPosition() >= 3 && checker.getRowPosition() <= 4 && checker.getColumnPosition() >= 3 && checker.getColumnPosition() <= 4)
            playerCenterPieces += 2;
          else
            playerCenterPieces++;
        }

      }
    }
    return aiCenterPieces/playerCenterPieces;
  }

  public float evaluateMove(List<Checker> pcheckers) {
    float pieceRatio = this.evaluatePieceRatio(pcheckers);
    float centerPieceRatio = this.evaluateCentralPositionRatio(pcheckers);
    float moveRating = 100*pieceRatio + 30*centerPieceRatio;
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
