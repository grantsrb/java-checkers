import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class AITest {
  private Game testGame;
  private AI testAI;
  private Checker testChecker;

  @Before
  public void initialize() {
    testGame = new Game(1);
    testGame.updatePlayerTurn();
    testAI = new AI(testGame);
    testChecker = new Checker(2,3,2,testGame.getId());
    testChecker.save();
    testAI.getCurrentGame().populateCheckers();
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void copyChecker_returnsDeepCopyOfCheckerArgument_Checker() {
    Checker checkerCopy = testChecker;
    assertTrue(testChecker.getId() > 0);
    checkerCopy.setToNull();
    assertEquals(0,testChecker.getId());
    checkerCopy = testAI.copyChecker(testChecker);
    testChecker.updatePosition(4,3);
    assertEquals(0,checkerCopy.getId());
  }

  @Test
  public void copyCheckers_returnsDeepCopyOfCheckersList_ArrayList() {
    List<Checker> checkerCopies = testGame.getCheckersList();
    assertTrue(checkerCopies.get(0).equals(testGame.getCheckersList().get(0)));
    checkerCopies = testAI.copyCheckers(testGame.getCheckersList());
    checkerCopies.get(0).setToNull();
    assertFalse(checkerCopies.get(0).equals(testGame.getCheckersList().get(0)));
  }

  @Test
  public void getCheckerByCoordinates_returnsCheckerAtSpecifiedCoordinates_Checker() {
    Checker checkerCopy = testAI.getCheckerByCoordinates(0,1);
    assertTrue(testAI.getCurrentGame().getCheckersList().get(0).equals(checkerCopy));
  }

  @Test
  public void generateFirstCapture_generatesTheFirstCaptureAvailable_boolean() {
    assertTrue("First capture", testAI.generateFirstCapture(1));
  }

  @Test
  public void generateMove_attemptsToGenerateAMoveForAGivenChecker_boolean() {
    assertFalse(testAI.generateMove(testChecker));
    assertTrue(testAI.generateMove(testAI.getCurrentGame().getCheckersList().get(8)));
  }

  @Test
  public void move_generatesRandomMoveIfNoCapturesAvailable_void() {
    testAI.move();
    assertTrue("Instanceof",testAI.getCheckerByCoordinates(4,3) instanceof Checker);
    List<Checker> checkerCopies = testAI.copyCheckers(testAI.getCurrentGame().getCheckersList());
    boolean allMatches = true;
    for(int i = 0; i < checkerCopies.size(); i++) {
      if(!checkerCopies.get(i).equals(testAI.getCurrentGame().getCheckersList().get(i))) {
        allMatches = false;
      }
    }
    assertTrue("allMatches true",allMatches);
    testAI.move();
    for(int i = 0; i < checkerCopies.size(); i++) {
      if(!checkerCopies.get(i).equals(testAI.getCurrentGame().getCheckersList().get(i)))
        allMatches = false;
    }
    assertFalse("allMatches false",allMatches);
  }

}
