import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    int[] board = {0,1,2,3,4,5,6,7};

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      for (int i = 0; i < Game.all().size(); i++) {
        Game game = Game.all().get(i);
        System.out.println(game.getId());
        game.delete();
      }
      Game newGame = new Game(2);
      model.put("checkers", Checker.all());
      model.put("rows", board);
      model.put("columns", board);
      model.put("template", "templates/checkers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/move/red", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      List<Integer> rows = new ArrayList<Integer>();
      List<Integer> columns = new ArrayList<Integer>();
      Checker checker = Checker.find(Integer.parseInt(request.queryParams("redChecker")));
      Game game = Game.findById(checker.getGameId());
      for (int i = 0; i < board.length ; i++ ) {
        for (int j = 0; j < board.length ; j++ ) {
          if(game.specificMoveIsValid(checker, i, j)) {
            rows.add(i);
            columns.add(j);
          }
        }
      }
      model.put("rowsLegal", rows);
      model.put("columnsLegal", columns);
      model.put("rows", board);
      model.put("columns", board);
      model.put("checkers", Checker.all());
      model.put("template", "templates/checkers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/", (request, response) -> {
    //   Map<String, Object> model =  new HashMap<>();
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
