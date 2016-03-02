import org.junit.*;
import static org.junit.Assert.*;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void author_instantiatesCorrectly_true() {
    Author newAuthor = new Author("Cheryl Story");
    assertTrue(newAuthor instanceof Author);
  }

  @Test
  public void equals_returnsTrueIfAuthorNameAreTheSame() {
    Author newAuthor = new Author("Bobby Bob");
    Author newAuthorToo = new Author("Bobby Bob");
    assertTrue(newAuthor.equals(newAuthorToo));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void save() {
    Author newAuthor = new Author("Bobby Bob");
    newAuthor.save();
    assertTrue(Author.all().contains(newAuthor));
  }

}
