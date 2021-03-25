package map;

import bullet.EnemyArmourPiercingBullet;
import obstacle.IronWall;
import obstacle.Obstacle;
import prop.Prop;
import prop.PropTypes;
import obstacle.BrickWall;
import obstacle.Tower;
import others.TimeController;
import tank.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//游戏进行界面
public abstract class GameMapUI extends MapUI {
	MainMap mainMapList;
	JPanel mainMap;
	JPanel secondaryMap;
	protected int score1;
	protected int score2;
	protected int life1;
	protected int life2;
	protected PlayerOne playerOne;
	protected PlayerTwo playerTwo;
	protected ArrayList<EnemyTank> enemyTankList=new ArrayList<>();
	private TimeController timeController =new TimeController(this,5000,2);
	Boolean canAddEnemyTank=true;
	String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
	String style2="</div></body></html>";
	Tower tower;
	
	public GameMapUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMap.setBackground(Color.BLACK);
		secondaryMap.setBackground(new Color(70,70,70));
		this.initMap();
	}

	void initMap() {
		//主地图初始化
		this.initMainMap();
		//副地图初始化
		this.initSecondaryMap();
	}

	//主地图初始化
	public abstract void initMainMap();

	//坦克角色添加
	public abstract void addTank();

	//副地图初始化
	public abstract void initSecondaryMap();


	public void addElement() {
		mainMap=new JPanel(); //游戏主地图
		secondaryMap=new JPanel(); //游戏边栏
		mainMap.setLayout(null);
		secondaryMap.setLayout(null);
		mainMap.setPreferredSize(new Dimension(616, 600));
		secondaryMap.setPreferredSize(new Dimension(184, 600));
		
		//背景颜色填充
		mainMap.setBackground(Color.BLACK);
		secondaryMap.setBackground(new Color(70,70,70));
		
		//主地图初始化
		//this.initMainMap();
		//副地图初始化
		//this.initSecondaryMap();
		
		//主容器填充
		JPanel sum=new JPanel();
		sum.setLayout(new BorderLayout());
		sum.add(mainMap,BorderLayout.CENTER);
		sum.add(secondaryMap,BorderLayout.EAST);
		this.setContentPane(sum);
	}
	
	/**
	 * 添加单个图片
	 * @param mainMap 添加目的地图
	 * @param imgpath 图片文件路径
	 * @param x 图片位置x坐标
	 * @param y 图片位置y坐标
	 */
	public void addImage(JPanel mainMap,String imgpath,int x,int y) {
		ImageIcon pic=new ImageIcon(imgpath);
		//图片填充自适应大小
		pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
		JLabel l=new JLabel();
		l.setBounds(x,y,25, 25);
		l.setIcon(pic);
		mainMap.add(l);
	}

	/**
	 * 向 mainMap中添加一个一号玩家坦克
	 * @param mainMap  接受添加的MainMap对象
	 * @param x 添加的X坐标
	 * @param y 添加的Y坐标
	 * @param canAttackTheOtherOne 是否可以攻击另一个玩家坦克
	 */
	public void addPlayerTankOne(JPanel mainMap,int x,int y,Boolean canAttackTheOtherOne){
		playerOne = new PlayerOne(this, mainMap, mainMapList, x, y, 50,canAttackTheOtherOne);
		addTower(mainMap,x-100,y-200);
	}

	/**
	 * 当玩家一阵亡一次，但仍有生命值时，对坦克进行重生
	 * @param playerTank 重生的玩家坦克对象
	 */
	public void repaintPlayerOne(PlayerTank playerTank){
		Boolean canAttackTheOtherOne=playerTank.getCanAttackTheOtherOne();
		int life=playerTank.getLife();
		mainMapList.removePlayerTank(playerTank);
		playerOne = new PlayerOne(this, mainMap, mainMapList, 9*25, 23*25, 50,canAttackTheOtherOne);
		playerOne.setLife(life);
		for(int i=0;i<24;i++) {
			for(int j=0;j<24;j++) {
				if(mainMapList.getMapList(i,j)==3)
					paintSingleElement(i,j);
			}
		}
		//重新设定跟踪对象
		for(EnemyTank em:mainMapList.getEnemyTanks()){
			if(em.getAI()==2){
				em.setTailAfterPlayTank(playerOne);
			}
		}
	}

	/**
	 * 向游戏地图中添加一个二号玩家坦克
	 * @param mainMap 接受二号玩家坦克的地图
	 * @param x 添加的x坐标
	 * @param y 添加的y坐标
	 * @param canAttackTheOtherOne 是否可以攻击另一个玩家坦克
	 */
	public void addPlayerTankTwo(JPanel mainMap,int x,int y,Boolean canAttackTheOtherOne){
		playerTwo = new PlayerTwo(this, mainMap, mainMapList, x, y, 50,canAttackTheOtherOne);
	}

	/**
	 * 当玩家二阵亡一次，但仍有生命值时，对坦克进行重生
	 * @param playerTank 重生的玩家坦克对象
	 */
	public void repaintPlayerTwo(PlayerTank playerTank){
		Boolean canAttackTheOtherOne=playerTank.getCanAttackTheOtherOne();
		int life=playerTank.getLife();
		mainMapList.removePlayerTank(playerTank);
		playerTwo = new PlayerTwo(this, mainMap, mainMapList, 13*25, 23*25, 50,canAttackTheOtherOne);
		playerTwo.setLife(life);
		for(int i=0;i<24;i++) {
			for(int j=0;j<24;j++) {
				if(mainMapList.getMapList(i,j)==3)
					paintSingleElement(i,j);
			}
		}
	}

	/**
	 * 当玩家二阵亡一次，但仍有生命时，对坦克进行重生
	 * @param playerTank 重生的玩家坦克对象
	 */
	public void reBornPlayerTank(PlayerTank playerTank){
		if(playerTank.getLife()>0){
			if(playerTank.toString().equals("PlayerOne")){
				repaintPlayerOne(playerTank);
				this.updateLifeLabel(playerTank,playerTank.getLife());
				//mainMap.repaint();
			}
			else if(playerTank.toString().equals("PlayerTwo")){
				repaintPlayerTwo(playerTank);
				this.updateLifeLabel(playerTank,playerTank.getLife());
			}
		}
		else
		{
			this.updateLifeLabel(playerTank,0);
			if(this.playerOne.getLife()==0)
			{
				if(this.playerTwo==null || this.playerTwo.getLife()==0)
					this.showGameOverUI();
			}
			mainMapList.removePlayerTank(playerTank);
		}
	}

	/**
	 * 向地图中添加一个敌方坦克
	 * @param mainMap 接受敌方坦克的对象
	 * @param x 添加的x坐标
	 * @param y 添加的y坐标
	 */
	public void addEnemyTank(JPanel mainMap,int x,int y){
		int flag=(int)(Math.random()*100);
		if(flag>=66) {
			String imgpath = ".\\src\\img\\enemies\\enemy1up.gif";
			ImageIcon pic = new ImageIcon(imgpath);
			//图片填充自适应大小
			pic = new ImageIcon(pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
			EnemyOriginalTank enemyTank = new EnemyOriginalTank(this, mainMap, mainMapList,playerOne,2,x, y);
			mainMapList.addEnemyTank(enemyTank);
			enemyTankList.add(enemyTank);
			enemyTank.setBounds(x, y, 25, 25);
			enemyTank.setIcon(pic);
			mainMap.add(enemyTank);
		}
		else if(flag>=33){
			String imgpath = ".\\src\\img\\enemies\\enemy2up.gif";
			ImageIcon pic = new ImageIcon(imgpath);
			//图片填充自适应大小
			pic = new ImageIcon(pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
			EnemyQuickTank enemyTank = new EnemyQuickTank(this, mainMap, mainMapList,playerOne,2, x, y);
			mainMapList.addEnemyTank(enemyTank);
			enemyTankList.add(enemyTank);
			enemyTank.setBounds(x, y, 25, 25);
			enemyTank.setIcon(pic);
			mainMap.add(enemyTank);
		}
		else {
			String imgpath = ".\\src\\img\\enemies\\enemy3up.gif";
			ImageIcon pic = new ImageIcon(imgpath);
			//图片填充自适应大小
			pic = new ImageIcon(pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
			EnemyHeavyTank enemyTank = new EnemyHeavyTank(this, mainMap, mainMapList,playerOne,1, x, y);
			mainMapList.addEnemyTank(enemyTank);
			enemyTankList.add(enemyTank);
			enemyTank.setBounds(x, y, 25, 25);
			enemyTank.setIcon(pic);
			mainMap.add(enemyTank);
		}
	}

	public void addBossTwo(){
		BossTwo bossTwo=new BossTwo(this,mainMap,mainMapList,11*25,7*25);
	}

	/**
	 *
	 * @param x bossTwo横坐标
	 * @param y
	 */
	/*public void addBossTwo(int x,int y){
		String imgpath=".\\src\\img\\enemies\\boss2.gif";
		ImageIcon pic=new ImageIcon(imgpath);
		//图片填充自适应大小
		pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));

		BossTwo bossTwo=new BossTwo(this,mainMap,mainMapList,x,y);
		mainMapList.setBossTwo(bossTwo);

		bossTwo.setBounds(x,y,75, 50);
		bossTwo.setIcon(pic);
		mainMap.add(bossTwo);
	}*/

	/**
	 * 向地图中添加砖墙
	 * @param mainMap 接受砖墙对象的JPanel对象
	 * @param x MapList中的x坐标
	 * @param y MapList中的y坐标
	 */
	public void addBrickWall(JPanel mainMap,int x,int y){
		String imgpath=".\\src\\img\\map\\brickwall.gif";
		ImageIcon pic=new ImageIcon(imgpath);
		//图片填充自适应大小
		pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
		BrickWall brickWall=new BrickWall(mainMap,mainMapList,x*25,y*25);
		mainMapList.addBrickWall(brickWall);
		brickWall.setBounds(x*25,y*25,25, 25);
		brickWall.setIcon(pic);
		mainMap.add(brickWall);
	}

	/**
	 * 向地图中添加铁墙
	 * @param mainMap 接受铁墙的JPanel对象
	 * @param x 铁墙在MapList中的x坐标
	 * @param y 铁墙在MapList中的y坐标
	 */
	public void addIronWall(JPanel mainMap,int x,int y){
		String imgpath=".\\src\\img\\map\\ironwall.gif";
		ImageIcon pic=new ImageIcon(imgpath);
		//图片填充自适应大小
		pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
		IronWall ironWall=new IronWall(mainMap,mainMapList,x*25,y*25);
		mainMapList.addIronWall(ironWall);
		mainMapList.setMapList(x,y,5);
		ironWall.setBounds(x*25,y*25,25, 25);
		ironWall.setIcon(pic);
		mainMap.add(ironWall);
	}

	/**
	 * 向地图中添加防御塔
	 * @param mainMap 接受防御塔对象的JPanel
	 * @param x 防御塔的x坐标
	 * @param y 防御塔的y坐标
	 */
	public void addTower(JPanel mainMap,int x,int y){
		String imgpath=".\\src\\img\\map\\Tower.png";
		ImageIcon pic=new ImageIcon(imgpath);
		//图片填充自适应大小
		pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
		tower=new Tower(this,mainMapList,mainMap,x,y);
		mainMapList.addTower(tower);
		tower.setBounds(x,y,25, 25);
		tower.setIcon(pic);
		mainMap.add(tower);
	}

	/**
	 * 将现有的老窝保护墙变成铁墙
	 */
	public void changeHomeBrickWallToIronWall(){
		ArrayList<Obstacle> list=new ArrayList<>();
		for(Obstacle ob:mainMapList.getObstacles()){
			if(ob.getContent()==4&&ob.getX()>=250&&ob.getX()<=300&&ob.getY()>=22*25&&ob.getY()<=23*25){
				list.add(ob);
				mainMap.remove(ob);
			}
		}
		mainMapList.getObstacles().removeAll(list);
		for(int i=10;i<13;i++) //老窝保护墙
		{
			addIronWall(mainMap,i,22);
		}
		addIronWall(mainMap,10,23);
		addIronWall(mainMap,12,23);
		repaint();
	}

	//根据数组添加地图
	public void paintElement(MainMap mainMapList) {
		for(int i=0;i<24;i++) {
			for(int j=0;j<24;j++) {
				if(mainMapList.getMapList(i,j)==3)
					paintSingleElement(i,j);
			}
		}
		for(int i=0;i<24;i++) {
	    	for(int j=0;j<24;j++) {
	    		if(mainMapList.getMapList(i,j)!=3)
	    			paintSingleElement(i,j);
	    	}
		}
	}

	public void paintSingleElement(int i,int j) {
		switch(mainMapList.getMapList(i, j)) {
		case 0:break;
		case 3:addGrass(i,j);
			   break;
		case 4:addBrickWall(mainMap,i,j);
		       break;
		case 5:addIronWall(mainMap,i,j);
		       break;
		case 6:addHomeImage(i,j);
		       break;
		case 7:addRiver(i,j);
		       break;
		case 8:addIce(i,j);
		       break;
		}
	}

	/**
	 * 随机的向地图中添加一个敌方坦克
	 */
	public void addEnemyTankAutomatically(){
		while(true) {
			int x=(int)(Math.random()*24);
			if(mainMapList.getMapList(x,2)==0){
				addEnemyTank(mainMap,x*25,2);
				break;
			}
		}
		setCanAddEnemyTank(false);
		new Thread(timeController).start();
	}
	
	//添加不同地图元素
	public void addHomeImage(int x,int y) {
		this.addImage(mainMap, "src/img/map/home.GIF", x*25, y*25);
	}
	public void addBrickWall(int x,int y) {
		this.addImage(mainMap,"src/img/map/brickwall.GIF",x*25,y*25);
	}
	public void addIronWall(int x,int y) {
		this.addImage(mainMap,"src/img/map/ironwall.GIF",x*25,y*25);
	}
	public void addGrass(int x,int y) {
		this.addImage(mainMap,"src/img/map/grass.GIF",x*25,y*25);
	}
	public void addIce(int x,int y) {
		this.addImage(mainMap,"src/img/map/ice.GIF",x*25,y*25);
	}
	public void addRiver(int x,int y) {
		this.addImage(mainMap,"src/img/map/river.GIF",x*25,y*25);
	}
	public Boolean getCanAddEnemyTank() {
		return canAddEnemyTank;
	}

	public void setCanAddEnemyTank(Boolean canAddEnemyTank) {
		this.canAddEnemyTank = canAddEnemyTank;
	}

	public abstract void showGameOverUI();
	//更新计分板
	public void addScore(PlayerTank tank) {
		if(tank instanceof PlayerOne) {
			score1=tank.getScore()+1;
		}else if(tank instanceof PlayerTwo) {
			score2=tank.getScore()+1;
		}
	}

	public void minusScore(PlayerTank tank) {
		if(tank instanceof PlayerOne) {
			if(score1>0) {
				score1=tank.getScore()-1;
			}
		}else if(tank instanceof PlayerTwo) {
			if(score2>0) {
				score2=tank.getScore()-1;
			}
		}
	}

	public void updateScoreLabel(PlayerTank tank,int snum) {}

	//更新生命值
	public void addLife(PlayerTank tank) {
		if(tank instanceof PlayerOne) {
			life1=tank.getLife()+1;
		}else if(tank instanceof PlayerTwo) {
			life2=tank.getLife()+1;
		}
	}
	public void minusLife(PlayerTank tank) {
		if(tank instanceof PlayerOne) {
			if(life1>0) {
				life1=tank.getScore()-1;
			}
		}else if(tank instanceof PlayerTwo) {
			if(life2>0) {
				life2=tank.getScore()-1;
			}
		}
	}
	public void updateLifeLabel(PlayerTank tank,int lnum) {}

	public void emptyEnemyList() {
		this.enemyTankList.clear();
	}

	public ArrayList<EnemyTank> getEnemyList(){
		return this.enemyTankList;
	}
}
