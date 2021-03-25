package map;

import obstacle.*;
import prop.Prop;
import tank.BossTwo;
import tank.EnemyTank;
import tank.PlayerTank;
import tank.Tank;

import javax.swing.*;
import java.util.ArrayList;

/**
 * 设置游戏地图数组 坐标24*24
 * 设置坦克地图数组 坐标（待定）
 * 0.空  1.玩家坦克1 2.玩家坦克2 
 * 3.草  4.砖墙  5.铁墙  6.老窝  7.河  8.冰   9.防御塔
 * 9.敌方坦克 
 *
 */
public class MainMap {

	GameMapUI gameMapUI;
	private int[][] mapList;
	private int[][] tankMapList;
	ArrayList<Obstacle> obstacles=new ArrayList<>();
	ArrayList<EnemyTank> enemyTanks=new ArrayList<>();
	ArrayList<PlayerTank> playerTanks=new ArrayList<>();
	ArrayList<Tank> tanks=new ArrayList<>();
	private ArrayList<Prop> props=new ArrayList<Prop>();
	//记录BossTwo
	private BossTwo bossTwo;
	JPanel map;

	
	public MainMap(JPanel map,GameMapUI gameMapUI) {
		this.initMapList();
		this.map=map;
		this.gameMapUI=gameMapUI;
	}

	public void emptyMapList() {
		this.mapList=new int[24][24]; //空地初始化
		for(int i=0;i<24;i++) {
			for(int j=0;j<24;j++) {
				this.mapList[i][j]=0;
			}
		}
		this.mapList[11][23]=6; //老窝
	}
	
	/**
	 * 地图初始化
	 */

	public void initMapList() {
		emptyMapList();
		
		this.mapList[11][23]=6; //老窝
		obstacles.add(new Obstacle(11*25,23*25,this,6));
		for(int i=10;i<13;i++) //老窝保护墙
		{
			this.mapList[i][22]=4;
			//obstacles.add(new Obstacle(i * 25, 22 * 25));
		}
		this.mapList[10][23]=4;
		//obstacles.add(new Obstacle(10 * 25, 23 * 25));
		this.mapList[12][23]=4;
		//obstacles.add(new Obstacle(12 * 25, 23 * 25));

		this.mapList[9][23]=1; //玩家初始化
		this.mapList[13][23]=2;
	}
	
	/**
	 * 产生随机地图数组
	 * @param g 草数量
	 * @param b 砖墙数量
	 * @param ir 铁墙数量
	 * @param r 河数量
	 * @param ic 冰数量
	 */
	public void initRandomMapList(int g,int b,int ir,int r,int ic) {
		initMapList();
		
		productPosition(g,3);
		productPosition(b,4);
		productPosition(ir,5);
		productPosition(r,7);
		productPosition(ic,8);
		
	}
	
	/**
	 * 地图数组添加随机障碍物
	 * @param num 障碍物数量
	 * @param content 障碍物标号
	 */
	public void productPosition(int num,int content) {
		int i=0;
		while(i<num) {
			int x=(int)(Math.random()*24);
			int y=(int)(Math.random()*24);
			if(isEmpty(this.mapList[x][y])&&isBossTwo(x,y)) {
				this.mapList[x][y]=content;
				if(content!=3&&content!=8&&content!=4&&content!=5&&content!=6) {
					obstacles.add(new Obstacle(x * 25, y * 25,this));
				}
				i++;
			}
			
		}
	}

	public void removeBossTwo(BossTwo bossTwo){
		map.remove(bossTwo);
		setBossTwo(null);
	}

	/**
	 * 用于检测随机生成的障碍物是否覆盖在BossTwo上
	 * @param x
	 * @param y
	 * @return
	 */

	public Boolean isBossTwo(int x,int y){
		if(x>=11&&x<=13&&y>=7&&y<=8){
			return false;
		}
		return true;
	}

	/**
	 * 判断当前地图元素是否为空
	 * @param mapElement 地图数组元素
	 * @return true为空
	 */
	public boolean isEmpty(int mapElement) {
		return mapElement == 0;
	}
	
	/**
	 * 地图单个元素设置
	 * @param x 地图数组x坐标
	 * @param y 地图数组y坐标
	 * @param content 设置目标元素标号
	 */
	public void setMapList(int x,int y,int content) {
		this.mapList[x][y]=content;
	}
	
	public int getMapList(int x,int y) {
		return this.mapList[x][y];
	}
	
	public void setTankMapList(int x,int y,int content) {
		this.tankMapList[x][y]=content;
	}
	
	public int getTankMapList(int x,int y) {
		return this.tankMapList[x][y];
	}

	public ArrayList<Obstacle> getObstacles(){return obstacles;}

	public ArrayList<EnemyTank> getEnemyTanks() {
		return enemyTanks;
	}

	public BossTwo getBossTwo() {
		return bossTwo;
	}

	public void setBossTwo(BossTwo bossTwo) {
		this.bossTwo = bossTwo;
	}

	public void addEnemyTank(EnemyTank enemyTank){
		enemyTanks.add(enemyTank);
		tanks.add(enemyTank);
	}

	public ArrayList<PlayerTank> getPlayerTanks() {
		return playerTanks;
	}

	public void addPlayerTank(PlayerTank playerTank){
		playerTanks.add(playerTank);
		tanks.add(playerTank);
	}

	public void removePlayerTank(PlayerTank playerTank){
		playerTanks.remove(playerTank);
		tanks.remove(playerTank);
		map.remove(playerTank);
		gameMapUI.removeKeyListener(playerTank);
	}

	public void removeEnemyTank(EnemyTank enemyTank){
		enemyTanks.remove(enemyTank);
		tanks.remove(enemyTank);
		map.remove(enemyTank);
	}

	public void addBrickWall(BrickWall brickWall){obstacles.add(brickWall);}

	public void removeObstacle(Obstacle obstacle){
		obstacles.remove(obstacle);
		map.remove(obstacle);
	}

	public void addIronWall(IronWall ironWall){
		obstacles.add(ironWall);
	}

	public void removeIronWall(IronWall ironWall){
		obstacles.remove(ironWall);
		map.remove(ironWall);
	}

	public void addObstacleList(int i,int j) {
		if(this.mapList[i][j]!=3&&this.mapList[i][j]!=5) {
			obstacles.add(new Obstacle(i * 25, j * 25,this));
		}
	}

	public void addHome(Home home){obstacles.add(home);}

	public void addTower(Tower tower){obstacles.add(tower);}

	public ArrayList<Tank> getTanks() {
		return tanks;
	}

	public ArrayList<Prop> getProps() {
		return props;
	}

	public GameMapUI getGameMapUI() {
		return gameMapUI;
	}
}
