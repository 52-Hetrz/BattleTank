package map;

import javax.swing.JFrame;

//窗口父类
public abstract class MapUI extends JFrame{
	private int windowWidth;
	private int windowHeight;
	private int screenSizeWidth;
	private int screenSizeHeight;
	
	public MapUI() {
		super("Battle City");
		this.setSize(800, 639);//窗口大小
		this.setLayout(null);//默认布局设空
		this.setResizable(false);//不可缩放
		init();
		addElement();
		this.setVisible(true);
	}

	//实现窗口居中
	public void init() {
		ScreenSize wsize=new ScreenSize();
		screenSizeWidth=wsize.getScreenSizeWidth();
		screenSizeHeight=wsize.screenSizeHeight();
		windowWidth=this.getWidth();
		windowHeight=this.getHeight();
		this.setLocation(screenSizeWidth/2-windowWidth/2,screenSizeHeight/2-windowHeight/2);
	}

	//添加界面元素
	public abstract void addElement();
}
