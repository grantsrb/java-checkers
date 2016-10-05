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
  /// special Methods

  // public void generateMoves(Checker pchecker) {
  //
  //   for(int row = 0; row < 8; row++) {
  //     for(int col = 0; col < 8; col++) {
  //       if(this.currentGame.)
  //     }
  //   }
  // }

  public void generateMove() {
    List<Checker> checkers = this.currentGame.getCheckers();
    for (int i = 0; i < checkers.size(); i++) {
      this.generateMoves(checker);
    }
  }

  public int evaluateMove() {

  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Game getCurrentGame() {
    return this.currentGame;
  }

  public void attachGame(int pgameId) {
    Integer attachedGameId;
    try (Connection con = DB.sql2o.open()) {
      attachedGameId = con.createQuery("SELECT gameId FROM easyAis_games WHERE aiId=:id AND gameId=:gameId")
        .addParameter("id", this.id)
        .addParameter("gameId", pgameId)
        .executeAndFetchFirst(Integer.class);
      if(attachedGameId == null) {
        con.createQuery("INSERT INTO easyAis_games (aiId, gameId) VALUES (:aiId, :gameId)")
          .addParameter("aiId", this.id)
          .addParameter("gameId", pgameId)
          .executeUpdate();
      }
    }
    this.currentGame = Game.findById(pgameId);
  }

  public List<Integer> getGameIds() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT gameId FROM easyAis_games WHERE aiId=:id")
        .addParameter("id", this.id)
        .executeAndFetch(Integer.class);
    }
  }

  public List<Game> getGames() {
    List<Integer> gameIds = this.getGameIds();
    List<Game> games = new ArrayList<>();
    for (int i = 0; i < gameIds.size(); i++) {
      games.add(Game.findById(gameIds.get(i)));
    }
    return games;
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
