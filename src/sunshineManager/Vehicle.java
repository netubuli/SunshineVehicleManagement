package sunshineManager;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vehicle extends JFrame
{
private JComboBox regCombo;
private JTextField vMakeF,vModelF,vd_classF,a_statusF;
private JLabel VmakeLabel, VmodelLabel , VdriveclassLabel,regnolabel,allocStatusLabel;
private String selectedregnumber;
private  JButton helpbutton,seatButton,loadbutton;

int numberofseats ;  //values inheritable by either Truck class or car class
double loadCapacity;
    private final JButton helpButton;

    public Vehicle()
    {
         Container cont=getContentPane();
         cont.setLayout(new FlowLayout());


             cont.add(regnolabel=new JLabel("Vehicle RegNumber:"));
             cont.add(new JLabel("    "));
             regCombo= new JComboBox();
             regCombo.setModel(new DefaultComboBoxModel(new String[] {"  ","KAC125","KAR233","KAB122","KAV124",
                                                                    "KAL233","KAC233","KAG188","KAB133",
                                                                     "KAL441","KAR144","KAV101" ,}));
             cont.add(regCombo);
            // regCombo.addActionListener();
             regCombo.addActionListener(new ActionListener()
             {
            public void actionPerformed(ActionEvent event)
            {
                regnocomboEvent();//method to handle event.
            }});

             VmakeLabel=new JLabel("Make");
             cont.add(VmakeLabel);
             vMakeF=new JTextField(20);
             cont.add(vMakeF);

              VmodelLabel=new JLabel("Model");
             cont.add(VmodelLabel);
             vModelF=new JTextField(20);
             cont.add(vModelF);
             //cont.add(new JLabel("                   "));
             cont.add(seatButton=new JButton("Capacity"));
                        seatButton.setVisible(false);
                        seatButton.addActionListener(new ActionListener()
                        {
                             public void actionPerformed(ActionEvent e)
                        {
                                  Car c=new Car(numberofseats);
                                  c.setVisible(true);
                        }
                        });

            cont.add(loadbutton=new JButton("Capacity"));
                       loadbutton.setVisible(false);
                        loadbutton.addActionListener(new ActionListener()
                        {
                             public void actionPerformed(ActionEvent e)
                        {
                            Truck t=new Truck(loadCapacity);
                            t.setVisible(true);
                        }
                        });
              cont.add(new JLabel("                   "));
               cont.add(new JLabel("                   "));
             VdriveclassLabel=new JLabel("Drive_class");
             cont.add(VdriveclassLabel);
             vd_classF = new JTextField(20);
             cont.add(vd_classF);

             allocStatusLabel=new JLabel("Allocation Status");
             cont.add(allocStatusLabel);
             a_statusF=new JTextField(20);
             cont.add( a_statusF);
            helpButton=new JButton("Help");
           helpButton.setBounds(200,50,14, 14);
           cont.add(new JLabel("                                         "));
            cont.add(helpButton);

             helpButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
               JOptionPane.showMessageDialog(null, "Select vehicle number above to get details ");
            }
        });


             setSize(250,300);
             setLocation(380,380);
             cont.setBackground(Color.getHSBColor(100, 100, 250));
             setResizable(false);
             inactivateComponents();

    }

    public void inactivateComponents()
    {
        vMakeF.setVisible(false);
        vModelF.setVisible(false);
        vd_classF.setVisible(false);
        a_statusF.setVisible(false);
        VmakeLabel.setVisible(false);
        VmodelLabel.setVisible(false);
        VdriveclassLabel.setVisible(false);
        //regnolabel.setVisible(false);
        allocStatusLabel.setVisible(false);
    }
     public void activateComponents()
    {
        vMakeF.setVisible(true);
        vModelF.setVisible(true);
        vd_classF.setVisible(true);
        a_statusF.setVisible(true);
        VmakeLabel.setVisible(true);
        VmodelLabel.setVisible(true);
        VdriveclassLabel.setVisible(true);
        //regnolabel.setVisible(false);
        allocStatusLabel.setVisible(true);
    }
     public void regnocomboEvent()
     {
     Connection conn;
     Statement stmnt;
     selectedregnumber=regCombo.getSelectedItem().toString();
     try
     {
         String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
         String user="root";
         String pw="";
         conn=DriverManager.getConnection(url,user,pw);

                   stmnt = conn.createStatement();
                   ResultSet rs=stmnt.executeQuery("Select * FROM vehicle_details where Vehicle_regNo='"+ selectedregnumber+"'");
                   while(rs.next())
                   {
                    vMakeF.setText(rs.getString("make"));
                    vModelF.setText(rs.getString("Model"));
                    if(rs.getString("Model").equals("Trooper"))
                            {
                                loadbutton.setVisible(true);
                                seatButton.setVisible(false);

                            }
                            else
                            {
                                seatButton.setVisible(true);
                                loadbutton.setVisible(false);
                            }
                    vd_classF.setText(rs.getString("drive_Class"));
                    a_statusF.setText(rs.getString("status"));
                    numberofseats=rs.getInt("seats");
                    loadCapacity=rs.getDouble("load");
                  }
                    activateComponents();
     }
        catch (SQLException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }       
     }
}
