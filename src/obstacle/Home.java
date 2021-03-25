package obstacle;

import map.MainMap;

public class Home extends Obstacle {


    public Home(int x, int y, MainMap mainMap){
        super(x,y,mainMap);
        setContent(6);
    }

}
