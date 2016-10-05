import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {
  private Checker testChecker;
  private Game testGame;

  @Before
  public void initialize() {
    testChecker = new Checker(1,0,1,1);
    testGame = new Game(1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_savesObjectToDatabase_void() {
    assertTrue(testGame.getId() > 0);
  }

  @Test
  public void getId_returnsIdOfGame_int() {
    assertTrue(testGame.getId() > 0);
  }

  @Test
  public void equals_returnsTrueIfGameIsSame_true() {
    assertTrue(Game.findById(testGame.getId()).equals(testGame));
  }

  @Test
  public void findById_returnsInstanceOfGameById_Game() {
    Game testGame2 = new Game(2);
    Game object1FoundById = Game.findById(testGame.getId());
    Game object2FoundById = Game.findById(testGame2.getId());
    assertTrue(testGame.equals(object1FoundById));
    assertTrue(testGame2.equals(object2FoundById));
  }

  @Test
  public void all_returnsAllInstancesOfGame_ArrayList() {
    Game testGame2 = new Game(2);
    Game object1FoundByAll = Game.all().get(0);
    Game object2FoundByAll = Game.all().get(1);
    assertTrue(testGame.equals(object1FoundByAll));
    assertTrue(testGame2.equals(object2FoundByAll));
  }

  @Test
  public void delete_deletesInstanceOfGame_void() {
    testGame.delete();
    assertEquals(0, Game.all().size());
  }

  @Test
  public void getPlayerCount_returnsPlayerCountOfGame_int() {
    assertTrue(testGame.getPlayerCount() == 1);
  }

  @Test
  public void getPlayerTurn_returnsPlayerTurnOfGame_int() {
    assertTrue(testGame.getPlayerTurn() == 2);
  }

  @Test
  public void attachUser_joinsUserToGame_void() {
    User testUser = new User("bill", "jull");
    testUser.save();
    testGame.attachUser(testUser.getId());
    assertTrue(testGame.getUsers().get(0).equals(testUser));
  }

  @Test
  public void getCheckers_returnsCheckersOfGame_CheckerList() {
    assertTrue("type match",testGame.getCheckers().get(0).getType() == 1);
    assertEquals("row match",0, testGame.getCheckers().get(0).getRowPosition());
    assertEquals("col match",1, testGame.getCheckers().get(0).getColumnPosition());
  }

  @Test
  public void getCheckerInSpace_returnsCheckerInSpecifiedPlaceIfOccupied_Checker() {
    assertEquals("type match",testChecker.getType(), testGame.getCheckerInSpace(0,1).getType());
    assertEquals("row match",testChecker.getRowPosition(), testGame.getCheckerInSpace(0,1).getRowPosition());
    assertEquals("col match",testChecker.getColumnPosition(), testGame.getCheckerInSpace(0,1).getColumnPosition());
  }

  @Test
  public void moveIsOffBoard_returnsTrueIfMoveIsOffBoardBoundries_boolean() {
    assertEquals("min row", true, testGame.moveIsOffBoard(testChecker, -1, 1));
    assertEquals("max row", true, testGame.moveIsOffBoard(testChecker, 8, 1));
    assertEquals("min col", true, testGame.moveIsOffBoard(testChecker, 1, -1));
    assertEquals("max col", true, testGame.moveIsOffBoard(testChecker, 1, 8));
  }

  @Test
  public void moveIsWrongDirection_returnsTrueIfMoveIsWrongDirection_boolean() {
    Checker testChecker1 = new Checker(1,3,1,1);
    Checker testChecker2 = new Checker(2,5,1,1);
    assertTrue("testChecker",testGame.moveIsWrongDirection(testChecker1, 2, 2));
    assertTrue("testChecker2",testGame.moveIsWrongDirection(testChecker2, 6, 2));
  }

  @Test
  public void moveIsSameColumnOrRow_returnsTrueIfMoveIsSameColumnOrRow_boolean() {
    assertTrue("row", testGame.moveIsSameColumnOrRow(testChecker, 0,0));
    assertTrue("col", testGame.moveIsSameColumnOrRow(testChecker, 1,1));
  }

  @Test
  public void moveIsOnNullSpace_returnsTrueIfMoveIsOnNullSpace_boolean() {
    assertTrue(testGame.moveIsOnNullSpace(testChecker, 0,0));
  }

  @Test
  public void spaceIsLegal_returnsTrueIfSpaceIsLegal_boolean() {
    assertTrue(testGame.spaceIsLegal(testChecker, 1,0));
    assertTrue(testGame.spaceIsLegal(testChecker, 1,2));
  }

  @Test
  public void isLegalMove_returnsTrueIfMoveIsLegal_boolean() {
    assertTrue(testGame.isLegalMove(testChecker,1,0));
    assertTrue(testGame.isLegalMove(testChecker,1,2));
  }

  @Test
  public void isLegalCapture_returnsTrueIfCaptureIsLegal_boolean() {
    assertTrue(testGame.isLegalCapture(testChecker,2,3));
  }

  @Test
  public void specificMoveIsValid_returnsTrueIfMoveIsValid_boolean() {
    Checker testChecker2 = testGame.getCheckers().get(0);
    Checker testChecker3 = new Checker(1,2,1,testGame.getId());
    assertEquals("piece conflict",false, testGame.specificMoveIsValid(testChecker2,1,0));
    assertTrue(testGame.specificMoveIsValid(testChecker3,3,0));
  }

  @Test
  public void getAdjacentOpponentChecker_returnsCheckerImmediatelyAdjacentToPieceIfOppositional_Checker() {
    Checker testChecker2 = new Checker(2,3,4,1);
    assertEquals("row check",2,testGame.getAdjacentOpponentChecker(testChecker2,1,2).getRowPosition());
    assertEquals("col check",3,testGame.getAdjacentOpponentChecker(testChecker2,1,2).getColumnPosition());
  }

  @Test
  public void specificCaptureIsValid_returnsTrueIfCaptureManeuverIsValid_boolean() {
    Checker testChecker2 = new Checker(2,5,0,1);
    Checker testChecker3 = new Checker(1,4,1,testGame.getId());
    testGame.addChecker(testChecker3);
    assertTrue(testGame.specificCaptureIsValid(testChecker2,3,2));
  }

  @Test
  public void generalCaptureIsAvailable_returnsTrueIfCaptureIsAvailable_boolean() {
    Checker testChecker2 = new Checker(2,5,0,1);
    Checker testChecker3 = new Checker(1,4,1,testGame.getId());
    testGame.addChecker(testChecker3);
    assertTrue(testGame.generalCaptureIsAvailable(testChecker2));
  }

  @Test
  public void generalMoveIsAvailable_returnsTrueIfMoveIsAvailable_boolean() {
    Checker testChecker2 = new Checker(2,5,2,1);
    Checker testChecker3 = new Checker(1,4,1,testGame.getId());
    testGame.addChecker(testChecker3);
    assertTrue(testGame.generalMoveIsAvailable(testChecker2));
  }

  @Test
  public void movePiece_recordsCheckerMoveInChecker_void() {
    Checker testChecker2 = testGame.getCheckerInSpace(2,1);
    testGame.movePiece(testChecker2, 3, 0);
    assertTrue(testChecker2.equals(testGame.getCheckerInSpace(3,0)));
  }

  @Test
  public void capturePiece_removesCapturedPieceAfterMovingCheckerInValidCaptureMove_void() {
    Checker testChecker2 = testGame.getCheckerInSpace(2,1);
    Checker testChecker3 = testGame.getCheckerInSpace(5,4);
    testGame.movePiece(testChecker2, 3, 2);
    testGame.movePiece(testChecker3, 4,3);
    testGame.capturePiece(testChecker2, 5,4);
    assertEquals(null, testGame.getCheckerInSpace(4,3));
  }

  @Test
  public void save_savesGameIntoUsersGamesJoinTable_true() {
    User testUser = new User("cgrahams", "1234gogo");
    testUser.save();
    Game firstGame = new Game(2);
    firstGame.attachUser(testUser.getId());
    Game secondGame = new Game(2);
    secondGame.attachUser(testUser.getId());
    assertTrue(testUser.getGames().get(0).equals(firstGame));
    assertTrue(testUser.getGames().get(1).equals(secondGame));
  }

  @Test
  public void deleteUnsaved_deletesAllUnassignedGames_true() {
    User testUser = new User("cgrahams", "1234gogo");
    testUser.save();
    Game firstGame = new Game(2);
    firstGame.attachUser(testUser.getId());
    Game secondGame = new Game(2);
    Game.deleteUnsaved();
    System.out.println(Game.all().get(0));
    assertEquals(Game.all().size(), 1);
  }

}
