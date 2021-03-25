package map;

import obstacle.Tower;
import tank.BossTwo;
import tank.EnemyTank;
import tank.PlayerTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 单人游戏地图
 *
 */
public class SinglePlayerUI extends GameMapUI {
	private JLabel score;
	private JLabel life;
	private JLabel level;
	private int scoremark;
	
	//当前关卡数
	private int levelnum=1;
	//通过一个关卡所需分数
	private final int standard=2;
	//总关卡数
	private final int totalLevel=3;
	
	public SinglePlayerUI() {}
	
	//主地图初始化
	public void initMainMap() {
		this.mainMapList=new MainMap(mainMap,this);
		this.mainMapList.initRandomMapList(10,10,10,10,10);
		//this.addTank();
		this.paintElement(mainMapList);
	}

	/**
	 * 坦克角色初始化
	 */
	public void addTank() {
		addPlayerTankOne(mainMap,9*25,23*25,false);
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
		if(levelnum==3) {
			addBossTwo();
		}
		playerOne.setScore(scoremark);
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
		String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
		String style2="</div></body></html>";
		String style3="<html><body><div style='color:#006699;font-size:18px;'>";
		JLabel scorel=new JLabel(style1+"分数"+style2,SwingConstants.CENTER);
		JLabel lifel=new JLabel(style1+"生命"+style2,SwingConstants.CENTER);
		JLabel levell=new JLabel(style1+"关卡"+style2,SwingConstants.CENTER);
		level=new JLabel(style1+"1"+style2,SwingConstants.CENTER);
		score=new JLabel(style1+"0"+style2,SwingConstants.CENTER);
		life=new JLabel(style1+"2"+style2,SwingConstants.CENTER);
		JLabel returnBtn=new JLabel(style3+"返回主菜单"+style2);
		returnBtn.setBorder(BorderFactory.createLineBorder(Color.blue));

		returnBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				dispose();
				new StartGameUI();
			}
		});
		
		levell.setBounds(25,30,80,50);
		level.setBounds(105,30,50,50);
		scorel.setBounds(25,100,80,50);
		score.setBounds(105,100,50,50);
		lifel.setBounds(25,170,80,50);
		life.setBounds(105,170,50,50);
		returnBtn.setBounds(30,530,120,30);
			
		secondaryMap.add(level);
		secondaryMap.add(levell);
		secondaryMap.add(scorel);
		secondaryMap.add(score);
		secondaryMap.add(lifel);
		secondaryMap.add(life);
		secondaryMap.add(returnBtn);
	}
	
	@Override
	public void updateScoreLabel(PlayerTank tank, int snum) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:20px;'>";
		String style2="</div></body></html>";
		
		//根据分数判断关卡跳转
		if(judgeAdvance(snum)) {
			this.scoremark=playerOne.getScore();
			mainMapList.removePlayerTank(playerOne);
			ArrayList<EnemyTank> enemyList=this.getEnemyList();
			
			for(EnemyTank etank : enemyList) {
				etank.emptyProp();
				etank.setLife(0);
				mainMapList.removeEnemyTank(etank);
			}
			tower.clear();
			RepaintMap repaintMap=new RepaintMap(mainMap,this);
			showGameWinUI(++levelnum);
			updateLevel(levelnum);
		}
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
		new GameOverUI(playerOne.getScore(),0);
		dispose();
	}
	
	public void showGameWinUI(int level) {
		if(level>this.totalLevel) {
			new GameWinUI(playerOne.getScore(),0);
			dispose();
		}
	}
	
	
}
