package sunshineManager;
import java.awt.*;
import javax.swing.*;

//Class to enable Display property unique to Trucks (Load capacity in this Case)

public class Truck extends javax.swing.JFrame
{
    private JTextField loadcapacityfield;
    private JLabel loadcapacitylabel;

     double loadvalue;

    public Truck(double load_fromVehicleClass)
            
    {

      loadvalue=load_fromVehicleClass;
       Container cont=getContentPane();
       cont.setLayout(new FlowLayout());

      cont.add( loadcapacitylabel=new JLabel("Maximum Load:"));
      loadcapacityfield=new JTextField(10);
      loadcapacityfield.setText(String.valueOf(loadvalue));
       cont.add(loadcapacityfield);

     setSize(300,300);
     setLocation(380,380);
     setResizable(false);
     cont.setBackground(Color.LIGHT_GRAY);
    }
}
