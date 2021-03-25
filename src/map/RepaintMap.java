package map;

import javax.swing.*;

public class RepaintMap extends SwingWorker<Void,Void>{
	private JPanel mainMap;
	private GameMapUI thisMap;
	public RepaintMap(JPanel mainMap,GameMapUI thisMap) {
		setMainMap(mainMap);
		setThisMap(thisMap);
		this.execute();
	}

	@Override
	protected Void doInBackground() throws Exception {
		this.mainMap.removeAll();
		this.mainMap.repaint();
		this.thisMap.initMainMap();
		this.mainMap.revalidate();
		return null;
	}

	public void setMainMap(JPanel mainMap) {
		this.mainMap=mainMap;
	}

	public void setThisMap(GameMapUI thisMap) {
		this.thisMap=thisMap;
	}
}