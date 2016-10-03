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
    Game secondGame = new Game(1);
    assertTrue(firstGame.equals(secondGame));
  }

}
