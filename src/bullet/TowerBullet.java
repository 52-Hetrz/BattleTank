package bullet;

import bullet.Bullet;
import map.MainMap;
import tank.EnemyTank;

import javax.swing.*;

public class TowerBullet extends Bullet {


    private int targetX;
    private int targetY;
    private double k;
    private int towerBulletDirection;
    private double b;

    /**
     * 有参构造函数
     *
     * @param map     从属的地图对象
     * @param mainMap 接受子弹对象的JPanel容器
     * @param x       子弹产生的x坐标
     * @param y       子弹产生的y坐标
     * @param targetX 子弹目标坦克的x坐标
     * @param targetY 子弹目标坦克的y坐标
     */
    public TowerBullet(JPanel map, MainMap mainMap, int x, int y,int targetX,int targetY) {
        super(map, mainMap, x, y);
        setTargetX(targetX);
        setTargetY(targetY);
        setKB(x,targetX,y,targetY);
        //new Thread(this).start();
    }

    void move() throws InterruptedException {
        while(check()&&checkPlayerTank()&&checkEnemyTank()) {
            if (towerBulletDirection < 0) {
                setX(getX() - 1);
                setY((int)(this.k * getX() + this.b));
                setBounds(getX(), getY(), 5, 5);
            } else if (towerBulletDirection > 0) {
                setX(getX() + 1);
                setY((int)(this.k * getX() + this.b));
                setBounds(getX(), getY(), 5, 5);
            }
            Thread.sleep(getSpeed());
        }
    }



    public Boolean checkEnemyTank(){
        for(EnemyTank em:getMainMap().getEnemyTanks()){
            if(getX()<(em.getX()+25)&&getX()>em.getX()&&getY()>em.getY()&&getY()<(em.getY()+25)) {
                em.setLife(em.getLife()-1);
                if(em.getLife()==0) {
                    getMainMap().removeEnemyTank(em);
                }
                map.remove(this);
                map.repaint();
                return false;
            }
        }
        return true;
    }


    public int getTargetX() {
        return targetX;
    }

    private void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    private void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    private void setKB(int x1, int x2, int y1, int y2){
        this.k=(double) (y1-y2)/(x1-x2);
        this.b=y1-this.k*x1;
    }

    public int getTowerBulletDirection() {
        return towerBulletDirection;
    }

    public void setTowerBulletDirection(int towerBulletDirection) {
        this.towerBulletDirection = towerBulletDirection;
    }

    double getK(){return this.k;}

}
