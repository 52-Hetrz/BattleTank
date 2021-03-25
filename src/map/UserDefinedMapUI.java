package map;

import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 玩家自定义地图游戏界面
 *
 */
public class UserDefinedMapUI extends GameMapUI {
	private int usr_x;
	private int usr_y;
	private JLabel score;
	private JLabel life;
	
	public UserDefinedMapUI() {}
	
	//主地图初始化
	public void initMainMap() {
		this.mainMapList=new MainMap(mainMap,this);
		this.mainMapList.emptyMapList();
		this.paintElement(mainMapList);
	}
	
	/**
	 * 坦克角色添加
	 */
	public void addTank() {
		mainMap.removeAll();
		mainMap.repaint();
		paintElement(mainMapList);
		mainMap.revalidate();
		addPlayerTankOne(mainMap,9*25,23*25,false);
		addEnemyTank(mainMap,50,50);
	}

	//副地图初始化
	public void initSecondaryMap() {
		String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
		String style3="<html><body><div style='color:#006699;font-size:20px;'>";
		String style2="</div></body></html>";
		String style4="<html><body><div style='color:#339966;font-size:15px;'>";
			
		JLabel scorel=new JLabel(style1+"分数"+style2,SwingConstants.CENTER);
		JLabel lifel=new JLabel(style1+"生命"+style2,SwingConstants.CENTER);
		score=new JLabel(style1+"0"+style2,SwingConstants.CENTER);
		life=new JLabel(style1+"2"+style2,SwingConstants.CENTER);
			
		JLabel mapElement=new JLabel(style1+"地图添加"+style2,SwingConstants.CENTER);
		JLabel returnBtn=new JLabel(style4+"返回主菜单"+style2);
		returnBtn.setBorder(BorderFactory.createLineBorder(Color.blue));

		returnBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				dispose();
				new StartGameUI();
			}
		});

		JLabel startGameBtn=new JLabel(style3+"开始游戏"+style2);
		startGameBtn.setBorder(BorderFactory.createLineBorder(Color.blue));
		//测试
		scorel.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 //addScore(score,style1,style2);
			 }
		});
		
		
		startGameBtn.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 addTank();
			 }
		});
		
		mapElement.setBounds(25, 250, 130, 70);
		scorel.setBounds(25,100,80,50);
		score.setBounds(105,100,50,50);
		lifel.setBounds(25,170,80,50);
		life.setBounds(105,170,50,50);
		startGameBtn.setBounds(30,430,120,30);
		returnBtn.setBounds(30,530,120,30);
		startGameBtn.setBounds(40,430,120,30);
		
		secondaryMap.add(mapElement);
		secondaryMap.add(scorel);
		secondaryMap.add(score);
		secondaryMap.add(lifel);
		secondaryMap.add(life);
		secondaryMap.add(startGameBtn);
		secondaryMap.add(returnBtn);
		
		addUserDefineMap(secondaryMap,"src/img/map/ironwall.GIF",25,330);
		addUserDefineMap(secondaryMap,"src/img/map/brickwall.GIF",80,330);
		addUserDefineMap(secondaryMap,"src/img/map/grass.GIF",135,330);
		addUserDefineMap(secondaryMap,"src/img/map/ice.GIF",25,370);
		addUserDefineMap(secondaryMap,"src/img/map/river.GIF",80,370);
		addUserDefineMap(secondaryMap,"src/img/map/empty.png",135,370);
	}
	
	/**
	 * 副地图添加供玩家选择地图元素
	 * @param mainMap 主地图
	 * @param imgpath 地图图片路径
	 * @param x 地图元素在副地图中x坐标
	 * @param y 地图元素在副地图中y坐标
	 */
	public void addUserDefineMap(JPanel mainMap,String imgpath,int x,int y) {
		ImageIcon pic=new ImageIcon(imgpath);
		//图片填充自适应大小
		pic=new ImageIcon(pic.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT));
		JLabel l=new JLabel();
		
		l.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 if(imgpath.equals("src/img/map/brickwall.GIF"))
					 addUserMap(4);
				 else if(imgpath.equals("src/img/map/ironwall.GIF")) 
					 addUserMap(5);
				 else if(imgpath.equals("src/img/map/grass.GIF")) 
					 addUserMap(3);
				 else if(imgpath.equals("src/img/map/ice.GIF")) 
					 addUserMap(8);
				 else if(imgpath.equals("src/img/map/river.GIF")) 
					 addUserMap(7);
				 else if(imgpath.equals("src/img/map/empty.png"))
					 addUserMap(0);
			 }
		});
		
		l.setBounds(x,y,25, 25);
		l.setIcon(pic);
		mainMap.add(l);
	}
	
	/**
	 * 向主地图添加玩家所选择元素
	 * @param content 地图元素标号
	 */
	public void addUserMap(int content) {
		this.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 if (e.getButton() == e.BUTTON1) {
					 usr_x=e.getX();
					 usr_y=e.getY();
				 }
				 //更新地图数组
				 mainMapList.setMapList((int)(usr_x/24-0.5), (int)((usr_y-39)/24), content);
				 mainMapList.addObstacleList((int)(usr_x/24-0.5), (int)((usr_y-39)/24));
				 mainMap.removeAll();
				 mainMap.repaint();
				 paintElement(mainMapList);
				 mainMap.revalidate();
			 }
		});
	}
	
	@Override
	public void updateScoreLabel(PlayerTank tank, int snum) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
		String style2="</div></body></html>";
		score.setText(style1+snum+style2);
		//System.out.println(snum);
	}
	@Override
	public void updateLifeLabel(PlayerTank tank, int lnum) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
		String style2="</div></body></html>";
		life.setText(style1+lnum+style2);
		//System.out.println(lnum);
	}
	
	@Override
	public void showGameOverUI() {
		new GameOverUI(playerOne.getScore(),0);
		dispose();
	}
	
}
