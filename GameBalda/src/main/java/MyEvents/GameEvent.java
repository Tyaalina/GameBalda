package MyEvents;

import MyGame.Player;

import java.util.EventObject;

public class GameEvent extends EventObject {
    Player _player;

    public void setPlayer(Player p) {
        _player = p;
    }

    public Player getPlayer(){
        return _player;
    }

    public GameEvent(Object source) {
        super(source);
    }
}
