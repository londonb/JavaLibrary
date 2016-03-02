import org.sql2o.*;
import java.util.List;

public class Author{
  private int id;
  private String author_name;


  public Author(String author_name) {
    this.author_name = author_name;
  }

  public int getId() {
    return id;
  }

  public String getAuthorName() {
    return author_name;
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getAuthorName().equals(newAuthor.getAuthorName());
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO authors (author_name) VALUES (:author_name)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("author_name", this.author_name)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Author> all() {
    String sql = "SELECT * FROM authors;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }
}
