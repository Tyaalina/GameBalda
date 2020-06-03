import MyGame.Dictionary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DictionaryTest {

    private Dictionary dictionary = new Dictionary();

    @Test
    public void test_dictionary_create(){
        assert(dictionary != null);
    }

    @Test
    public void test_dictionary_generatingRandomWord(){
        int n =5;
        String word = dictionary.generatingRandomWord(n);
        assert(word != null);
        assertEquals(n, word.length());
    }

    @Test
    public void test_dictionary_isInDictionary_true(){
        String word = "аббатство";
        boolean available = dictionary.isInDictionary(word);
        assertEquals(true, available);
    }

    @Test
    public void test_dictionary_isInDictionary_false(){
        String word = "абббрат";
        boolean available = dictionary.isInDictionary(word);
        assertEquals(false, available);
    }
}
