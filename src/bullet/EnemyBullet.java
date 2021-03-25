package bullet;

import bullet.Bullet;
import map.MainMap;
import others.Direction;
import tank.BossTwo;
import tank.EnemyTank;
import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;

public class EnemyBullet extends Bullet {


    /**
     * 有参构造函数
     *
     * @param map     从属的地图对象
     * @param mainMap
     * @param d       发射方向
     * @param x       子弹产生的x坐标
     * @param y       子弹产生的y坐标
     */
    public EnemyBullet(JPanel map, MainMap mainMap,EnemyTank enemyTank, Direction d, int x, int y) {
        super(map, mainMap, x, y);
        setDirection(d);
        setEnemyTank(enemyTank);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\enemyBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
    }

    public EnemyBullet(JPanel map, MainMap mainMap,BossTwo bossTwo, Direction d, int x, int y) {
        super(map, mainMap, x, y);
        setDirection(d);
        setBossTwo(bossTwo);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\enemyBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
    }

    @Override
    void move()  throws InterruptedException {                       //根据传入的坦克的炮筒朝向完成子弹的移动
        if (getDirection() == Direction.UP) {
            while(checkPlayerTank()&&check()&&checkBossTwo())
            {
                setY(getY()-2);
                setBounds(getX(),getY(),5,5);
                Thread.sleep(getSpeed());
            }
        }
        else if (getDirection() == Direction.DOWN) {
            while(checkPlayerTank()&&check()&&checkBossTwo())
            {
                setY(getY()+2);
                setBounds(getX(),getY(),5,5);
                Thread.sleep(getSpeed());
            }
        }
        else if (getDirection() == Direction.RIGHT) {
            while(checkPlayerTank()&&check()&&checkBossTwo())
            {
                setX(getX()+2);
                setBounds(getX(),getY(),5,5);
                Thread.sleep(getSpeed());
            }
        }
        else if (getDirection() == Direction.LEFT) {
            while(checkPlayerTank()&&check()&&checkBossTwo())
            {
                setX(getX()-2);
                setBounds(getX(),getY(),5,5);
                Thread.sleep(getSpeed());
            }
        }
    }

    public Boolean checkBossTwo(){
        if(getDirection()==Direction.RIGHT){
            if(this.getMainMap().getBossTwo()!=null){
                if((getX()+5)<=this.getMainMap().getBossTwo().getX()&&(getX()+7)>this.getMainMap().getBossTwo().getX()&&
                        (getY()+5)>=this.getMainMap().getBossTwo().getY()&&(getY()+5)<(this.getMainMap().getBossTwo().getY()+55)){
                    map.remove(this);
                    return false;
                }
                System.out.println("r");
            }
        }
        else if(getDirection()==Direction.LEFT){
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()+75)&&(getX()-2)<(this.getMainMap().getBossTwo().getX()+75)&&
                        (getY()+5)>=this.getMainMap().getBossTwo().getY()&&(getY()+5)<(this.getMainMap().getBossTwo().getY()+55)){
                    map.remove(this);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.UP){
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()-5)&&getX()<(this.getMainMap().getBossTwo().getX()+75)&&
                        getY()>=(this.getMainMap().getBossTwo().getY()+50)&&(getY()-2)<(this.getMainMap().getBossTwo().getY()+50)){
                    map.remove(this);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN){
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()-5)&&getX()<(this.getMainMap().getBossTwo().getX()+75)&&
                        (getY()+5)<=this.getMainMap().getBossTwo().getY()&&(getY()+7)>(this.getMainMap().getBossTwo().getY()+50)){
                    map.remove(this);
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean checkPlayerTank1() {
        if (getDirection() == Direction.RIGHT) {
            for (PlayerTank em : getMainMap().getPlayerTanks()) {
                if ((getX() + 5) > em.getX() && getX() < em.getX() && (getY() + 5) > em.getY() && getY() < (em.getY() + 25)) {
                    em.setLife(em.getLife() - 1);
                    if (em.getLife() == 0) {
                        getMainMap().removePlayerTank(em);
                        getMap().remove(em);
                        getMap().removeKeyListener(em);
                    }
                    map.remove(this);
                    //map.repaint();
                    return false;
                }
            }

        } else if (getDirection() == Direction.LEFT) {
            for (PlayerTank em : getMainMap().getPlayerTanks()) {
                if ((getX() - 5) < (em.getX() + 25) && (getX() + 5) > (em.getX() + 25) && (getY() + 5) > em.getY() && getY() < (em.getY() + 25)) {
                    em.setLife(em.getLife() - 1);
                    if (em.getLife() == 0) {
                        getMainMap().getPlayerTanks().remove(em);
                        getMap().remove(em);
                        getMap().removeKeyListener(em);
                    }
                    map.remove(this);
                    //map.repaint();
                    return false;
                }
            }
        } else if (getDirection() == Direction.UP) {
            for (PlayerTank em : getMainMap().getPlayerTanks()) {
                if (getY() < (em.getY() + 25) && (getY() + 5) > (em.getY() + 25) && (getX() + 5) > em.getX() && getX() < (em.getX() + 25)) {
                    em.setLife(em.getLife() - 1);
                    if (em.getLife() == 0) {
                        getMainMap().getPlayerTanks().remove(em);
                        getMap().remove(em);
                        getMap().removeKeyListener(em);
                    }
                    map.remove(this);
                    //map.repaint();
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN) {
            for (PlayerTank em : getMainMap().getPlayerTanks()) {
                if((getY()+10)>em.getY()&&getY()<em.getY()&&(getX()+5)>em.getX()&&getX()<(em.getX()+25)) {
                    em.setLife(em.getLife()-1);
                    if(em.getLife()==0) {
                        getMainMap().getPlayerTanks().remove(em);
                        getMap().remove(em);
                        getMap().removeKeyListener(em);
                    }
                    map.remove(this);
                    //map.repaint();
                    return false;
                }
            }
        }
        return true;
    }
}
