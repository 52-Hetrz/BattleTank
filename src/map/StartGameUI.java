package map;


import music.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartGameUI extends MapUI {
	private JPanel titlePanel;
	private JPanel choosePanel;
	Music beginMusic=new Music(".\\src\\music\\Begin2.wav");

	public StartGameUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(beginMusic).start();
	}
	
	public void addElement() {
		titlePanel=new JPanel();
		choosePanel=new JPanel();
		titlePanel.setLayout(null);
		choosePanel.setLayout(null);
		titlePanel.setPreferredSize(new Dimension(800, 300));
		choosePanel.setPreferredSize(new Dimension(800, 300));
		
		titlePanel.setBackground(Color.BLACK);
		choosePanel.setBackground(Color.BLACK);
		
		//游戏标题图片
		addTitleImage(50,150);
		//模式选择按钮
		addChooseButton();
		
		//主容器填充
		JPanel sum=new JPanel();
		sum.setLayout(new BorderLayout());
		sum.add(titlePanel,BorderLayout.NORTH);
		sum.add(choosePanel,BorderLayout.SOUTH);
		this.setContentPane(sum);
	}

	//添加游戏标题图片
	public void addTitleImage(int x,int y) {
		ImageIcon pic=new ImageIcon("./src/img/start/pres.png");
		pic=new ImageIcon(pic.getImage().getScaledInstance(700, 100,Image.SCALE_DEFAULT));
		JLabel l=new JLabel();
		l.setBounds(x,y,700, 100);
		l.setIcon(pic);
		titlePanel.add(l);
	}
	
	//添加模式选择按钮
	public void addChooseButton() {
		String style1="<html><body><div style='color:#606060;font-size:25px;'>";
		String style3="<html><body><div style='color:#606060;font-size:20px;'>";
		String style2="</div></body></html>";
		
		JLabel lb1=new JLabel(style1+"单人闯关"+style2,SwingConstants.CENTER);
		JLabel lb2=new JLabel(style1+"双人对战"+style2,SwingConstants.CENTER);
		JLabel lb3=new JLabel(style1+"地图自定义"+style2,SwingConstants.CENTER);
		JLabel lb4=new JLabel(style1+"无尽挑战"+style2,SwingConstants.CENTER);
		JLabel instruction=new JLabel(style3+"游戏说明"+style2,SwingConstants.CENTER);
		
		lb1.setBounds(280,30,200, 50);
		lb2.setBounds(280,80,200, 50);
		lb3.setBounds(280,130,200, 50);
		lb4.setBounds(280,180,200, 50);
		instruction.setBounds(620,240,140,40);
		instruction.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		choosePanel.add(lb1);
		choosePanel.add(lb2);
		choosePanel.add(lb3);
		choosePanel.add(lb4);
		choosePanel.add(instruction);
		
		//打开游戏说明
		instruction.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				new InstructionUI();
			}
		});
		
		//单人闯关
		lb1.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 dispose();
				 try {
					 beginMusic.musicClose();
				 } catch (InterruptedException ex) {
					 ex.printStackTrace();
				 }
				 new SinglePlayerUI();
			 }
		});
		//双人对战
		lb2.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 dispose();
				 try {
					 beginMusic.musicClose();
				 } catch (InterruptedException ex) {
					 ex.printStackTrace();
				 }
				 new DoublePlayersUI(true);
			 }
		});
		//地图自定义
		lb3.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 dispose();
				 try {
					 beginMusic.musicClose();
				 } catch (InterruptedException ex) {
					 ex.printStackTrace();
				 }
				 new UserDefinedMapUI();
			 }
		});
		//无尽挑战
		lb4.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e){
				 dispose();
				 try {
					 beginMusic.musicClose();
				 } catch (InterruptedException ex) {
					 ex.printStackTrace();
				 }
				 new DoublePlayersUI(false);
			 }
		});
	}

	public static void main(String[] args) {
		new StartGameUI();
	}
}
