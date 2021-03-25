package bullet;

import map.GameMapUI;
import obstacle.BrickWall;
import obstacle.IronWall;
import obstacle.Obstacle;
import map.MainMap;
import others.Check;
import others.Direction;
import tank.BossTwo;
import tank.EnemyTank;
import tank.PlayerTank;

import javax.swing.*;

public abstract class Bullet extends JLabel implements Runnable, Check {

    /**
     * 子弹类，产生坦克发射的子弹
     *
     * @param map 子弹从属的地图对象
     * @param direction 子弹发射方向
     * @param speed 子弹发射速度
     * @param x 子弹的x坐标
     * @param y 子弹的y坐标
     */

    JPanel map;
    private Direction direction;
    private int speed;
    private int x;
    private int y;
    private MainMap mainMap;
    private PlayerTank playerTank;
    private EnemyTank enemyTank;
    private BossTwo bossTwo;
    int damage=1;


    /**
     * 有参构造函数
     * @param map 从属的地图对象
     * @param x 子弹产生的x坐标
     * @param y 子弹产生的y坐标
     */
    public Bullet(JPanel map, MainMap mainMap, int x, int y) {
        setX(x);
        setY(y);
        setSpeed(10);
        setMap(map);
        setMainMap(mainMap);
    }


    /**
     * 实现子弹的移动
     * @throws InterruptedException 线程异常
     */

    void move() throws InterruptedException {                       //根据传入的坦克的炮筒朝向完成子弹的移动
    }


    @Override
    public void run() {
        try {
            move ();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 击中砖块产生的影响
     * @param obstacle 受攻击的障碍物对象
     */
    void hitObstacle(Obstacle obstacle){
        obstacle.setLife(obstacle.getLife()-1);
        if(obstacle.getLife()<=0){
            obstacle.getMainMap().removeObstacle(obstacle);
        }
    }

    /**
     * 新的check函数
     * @return 碰到障碍物返回false 否则返回true
     */
    @Override
    public Boolean check() {
        Obstacle o = null;
        int flag=0;
        for (Obstacle ob : getMainMap().getObstacles()) {
            if (getX() > ob.getX() && getX() < (ob.getX() + 25) && getY() > ob.getY() && getY() < (ob.getY() + 25)) {
                flag=1;
                map.remove(this);
                map.repaint();
                if (ob.getContent() == 4) {
                    o=ob;
                    break;
                }
                else if(ob.getContent()==6){
                    hitHome();
                }
            }
        }
        if(o!=null){
            hitObstacle(o);
        }
        return flag != 1;
    }



    void hitHome(){
        mainMap.getGameMapUI().showGameOverUI();
    }

    /**
     * 检测击中玩家坦克 并产生影响
     * @return 如果击中返回true
     */
    public Boolean checkPlayerTank() {
        for (PlayerTank pt : getMainMap().getPlayerTanks()) {
            if (getX()> pt.getX() && getX() < (pt.getX()+25) && getY()> pt.getY() && getY() < (pt.getY() + 25)) {
                pt.setLife(pt.getLife() - 1);
                pt.getGameMapUI().reBornPlayerTank(pt);
                map.remove(this);
                map.repaint();
                return false;
            }
        }
        return true;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public JPanel getMap() {
        return map;
    }

    public void setMap(JPanel  map) {
        this.map = map;
    }

    public MainMap getMainMap() {
        return mainMap;
    }

    public void setMainMap(MainMap mainMap) {
        this.mainMap = mainMap;
    }

    public PlayerTank getPlayerTank() {
        return playerTank;
    }

    public void setPlayerTank(PlayerTank playerTank) {
        this.playerTank = playerTank;
    }

    public EnemyTank getEnemyTank() {
        return enemyTank;
    }

    public void setEnemyTank(EnemyTank enemyTank) {
        this.enemyTank = enemyTank;
    }

    public BossTwo getBossTwo() {
        return bossTwo;
    }

    public void setBossTwo(BossTwo bossTwo) {
        this.bossTwo = bossTwo;
    }

    public Boolean check1() {
        if(getDirection()== Direction.RIGHT) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if((getX()+5)>ob.getX()&&getX()<ob.getX()&&(getY()+5)>ob.getY()&&getY()<(ob.getY()+25)) {
                    if(ob.getContent()==4){
                        hitObstacle((BrickWall) ob);
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }

        }
        else if(getDirection()== Direction.LEFT) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if((getX()-5)<(ob.getX()+25)&&(getX()+5)>(ob.getX()+25)&&(getY()+5)>ob.getY()&&getY()<(ob.getY()+25)) {
                    if(ob.getContent()==4){
                        hitObstacle((BrickWall) ob);
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }
        }
        else if(getDirection()== Direction.UP) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if(getY()<(ob.getY()+25)&&(getY()+5)>(ob.getY()+25)&&(getX()+5)>ob.getX()&&getX()<(ob.getX()+25)) {
                    if(ob.getContent()==4){
                        hitObstacle((BrickWall) ob);
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }
        }
        else if(getDirection()== Direction.DOWN) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if((getY()+10)>ob.getY()&&getY()<ob.getY()&&(getX()+5)>ob.getX()&&getX()<(ob.getX()+25)) {
                    if(ob.getContent()==4){
                        hitObstacle((BrickWall) ob);
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }
        }
        else if(getY()>=600||getX()>=600){
            map.remove(this);
            map.repaint();
            return false;
        }
        return true;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
