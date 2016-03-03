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

  @Test
  public void update_updatesBookTitle() {
    Book newBook = new Book("History");
    newBook.save();
    newBook.update("Charles");
    assertEquals("Charles", newBook.getTitle());
  }

  @Test
  public void delete_removesBook() {
    Book newBook = new Book("Literature");
    newBook.save();
    assertTrue(Book.all().contains(newBook));
    newBook.delete();
    assertFalse(Book.all().contains(newBook));
  }

  @Test
  public void find_returnesBookWithId() {
    Book newBook = new Book("Literature");
    newBook.save();
    assertEquals(Book.find(newBook.getId()), newBook);
  }


  @Test
  public void getAuthors_returnesBookWithId() {
    Book newBook = new Book("Literature");
    Author newAuthor = new Author("Bobby Bob");
    newBook.save();
    newAuthor.save();
    newBook.addAuthor(newAuthor.getId());
    assertTrue(newBook.getAuthors().contains(newAuthor));
  }

  @Test
  public void allCopiesOf_returnsAListOfCopies() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy copy1 = new Copy(newBook.getId());
    Copy copy2 = new Copy(newBook.getId());
    Copy copy3 = new Copy(newBook.getId());
    copy1.save();
    copy2.save();
    copy3.save();
    assertEquals(3 , newBook.allCopiesOf().size());
  }

  @Test
  public void availableCopies_returnsAListOfAvailableCopies() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy copy1 = new Copy(newBook.getId());
    Copy copy2 = new Copy(newBook.getId());
    Copy copy3 = new Copy(newBook.getId());
    copy1.save();
    copy2.save();
    copy3.save();
    copy1.checkout();
    assertEquals(2 , newBook.availableCopies().size());
  }
}
