package obstacle;

import com.sun.tools.javac.Main;
import map.MainMap;

import javax.swing.*;

public class Obstacle extends JLabel {
    int x;
    int y;
    int content;
    JPanel map;
    int life=2;
    MainMap mainMap;

    public Obstacle(int x, int y, MainMap mainMap){
        setX(x);
        setY(y);
        setMainMap(mainMap);
    }
    public Obstacle(int x, int y, MainMap mainMap,int content){
        setX(x);
        setY(y);
        setMainMap(mainMap);
        setContent(content);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public JPanel getMap() {
        return map;
    }

    public void setMap(JPanel map) {
        this.map = map;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public MainMap getMainMap() {
        return mainMap;
    }

    public void setMainMap(MainMap mainMap) {
        this.mainMap = mainMap;
    }
}
