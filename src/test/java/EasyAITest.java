import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class EasyAITest {
  private Game testGame;
  private EasyAI testAI;

  @Before
  public void initialize() {
    testGame = new Game(1);
    testAI = new EasyAI("Satchel", testGame.getId());
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void generateRandomMove_makesARandomMoveForAiCheckers_void() {
    testAI.generateRandomMove();
    for(int i = 0; i < testGame.getCheckersList().size(); i++) {
      System.out.println(testAI.getCurrentGame().getCheckersList().get(i).getRowPosition() + " " + testAI.getCurrentGame().getCheckersList().get(i).getColumnPosition());
    }
    assertEquals(null, 1);
  }

  @Test
  public void generateMove_generatesMove_true() {
    testAI.move();
    for(int i = 0; i < testGame.getCheckersList().size(); i++) {
      System.out.println(testGame.getCheckersList().get(i).getRowPosition() + " " + testGame.getCheckersList().get(i).getColumnPosition());
    }
    assertEquals(null, 1);
  }
}
