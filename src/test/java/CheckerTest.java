import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CheckerTest {
  private Checker firstChecker;
  private Checker secondChecker;

  @Before
  public void initialize() {
    firstChecker = new Checker(1, 2, 3, 4);
    secondChecker = new Checker(1, 1, 1, 4);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Checker_instantiatesCorrectly_true() {
    assertEquals(true, firstChecker instanceof Checker);
  }

  @Test
  public void getType_returnsType_int() {
    assertEquals(1, firstChecker.getType());
  }

  @Test
  public void getRowPosition_returnsRowPosition_int() {
    assertEquals(2, firstChecker.getRowPosition());
  }


  @Test
  public void getColumnPosition_returnsColumnPosition_int() {
    assertEquals(3, firstChecker.getColumnPosition());
  }

  @Test
  public void getGameId_returnsGameId_int() {
    assertEquals(4, firstChecker.getGameId());
  }

  @Test
  public void getId_returnsId_true() {
    firstChecker.save();
    assertTrue(firstChecker.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfChecker_true() {
    firstChecker.save();
    secondChecker.save();
    assertTrue(Checker.all().get(0).equals(firstChecker));
    assertTrue(Checker.all().get(1).equals(secondChecker));
  }

  @Test
  public void find_returnsCheckerWithSameId_secondChecker() {
    firstChecker.save();
    secondChecker.save();
    assertEquals(Checker.find(secondChecker.getId()), secondChecker);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Checker myChecker = new Checker(1, 2, 3, 4);
    assertTrue(firstChecker.equals(myChecker));
  }

  @Test
  public void save_returnsTrueIfTypeAreTheSame() {
    firstChecker.save();
    assertTrue(Checker.all().get(0).equals(firstChecker));
  }

  @Test
  public void save_assignsIdToObject() {
    firstChecker.save();
    Checker savedChecker = Checker.all().get(0);
    assertEquals(firstChecker.getId(), savedChecker.getId());
  }

  @Test
  public void delete_deletesChecker_true() {
    firstChecker.save();
    int firstCheckerId = firstChecker.getId();
    secondChecker.save();
    int secondCheckerId = secondChecker.getId();
    secondChecker.delete();
    assertEquals(firstChecker, Checker.find(firstCheckerId));
    assertEquals(null, Checker.find(secondCheckerId));
  }
}
