import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(Person.class).verify();
    }
}