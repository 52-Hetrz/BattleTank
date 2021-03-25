package tank;

import map.GameMapUI;
import map.MainMap;
import bullet.EnemyBullet;
import others.Direction;
import others.MoveAction;
import others.TimeController;
import prop.Prop;
import prop.PropTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class EnemyTank extends Tank implements Runnable {

    private Boolean isSuspense =false;
    private TimeController timeController =new TimeController(this,2000,1);
    private PropTypes beCarriedProp;
    //被跟踪的玩家坦克
    private PlayerTank tailAfterPlayTank;
    //控制寻路的AI
    private int AI;
    //与障碍物碰撞的频率
    private int obstaclCollisionTime=0;
    //敌方坦克改变频率的次数
    private int changeMoveActionFrequency=0;


    public EnemyTank(GameMapUI gameMapUI, JPanel map, MainMap mainMap,PlayerTank trackedTank,int AI,int x, int y) {

        setSpeed(120);
        setGameMapUI(gameMapUI);
        setCanShoot(true);
        setMap(map);
        setMainMap(mainMap);
        setX(x);
        setY(y);
        setLife(1);
        setTailAfterPlayTank(trackedTank);
        setAI(AI);
        setBeCarriedProp();
        setTankCollision(0);
        new Thread(this).start();
    }

    private void changeMoveAction() {

        int randomDirection;
            randomDirection= (int) (150 * Math.random());
            if (randomDirection < 5) {
                setDirection(Direction.UP);
                setMoveAction(MoveAction.UP);
                setImageIcon(MoveAction.UP);

            } else if (randomDirection < 15) {
                setDirection(Direction.LEFT);
                setMoveAction(MoveAction.LEFT);
                setImageIcon(MoveAction.LEFT);

            } else if (randomDirection < 25) {
                setDirection(Direction.RIGHT);
                setMoveAction(MoveAction.RIGHT);
                setImageIcon(MoveAction.RIGHT);

            } else if (randomDirection < 40) {
                setDirection(Direction.DOWN);
                setMoveAction(MoveAction.DOWN);
                setImageIcon(MoveAction.DOWN);
            }
    }

    public void changeMoveActionTailAfterPlayerTank() {
        //追踪的玩家坦克在此敌方坦克的右下角
        if (this.getX() <= tailAfterPlayTank.getX() && this.getY() <= tailAfterPlayTank.getY()) {
            if (tailAfterPlayTank.getX() - this.getX() >= tailAfterPlayTank.getY() - this.getY()) {
                if(getTankCollision()==0){
                    setDirection(Direction.RIGHT);
                    setMoveAction(MoveAction.RIGHT);
                    setImageIcon(MoveAction.RIGHT);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.DOWN);
                    setMoveAction(MoveAction.DOWN);
                    setImageIcon(MoveAction.DOWN);
                }
                else if(getTankCollision()==2) {
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.LEFT);
                        setMoveAction(MoveAction.LEFT);
                        setImageIcon(MoveAction.LEFT);
                    } else {
                        setDirection(Direction.UP);
                        setMoveAction(MoveAction.UP);
                        setImageIcon(MoveAction.UP);
                    }
                }
            } else {
                if(getTankCollision()==0){
                    setDirection(Direction.DOWN);
                    setMoveAction(MoveAction.DOWN);
                    setImageIcon(MoveAction.DOWN);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.RIGHT);
                    setMoveAction(MoveAction.RIGHT);
                    setImageIcon(MoveAction.RIGHT);
                }
                else if(getTankCollision()==2){
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.LEFT);
                        setMoveAction(MoveAction.LEFT);
                        setImageIcon(MoveAction.LEFT);
                    } else {
                        setDirection(Direction.UP);
                        setMoveAction(MoveAction.UP);
                        setImageIcon(MoveAction.UP);
                    }
                }
            }
        }
        //追踪的敌方坦克在此敌方坦克的左下角
        else if (this.getX() > tailAfterPlayTank.getX() && this.getY() <= tailAfterPlayTank.getY()) {
            if (this.getX() - tailAfterPlayTank.getX() >= tailAfterPlayTank.getY() - this.getY()) {
                if(getTankCollision()==0){
                    setDirection(Direction.LEFT);
                    setMoveAction(MoveAction.LEFT);
                    setImageIcon(MoveAction.LEFT);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.DOWN);
                    setMoveAction(MoveAction.DOWN);
                    setImageIcon(MoveAction.DOWN);
                }
                else if(getTankCollision()==2){
                    setDirection(Direction.RIGHT);
                    setMoveAction(MoveAction.RIGHT);
                    setImageIcon(MoveAction.RIGHT);
                }
                else if(getTankCollision()==3){
                    setDirection(Direction.UP);
                    setMoveAction(MoveAction.UP);
                    setImageIcon(MoveAction.UP);
                }
            }
            else {
                if(getTankCollision()==0){
                    setDirection(Direction.DOWN);
                    setMoveAction(MoveAction.DOWN);
                    setImageIcon(MoveAction.DOWN);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.LEFT);
                    setMoveAction(MoveAction.LEFT);
                    setImageIcon(MoveAction.LEFT);
                }
                else if(getTankCollision()==2) {
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.RIGHT);
                        setMoveAction(MoveAction.RIGHT);
                        setImageIcon(MoveAction.RIGHT);
                    } else {
                        setDirection(Direction.UP);
                        setMoveAction(MoveAction.UP);
                        setImageIcon(MoveAction.UP);
                    }
                }
            }
        }
        //追踪的敌方坦克在此坦克的右上角
        else if (this.getX() <= tailAfterPlayTank.getX() && this.getY() > tailAfterPlayTank.getY()) {
            if (this.getX() - tailAfterPlayTank.getX() > this.getY() - tailAfterPlayTank.getY()) {
                if(getTankCollision()==0){
                    setDirection(Direction.RIGHT);
                    setMoveAction(MoveAction.RIGHT);
                    setImageIcon(MoveAction.RIGHT);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.UP);
                    setMoveAction(MoveAction.UP);
                    setImageIcon(MoveAction.UP);
                }
                else if(getTankCollision()==2){
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.LEFT);
                        setMoveAction(MoveAction.LEFT);
                        setImageIcon(MoveAction.LEFT);
                    } else {
                        setDirection(Direction.DOWN);
                        setMoveAction(MoveAction.DOWN);
                        setImageIcon(MoveAction.DOWN);
                    }
                }

            } else {
                if(getTankCollision()==0){
                    setDirection(Direction.UP);
                    setMoveAction(MoveAction.UP);
                    setImageIcon(MoveAction.UP);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.RIGHT);
                    setMoveAction(MoveAction.RIGHT);
                    setImageIcon(MoveAction.RIGHT);
                }
                else if(getTankCollision()==2){
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.LEFT);
                        setMoveAction(MoveAction.LEFT);
                        setImageIcon(MoveAction.LEFT);
                    } else {
                        setDirection(Direction.DOWN);
                        setMoveAction(MoveAction.DOWN);
                        setImageIcon(MoveAction.DOWN);
                    }
                }
            }
        }
        //追踪的玩家坦克在此敌方坦克的左上角
        else if (this.getX() > tailAfterPlayTank.getX() && this.getY() > tailAfterPlayTank.getY()) {
            if (this.getX() - tailAfterPlayTank.getX() > this.getY() - tailAfterPlayTank.getY()) {
                if(getTankCollision()==0){
                    setDirection(Direction.LEFT);
                    setMoveAction(MoveAction.LEFT);
                    setImageIcon(MoveAction.LEFT);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.UP);
                    setMoveAction(MoveAction.UP);
                    setImageIcon(MoveAction.UP);
                }
                else if(getTankCollision()==2){
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.RIGHT);
                        setMoveAction(MoveAction.RIGHT);
                        setImageIcon(MoveAction.RIGHT);
                    } else {
                        setDirection(Direction.DOWN);
                        setMoveAction(MoveAction.DOWN);
                        setImageIcon(MoveAction.DOWN);
                    }
                }
            }
            else {
                if(getTankCollision()==0){
                    setDirection(Direction.UP);
                    setMoveAction(MoveAction.UP);
                    setImageIcon(MoveAction.UP);
                }
                else if(getTankCollision()==1){
                    setDirection(Direction.LEFT);
                    setMoveAction(MoveAction.LEFT);
                    setImageIcon(MoveAction.LEFT);
                }
                else if(getTankCollision()==2){
                    int randomAction = (int) (Math.random() * 10);
                    if (randomAction > 5) {
                        setDirection(Direction.RIGHT);
                        setMoveAction(MoveAction.RIGHT);
                        setImageIcon(MoveAction.RIGHT);
                    } else {
                        setDirection(Direction.DOWN);
                        setMoveAction(MoveAction.DOWN);
                        setImageIcon(MoveAction.DOWN);
                    }
                }
            }
        }
    }


    @Override
    public void move() {
        if(check()&&checkPlayerTank()) {
            if (getMoveAction() == MoveAction.UP) {
                setY(getY() - 5);
                setBounds(getX(), getY(), 25, 25);
            } else if (getMoveAction() == MoveAction.DOWN) {
                setY(getY() + 5);
                setBounds(getX(), getY(), 25, 25);
            } else if (getMoveAction() == MoveAction.RIGHT) {
                setX(getX() + 5);
                setBounds(getX(), getY(), 25, 25);
            } else if (getMoveAction() == MoveAction.LEFT) {
                setX(getX() - 5);
                setBounds(getX(), getY(), 25, 25);
            }

            try {
                Thread.sleep(getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(this.isSuspense){
                try{
                    Thread.sleep(5000);
                    setSuspense(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发射子弹
     * @param direction 子弹发射的方向
     * @param x 子弹的初始X坐标
     * @param y 子弹的初始y坐标
     */
    public void shootBullet(Direction direction,int x,int y){
        EnemyBullet bullet=new EnemyBullet (map,mainMap,this,direction,x,y);
    }

    /**
     * 检测是否撞到玩家坦克
     * @return 返回false撞到玩家坦克，无法在当前方向继续移动
     */
    private Boolean checkPlayerTank(){
        if(getDirection()==Direction.RIGHT) {
            for(PlayerTank em:getMainMap().getPlayerTanks()) {
                if(em.getX()>=(getX()+25)&&em.getX()<(getX()+30)&&(em.getY()+25)>getY()&&em.getY()<(getY()+25)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.LEFT) {
            for(PlayerTank em:getMainMap().getPlayerTanks()) {
                if((em.getX()+25)<=getX()&&(em.getX()+25)>(getX()-5)&&(em.getY()+25)>getY()&&em.getY()<(getY()+25)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.UP) {
            for(PlayerTank em:getMainMap().getPlayerTanks()) {
                if((em.getX()+25)>getX()&&em.getX()<(getX()+25)&&(em.getY()+25)<=getY()&&(em.getY()+25)>(getY()-5)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        else if(getDirection()==Direction.DOWN) {
            for(PlayerTank em:getMainMap().getPlayerTanks()) {
                if((em.getX()+25)>getX()&&em.getX()<(getX()+25)&&em.getY()>=(getY()+25)&&em.getY()<(getY()+30)) {
                    setMoveAction(MoveAction.STOP);
                    return false;
                }
            }
        }
        return true;
    }


    public void setImageIcon(MoveAction moveAction) {
        String up = ".\\src\\img\\enemies\\enemy1up.gif";
        String left = ".\\src\\img\\enemies\\enemy1left.gif";
        String right = ".\\src\\img\\enemies\\enemy1right.gif";
        String down = ".\\src\\img\\enemies\\enemy1down.gif";
        ImageIcon pic = null;
        if(moveAction==MoveAction.UP){
            pic=new ImageIcon(up);
        }
        else if(moveAction==MoveAction.RIGHT){
            pic=new ImageIcon(right);
        }
        else if(moveAction==MoveAction.LEFT){
            pic=new ImageIcon(left);
        }
        else{
            pic=new ImageIcon(down);
        }
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
        this.setIcon(pic);
    }

    @Override
    public void run() {
        while(getLife()>0){
            if(getCanShoot()) {
                shootBullet(getDirection(), (getX() + 12), (getY() + 12));
                setCanShoot(false);
                new Thread(timeController).start();
            }
            if(this.getAI()==1){
                changeMoveAction();
            }
            else if(this.getAI()==2){
                changeMoveActionTailAfterPlayerTank();
            }
            move();
        }
    }

    @Override
    public void changeMoveAction(KeyEvent e) {
    }

    public void setBeCarriedProp(){
        int flag=(int)(Math.random()*100);
        if(flag>=75){
            this.beCarriedProp=PropTypes.NULL;
        }
        else if(flag > 55){
            this.beCarriedProp=PropTypes.UPDATE;         //update
        }
        else if(flag>45){
            this.beCarriedProp=PropTypes.TIME;         //静止
        }
        else if(flag>25){
            this.beCarriedProp=PropTypes.LIFE;        //加生命值
        }
        else if(flag>10){
            this.beCarriedProp=PropTypes.IRON;       //铁墙
        }
        else
            this.beCarriedProp=PropTypes.WEAPON;        //炸弹
    }

    public Boolean getIsSuspense() {
        return isSuspense;
    }

    public void setSuspense(Boolean suspense) {
        isSuspense = suspense;
    }

    /**
     * 敌方坦克被击毁之后掉落道具
     */
    public void dropProp(){
        if(beCarriedProp!=PropTypes.NULL){
            new Prop(getMainMap(),getMap(),beCarriedProp,getGameMapUI(),getX(),getY());
        }
    }

    public void emptyProp(){this.beCarriedProp=null;}

    public String toString(){
        return "EnemyTank";
    }

    public PlayerTank getTailAfterPlayTank() {
        return tailAfterPlayTank;
    }

    public void setTailAfterPlayTank(PlayerTank tailAfterPlayTank) {
        this.tailAfterPlayTank = tailAfterPlayTank;
    }

    public int getAI() {
        return AI;
    }

    public void setAI(int AI) {
        this.AI = AI;
    }

    public int getObstaclCollisionTime() {
        return obstaclCollisionTime;
    }

    public void setObstaclCollisionTime(int obstaclCollisionTime) {
        this.obstaclCollisionTime = obstaclCollisionTime;
    }

    public int getChangeMoveActionFrequency() {
        return changeMoveActionFrequency;
    }

    public void setChangeMoveActionFrequency(int changeMoveActionFrequency) {
        this.changeMoveActionFrequency = changeMoveActionFrequency;
    }
}
