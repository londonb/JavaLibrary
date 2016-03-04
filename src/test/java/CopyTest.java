import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void copy_instantiatesCorrectly_true() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    assertTrue(newCopy instanceof Copy);
  }

  @Test
  public void equals_returnsTrueIfCopiesTitlesAreTheSame() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    assertTrue(newCopy.equals(newCopy));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  @Test
  public void save() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertTrue(Copy.all().contains(newCopy));
  }

  @Test
  public void checkout_updatesDueDateAndCheckoutStatusOfCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    newCopy.checkout();
      Calendar now = Calendar.getInstance();
      long current_date = now.getTimeInMillis();
      long due_date = current_date + 2592000000L;
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy E");
    assertEquals(sdf.format(due_date), newCopy.displayDueDate());
    assertEquals(newCopy.getCheckout(), true);
  }

  @Test
  public void checkin_updatesCheckoutStatusOfCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    newCopy.checkout();
    newCopy.checkin();
    assertEquals(newCopy.getCheckout(), false);
  }

  @Test
  public void delete_removesCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertTrue(Copy.all().contains(newCopy));
    newCopy.delete();
    assertFalse(Copy.all().contains(newCopy));
  }

  @Test
  public void find_returnesCopyWithId() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertEquals(Copy.find(newCopy.getId()), newCopy);
  }

  @Test
  public void getBook_returnesBookForSpecificCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertEquals(newBook, newCopy.getBook());
  }

  @Test
  public void getBookTitle_returnesBookTitleForSpecificCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertEquals(newBook.getTitle(), newCopy.getBookTitle());
  }

  @Test
  public void displayDueDate() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    newCopy.checkout();
    Calendar now = Calendar.getInstance();
    long current_date = now.getTimeInMillis();
    long due_date = current_date + 2592000000L;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy E");
    assertEquals(sdf.format(due_date), newCopy.displayDueDate());
  }


}
