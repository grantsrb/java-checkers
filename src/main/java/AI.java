import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class AI {
  private Game currentGame;
  private int id;

  public static final int AI_TEAM = 1;

  public AI(int gameId) {
    this.currentGame = Game.findById(gameId);
  }

  public Game getCurrentGame() {
    return this.currentGame;
  }

  public Checker copyChecker(Checker pchecker) {
    Checker checkerCopy = new Checker(pchecker.getType(), pchecker.getRowPosition(), pchecker.getColumnPosition(), pchecker.getGameId());
    checkerCopy.initializeId(pchecker.getId());
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

  public boolean generateFirstCapture(int team) {
    List<Checker> checkers = this.currentGame.getCheckersList();
    for(int k = 0; k < checkers.size(); k++) {
      if(checkers.get(k).getType()%2 == team%2 && this.currentGame.generalCaptureIsAvailable(checkers.get(k))) {
        for (int i = -2; i <= 2; i+=4) {
          for (int j = -2; j <= 2; j+=4) {
            int success = this.currentGame.capturePiece(checkers.get(k), checkers.get(k).getRowPosition()+i, checkers.get(k).getColumnPosition()+j);
            if (success > 0) {
              return true;
    }}}}}
    return false;
  }

  public boolean generateMove(Checker checker) {
    for (int i = -1; i <= 1; i+=2) {
      for (int j = -1; j <= 1; j+=2) {
        int success = this.currentGame.movePiece(checker, checker.getRowPosition()+i, checker.getColumnPosition()+j);
        if(success > 0) {
          return true;
    }}}
    return false;
  }

  public void move() {
    Random rand = new Random();
    boolean moveIsGenerated = this.generateFirstCapture(AI_TEAM);
    while (!moveIsGenerated) {
      int randRow = rand.nextInt(8);
      int randCol = rand.nextInt(8);
      Checker checker = this.getCheckerByCoordinates(randRow, randCol);
      if(checker != null && checker.getType() % 2 == AI_TEAM%2) {
        if (this.currentGame.generalMoveIsAvailable(checker)) {
          moveIsGenerated = this.generateMove(checker);
  } } } }


}
