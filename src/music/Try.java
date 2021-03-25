package music;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;

public class Try extends JFrame implements MouseListener {

    JButton start=new JButton("开始");
    JButton stop=new JButton("结束");
    Music m2=new Music("./src/music/Begin2.wav");

    public Try() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.setSize(new Dimension(500,500));
        this.setLayout(new BorderLayout());

        start.addMouseListener(this);
        stop.addMouseListener(this);
        this.add(start,BorderLayout.NORTH);
        this.add(stop,BorderLayout.SOUTH);
        this.setVisible(true);
    }
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Try t=new Try();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getSource()==start)
        {
            System.out.println("play");
            new Thread(m2).start();
        }
        else if(mouseEvent.getSource()==stop){
            try {
                m2.musicClose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}


