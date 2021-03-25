package obstacle;

import map.MainMap;

import javax.swing.*;

public class IronWall extends Obstacle{

    private int life;
    public IronWall(JPanel map, MainMap mainMap,int x, int y) {
        super(x, y,mainMap);
        setMainMap(mainMap);
        setMap(map);
        setContent(5);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

}
