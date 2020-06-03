import MyGame.Alphabet;
import MyGame.Letter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlphabetTest {

    private Alphabet alphabet = new Alphabet();

    @Test
    public void test_alphabet_create(){
        assert(alphabet != null);
    }

    @Test
    public void test_alphabet_setActivateLetter(){
        Letter l= new Letter("a");

        alphabet.setActivateLetter(l);

        assert(alphabet.getActiveLetter().equal(new Letter("a")));
    }

    @Test
    public void test_alphabet_getActivateLetter(){
        Alphabet _alphabet = new Alphabet(new Letter("c"));

        Letter l= new Letter("c");

        assert(_alphabet.getActiveLetter().equal(l));
    }

}
