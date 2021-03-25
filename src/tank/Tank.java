package tank;

import map.GameMapUI;
import map.MainMap;
import obstacle.Obstacle;
import others.Check;
import others.Direction;
import others.Move;
import others.MoveAction;

import javax.swing.*;

public abstract class Tank extends JLabel implements Move, Check {
    //坦克类    继承JLabel
    private int speed;
    private int x;
    private int y;
    int life;
    private Direction direction = Direction.UP;
    private MoveAction moveAction = MoveAction.STOP;
    //用于检测坦克的碰撞次数的参数
    private int TankCollision=0;
    JPanel map;
    private Boolean canShoot;
    int threadCount = 0;
    MainMap mainMap;
    GameMapUI gameMapUI;



    public MoveAction getMoveAction() {
        return moveAction;
    }

    public void setMoveAction(MoveAction moveAction) {
        this.moveAction = moveAction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void shootBullet(Direction direction, int x, int y) {
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public JPanel getMap() {
        return map;
    }

    public void setMap(JPanel map) {
        this.map = map;
    }

    public Boolean getCanShoot() {
        return canShoot;
    }

    public void setCanShoot(Boolean canShoot) {
        this.canShoot = canShoot;
    }

    public MainMap getMainMap() {
        return mainMap;
    }

    public void setMainMap(MainMap mainMap) {
        this.mainMap = mainMap;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getTankCollision() {
        return TankCollision;
    }

    public void setTankCollision(int TankCollision) {
        this.TankCollision = TankCollision;
    }

    /**
     * 实现坦克的移动
     */
    @Override
    public void move() {
    }

    /**
     * 用于检测障碍物、边界、BossTwo
     * @return
     */

    @Override
    public Boolean check() {
        if (getDirection() == Direction.RIGHT) {
            for (Obstacle ob : getMainMap().getObstacles()) {
                if (ob.getX() >= (getX() + 25) && ob.getX() < (getX() + 30) && (ob.getY() + 25) > getY() && ob.getY() < (getY() + 25)) {
                    setMoveAction(MoveAction.STOP);
                    setTankCollision(getTankCollision()+1);
                    return false;
                }
            }
            if ((getX() + 25) >= 600) {
                setMoveAction(MoveAction.STOP);
                setTankCollision(getTankCollision()+1);
                return false;
            }
            if (this.getMainMap().getBossTwo() != null) {
                if ((getX() + 25) <= this.getMainMap().getBossTwo().getX() && (getX() + 30) > this.getMainMap().getBossTwo().getX() &&
                        (getY() + 25) >= this.getMainMap().getBossTwo().getY() && (getY() + 25) < (this.getMainMap().getBossTwo().getY() + 75)) {
                    setMoveAction(MoveAction.STOP);
                    System.out.println("r");
                    setTankCollision(getTankCollision()+1);
                    return false;
                }
            }
        } else if (getDirection() == Direction.LEFT) {
            for (Obstacle ob : getMainMap().getObstacles()) {
                if ((ob.getX() + 25) <= getX() && (ob.getX() + 25) > (getX() - 5) && (ob.getY() + 25) > getY() && ob.getY() < (getY() + 25)) {
                    setMoveAction(MoveAction.STOP);
                    setTankCollision(getTankCollision()+1);
                    return false;
                }
            }
            if (getX() <= 0) {
                setMoveAction(MoveAction.STOP);
                setTankCollision(getTankCollision()+1);
                return false;
            }
            if (this.getMainMap().getBossTwo() != null) {
                if (getX() >= (this.getMainMap().getBossTwo().getX() + 75) && (getX() - 5) < (this.getMainMap().getBossTwo().getX() + 75) &&
                        (getY() + 25) >= this.getMainMap().getBossTwo().getY() && (getY() + 25) < (this.getMainMap().getBossTwo().getY() + 75)) {
                    setMoveAction(MoveAction.STOP);
                    System.out.println("l");
                    setTankCollision(getTankCollision()+1);
                    return false;
                }
            }
        } else if (getDirection() == Direction.UP) {
                for (Obstacle ob : getMainMap().getObstacles()) {
                    if ((ob.getX() + 25) > getX() && ob.getX() < (getX() + 25) && (ob.getY() + 25) <= getY() && (ob.getY() + 25) > (getY() - 5)) {
                        setMoveAction(MoveAction.STOP);
                        setTankCollision(getTankCollision()+1);
                        return false;
                    }
                }
                if (getY() <= 0) {
                    setMoveAction(MoveAction.STOP);
                    setTankCollision(getTankCollision()+1);
                    return false;
                }
                if (this.getMainMap().getBossTwo() != null) {
                    if (getX() >= (this.getMainMap().getBossTwo().getX() - 25) && getX() < (this.getMainMap().getBossTwo().getX() + 75) &&
                            getY() >= (this.getMainMap().getBossTwo().getY() + 50) && (getY() - 5) < (this.getMainMap().getBossTwo().getY() + 50)) {
                        setMoveAction(MoveAction.STOP);
                        System.out.println("u");
                        setTankCollision(getTankCollision()+1);
                        return false;
                    }
                }
            } else if (getDirection() == Direction.DOWN) {
                for (Obstacle ob : getMainMap().getObstacles()) {
                    if ((ob.getX() + 25) > getX() && ob.getX() < (getX() + 25) && ob.getY() >= (getY() + 25) && ob.getY() < (getY() + 30)) {
                        setMoveAction(MoveAction.STOP);
                        setTankCollision(getTankCollision()+1);
                        return false;
                    }
                }
                if ((getY() + 25) >= 600) {
                    setMoveAction(MoveAction.STOP);
                    setTankCollision(getTankCollision()+1);
                    return false;
                }
                if (this.getMainMap().getBossTwo() != null) {
                    if (getX() >= (this.getMainMap().getBossTwo().getX() - 25) && getX() < (this.getMainMap().getBossTwo().getX() + 75) && (getY() + 25) <= this.getMainMap().getBossTwo().getY() && (getY() + 30) > this.getMainMap().getBossTwo().getY()) {
                        setMoveAction(MoveAction.STOP);
                        System.out.println("d");
                        setTankCollision(getTankCollision()+1);
                        return false;
                    }
                }
            }
            setTankCollision(0);
            return true;
        }

        public GameMapUI getGameMapUI(){
            return gameMapUI;
        }

        public void setGameMapUI (GameMapUI gameMapUI){
            this.gameMapUI = gameMapUI;
        }

}
