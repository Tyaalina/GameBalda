import MyEvents.AchievementsListEvent;
import MyEvents.AchievementsListListener;
import MyGame.AchievementsList;
import MyGame.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AchievementsListTest {
    private enum EVENT {WORD_NOT_IN_DICTIONARY, WORD_ALREADY_USED, WORD_ADD}

    private List<EVENT> events = new ArrayList<>();
    private List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements AchievementsListListener {

        @Override
        public void wordNotInDictionary(AchievementsListEvent e) {
            events.add(EVENT.WORD_NOT_IN_DICTIONARY);
        }

        @Override
        public void wordAlreadyUsed(AchievementsListEvent e) {
            events.add(EVENT.WORD_ALREADY_USED);
        }

        @Override
        public void wordAddInAchievementsList(AchievementsListEvent e) {
            events.add(EVENT.WORD_ADD);
        }
    }

    private AchievementsList achievementsList = new AchievementsList();
    private Player pFirst = new Player("1");
    private Player pZero = new Player("0");

    @Test
    public void test_achievementsList_getRating(){
        achievementsList.addAchievementsListListener(new EventsListener());

        achievementsList.getRating().put(pFirst,1);

        HashMap<Player, Integer> _rating = new HashMap<>();
        _rating.put(pFirst,1);

        assert(!achievementsList.getRating().isEmpty());
        assertEquals(achievementsList.getRating(),_rating);
        assert(events.isEmpty());
    }

    @Test
    public void test_achievementsList_updateGameRating(){
        achievementsList.addAchievementsListListener(new EventsListener());

        achievementsList.getRating().put(pZero,2);
        achievementsList.updateAchievementsList("архитектор",pZero);

        HashMap<String, Player> _usedWord = new HashMap<>();
        _usedWord.put("архитектор",pZero);

        assertEquals(achievementsList.getRating().get(pZero),12);
        assertEquals(achievementsList.getUsedWord(),_usedWord);
        assert(events.isEmpty());
    }

    @Test
    public void test_achievementsList_addWordInAchievementsList(){
        achievementsList.addAchievementsListListener(new EventsListener());
        
        achievementsList.getRating().put(pZero,2);
        achievementsList.addWordInAchievementsList("архитектор",pZero);

        HashMap<String, Player> _usedWord = new HashMap<>();
        _usedWord.put("архитектор",pZero);

        expectedEvents.add(EVENT.WORD_ADD);

        assertEquals(achievementsList.getRating().get(pZero),12);
        assertEquals(achievementsList.getUsedWord(),_usedWord);
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_achievementsList_isUsedWord_wordsIsAlreadyUsed(){
        achievementsList.addAchievementsListListener(new EventsListener());

        achievementsList.getUsedWord().put("архитектор",pFirst);

        assert(achievementsList.isUsedWord("архитектор"));
        assert(events.isEmpty());
    }

    @Test
    public void test_achievementsList_isUsedWord_wordsIsNotUsed(){
        achievementsList.addAchievementsListListener(new EventsListener());

        achievementsList.getUsedWord().put("архетектар",pFirst);

        assert(!achievementsList.isUsedWord("архитектор"));
        assert(events.isEmpty());
    }

    @Test
    public void test_achievementsList_addWordInAchievementsList_wordsIsAlreadyUsed(){
        achievementsList.addAchievementsListListener(new EventsListener());

        achievementsList.getRating().put(pZero,2);
        achievementsList.getUsedWord().put("архитектор",pFirst);
        achievementsList.addWordInAchievementsList("архитектор",pZero);

        expectedEvents.add(EVENT.WORD_ALREADY_USED);

        assertEquals(achievementsList.getRating().get(pZero),2);
        assertEquals(achievementsList.getUsedWord().size(),1);
        assertEquals(events, expectedEvents);
    }

    @Test
    public void test_achievementsList_addWordInAchievementsList_worsIsNotInDictionary(){
        achievementsList.addAchievementsListListener(new EventsListener());

        achievementsList.getRating().put(pZero,2);
        achievementsList.addWordInAchievementsList("архетектар",pZero);

        expectedEvents.add(EVENT.WORD_NOT_IN_DICTIONARY);

        assertEquals(achievementsList.getRating().get(pZero),2);
        assertEquals(achievementsList.getUsedWord().size(),0);
        assertEquals(events, expectedEvents);
    }

}
