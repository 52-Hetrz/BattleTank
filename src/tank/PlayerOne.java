package tank;

import map.GameMapUI;
import map.MainMap;
import others.Direction;
import others.Move;
import others.MoveAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 一号玩家类继承父类Tank
 * 使用 W A S D 控制方向，空格发射子弹
 * 使用时需要向地图中addKeyListener，监听器为玩家坦克对象
 */


public class PlayerOne extends PlayerTank implements Move, KeyListener,Runnable {

    /**
     * @param imageIcon 引入坦克图片
     * @param image 坦克图片对象
     * @param String_up_down_right_left 用来存储图片文件路径
     */
    private String up=".\\src\\img\\players\\Player1up.png";
    String down=".\\src\\img\\players\\Player1down.gif";
    String right=".\\src\\img\\players\\Player1right.gif";
    String left=".\\src\\img\\players\\Player1left.gif";
    //private ImageIcon imageIcon=new ImageIcon(".\\src\\img\\players\\Player1up.gif");
    //Image image=imageIcon.getImage();


    /**
     *
     * @param gameMapUI 该坦克所属的GameMapUI对象
     * @param map 容纳该坦克的Map
     * @param mainMap map的mainMap
     * @param x 初始x坐标
     * @param y 初始y坐标
     * @param speed 移动速度
     * @param canAttackTheOtherOne 是否可以攻击另一个玩家坦克
     */
    public PlayerOne(GameMapUI gameMapUI,JPanel map, MainMap mainMap, int x, int y, int speed,Boolean canAttackTheOtherOne){
        setCanAttackTheOtherOne(canAttackTheOtherOne);
        setSpeed(speed);
        this.gameMapUI=gameMapUI;
        setX(x);
        setY(y);
        setMap(map);
        setMainMap(mainMap);
        setBegin();
    }

    /**
     * 对坦克的图片以及位置进行初始化
     */
    void setBegin(){
        String imgpath=".\\src\\img\\players\\Player1up.gif";
        ImageIcon pic=new ImageIcon(imgpath);
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
       // PlayerOne playerOne = new PlayerOne(this, mainMap, mainMapList, getX(), getY(), 50,false);
        this.setBounds(getX(),getY(),25, 25);
        this.setIcon(pic);
        getMap().add(this);
        getMainMap().addPlayerTank(this);
        getGameMapUI().addKeyListener(this);
    }

    public PlayerOne(JPanel map, MainMap mainMap, int x, int y, int speed){
        setSpeed(speed);
        setX(x);
        setY(y);
        setMap(map);
        setMainMap(mainMap);
    }


    /**
     * 根据移动方向的改变更换图片
     * @param str 图片路径参数
     */
    private void setImageIcon(String str) {
        ImageIcon pic=new ImageIcon(str);
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
        this.setIcon(pic);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //检测是否需要自动生成敌方坦克
        if(gameMapUI.getCanAddEnemyTank()&&mainMap.getEnemyTanks().size()<=3){
            gameMapUI.addEnemyTankAutomatically();
        }
        //切换子弹
        if(keyEvent.getKeyCode()==KeyEvent.VK_R){
            setWhichCurrentBullet((getWhichCurrentBullet()+1)%3);
        }
        else if(keyEvent.getKeyCode()==32&& getCanShoot()) {
            shootBullet(getDirection(),(getX()+12),(getY()+12));
            setCanShoot(false);
            new Thread(timeController).start();
        }
        else {
            MoveAction d = getMoveAction();
            changeMoveAction(keyEvent);
            //在当前不存在移动线程的前提下才会创建一个新的线程
            if (d == MoveAction.STOP && d != getMoveAction()&&threadCount==0) {
                threadCount++;
                //坦克移动线程开启
                new Thread(this).start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W&&getMoveAction()==MoveAction.UP)
            setMoveAction(MoveAction.STOP);
        else if(e.getKeyCode()==KeyEvent.VK_S&&getMoveAction()==MoveAction.DOWN)
            setMoveAction(MoveAction.STOP);
        else if(e.getKeyCode()==KeyEvent.VK_A&&getMoveAction()==MoveAction.LEFT)
            setMoveAction(MoveAction.STOP);
        else if(e.getKeyCode()==KeyEvent.VK_D&&getMoveAction()==MoveAction.RIGHT)
            setMoveAction(MoveAction.STOP);
    }

    @Override
    public void changeMoveAction(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            setMoveAction(MoveAction.UP);
            setDirection(Direction.UP);
            setImageIcon(up);

        }
        else if(e.getKeyCode()==KeyEvent.VK_S) {
           setMoveAction(MoveAction.DOWN);
           setDirection(Direction.DOWN);
           setImageIcon(down);
        }
        else if(e.getKeyCode()==KeyEvent.VK_D) {
            setMoveAction(MoveAction.RIGHT);
            setDirection(Direction.RIGHT);
            setImageIcon(right);
        }
        else if(e.getKeyCode()==KeyEvent.VK_A) {
            setMoveAction(MoveAction.LEFT);
            setDirection(Direction.LEFT);
            setImageIcon(left);
        }
        //else{
            //setMoveAction(MoveAction.STOP);
        //}
    }

    @Override
    public void run() {
        move();
        threadCount--;
        //坦克移动线程结束
    }

    public String toString(){
        return "PlayerOne";
    }

}
