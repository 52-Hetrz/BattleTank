package tank;

import map.GameMapUI;
import map.MainMap;

import javax.swing.*;

public class EnemyOriginalTank extends EnemyTank{
    public EnemyOriginalTank(GameMapUI gameMapUI, JPanel map, MainMap mainMap,PlayerTank trackedTank,int AI,int x, int y) {
        super(gameMapUI, map, mainMap,trackedTank,AI,x, y);
    }
}
