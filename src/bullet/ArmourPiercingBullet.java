package bullet;


import map.MainMap;
import obstacle.BrickWall;
import obstacle.IronWall;
import obstacle.Obstacle;
import others.Direction;
import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;

public class ArmourPiercingBullet extends PlayerBullet {

    /**
     * 穿甲弹 可以打铁块
     * 有参构造函数
     *
     * @param map     从属的地图对象
     * @param mainMap 坦克从属的mainMap
     * @param tank    发射子弹的坦克
     * @param d       发射方向
     * @param x       子弹产生的x坐标
     * @param y       子弹产生的y坐标
     */
    public ArmourPiercingBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,int speed,Boolean canAttackTheOtherTank) {
        super(map, mainMap, tank, d, x, y,speed,canAttackTheOtherTank);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\ArmourPiercingBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
    }

    public ArmourPiercingBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,Boolean canAttackTheOtherTank) {
        super(map, mainMap, tank, d, x, y,canAttackTheOtherTank);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\ArmourPiercingBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
        setSpeed(8);
    }


    @Override
    public Boolean check() {
        int flag=0;
        Obstacle o=null;
        for (Obstacle ob : getMainMap().getObstacles()) {
            if (getX() > ob.getX() && getX() < (ob.getX() + 25) && getY() > ob.getY() && getY() < (ob.getY() + 25)) {
                map.remove(this);
                flag=1;
                //map.repaint();
                if (ob.getContent() == 4||ob.getContent()==5) {
                    o=ob;
                    break;
                }

            }
        }
        if(o!=null){
            hitObstacle( o);
        }
        return flag != 1;
    }
}
