package bullet;

import map.MainMap;
import obstacle.BrickWall;
import obstacle.IronWall;
import obstacle.Obstacle;
import others.Direction;
import tank.EnemyTank;
import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;

public class FrozenBullet extends PlayerBullet{
    public FrozenBullet(JPanel map, MainMap mainMap, PlayerTank tank, Direction d, int x, int y,int speed,Boolean canAttackTheOtherTank) {
        super(map, mainMap, tank, d, x, y,speed,canAttackTheOtherTank);
        ImageIcon pic=new ImageIcon(".\\src\\img\\bullet\\TowerBullet.png");
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
        this.setBounds(x,y,5,5);
        this.setIcon(pic);
        map.add(this);
        new Thread(this).start();
    }


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
                else if(em.getLife()>0){
                    em.setSpeed(300);
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

}
