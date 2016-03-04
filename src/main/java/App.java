import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/login.vtl");
      // model.put("user", request.session().attribute("user"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/home", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String inputtedUser = request.queryParams("user");
      request.session().attribute("user", inputtedUser);
      model.put("user", inputtedUser);
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/how-many", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String bookTitle = request.queryParams("title");
      request.session().attribute("title", bookTitle);
      model.put("title", bookTitle);
      model.put("user", request.session().attribute("user"));
      model.put("template", "templates/how-many.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/input-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int numberOfAuthors = Integer.parseInt(request.queryParams("authorNumber"));
      request.session().attribute("authorNumber", numberOfAuthors);
      model.put("authorNumber", numberOfAuthors);
      model.put("user", request.session().attribute("user"));
      model.put("title", request.session().attribute("title"));
      model.put("template", "templates/input-author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int numberOfAuthors = request.session().attribute("authorNumber");
      String title = request.session().attribute("title");
      String author1Name = request.queryParams("name1");
      String author2Name = request.queryParams("name2");
      String author3Name = request.queryParams("name3");
      Book newBook = new Book(title);
      newBook.save();
      Author newAuthor = new Author(author1Name);
      newAuthor.save();
      newBook.addAuthor(newAuthor.getId());
      model.put("books", Book.all());
      model.put("user", request.session().attribute("user"));
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
