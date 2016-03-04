import org.sql2o.*;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Copy {
  private int id;
  private int book_id;
  private boolean checkout;
  private long due_date;
  private int patron_id;


  public Copy(int bookId) {
    book_id = bookId;
    checkout = false;
  }

  public int getId() {
    return id;
  }

  public int getBookId() {
    return book_id;
  }

  public boolean getCheckout() {
    return checkout;
  }

  public long getDueDate() {
    return due_date;
  }

  public int getPatronId() {
    return patron_id;
  }

  @Override
  public boolean equals(Object otherCopy){
    if (!(otherCopy instanceof Copy)) {
      return false;
    } else {
      Copy newCopy = (Copy) otherCopy;
      return getId() == (newCopy.getId());
    }
  }



  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO copies (checkout, book_id) VALUES (:checkout, :book_id)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("checkout", checkout)
         .addParameter("book_id", book_id)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Copy> all() {
    String sql = "SELECT * FROM copies;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }

  public static Copy find(int id) {
    String sql = "SELECT * FROM copies WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Copy.class);
    }
  }

  public Book getBook() {
    String sql = "SELECT * FROM books WHERE id=:book_id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("book_id", book_id)
      .executeAndFetchFirst(Book.class);
    }
  }

  public String getBookTitle() {
    Book myBook = this.getBook();
    return myBook.getTitle();
  }

  public String displayDueDate() {
    Copy myCopy = Copy.find(id);
    long due_date = myCopy.getDueDate();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy E");
    String dueDate = sdf.format(due_date);
    return dueDate;
  }

  //UPDATE

  public void checkout() {
    checkout = true;
    Calendar now = Calendar.getInstance();
    long current_date = now.getTimeInMillis();
    long due_date = current_date + 2592000000L;
    String sql = ("UPDATE copies SET checkout=:checkout, due_date=:due_date  WHERE id=:id;");
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("checkout", checkout)
        .addParameter("due_date", due_date)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void checkin() {
    checkout = false;
  }


  // DELETE
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM copies WHERE id=:id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }
}
