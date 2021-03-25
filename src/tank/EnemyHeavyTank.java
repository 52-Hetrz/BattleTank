package tank;

import bullet.EnemyArmourPiercingBullet;
import bullet.EnemyBullet;
import map.GameMapUI;
import map.MainMap;
import others.Direction;
import others.MoveAction;

import javax.swing.*;
import java.awt.*;

public class EnemyHeavyTank extends EnemyTank{

    private String up = ".\\src\\img\\enemies\\enemy3up.gif";
    private String down = ".\\src\\img\\enemies\\enemy3down.gif";
    private String left = ".\\src\\img\\enemies\\enemy3left.gif";
    private String right = ".\\src\\img\\enemies\\enemy3right.gif";

    public EnemyHeavyTank(GameMapUI gameMapUI, JPanel map, MainMap mainMap,PlayerTank trackedTank,int AI,int x, int y) {
        super(gameMapUI, map, mainMap,trackedTank,AI,x, y);
        setSpeed(140);
        setLife(3);
    }

    public void shootBullet(Direction direction, int x, int y){
        EnemyArmourPiercingBullet bullet=new EnemyArmourPiercingBullet (map,mainMap,this,direction,x,y);
    }

    public void setImageIcon(MoveAction moveAction) {
        String up1 = ".\\src\\img\\enemies\\enemy3up.gif";
        String left1 = ".\\src\\img\\enemies\\enemy3left.gif";
        String right1 = ".\\src\\img\\enemies\\enemy3right.gif";
        String down1 = ".\\src\\img\\enemies\\enemy3down.gif";

        String up2 = ".\\src\\img\\enemies\\greenTankUp.gif";
        String left2 = ".\\src\\img\\enemies\\greenTankLeft.gif";
        String right2 = ".\\src\\img\\enemies\\greenTankRight.gif";
        String down2 = ".\\src\\img\\enemies\\greenTankDown.gif";

        String up3 = ".\\src\\img\\enemies\\yellowTankUp.gif";
        String left3 = ".\\src\\img\\enemies\\yellowTankLeft.gif";
        String right3 = ".\\src\\img\\enemies\\yellowTankRight.gif";
        String down3 = ".\\src\\img\\enemies\\yellowTankDown.gif";

        ImageIcon pic = null;
        if(moveAction==MoveAction.UP){
            if(getLife()==3){
                pic=new ImageIcon(up2);
            }
            else if(getLife()==2){
                pic=new ImageIcon(up3);
            }
            else if(getLife()==1){
                pic=new ImageIcon(up1);
            }
        }
        else if(moveAction==MoveAction.RIGHT){
            if(getLife()==3){
                pic=new ImageIcon(right2);
            }
            else if(getLife()==2){
                pic=new ImageIcon(right3);
            }
            else if(getLife()==1){
                pic=new ImageIcon(right1);
            }
        }
        else if(moveAction==MoveAction.LEFT){
            if(getLife()==3){
                pic=new ImageIcon(left2);
            }
            else if(getLife()==2){
                pic=new ImageIcon(left3);
            }
            else if(getLife()==1){
                pic=new ImageIcon(left1);
            }
        }
        else{
            if(getLife()==3){
                pic=new ImageIcon(down2);
            }
            else if(getLife()==2){
                pic=new ImageIcon(down3);
            }
            else if(getLife()==1){
                pic=new ImageIcon(down1);
            }
        }
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.setIcon(pic);
    }

}
