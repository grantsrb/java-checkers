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

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("loggedInStatus", User.loggedIn);
      model.put("loggedInUser", User.loggedInUser);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/checkers-game", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int[] board = {0,1,2,3,4,5,6,7};
      model.put("rows", board);
      model.put("columns", board);
      model.put("template", "templates/checkers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/login",(request,response) -> { // Directs to sign in page
      Map<String,Object> model = new HashMap<>();
      return new ModelAndView(model, "templates/login.vtl");
    },new VelocityTemplateEngine());

    post("/login", (request,response) -> { // Directs to home page if successful login
      Map<String,Object> model = new HashMap<>();
      String userName = request.queryParams("user-name");
      String password = request.queryParams("password");
      try {
        User newUser = User.login(userName,password);
        User.loggedIn = true;
        User.loggedInUser = newUser;
        model.put("loggedInStatus", User.loggedIn);
        model.put("loggedInUser", User.loggedInUser);
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      } catch(RuntimeException exception) { }
      model.put("invalidLogin", true);
      return new ModelAndView(model, "templates/login.vtl");
    },new VelocityTemplateEngine());

    post("/new-account", (request,response) -> {
      Map<String,Object> model = new HashMap<>();
      String userName = request.queryParams("create-user-name");
      String password = request.queryParams("create-password");
      if (!User.userAlreadyExists(userName)) { // If user name is valid, directs to home page
        User.loggedIn = true;
        User newUser = new User(userName, password);
        newUser.save();
        User.loggedInUser = newUser;
        model.put("loggedInStatus", User.loggedIn);
        model.put("loggedInUser", User.loggedInUser);
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      } else {
        model.put("invalidUserName", true);
        return new ModelAndView(model, "templates/login.vtl");
      }
    },new VelocityTemplateEngine());

    post("/logout", (request, response) -> {
      Map<String,Object> model = new HashMap<>();
      User.loggedIn = false;
      User.loggedInUser = null;
      model.put("loggedInStatus", User.loggedIn);
      model.put("loggedInUser", User.loggedInUser);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    // get("/", (request, response) -> {
    //   Map<String, Object> model = HashMap<>();
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
