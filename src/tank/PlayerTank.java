package tank;

import bullet.ArmourPiercingBullet;
import bullet.FrozenBullet;
import bullet.OriginBullet;
import music.Music;
import prop.Prop;
import obstacle.Obstacle;
import others.Direction;
import others.MoveAction;
import others.TimeController;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public abstract class PlayerTank extends Tank implements KeyListener {

    Boolean canAttackTheOtherOne;
    private int score;
    //GameMapUI gameMapUI;
    TimeController timeController =new TimeController(this,1000,1);
    private Boolean onIce=false;
    private int level=1;
    private Boolean isQuickShoot;
    private int whichCurrentBullet=0;
    private int[] cartridgeClip=new int[3];

    PlayerTank()  {
        setScore(0);
        setLife(2);
        setLevel(1);
        setCanShoot(true);
        setQuickShoot(false);
        initCartridge();
    }

    /**
     * 发射子弹
     * @param direction 子弹发射的方向
     * @param x 子弹的初始x坐标
     * @param y 子弹的初始y坐标
     */
    public void shootBullet(Direction direction, int x, int y){
        new Thread(new Music(".\\src\\music\\shoot.wav")).start();

        if(getWhichCurrentBullet()==0&&getWhichInCartridge(0)>0){
            OriginBullet bullet=new OriginBullet(map,mainMap,this,direction,x,y,10,getCanAttackTheOtherOne());
            this.cartridgeClip[0]--;
            System.out.println(getWhichInCartridge(0));
        }

        else if(getWhichCurrentBullet()==1&&getWhichInCartridge(1)>0){
            ArmourPiercingBullet bullet = new ArmourPiercingBullet(map, mainMap, this, direction, x, y,10,getCanAttackTheOtherOne());
            this.cartridgeClip[1]--;
        }

        else if(getWhichCurrentBullet()==2&&getWhichInCartridge(2)>0){
            FrozenBullet bullet = new FrozenBullet(map, mainMap, this, direction, x, y,10,getCanAttackTheOtherOne());
            this.cartridgeClip[2]--;
        }
        /*if(getLevel()==1){
            OriginBullet bullet=new OriginBullet(map,mainMap,this,direction,x,y,getCanAttackTheOtherOne());
        }
        else if(getLevel()==2){
            OriginBullet bullet1=new OriginBullet(map,mainMap,this,direction,x,y,7,getCanAttackTheOtherOne());
            if(!getQuickShoot()) {
                timeController = new TimeController(this, 500, 1);
                setQuickShoot(true);
            }
        }
        else if(getLevel()==3){
            OriginBullet bullet=new OriginBullet(map,mainMap,this,direction,x,y,7,getCanAttackTheOtherOne());
            if(getSpeed()==50){
                setSpeed(35);
            }
        }
        else if(getLevel()==4) {
            ArmourPiercingBullet bullet = new ArmourPiercingBullet(map, mainMap, this, direction, x, y,getCanAttackTheOtherOne());
        }*/
    }

    /**
     * 实现坦克的移动
     */
    public void move() {
        while(getMoveAction()!= MoveAction.STOP) {
            checkProp();
            if (getMoveAction() == MoveAction.UP) {
                if(check()&&checkEnemyTank()) {
                    setY(getY() - 5);
                    setBounds(getX(), getY(), 25, 25);
                }
            } else if (getMoveAction()== MoveAction.DOWN) {
                if(check()&&checkEnemyTank()) {
                    setY(getY() + 5);
                    setBounds(getX(), getY(), 25, 25);
                }
            } else if (getMoveAction()== MoveAction.RIGHT) {
                if(check()&&checkEnemyTank()) {
                    setX(getX() + 5);
                    setBounds(getX(), getY(), 25, 25);
                }
            } else if (getMoveAction()== MoveAction.LEFT) {
                if(check()&&checkEnemyTank()) {
                    setX(getX() - 5);
                    setBounds(getX(), getY(), 25, 25);
                }
            }
            try {
                Thread.sleep(getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对地图元素进行检验，是否碰到障碍物，并产生影响
     * @return 返回值为true 可以继续移动，否则应该静止
     */
    public Boolean check(){
        if(getMainMap().getMapList(getX()/25,getY()/25)==8){
            setSpeed(getSpeed()+50);
            setOnIce(true);
        }
        else if(getOnIce()){
            setSpeed(50);
            setOnIce(false);
        }
        if(getDirection()==Direction.RIGHT) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if(ob.getX()>=(getX()+25)&&ob.getX()<(getX()+30)&&(ob.getY()+25)>getY()&&ob.getY()<(getY()+25)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
            if((getX()+25)>=600){
                setMoveAction(MoveAction.STOP);
                return false;
            }
            if(this.getMainMap().getBossTwo()!=null){
                if((getX()+25)<=this.getMainMap().getBossTwo().getX()&&(getX()+30)>this.getMainMap().getBossTwo().getX()&&
                        (getY()+25)>=this.getMainMap().getBossTwo().getY()&&(getY()+25)<(this.getMainMap().getBossTwo().getY()+75)){
                    setMoveAction(MoveAction.STOP);
                    System.out.println("r");
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.LEFT) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if((ob.getX()+25)<=getX()&&(ob.getX()+25)>(getX()-5)&&(ob.getY()+25)>getY()&&ob.getY()<(getY()+25)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
            if(getX()<=0){
                setMoveAction(MoveAction.STOP);
                return false;
            }
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()+75)&&(getX()-5)<(this.getMainMap().getBossTwo().getX()+75)&&
                        (getY()+25)>=this.getMainMap().getBossTwo().getY()&&(getY()+25)<(this.getMainMap().getBossTwo().getY()+75)){
                    setMoveAction(MoveAction.STOP);
                    System.out.println("l");
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.UP) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if((ob.getX()+25)>getX()&&ob.getX()<(getX()+25)&&(ob.getY()+25)<=getY()&&(ob.getY()+25)>(getY()-5)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
            if(getY()<=0){
                setMoveAction(MoveAction.STOP);
                return false;
            }
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()-25)&&getX()<(this.getMainMap().getBossTwo().getX()+75)&&
                        getY()>=(this.getMainMap().getBossTwo().getY()+50)&&(getY()-5)<(this.getMainMap().getBossTwo().getY()+50)){
                    setMoveAction(MoveAction.STOP);
                    System.out.println("u");
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN) {
            for(Obstacle ob:getMainMap().getObstacles()) {
                if((ob.getX()+25)>getX()&&ob.getX()<(getX()+25)&&ob.getY()>=(getY()+25)&&ob.getY()<(getY()+30)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
            if((getY()+25)>=600){
                setMoveAction(MoveAction.STOP);
                return false;
            }
            if(this.getMainMap().getBossTwo()!=null){
                if(getX()>=(this.getMainMap().getBossTwo().getX()-25)&&getX()<(this.getMainMap().getBossTwo().getX()+75)&&
                        (getY()+25)<=this.getMainMap().getBossTwo().getY()&&(getY()+30)>this.getMainMap().getBossTwo().getY()){
                    setMoveAction(MoveAction.STOP);
                    System.out.println("d");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检测是否碰撞到敌方坦克
     * @return 返回true 没撞到坦克可以继续前进，否则为撞到坦克，不能能继续沿当前方向移动
     */
    public Boolean checkEnemyTank(){
        if(getDirection()==Direction.RIGHT) {
            for(EnemyTank em:getMainMap().getEnemyTanks()) {
                if(em.getX()>=(getX()+25)&&em.getX()<(getX()+30)&&(em.getY()+25)>getY()&&em.getY()<(getY()+25)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.LEFT) {
            for(EnemyTank em:getMainMap().getEnemyTanks()) {
                if((em.getX()+25)<=getX()&&(em.getX()+25)>(getX()-5)&&(em.getY()+25)>getY()&&em.getY()<(getY()+25)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.UP) {
            for(EnemyTank em:getMainMap().getEnemyTanks()) {
                if((em.getX()+25)>getX()&&em.getX()<(getX()+25)&&(em.getY()+25)<=getY()&&(em.getY()+25)>(getY()-5)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN) {
            for(EnemyTank em:getMainMap().getEnemyTanks()) {
                if((em.getX()+25)>getX()&&em.getX()<(getX()+25)&&em.getY()>=(getY()+25)&&em.getY()<(getY()+30)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检测是否吃到道具
     * @return 返回true 吃到道具
     */
    public Boolean checkProp(){

        if(getDirection()==Direction.RIGHT) {
            for(Prop currentProp:getMainMap().getProps()) {
                if((currentProp.getX())>=(getX()+25)&&(currentProp.getX())<(getX()+30)&&
                        (currentProp.getY()+25)>getY()&&(currentProp.getY())<(getY()+25)) {
                    currentProp.propEffect(this);
                    currentProp.removeProp();
                    return true;
                }
            }
        }
        else if(getDirection()==Direction.LEFT) {
            for(Prop currentProp:getMainMap().getProps()) {
                if((currentProp.getX()+25)<=getX()&&(currentProp.getX()+25)>(getX()-5) &&
                        (currentProp.getY()+25)>getY()&&currentProp.getY()<(getY()+25)) {
                    currentProp.propEffect(this);
                    currentProp.removeProp();
                    return true;
                }
            }
        }
        else if(getDirection()==Direction.UP) {
            for(Prop currentProp:getMainMap().getProps()) {
                if((currentProp.getX()+25)>getX()&&(currentProp.getX())<(getX()+25)&&
                        (currentProp.getY()+25)<=getY()&&(currentProp.getY()+25)>(getY()-5)) {
                    currentProp.propEffect(this);
                    currentProp.removeProp();
                    return true;
                }
            }
        }
        else if(getDirection()==Direction.DOWN) {
            for(Prop currentProp:getMainMap().getProps()) {
                if((currentProp.getX()+25)>getX()&&(currentProp.getX())<(getX()+25)&&
                        (currentProp.getY())>=(getY()+25)&&(currentProp.getY())<(getY()+30)) {
                    setMoveAction(MoveAction.STOP);
                    currentProp.propEffect(this);
                    currentProp.removeProp();
                    return true;
                }
            }
        }
        return false;

    }

    public void initCartridge(){
        this.cartridgeClip[0]=150;
        this.cartridgeClip[1]=5;
        this.cartridgeClip[2]=5;
    }

    //查看某一种子弹
    public int getWhichInCartridge(int type){
        return this.cartridgeClip[type];
    }
    //将某一种子弹增加某一数量
    public void setWhichInCartridge(int type,int x){
        this.cartridgeClip[type]=+x;
    }

    public int getWhichCurrentBullet() {
        return whichCurrentBullet;
    }

    public void setWhichCurrentBullet(int whichCurrentBullet) {
        this.whichCurrentBullet = whichCurrentBullet;
    }

    public int[] getCartridgeClip() {
        return cartridgeClip;
    }

    public void setCartridgeClip(int[] cartridgeClip) {
        this.cartridgeClip = cartridgeClip;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Boolean getOnIce() {
        return onIce;
    }

    public void setOnIce(Boolean onIce) {
        this.onIce = onIce;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void updateLevel(){
        if(level<4){
            level++;
        }
    }

    public Boolean getQuickShoot() {
        return isQuickShoot;
    }

    public void setQuickShoot(Boolean quickShoot) {
        isQuickShoot = quickShoot;
    }

    public Boolean getCanAttackTheOtherOne() {
        return canAttackTheOtherOne;
    }

    public void setCanAttackTheOtherOne(Boolean canAttackTheOtherOne) {
        this.canAttackTheOtherOne = canAttackTheOtherOne;
    }
}
