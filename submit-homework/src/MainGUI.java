import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;



/**
 * Created by inksmallfrog on 10/2/15.
 */
public class MainGUI extends JFrame{
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGui frame = new LoginGui();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setTitle("成绩爬取--登录");
            }
        });
    }

    public static void EnterTableGui(){
        TableGui frame = new TableGui();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("成绩爬取--计算");

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                frame.Reset();
            }
        });
    }
}