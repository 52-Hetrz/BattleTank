package bullet;

import others.Direction;
import tank.PlayerTank;
import map.MainMap;
import tank.EnemyTank;

import javax.swing.*;

public class PlayerBullet extends Bullet {

    Boolean canAttackTheOtherOne;

    /**
     * 有参构造函数
     * @param map 从属的地图对象
     * @param mainMap 坦克从属的mainMap
     * @param tank 发射子弹的坦克
     * @param d 发射方向
     * @param x 子弹产生的x坐标
     * @param y 子弹产生的y坐标
     */
    public PlayerBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,int speed,Boolean canAttackTheOtherOne) {
        super(map, mainMap, x, y);
        setSpeed(speed);
        setDirection(d);
        setPlayerTank(tank);
        setCanAttackTheOtherOne(canAttackTheOtherOne);
        setSpeed(7);
    }

    public PlayerBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,Boolean canAttackTheOtherOne) {
        super(map, mainMap, x, y);
        setSpeed(7);
        setDirection(d);
        setPlayerTank(tank);
        setCanAttackTheOtherOne(canAttackTheOtherOne);
    }

    /**
     * 检测击中敌方坦克 并产生影响
     * @return 击中返回false
     */
    public Boolean checkEnemyTank(){
        int flag=0;
        EnemyTank enemyTank=null;
        for(EnemyTank em:getMainMap().getEnemyTanks()){
            if(getX()<(em.getX()+25)&&getX()>em.getX()&&getY()>em.getY()&&getY()<(em.getY()+25)) {
                flag=1;
                em.setLife(em.getLife()-1);
                if(em.getLife()==0) {
                    enemyTank=em;
                    int score=getPlayerTank().getScore();
                    getPlayerTank().setScore(score+1);
                    getMainMap().getGameMapUI().updateScoreLabel(getPlayerTank(),getPlayerTank().getScore());

                }
                map.remove(this);
                break;
            }
        }
        if(enemyTank!=null){
           enemyTank.dropProp();
           getMainMap().removeEnemyTank(enemyTank);
           return false;
        }
        else return flag != 1;
    }

    public Boolean checkBossTwo(){
        if(getDirection()==Direction.RIGHT){
            if(this.getMainMap().getBossTwo()!=null){
                if((getX()+5)<=this.getMainMap().getBossTwo().getX()&&(getX()+7)>this.getMainMap().getBossTwo().getX()&&
                        (getY()+5)>=this.getMainMap().getBossTwo().getY()&&(getY()+5)<(this.getMainMap().getBossTwo().getY()+55)){
                    //击中时生命减一
                    this.getMainMap().getBossTwo().setLife(this.getMainMap().getBossTwo().getLife()-1);
                    if(this.getMainMap().getBossTwo().getLife()==0){
                        this.getMainMap().removeBossTwo(this.getMainMap().getBossTwo());
                        map.repaint();
                    }
                    map.remove(this);
                    System.out.println("r");
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.LEFT){
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()+75)&&(getX()-2)<(this.getMainMap().getBossTwo().getX()+75)&&
                        (getY()+5)>=this.getMainMap().getBossTwo().getY()&&(getY()+5)<(this.getMainMap().getBossTwo().getY()+55)){
                    this.getMainMap().getBossTwo().setLife(this.getMainMap().getBossTwo().getLife()-1);
                    if(this.getMainMap().getBossTwo().getLife()==0){
                        this.getMainMap().removeBossTwo(this.getMainMap().getBossTwo());
                        map.repaint();
                    }
                    map.remove(this);
                    System.out.println("l");
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.UP){
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()-5)&&getX()<(this.getMainMap().getBossTwo().getX()+75)&&
                        getY()>=(this.getMainMap().getBossTwo().getY()+50)&&(getY()-2)<(this.getMainMap().getBossTwo().getY()+50)){
                    this.getMainMap().getBossTwo().setLife(this.getMainMap().getBossTwo().getLife()-1);
                    if(this.getMainMap().getBossTwo().getLife()==0){
                        this.getMainMap().removeBossTwo(this.getMainMap().getBossTwo());
                        map.repaint();
                    }
                    map.remove(this);
                    System.out.println("u");
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN){
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()-5)&&getX()<(this.getMainMap().getBossTwo().getX()+75)&&
                        (getY()+5)<=this.getMainMap().getBossTwo().getY()&&(getY()+7)>this.getMainMap().getBossTwo().getY()){
                    this.getMainMap().getBossTwo().setLife(this.getMainMap().getBossTwo().getLife()-1);
                    if(this.getMainMap().getBossTwo().getLife()==0){
                        this.getMainMap().removeBossTwo(this.getMainMap().getBossTwo());
                        map.repaint();
                    }
                    map.remove(this);
                    System.out.println("d");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检测是否攻击到了另一个坦克，如果击中了则产生影响
     * @return 返回true 代表没有击中
     */
    private Boolean checkTheOtherPlayerTank(){
        int flag=0;
        PlayerTank playerTank=null;
        for(PlayerTank pt:getMainMap().getPlayerTanks()) {
            if (!pt.toString().equals(getPlayerTank().toString())) {
                if (getX() < (pt.getX() + 25) && getX() > pt.getX() && getY() > pt.getY() && getY() < (pt.getY() + 25)) {
                    flag=1;
                    pt.setLife(pt.getLife() - 1);
                    playerTank=pt;
                    //pt.getGameMapUI().reBornPlayerTank(pt);
                    map.remove(this);
                    map.repaint();
                }
            }
        }
        if(playerTank!=null)
        {
            playerTank.getGameMapUI().reBornPlayerTank(playerTank);
        }
        return flag != 1;
    }

    public Boolean checkEnemyTank1() {
        if(getDirection()==Direction.RIGHT) {
            for(EnemyTank em:getMainMap().getEnemyTanks()){
                if((getX()+5)>em.getX()&&getX()<em.getX()&&(getY()+5)>em.getY()&&getY()<(em.getY()+25)) {
                    em.setLife(em.getLife()-1);
                    if(em.getLife()==0) {
                        getMainMap().removeEnemyTank(em);
                        int score= getPlayerTank().getScore();
                        getPlayerTank().setScore(score+1);
                        //getFatherTank().getGameMapUI().updateScore(getFatherTank().getScore());
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }

        }
        else if(getDirection()==Direction.LEFT) {
            for(EnemyTank em:getMainMap().getEnemyTanks()){
                if((getX()-5)<(em.getX()+25)&&(getX()+5)>(em.getX()+25)&&(getY()+5)>em.getY()&&getY()<(em.getY()+25)) {
                    em.setLife(em.getLife()-1);
                    if(em.getLife()==0) {
                        getMainMap().removeEnemyTank(em);
                        int score= getPlayerTank().getScore();
                        getPlayerTank().setScore(score+1);
                        //getFatherTank().getGameMapUI().updateScore(getFatherTank().getScore());
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.UP) {
            for(EnemyTank em:getMainMap().getEnemyTanks()) {
                if(getY()<(em.getY()+25)&&(getY()+5)>(em.getY()+25)&&(getX()+5)>em.getX()&&getX()<(em.getX()+25)) {
                    em.setLife(em.getLife()-1);
                    if(em.getLife()==0) {
                        getMainMap().removeEnemyTank(em);
                        int score= getPlayerTank().getScore();
                        getPlayerTank().setScore(score+1);
                        //getFatherTank().getGameMapUI().updateScore(getFatherTank().getScore());
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN) {
            EnemyTank enemyTank;
            for(EnemyTank em:getMainMap().getEnemyTanks()) {
                if((getY()+10)>em.getY()&&getY()<em.getY()&&(getX()+5)>em.getX()&&getX()<(em.getX()+25)) {
                    em.setLife(em.getLife()-1);
                    if(em.getLife()==0) {
                        enemyTank=em;
                        int score= getPlayerTank().getScore();
                        getPlayerTank().setScore(score+1);
                       // getFatherTank().getGameMapUI().updateScore(getFatherTank().getScore());
                    }
                    map.remove(this);
                    map.repaint();
                    return false;
                }
            }
        }
        return true;
    }

    void move() throws InterruptedException {                       //根据传入的坦克的炮筒朝向完成子弹的移动
        if(!getCanAttackTheOtherOne()) {
            if (getDirection() == Direction.UP) {
                while (checkEnemyTank() && check()&&checkBossTwo()) {
                    setY(getY() - 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            } else if (getDirection() == Direction.DOWN) {
                while (checkEnemyTank() && check()&&checkBossTwo()) {
                    setY(getY() + 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            } else if (getDirection() == Direction.RIGHT) {
                while (checkEnemyTank() && check()&&checkBossTwo()) {
                    setX(getX() + 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            } else if (getDirection() == Direction.LEFT) {
                while (checkEnemyTank() && check()&&checkBossTwo()) {
                    setX(getX() - 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            }
        }
        else {
            if (getDirection() == Direction.UP) {
                while (checkEnemyTank() && check()&&checkTheOtherPlayerTank()&&checkBossTwo()){
                    setY(getY() - 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            } else if (getDirection() == Direction.DOWN) {
                while (checkEnemyTank() && check()&&checkTheOtherPlayerTank()&&checkBossTwo()) {
                    setY(getY() + 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            } else if (getDirection() == Direction.RIGHT) {
                while (checkEnemyTank() && check()&&checkTheOtherPlayerTank()&&checkBossTwo()) {
                    setX(getX() + 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            } else if (getDirection() == Direction.LEFT) {
                while (checkEnemyTank() && check()&&checkTheOtherPlayerTank()&&checkBossTwo()) {
                    setX(getX() - 2);
                    setBounds(getX(), getY(), 5, 5);
                    Thread.sleep(getSpeed());
                }
            }
        }
    }

    public Boolean getCanAttackTheOtherOne() {
        return canAttackTheOtherOne;
    }

    public void setCanAttackTheOtherOne(Boolean canAttackTheOtherOne) {
        this.canAttackTheOtherOne = canAttackTheOtherOne;
    }
}
