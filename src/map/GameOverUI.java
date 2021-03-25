package map;

import javax.swing.*;
import java.awt.*;

public class GameOverUI extends MapUI {
	private JPanel titlePanel;
	private JPanel scorePanel;
	private int s1;
	private int s2;
	private JLabel score1;
	private JLabel score2;
	
	public GameOverUI(int s1, int s2) {
		this.s1=s1;
		this.s2=s2;
		this.setScore(s1, s2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addElement() {
		titlePanel=new JPanel();
		scorePanel=new JPanel();
		titlePanel.setLayout(null);
		scorePanel.setLayout(null);
		titlePanel.setPreferredSize(new Dimension(800, 300));
		scorePanel.setPreferredSize(new Dimension(800, 300));
		
		titlePanel.setBackground(Color.BLACK);
		scorePanel.setBackground(Color.BLACK);
		
		//添加游戏结束标题图片
		addTitleImage(215,150);
		//玩家分数面板
		addScorePanel();
		
		JPanel sum=new JPanel();
		sum.setLayout(new BorderLayout());
		sum.add(titlePanel,BorderLayout.NORTH);
		sum.add(scorePanel,BorderLayout.SOUTH);
		this.setContentPane(sum);
	}
	
	//添加游戏结束标题图片
	public void addTitleImage(int x,int y) {
		ImageIcon pic=new ImageIcon("./src/img/over/gameover.png");
		pic=new ImageIcon(pic.getImage().getScaledInstance(350, 100,Image.SCALE_DEFAULT));
		JLabel title=new JLabel();
		title.setBounds(x,y,350,100);
		title.setIcon(pic);
		titlePanel.add(title);
	}

	//添加玩家分数显示
	public void addScorePanel() {
		String style1="<html><body><div style='color:#D8D8D8;font-size:23px;'>";
		String style2="</div></body></html>";
		String style3="<html><body><div style='color:#339966;font-size:23px;'>";
		
		JLabel player1=new JLabel(style1+"玩家1"+style2,SwingConstants.CENTER);
		JLabel scorel1=new JLabel(style1+"分数"+style2,SwingConstants.CENTER);
		score1=new JLabel(style1+this.s1+style2,SwingConstants.CENTER);
		
		JLabel player2=new JLabel(style3+"玩家2"+style2,SwingConstants.CENTER);
		JLabel scorel2=new JLabel(style3+"分数"+style2,SwingConstants.CENTER);
		score2=new JLabel(style3+this.s2+style2,SwingConstants.CENTER);
		
		player1.setBounds(170, 30, 150, 50);
		scorel1.setBounds(170, 80, 150, 50);
		score1.setBounds(170, 130, 150, 50);
		player2.setBounds(460, 30, 150, 50);
		scorel2.setBounds(460, 80, 150, 50);
		score2.setBounds(460, 130, 150, 50);
		
		scorePanel.add(player1);
		scorePanel.add(scorel1);
		scorePanel.add(score1);
		scorePanel.add(player2);
		scorePanel.add(scorel2);
		scorePanel.add(score2);
		
	}
	
	public void setScore(int score1,int score2) {
		String style1="<html><body><div style='color:#D8D8D8;font-size:23px;'>";
		String style2="</div></body></html>";
		String style3="<html><body><div style='color:#339966;font-size:23px;'>";
		
		this.score1.setText(style1+score1+style2);
		this.score2.setText(style3+score2+style2);
	}
	
}
