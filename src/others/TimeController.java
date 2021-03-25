package others;

import map.GameMapUI;
import obstacle.Tower;
import tank.BossTwo;
import tank.Tank;

public class TimeController implements Runnable {

    Tank tank;
    int time;
    int flag;
    GameMapUI gameMapUI;
    Tower tower;
    private BossTwo bossTwo;

    /**
     * 针对坦克的时间控制器构造函数
     * @param tank 传入该时间控制器控制的坦克对象
     * @param time 传入控制器控制的时间
     * @param flag 控制器的种类   1：发射子弹控制器
     */
    public TimeController(Tank tank, int time, int flag){
        this.tank=tank;
        this.time=time;
        this.flag=flag;
    }

    /**
     * 针对地图自动产生敌方坦克的时间控制函数
     * @param map 受控制的地图
     * @param time 控制时间
     * @param flag  2： 产生敌方坦克控制器
     */
    public TimeController(GameMapUI map, int time, int flag){
        gameMapUI=map;
        this.time=time;
        this.flag=flag;
    }

    /**
     * 针对防御塔发射子弹间隔的控制
     * @param tower
     * @param time
     * @param flag
     */
    public TimeController(Tower tower,int time,int flag){
        this.tower=tower;
        this.time=time;
        this.flag=flag;

    }

    /**
     *
     * @param bossTwo
     * @param time
     * @param flag   flag=4控制BossTwo子弹发射的频率
     */

    public TimeController(BossTwo bossTwo, int time, int flag){
        this.bossTwo=bossTwo;
        this.time=time;
        this.flag=flag;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(flag==2){
            gameMapUI.setCanAddEnemyTank(true);
        }
        else if(flag==1) {
            tank.setCanShoot(true);
        }
        else if(flag==3){
            tower.setCanShoot(true);
            //System.out.println(tower.getCanShoot());
        }
        else if(flag==4){
            bossTwo.setBossShoot(true);
        }
    }
}
