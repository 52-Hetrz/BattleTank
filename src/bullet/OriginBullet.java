package bullet;

import map.MainMap;
import others.Direction;
import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;

public class OriginBullet extends PlayerBullet {
    /**
     * 有参构造函数
     *
     * @param map     从属的地图对象
     * @param mainMap 坦克从属的mainMap
     * @param tank    发射子弹的坦克
     * @param d       发射方向
     * @param x       子弹产生的x坐标
     * @param y       子弹产生的y坐标
     */
    public OriginBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,int speed,Boolean canShootTheOtherTank) {
        super(map, mainMap, tank, d, x, y,speed,canShootTheOtherTank);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\originPlayerBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
    }

    public OriginBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,Boolean canShootTheOtherTank) {
        super(map, mainMap, tank, d, x, y,canShootTheOtherTank);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\originPlayerBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
    }
}
