
package sunshineManager;

import java.awt.*;
import javax.swing.*;


public class Car extends javax.swing.JFrame
{
    private JTextField seatsnofield;
    private JLabel seatslabel;

     int seatno;

    public Car(int seatNo_fromVehicleClass)
  
    {

      seatno=seatNo_fromVehicleClass;
       Container cont=getContentPane();
       cont.setLayout(new FlowLayout());

      cont.add( seatslabel=new JLabel("Available Seats"));
      seatsnofield=new JTextField(10);
      seatsnofield.setText(String.valueOf(seatno));
       cont.add(seatsnofield);
      cont.setBackground(Color.cyan);
      setResizable(false);
      setLocation(380,380);
      setSize(300,100);
    }

}

