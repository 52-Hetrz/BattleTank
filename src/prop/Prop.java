package prop;

import map.GameMapUI;
import map.MainMap;
import music.Music;
import tank.EnemyTank;
import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Prop extends JLabel{

    private int x;
    private int y;
    private GameMapUI gameMapUI;


    private PropTypes propType;
    private MainMap mapMessage;
    private JPanel gameMap;

    private String prop1="src//img//props//prop1.gif";
    private String prop2="src//img//props//prop2.gif";
    private String prop3="src//img//props//prop3.gif";
    private String prop4="src//img//props//prop4.gif";
    private String prop5="src//img//props//prop5.gif";

    /**
     * 随机位置生成道具
     * @param mapMessage MainMap对象
     * @param gameMap 道具存在的游戏地图
     * @param propType 道具种类
     * @param gameMapUI 游戏界面
     */
    public Prop(MainMap mapMessage, JPanel gameMap, PropTypes propType, GameMapUI gameMapUI){
        this.propType=propType;
        this.mapMessage=mapMessage;
        this.gameMap=gameMap;
        this.gameMapUI=gameMapUI;
        initCoordinate();
        addPropImage();
    }

    /**
     * 指定位置生成道具
     * @param mapMessage MainMap对象
     * @param gameMap 道具存在的游戏地图
     * @param propType 道具种类
     * @param gameMapUI 游戏界面
     * @param x 道具生成的x坐标
     * @param y 道具生成的y坐标
     */
    public Prop(MainMap mapMessage, JPanel gameMap, PropTypes propType, GameMapUI gameMapUI,int x,int y){
        this.propType=propType;
        this.mapMessage=mapMessage;
        this.gameMap=gameMap;
        this.gameMapUI=gameMapUI;
        initCoordinate(x,y);
        addPropImage();
    }

    /**
     * 产生随机位置道具的坐标
     */
    private void initCoordinate(){
        int intX;
        int intY;
        while(true){
            intX =(int)(Math.random()*24);
            intY =(int)(Math.random()*24);
            if(this.mapMessage.getMapList(intX,intY)==0&&!ifHasProps(intX,intY)){
                setX(intX*25);
                setY(intY*25);
                this.mapMessage.setMapList(intX,intY,10);
                this.mapMessage.getProps().add(this);
                break;
            }
        }
    }

    /**
     * 初始化道具坐标
     * @param x 道具产生的x坐标
     * @param y 道具产生的y坐标
     */
    private void initCoordinate(int x,int y){
        while(true){
            if(this.mapMessage.getMapList(x/25,y/25)==0&&!ifHasProps(x,y)){
                setX(x);
                setY(y);
                this.mapMessage.setMapList(x/25,y/25,10);
                this.mapMessage.getProps().add(this);
                break;
            }
        }
    }

    /**
     * 检测当前位置是否有道具
     * @param intX 检测的x坐标
     * @param intY 检测的y坐标
     * @return 返回true代表有道具
     */
    private Boolean ifHasProps(int intX, int intY){
        for(Prop currentProp:this.mapMessage.getProps()){
            if(currentProp.getX()==intX&&currentProp.getY()==intY){
                return true;
            }
        }
        return false;
    }

    /**
     * 给道具添加指定的图像
     */
    private void addPropImage() {
        String imgPath;
        switch (getPropType()){
            case IRON:{
                imgPath=prop1;
                break;
            }
            case LIFE:{
                imgPath=prop2;
                break;
            }
            case TIME:{
                imgPath=prop3;
                break;
            }
            case WEAPON:{
                imgPath=prop4;
                break;
            }
            case UPDATE:{
                imgPath=prop5;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + getPropType());
        }
            ImageIcon pic = new ImageIcon(imgPath);
            //图片填充自适应大小
            pic = new ImageIcon(pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            this.setIcon(pic);
            setBounds(getX(), getY(), 25, 25);
            gameMap.add(this);
    }

    /**
     * 在地图MainMap中的数组中将该道具删除，在道具数组中删除此道具，在地图上移除该道具
     */
    public void removeProp(){
        this.mapMessage.setMapList(getX()/25,getY()/25,0);

        this.mapMessage.getProps().remove(this);
        this.gameMap.remove(this);
    }

    /**
     * 玩家吃到坦克之后产生影响
     * @param currentPlayer 吃到道具的坦克
     */
    public void propEffect(PlayerTank currentPlayer){
        switch (getPropType()){
            case IRON:{
                gameMapUI.changeHomeBrickWallToIronWall();
                break;
            }
            case LIFE:{
                currentPlayer.setLife(currentPlayer.getLife()+1);
                break;
            }
            case TIME:{
                for(EnemyTank currentEnemyTank:this.mapMessage.getEnemyTanks()){
                    currentEnemyTank.setSuspense(true);
                }
                break;
            }
            case WEAPON:{
                new Thread(new Music(".\\src\\music\\boob.wav")).start();
                ArrayList<EnemyTank> list=new ArrayList<>();
                for(EnemyTank enemyTank:mapMessage.getEnemyTanks()){
                    list.add(enemyTank);
                    enemyTank.setLife(0);
                    currentPlayer.setLife(currentPlayer.getLife()+1);
                    gameMap.remove(enemyTank);
                }
                mapMessage.getEnemyTanks().removeAll(list);
                System.out.println(mapMessage.getEnemyTanks().size());
                mapMessage.getTanks().removeAll(list);
                break;
            }
            case UPDATE:{
                currentPlayer.updateLevel();
                break;
            }
        }
    }

    public void addImage(String imgPath,int intX,int intY){
        ImageIcon pic=new ImageIcon(imgPath);
        System.out.println(imgPath);
        //图片填充自适应大小
        pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
        JLabel l=new JLabel();
        l.setBounds(intX,intY,25, 25);
        l.setIcon(pic);
        gameMap.add(l);
    }

    public GameMapUI getGameMapUI() {
        return gameMapUI;
    }

    public void setGameMapUI(GameMapUI gameMapUI) {
        this.gameMapUI = gameMapUI;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPropType(PropTypes propType) {
        this.propType = propType;
    }

    public void setMapMessage(MainMap mapMessage) {
        this.mapMessage = mapMessage;
    }

    public void setGameMap(JPanel gameMap) {
        this.gameMap = gameMap;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PropTypes getPropType() {
        return propType;
    }

    public MainMap getMapMessage() {
        return mapMessage;
    }

    public JPanel getGameMap() {
        return gameMap;
    }
}
