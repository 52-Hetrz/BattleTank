package obstacle;

import map.MainMap;

import javax.swing.*;

public class BrickWall extends Obstacle {

    private int life;
    public BrickWall(JPanel map, MainMap mainMap, int x, int y){
        super(x,y,mainMap);
        setMap(map);
        setLife(2);
        setContent(4);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

}
