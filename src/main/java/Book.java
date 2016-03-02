import org.sql2o.*;
import java.util.List;

public class Book {
  private int id;
  private String title;

  public Book (String title) {
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle());
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO books (title) VALUES (:title)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("title", this.title)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Book> all() {
    String sql = "SELECT * FROM books;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }
}
