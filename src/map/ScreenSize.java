package map;

import java.awt.Dimension;
import java.awt.Toolkit;
/**
 * 获取屏幕大小
 *
 */
public class ScreenSize {
	private int screenSizeWidth;
	private int screenSizeHeight;
	
	public ScreenSize() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.screenSizeWidth = (int) dimension.getWidth();
		this.screenSizeHeight = (int) dimension.getHeight();
	}
	
	public int getScreenSizeWidth() {
		return this.screenSizeWidth;
	}
	public int screenSizeHeight() {
		return this.screenSizeHeight;
	}
}
