import org.junit.*;
import static org.junit.Assert.*;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    Book newBook = new Book("Cheryl's Story");
    assertTrue(newBook instanceof Book);
  }

  @Test
  public void equals_returnsTrueIfBooksTitlesAreTheSame() {
    Book newBook = new Book("Bobby");
    Book newBookToo = new Book("Bobby");
    assertTrue(newBook.equals(newBookToo));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void save() {
    Book newBook = new Book("Bobby");
    newBook.save();
    assertTrue(Book.all().contains(newBook));
  }


}
