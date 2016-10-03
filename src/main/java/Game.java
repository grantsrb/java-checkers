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
    return this.playerCount;
  }

  // //TODO caleb and sandro
  // public List<Integer> getUserIds() {
  //
  // }
  //
  // public List<User> getUsers() {
  //
  // }
  // //===
  //
  // public int[][] getBoard() {
  //
  // }

  /////////////////////////////////////////////////////////////////////////////
  /// database managament Methods

  //TODO Compare boards
  @Override
  public boolean equals(Object otherGame) {
    if(!(otherGame instanceof Game)) {
      return false;
    } else {
      Game newGame = (Game) otherGame;
      return this.id == newGame.getId() &&
             this.playerCount == newGame.getPlayerCount();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// validator Methods

  // public boolean generalMoveIsAvailable(int pRow, int pCol) {
  //
  // }
  //
  // public boolean generalCaptureIsAvailable(int pRow, int pCol) {
  //
  // }
  //
  // public boolean legalMove(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {
  //
  // }
  //
  // public boolean specificMoveIsAvailable(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {
  //
  // }
  //
  // public boolean specificCaptureIsAvailable(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {
  //
  // }

  ///////////////////////////////////////////////////////////////////////////
  // gamePlay Methods

  // public void movePiece(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {
  //
  // }
  //
  // public void capturePiece(int pRowStart, int pColStart, int pRowFinish, int pColFinish) {
  //
  // }
  //
  // public void makeKing(int pRow, int pCol) {
  //
  // }

}
