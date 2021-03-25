package tank;

import java.awt.*;
import java.awt.event.KeyEvent;

import bullet.EnemyBullet;
import map.GameMapUI;
import map.MainMap;
import obstacle.Obstacle;
import others.*;

import javax.swing.*;

public class BossTwo extends Tank implements Runnable{

    private  double centerX;
    private  double centerY;
    private boolean BossShoot=true;
    private int shootDirection;
    private TimeController timeController1=new TimeController(this, 2000, 4);
    public BossTwo(GameMapUI gameMapUI, JPanel map, MainMap mainMap,int x,int y){

        setGameMapUI(gameMapUI);
        setMap(map);
        setMainMap(mainMap);
        setLife(10);
        setX(x);
        setY(y);
        setCenterX(getX()+25*1.5);
        setCenterY(getY()+25);
        //timeController1 = new TimeController(this, 1000, 1);
        setCanShoot(true);
        //将MainMap中的bossTwo指向该bossTwo对象
        getMainMap().setBossTwo(this);
        this.setImageIcon();
        new Thread(this).start();
        System.out.println(getX()+"  "+getY()+"  ");
    }


    public void setImageIcon() {
        String bossTwoImg = ".\\src\\img\\enemies\\boss2.gif";

        ImageIcon pic = new ImageIcon(bossTwoImg);
        //图片填充自适应大小
        pic = new ImageIcon(pic.getImage().getScaledInstance(25*3, 25*2, Image.SCALE_DEFAULT));
        this.setIcon(pic);
        this.setBounds(this.getX(),this.getY(),25*3,25*2);
        this.getMap().add(this);
        System.out.println("2");
    }



    public void shootBullet(){
        shootDirection=(int)(Math.random()*220);
        if(shootDirection<10){
            EnemyBullet bullet1 = new EnemyBullet(map, mainMap, this,Direction.DOWN, this.getX()+15,this.getY()+62);
            EnemyBullet bullet2 = new EnemyBullet(map, mainMap, this,Direction.DOWN, this.getX()+35,this.getY()+62);
            EnemyBullet bullet3 = new EnemyBullet(map, mainMap, this,Direction.DOWN, this.getX()+65,this.getY()+62);
            //EnemyBullet bullet4 = new EnemyBullet(map, mainMap, this,Direction.DOWN, this.getX()+75,this.getY()+62);
        }
        else if(shootDirection<80){
            EnemyBullet bullet4 = new EnemyBullet(map, mainMap, this,Direction.LEFT, this.getX()-12,this.getY()+12);
            EnemyBullet bullet5 = new EnemyBullet(map, mainMap, this,Direction.LEFT, this.getX()-12,this.getY()+37);
        }
        else if(shootDirection<150){
            EnemyBullet bullet6 = new EnemyBullet(map, mainMap, this,Direction.UP, this.getX()+15,this.getY()-12);
            EnemyBullet bullet7 = new EnemyBullet(map, mainMap, this,Direction.UP, this.getX()+35,this.getY()-12);
            EnemyBullet bullet8 = new EnemyBullet(map, mainMap, this,Direction.UP, this.getX()+65,this.getY()-12);
        }
        else if(shootDirection<220){
            EnemyBullet bullet9 = new EnemyBullet(map, mainMap, this,Direction.RIGHT, this.getX()+87,this.getY()+12);
            EnemyBullet bullet10 = new EnemyBullet(map, mainMap, this,Direction.RIGHT, this.getX()+87,this.getY()+37);
        }
    }

    @Override
    public void run() {
        while(getLife()>0){
            if(isBossShoot()){
                shootBullet();
                setBossShoot(false);
                new Thread(getTimeController1()).start();
                System.out.println("1");
            }
            System.out.println(" ");
        }
    }


    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public boolean isBossShoot() {
        return this.BossShoot;
    }

    public void setBossShoot(boolean BossShoot) {
        this.BossShoot = BossShoot;
    }

    public TimeController getTimeController1() {
        return timeController1;
    }

    public void setTimeController1(TimeController timeController1) {
        this.timeController1 = timeController1;
    }

    @Override
    public void changeMoveAction(KeyEvent e) {

    }

}