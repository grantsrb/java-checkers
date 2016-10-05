import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class EasyAI {
  private Game currentGame;
  private String name;
  private int id;

  public EasyAI(String pname, int pboardId) {
    this.name = pname;
    this.currentGame = Game.findById(pboardId);
  }

  /////////////////////////////////////////////////////////////////////////////
  /// database Methods

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO ais (name) VALUES (:name)", true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherAI) {
    if(!(otherAI instanceof EasyAI)) {
      return false;
    } else {
      EasyAI newAI = (EasyAI) otherAI;
      return this.id == newAI.getId() &&
             this.name == newAI.getName();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// static Methods

  public static EasyAI findById(int pId, int pgameId) {
    Game game;
    EasyAI ai;
    try (Connection con = DB.sql2o.open()) {
      ai = con.createQuery("SELECT * FROM ais WHERE id=:id")
        .addParameter("id", pId)
        .executeAndFetchFirst(EasyAI.class);
      game = con.createQuery("SELECT * FROM games WHERE id=:id")
        .addParameter("id", pgameId)
        .executeAndFetchFirst(Game.class);
    }
    game.populateCheckers();
    ai.updateGame(game);
    return ai;
  }

}
