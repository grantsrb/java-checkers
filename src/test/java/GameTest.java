import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void equals_returnsTrueIfGameIsSame_true() {
    Game firstGame = new Game(1);
    assertTrue(Game.findById(firstGame.getId()).equals(firstGame));
  }

  @Test
  public void getId_returnsIdOfGame_int() {
    Game testGame = new Game(1);
    testGame.save();
    assertTrue(testGame.getId() > 0);
  }

  @Test
  public void save_savesObjectToDatabase_void() {
    Game firstGame = new Game(1);
    assertTrue(firstGame.getId() > 0);
  }

  @Test
  public void findById_returnsInstanceOfGameById_Game() {
    Game testGame1 = new Game(1);
    Game testGame2 = new Game(2);
    Game object1FoundById = Game.findById(testGame1.getId());
    Game object2FoundById = Game.findById(testGame2.getId());
    assertTrue(testGame1.equals(object1FoundById));
    assertTrue(testGame2.equals(object2FoundById));
  }

  @Test
  public void all_returnsAllInstancesOfGame_ArrayList() {
    Game testGame1 = new Game(1);
    Game testGame2 = new Game(2);
    Game object1FoundByAll = Game.all().get(0);
    Game object2FoundByAll = Game.all().get(1);
    assertTrue(testGame1.equals(object1FoundByAll));
    assertTrue(testGame2.equals(object2FoundByAll));
  }

  @Test
  public void delete_deletesInstanceOfGame_void() {
    Game testGame = new Game(1);
    testGame.delete();
    assertEquals(0, Game.all().size());
  }

  @Test
  public void getPlayerCount_returnsPlayerCountOfGame_int() {
    Game testGame = new Game(1);
    assertTrue(testGame.getPlayerCount() == 1);
  }

  @Test
  public void getPlayerTurn_returnsPlayerTurnOfGame_int() {
    Game testGame = new Game(1);
    assertTrue(testGame.getPlayerTurn() == 1);
  }

  @Test
  public void attachUser_joinsUserToGame_void() {
    Game testGame = new Game(1);
    User testUser = new User("bill", "jull");
    testUser.save();
    testGame.attachUser(testUser);
    assertTrue(testGame.getUsers().get(0).equals(testUser));
  }

  @Test
  public void getUserIds_returnsUserIdsOfGame_UserList() {
    Game testGame = new Game(1);
    assertTrue(testGame.getCheckers().get(0).getType() == 1);
  }

  @Test
  public void spotIsTaken_returnsTrueIfPlaceIsOccupiedByPiece_boolean() {
    Game testGame = new Game(1);
    assertTrue(testGame.spotIsTaken(0,1));
  }

  @Test
  public void isLegal_returnsTrueIfMoveIsLegal_boolean() {
    Game testGame = new Game(1);
    assertTrue(testGame.spotIsTaken(0,1));
  }


}
