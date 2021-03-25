package map;


import tank.EnemyTank;
import tank.PlayerOne;
import tank.PlayerTank;
import tank.PlayerTwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 双人对战游戏界面
 *
 */
public class DoublePlayersUI extends GameMapUI {
	private JLabel score1;
	private JLabel score2;
	private JLabel life1;
	private JLabel life2;
	private JLabel level;
	private int scoremark1;
	private int scoremark2;
	private int lifemark1=2;
	private int lifemark2=2;
	private Boolean hasEnd;
	
	//当前关卡数
	private int levelnum=1;
	//通过一个关卡所需分数
	private final int standard=2;
	//总关卡数
	private final int totalLevel=3;
	
	public DoublePlayersUI(Boolean hasEnd) {
		this.hasEnd=hasEnd;
		addPlayerTankOne(mainMap,9*25,23*25,true);
		addPlayerTankTwo(mainMap,13*25,23*25,true);
	}
	
	//主地图初始化
	public void initMainMap() {
		this.mainMapList=new MainMap(mainMap,this);
		this.mainMapList.initRandomMapList(5,5,5,5,5);
		//this.addTank();
		this.paintElement(mainMapList);
	}
	
	/**
	 * 添加坦克角色
	 */
	public void addTank() {
		if(lifemark1!=0) {
			addPlayerTankOne(mainMap,9*25,23*25,true);
			playerOne.setLife(lifemark1);
			playerOne.setScore(scoremark1);
		}
		if(lifemark2!=0) {
			addPlayerTankTwo(mainMap,13*25,23*25,true);
			playerTwo.setLife(lifemark2);
			playerTwo.setScore(scoremark2);
		}
		
		
		addEnemyTank(mainMap,50,50);
	}

	//根据数组添加地图
	public void paintElement(MainMap mainMapList) {
		for(int i=0;i<24;i++) {
			for(int j=0;j<24;j++) {
				if(mainMapList.getMapList(i,j)==3)
					paintSingleElement(i,j);
			}
		}
		
		addTank();
		
		for(int i=0;i<24;i++) {
			for(int j=0;j<24;j++) {
				if(mainMapList.getMapList(i,j)!=3)
					paintSingleElement(i,j);
			}
		}
	}
	/**
	 * 副地图初始化
	 */
	public void initSecondaryMap() {
		String style1="<html><body><div style='color:#D8D8D8;font-size:15px;'>";
		String style2="</div></body></html>";
		String style3="<html><body><div style='color:#339966;font-size:15px;'>";
		//玩家1侧栏
		JLabel player1=new JLabel(style1+"玩家1"+style2,SwingConstants.CENTER);
		JLabel scorel1=new JLabel(style1+"分数"+style2,SwingConstants.CENTER);
		JLabel lifel1=new JLabel(style1+"生命"+style2,SwingConstants.CENTER);
		score1=new JLabel(style1+"0"+style2,SwingConstants.CENTER);
		life1=new JLabel(style1+"2"+style2,SwingConstants.CENTER);
		
		//玩家2侧栏
		JLabel player2=new JLabel(style3+"玩家2"+style2,SwingConstants.CENTER);
		JLabel scorel2=new JLabel(style3+"分数"+style2,SwingConstants.CENTER);
		JLabel lifel2=new JLabel(style3+"生命"+style2,SwingConstants.CENTER);
		score2=new JLabel(style3+"0"+style2,SwingConstants.CENTER);
		life2=new JLabel(style3+"2"+style2,SwingConstants.CENTER);
		
		JLabel levell=new JLabel(style1+"关卡"+style2,SwingConstants.CENTER);
		level=new JLabel(style1+"1"+style2,SwingConstants.CENTER);
		JLabel returnBtn=new JLabel(style3+"返回主菜单"+style2);
		returnBtn.setBorder(BorderFactory.createLineBorder(Color.blue));

		returnBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				dispose();
				new StartGameUI();
			}
		});
		
		player1.setBounds(28,50,80,50);
		scorel1.setBounds(25,90,80,50);
		score1.setBounds(105,90,50,50);
		lifel1.setBounds(25,140,80,50);
		life1.setBounds(105,140,50,50);
		
		player2.setBounds(28,190,80,50);
		scorel2.setBounds(25,230,80,50);
		score2.setBounds(105,230,50,50);
		lifel2.setBounds(25,280,80,50);
		life2.setBounds(105,280,50,50);
		
		levell.setBounds(25,15,80,50);
		level.setBounds(105,15,50,50);
		returnBtn.setBounds(30,530,120,30);

		
		secondaryMap.add(player1);
		secondaryMap.add(scorel1);
		secondaryMap.add(score1);
		secondaryMap.add(lifel1);
		secondaryMap.add(life1);
		secondaryMap.add(player2);
		secondaryMap.add(scorel2);
		secondaryMap.add(score2);
		secondaryMap.add(lifel2);
		secondaryMap.add(life2);
		secondaryMap.add(level);
		secondaryMap.add(levell);
		secondaryMap.add(returnBtn);
	}
	
	@Override
	public void updateScoreLabel(PlayerTank tank, int snum) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:15px;'>";
		String style2="</div></body></html>";
		String style3="<html><body><div style='color:#339966;font-size:15px;'>";
		if(tank instanceof PlayerOne) {
			score1.setText(style1+snum+style2);
		}else if(tank instanceof PlayerTwo) {
			score2.setText(style3+snum+style2);
		}
		
		if(judgeAdvance(playerOne.getScore()+playerTwo.getScore())) {
			this.scoremark1=playerOne.getScore();
			this.scoremark2=playerTwo.getScore();
			this.lifemark1=playerOne.getLife();
			this.lifemark2=playerTwo.getLife();
			mainMapList.removePlayerTank(playerOne);
			mainMapList.removePlayerTank(playerTwo);
			
			ArrayList<EnemyTank> enemyList=this.getEnemyList();
			for(EnemyTank etank : enemyList) {
				etank.emptyProp();
				etank.setLife(0);
				mainMapList.removeEnemyTank(etank);
			}
			RepaintMap repaintMap=new RepaintMap(mainMap,this);
			
			if(hasEnd) {
				showGameWinUI(++levelnum);
			}
			
			updateLevel(levelnum);
		}
		
	}
	@Override
	public void updateLifeLabel(PlayerTank tank, int lnum) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:15px;'>";
		String style2="</div></body></html>";
		String style3="<html><body><div style='color:#339966;font-size:15px;'>";
		if(tank instanceof PlayerOne) {
			life1.setText(style1+lnum+style2);
		}else if(tank instanceof PlayerTwo) {
			life2.setText(style3+lnum+style2);
		}
		
	}
	
	/**
	 * 更新关卡数
	 * @param l 更新目的关卡
	 */
	public void updateLevel(int l) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
		String style2="</div></body></html>";
		level.setText(style1+l+style2);
	}
	
	/**
	 * 根据分数判断是否进入下一关
	 * @param score 现分数
	 * @return 是否下一关
	 */
	public boolean judgeAdvance(int score) {
		if(score>=standard*levelnum) {
			return true;
		}
		return false;
	}
	
	@Override
	public void showGameOverUI() {
		new GameOverUI(playerOne.getScore(),playerTwo.getScore());
		dispose();
	}
	
	public void showGameWinUI(int level) {
		if(level>this.totalLevel) {
			new GameWinUI(playerOne.getScore(),0);
			dispose();
		}
	}
}
