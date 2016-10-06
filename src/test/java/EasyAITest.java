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
  public void generateMove_generatesMove_true() {
    List<Checker> gameCheckers = testGame.getCheckers();
    for(int i = 0; i < gameCheckers.size(); i++) {
      System.out.println(gameCheckers.get(i).getRowPosition() + " " + gameCheckers.get(i).getColumnPosition());
    }
    testAI.generateMove(testGame.getCheckers());
    for(int i = 0; i < gameCheckers.size(); i++) {
      System.out.println(gameCheckers.get(i).getRowPosition() + " " + gameCheckers.get(i).getColumnPosition());
    }
    assertEquals(null, 1);
  }
}
