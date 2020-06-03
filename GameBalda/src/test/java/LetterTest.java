import MyGame.Cell;
import MyGame.Letter;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LetterTest {
    private Letter letter = new Letter("b");

    @Test
    public void test_letter_create(){
        assert(letter != null);
        assert(letter.equal(new Letter("b")));
    }

    @Test
    public void test_letter_equal_true(){
        Letter letterClone = new Letter("b");

        assert(letter.equal(letterClone));
    }

    @Test
    public void test_letter_equal_false(){
        Letter letterClone = new Letter("a");

        assert(!letter.equal(letterClone));
    }

    @Test
    public void test_letter_toString(){

        assertEquals("b",letter.ToString());
    }

    @Test
    public void test_letter_setCell(){
        Cell cell = new Cell(new Point(1,2));

        letter.setCell(cell);

        Cell cell1 = new Cell(new Point(1,2));

        assert(letter.getCell().equal(cell1));
    }

    @Test
    public void test_letter_getCell(){
        Letter _letter = new Letter("b",new Cell(new Point(1,2)));

        assert(_letter.getCell().equal(new Cell(new Point(1,2))));
    }
}
