package tank;

import map.GameMapUI;
import map.MainMap;
import others.MoveAction;

import javax.swing.*;
import java.awt.*;

public class EnemyQuickTank extends EnemyTank{


    public EnemyQuickTank(GameMapUI gameMapUI, JPanel map, MainMap mainMap,PlayerTank trackedTank,int AI,int x, int y) {
        super(gameMapUI, map, mainMap,trackedTank,AI,x, y);
        setLife(1);
        setSpeed(100);
    }

    public void setImageIcon(MoveAction moveAction) {
        String up = ".\\src\\img\\enemies\\enemy2up.gif";
        String left = ".\\src\\img\\enemies\\enemy2left.gif";
        String right = ".\\src\\img\\enemies\\enemy2right.gif";
        String down = ".\\src\\img\\enemies\\enemy2down.gif";
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
        pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.setIcon(pic);
    }

}
