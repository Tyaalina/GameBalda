package MyEvents;

import java.util.EventListener;

public  interface AchievementsListListener extends EventListener {
    void wordNotInDictionary(AchievementsListEvent e);

    void wordAlreadyUsed(AchievementsListEvent e);

    void wordAddInAchievementsList(AchievementsListEvent e);
}
