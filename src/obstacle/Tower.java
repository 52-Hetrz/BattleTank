package obstacle;

import bullet.TowerBullet;
import map.GameMapUI;
import map.MainMap;
import others.TimeController;
import tank.Tank;

import javax.swing.*;
import java.awt.*;

public class Tower extends Obstacle implements Runnable{

    private int x;
    private int y;
    private GameMapUI gameMapUI;
    private MainMap mainMap;
    private Boolean canShoot;
    TimeController timeController=new TimeController(this,100,3);
    Thread thread;

    public Tower(GameMapUI gameMapUI,MainMap mainMap,JPanel map,int x,int y){
        super(x,y,mainMap);
        setMap(map);
        setGameMapUI(gameMapUI);
        setCanShoot(true);
        setContent(9);
        thread=new Thread(this);
        thread.start();
    }

    /**
     * 发射子弹
     * @param targetX 目标x坐标
     * @param targetY 目标y坐标
     */
    void shootBullet(int targetX,int targetY){
        TowerBullet towerBullet;
        if(targetX<getX()&&targetY<getX()) {
            towerBullet=new TowerBullet(map,getMainMap(),getX()-2,getY()-2,targetX,targetY);
           towerBullet.setTowerBulletDirection(-1);
        }
        else if(targetX<getX()&&targetY>getY()){
            towerBullet=new TowerBullet(map,getMainMap(),getX()-2,getY()+25+2,targetX,targetY);
            towerBullet.setTowerBulletDirection(-1);
        }
        else if(targetX>getX()&&targetY>getY()){
            towerBullet=new TowerBullet(map,getMainMap(),getX()+25+2,getY()+25+2,targetX,targetY);
            towerBullet.setTowerBulletDirection(1);
        }
        else {
            towerBullet=new TowerBullet(map,getMainMap(),getX()+25+2,getY()-2,targetX,targetY);
            towerBullet.setTowerBulletDirection(1);
        }
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\TowerBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        towerBullet.setBounds(getX(),getY(),5,5);
        towerBullet.setIcon(pic);
        map.add(towerBullet);
        new Thread(towerBullet).start();
    }

    public void clear(){
        gameMapUI.remove(this);
        thread.stop();
        thread=null;

    }

    private void Fire() throws InterruptedException {
        while(true) {
            //检测坦克容器中的坦克，有没有在防御塔一定范围内的，如果有发射子弹指向坦克，设定canFire为false，启动计时器
            shootBullet((int)(Math.random()*600+getX()),(int)(Math.random()*600+getY()));
            shootBullet((int)(-Math.random()*600+getX()),(int)(-Math.random()*600+getY()));
            shootBullet((int)(Math.random()*600+getX()),(int)(-Math.random()*600+getY()));
            shootBullet((int)(-Math.random()*600+getX()),(int)(Math.random()*600+getY()));
            //shootBullet(getX()-25,getY()+25);
            //shootBullet(getX()+25,getY()-25);
            //shootBullet(getX()-25,getY()-25);
            Thread.sleep(4000);
        }
    }

    void fire2(){
        while(true){
            if(getCanShoot()){
                for(Tank tank:getMainMap().getTanks())
                {
                    if(tank.getY()<(getY()+75)&&tank.getY()>(getY()-75)&&tank.getX()<(getX()+75)&&tank.getX()>(getX()-75)){
                        shootBullet(tank.getX(),tank.getY());
                        setCanShoot(false);
                        break;
                    }
                }
                new Thread(timeController).start();
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameMapUI getGameMapUI() {
        return gameMapUI;
    }

    public void setGameMapUI(GameMapUI gameMapUI) {
        this.gameMapUI = gameMapUI;
    }

    public Boolean getCanShoot() {
        return canShoot;
    }

    public void setCanShoot(Boolean canShoot) {
        this.canShoot = canShoot;
    }

    @Override
    public void run() {
        try {
            Fire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
