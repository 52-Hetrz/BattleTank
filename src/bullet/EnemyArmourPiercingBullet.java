package bullet;

import map.MainMap;
import obstacle.Obstacle;
import others.Direction;
import tank.EnemyTank;

import javax.swing.*;

public class EnemyArmourPiercingBullet extends EnemyBullet{
    /**
     * 有参构造函数
     *
     * @param map       从属的地图对象
     * @param mainMap   存储敌方坦克的MainMap对象
     * @param enemyTank 发射子弹的敌方坦克对象
     * @param d         发射方向
     * @param x         子弹产生的x坐标
     * @param y         子弹产生的y坐标
     */
    public EnemyArmourPiercingBullet(JPanel map, MainMap mainMap, EnemyTank enemyTank, Direction d, int x, int y) {
        super(map, mainMap, enemyTank, d, x, y);
    }

    public Boolean check() {
        int flag=0;
        Obstacle o=null;
        for (Obstacle ob : getMainMap().getObstacles()) {
            if (getX() > ob.getX() && getX() < (ob.getX() + 25) && getY() > ob.getY() && getY() < (ob.getY() + 25)) {
                flag=1;
                //map.repaint();
                if (ob.getContent() == 4||ob.getContent()==5) {
                    o=ob;
                    break;
                }
            }
        }
        if(o!=null){
            hitObstacle(o);
        }
        if(flag==1){
            map.remove(this);
            repaint();
        }
        return flag != 1;
    }

}
