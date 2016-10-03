
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Game {
  private int[][] board = new int [8][8];
  private int playerCount;
  private int id;

  public Game(int pPlayerCount) {
    this.playerCount = pPlayerCount;
    for (int i = 0; i < 8; i++) {
      for(int j = i%2; j < 8; j+=2) {
        if (i <=2)
          board[i][j] = 1;
        else if (i >= 3 && i <= 4)
          board[i][j] = 0;
        else
          board[i][j] = 2;
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and Methods

  public int getId() {
    return this.id;
  }

  public int getPlayerCount() {

  }

  public List<Integer> getUserIds() {

  }

  public List<User> getUsers() {

  }

  public int[][] getBoard() {

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

}
