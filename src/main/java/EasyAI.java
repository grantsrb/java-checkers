import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class EasyAI {
  private Game currentGame;
  private int id;
  public static final int LOOP_COUNT = 5;


////////////////////////////////////////////////////////////////////////
  /// member classes

  private class MoveValue{
    public float moveValue;
    public int row;
    public int column;
    public float valueDifference;
    public Checker checkerToMove;

    public MoveValue() {
      this.clearContents();
    }

    public void clearContents() {
      this.moveValue = 0f;
      this.row = -1;
      this.column = -1;
      this.valueDifference = 0f;
      this.checkerToMove = null;
    }

    public void setEqualTo(MoveValue equalizer) {
      this.moveValue = equalizer.moveValue;
      this.row = equalizer.row;
      this.column = equalizer.column;
      this.valueDifference = equalizer.valueDifference;
      this.checkerToMove = equalizer.checkerToMove;
    }
  }




  public EasyAI(int pboardId) {
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

  public Checker getCheckerByCoordinates(int prow, int pcol) {
    List<Checker> checkers = this.currentGame.getCheckersList();
    for (int i = 0; i < checkers.size(); i++) {
      Checker checker = checkers.get(i);
      if(checker.getRowPosition() == prow && checker.getColumnPosition() == pcol) {
        return checker;
      }
    }
    return null;
  }

  public void generateMoves(int range, List<Checker> pcheckers, int pcheckerIndex, MoveValue paiMoveValue, MoveValue popponentMoveValue, int pmoveCount) {
    if(pmoveCount % 2 == LOOP_COUNT % 2) {
      this.currentGame.setPlayerTurn(1);
    } else {
      this.currentGame.setPlayerTurn(2);
    }
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
        if(success) {
          this.recursiveMoveEvaluation(originalCheckers, pmoveCount-1, paiMoveValue, popponentMoveValue);
        }
        if (pmoveCount == LOOP_COUNT && (paiMoveValue.moveValue - popponentMoveValue.moveValue) > paiMoveValue.valueDifference && pcheckers.get(pcheckerIndex).getType() % 2 == 1) {
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
    if(pmoveCount % 2 == LOOP_COUNT % 2) {
      this.currentGame.setPlayerTurn(1);
    } else {
      this.currentGame.setPlayerTurn(2);
    }
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
    }
  }

  public void move() {
    this.currentGame.setPlayerTurn(1);
    List<Checker> checkers = this.currentGame.getCheckersList();
    List<MoveValue> aiMoves = new ArrayList<>();
    List<MoveValue> opponentMoves = new ArrayList<>();
    MoveValue bestAiMoveValue = new MoveValue();
    for (int k = 0; k < checkers.size(); k++) {
      aiMoves.add(new MoveValue());
      opponentMoves.add(new MoveValue());
      Checker currentChecker = checkers.get(k);
      if(currentChecker.getType() % 2 == this.currentGame.getPlayerTurn() % 2) {
        if(this.currentGame.generalMoveIsAvailable(currentChecker)) {
          this.generateMoves(1, checkers, k, aiMoves.get(k), opponentMoves.get(k), LOOP_COUNT);
        }
        if(this.currentGame.generalCaptureIsAvailable(currentChecker)) {
          this.generateMoves(2, checkers, k, aiMoves.get(k), opponentMoves.get(k), LOOP_COUNT);
        }
      }
    }
    for(int i = 0; i < aiMoves.size(); i++) {
      if(aiMoves.get(i).valueDifference > bestAiMoveValue.valueDifference){
        bestAiMoveValue.setEqualTo(aiMoves.get(i));
      }
    }
    System.out.println(bestAiMoveValue.row);
    System.out.println(bestAiMoveValue.column);
    this.currentGame.populateCheckers();
    if(bestAiMoveValue.checkerToMove == null) {
      this.generateRandomMove();
      System.out.println("Random Generation");
    }else {
      Checker chosenChecker = this.currentGame.getCheckerInSpace(bestAiMoveValue.checkerToMove.getRowPosition(), bestAiMoveValue.checkerToMove.getColumnPosition());
      System.out.println(chosenChecker.getRowPosition() + ", " + chosenChecker.getColumnPosition());
      this.currentGame.movePiece(chosenChecker, bestAiMoveValue.row, bestAiMoveValue.column);
      this.currentGame.capturePiece(chosenChecker, bestAiMoveValue.row, bestAiMoveValue.column);
      System.out.println("Move Generation");
    }
    if(this.currentGame.getPlayerTurn() == 1) {
      this.currentGame.updatePlayerTurn();
    }
  }

  public boolean generateRandomMove() {
    Random rand = new Random();
    List<Checker> pcheckers = this.currentGame.getCheckersList();
    boolean moveIsNotGenerated = true;
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
              boolean success = this.currentGame.movePiece(checker, checker.getRowPosition()+i, checker.getColumnPosition()+j);
              if(success) {
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
    float aiCheckerCount = 1f;
    float playerCheckerCount = 1f;
    for(int i = 0; i < pcheckers.size(); i++) {
      if(pcheckers.get(i).getType() % 2 == 1) {
        aiCheckerCount++;
      } else {
        playerCheckerCount++;
      }
    }
    return aiCheckerCount/playerCheckerCount;
  }

  public float evaluateKingRatio(List<Checker> pcheckers) {
    float aiCheckerCount = 1f;
    float playerCheckerCount = 1f;
    for(int i = 0; i < pcheckers.size(); i++) {
      if(pcheckers.get(i).getType() == 3) {
        aiCheckerCount++;
      } else if (pcheckers.get(i).getType() == 4) {
        playerCheckerCount++;
      }
    }
    return aiCheckerCount/playerCheckerCount;
  }

  public float evaluateCentralPositionRatio(List<Checker> pcheckers) {
    float aiCenterPieces = 1f;
    float playerCenterPieces = 1f;
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
    float kingRatio = this.evaluateKingRatio(pcheckers);
    float moveRating = 200*kingRatio + 100*pieceRatio + 3*centerPieceRatio;
    return moveRating;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public Game getCurrentGame() {
    return this.currentGame;
  }
}
