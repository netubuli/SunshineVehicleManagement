package sunshineManager;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.JProgressBar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Allocation extends JFrame implements ItemListener
{

    private final JTextField classfield;
    private final JTextField namefield;
    private final JButton ok;
    private final JLabel pfNumberTF;
    private JComboBox pfcombo;
    private final JComboBox Vregcombo;
    private JProgressBar progressValidation;
     private String regNumber,allocatedCheck;

   private int  empDriveClass,driveclassfromdb;
    private final JButton helpButton;

    private String selectedguipfnumber;
    private final JLabel errorLabel;
    private final JLabel namelabel;
    private final JLabel driveLabel;
    private final JLabel vehicleLabel;

     private Connection conn;
     private Statement stmnt;
     private PreparedStatement psinsert,psinsert2;
     private Connection conn1;
    private Statement stmnt1;
    private ResultSet rs1;


    public Allocation()
    {
        Container cont=getContentPane();
        cont.setLayout(new FlowLayout());

        pfNumberTF = new JLabel("Employee Personal File Number");
        cont.add( pfNumberTF);

        pfcombo = new JComboBox();
        pfcombo.setModel(new DefaultComboBoxModel(new String[] {"  ", "101", "102", "103", "104", "105", "106",
                                                                  "107", "108", "109", "110", "111", " " }));
        cont.add(new JLabel("                "));

        cont.add(pfcombo);

        pfcombo.addItemListener(this);

        cont.add(new JLabel("                 "));
        cont.add(namelabel=new JLabel("Employee Name:"));
        namelabel.setVisible(false);
        namefield = new JTextField(20);
        cont.add(namefield);
        namefield.setVisible(false);


        cont.add(driveLabel=new JLabel("DRIVE  CLASS :"));
        driveLabel.setVisible(false);
        classfield = new JTextField(20);
        classfield.setVisible(false);
        cont.add(classfield);



        errorLabel=new JLabel();
        errorLabel.setVisible(false);
        errorLabel.setForeground(Color.BLUE);
        cont.add(errorLabel);

        cont.add(vehicleLabel=new JLabel("Vehicle Registration Number:"));
        vehicleLabel.setVisible(false);
        Vregcombo  = new JComboBox();
        Vregcombo.setModel(new DefaultComboBoxModel(new String[] {"   ","KAC125","KAR233","KAB122","KAV124",
                                                                    "KAL233","KAC233","KAG188","KAB133",
                                                                     "KAL441","KAR144","KAV101" ,}));

        Vregcombo.setVisible(false);
        Vregcombo.addItemListener(this);

        Vregcombo.setEnabled(false);
        cont.add(Vregcombo);

        ok = new JButton("FINISH");
        ok.setVisible(false);
        ok.setEnabled(false);
         cont.add(ok );
         ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                  try
                  {
                  selectedguipfnumber=pfcombo.getSelectedItem().toString();
                  Class.forName("com.mysql.jdbc.Driver");
                   conn = DriverManager.getConnection("jdbc:mysql://localhost/sunshineVehicleManagementDB", "root", "");
                   stmnt = conn.createStatement();

                   psinsert=conn.prepareStatement("UPDATE  employee_details  SET status= 'true',vehicle_No='"+regNumber+"' WHERE Emp_PF='"+ selectedguipfnumber+"'");
                   psinsert.executeUpdate();

                   psinsert2=conn.prepareStatement("UPDATE  vehicle_details  SET status= 'true' where Vehicle_regNo='"+regNumber+"'"  );
                   psinsert2.executeUpdate();
                    Thread progress=new Thread(new ProgressBar());
                     progress.start();
                      progressValidation.setVisible(true);
                   JOptionPane.showMessageDialog(null,"Details recorded in Employee and Vehicle Tables","Message"
                           ,JOptionPane.INFORMATION_MESSAGE);
                        progressValidation.setVisible(false);
                  }catch(Exception ex){}

                  clearControls();
                  inactivateComponents();
                  helpButton.setVisible(true);

            }


            });
       helpButton=new JButton("Help");
       helpButton.setBounds(200,50,14, 14);
       cont.add(new JLabel("                                         "));
       cont.add(helpButton);
        helpButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
               JOptionPane.showMessageDialog(null, "Click on the arrow and select employee PF number!!! ");
            }
        });
        progressValidation=new JProgressBar();
        progressValidation.setPreferredSize(new java.awt.Dimension(140,10));
        progressValidation.setBackground(Color.ORANGE);
        progressValidation.setForeground(Color.blue);
        progressValidation.setVisible(false);
         cont.add(progressValidation);
         setSize(400,250);
         setLocation(380,380);
     cont.setBackground(Color.LIGHT_GRAY);
         setResizable(false);

    }
      private void clearControls()
            {
          classfield.setText(null);
          namefield.setText(null);
          Vregcombo.setEnabled(false);
          ok.setEnabled(false);
          errorLabel.setText(null);
              }
      private void activateComponents()
      {
          namefield.setVisible(true);
          classfield.setVisible(true);
          ok.setVisible(true);
          errorLabel.setVisible(true);
          Vregcombo.setVisible(true);
          driveLabel.setVisible(true);
          vehicleLabel.setVisible(true);
           namelabel.setVisible(true);

      }

       private void inactivateComponents()
      {
          namefield.setVisible(false);
          classfield.setVisible(false);
          Vregcombo.setVisible(false);
          driveLabel.setVisible(false);
          vehicleLabel.setVisible(false);
           namelabel.setVisible(false);
          ok.setVisible(false);
          errorLabel.setVisible(false);

      }

    public void pfcomboEvent()
    {

     try
     {
                  selectedguipfnumber=pfcombo.getSelectedItem().toString();
                    Class.forName("com.mysql.jdbc.Driver");
                   conn = DriverManager.getConnection("jdbc:mysql://localhost/sunshineVehicleManagementDB", "root", "");
                   stmnt = conn.createStatement();

                   ResultSet rs=stmnt.executeQuery("Select * FROM employee_details where Emp_PF='"+ selectedguipfnumber+"'");
                   while(rs.next())
                   {
                    empDriveClass=rs.getInt("drive_class");
                    namefield.setText(rs.getString("name"));
                    classfield.setText(rs.getString("drive_class"));

                      if (rs.getString("status").equals("false") )
                      {

                          errorLabel.setText("Employee not allocated a vehicle, select a vehicle below to allocate");
                          ok.setEnabled(true);
                           Vregcombo.setEnabled(true);

                      }
                      else
                      {

                         errorLabel.setText("Employee is currently allocated a different vehicle, deallocate first");
                          ok.setEnabled(false);
                          Vregcombo.setEnabled(false);
                      }
           activateComponents();
           helpButton.setVisible(false);

                   }
    }catch(Exception ex)
    {
        Logger.getLogger(Allocation.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("you have an error"+ex.getMessage());}
    }


    public void regComboEvent()
    {
    try
    {              regNumber=Vregcombo.getSelectedItem().toString();
                   Class.forName("com.mysql.jdbc.Driver");
                   conn1 = DriverManager.getConnection("jdbc:mysql://localhost/sunshineVehicleManagementDB", "root", "");
                   stmnt1 = conn1.createStatement();
                    rs1=stmnt1.executeQuery("Select * FROM vehicle_details where Vehicle_regNo='"+ regNumber+"'");
                   while(rs1.next())
                   {
                       allocatedCheck=(rs1.getString("status"));
                       driveclassfromdb=rs1.getInt("drive_class");

                     }

                       if(allocatedCheck.equals("false"))

                       {
                                 if(driveclassfromdb==2 && empDriveClass==1)

                           {
                               JOptionPane.showMessageDialog(null, "Sorry!!! Drive class 1 employee cannot be allocated a class 2 vehicle");
                               ok.setEnabled(false);
                           }
                           else //if(rs.getString("Drive_Class").equals("1"))
                           {
                               JOptionPane.showMessageDialog(null, "Allocation approved,Click FINISH to accept");
                               ok.setEnabled(true);
                           }
                       }
                      // else if(rs1.getString("Allocated").equals("true"))
                          else if(allocatedCheck.equals("true"))
                       {
                           JOptionPane.showMessageDialog(null, "Sorry!! Vehicle Engaged for now,please choose another one");
                           ok.setEnabled(false);
                       }
                  // }
                   }
    catch(Exception ex)
            {
        Logger.getLogger(Allocation.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("you have an error"+ex.getMessage());}

    }
   private class ProgressBar implements Runnable
   {
        @Override
        public void run() {
            int i = 0;

            progressValidation.setVisible(true);
            while (i < 10000)
            {
                progressValidation.setValue(i);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                i += 2;
            }
            progressValidation.setValue(progressValidation.getMinimum());
        }
    }

    public void itemStateChanged( ItemEvent event )
    {
        if(event.getSource().equals(pfcombo))
        {
          pfcomboEvent();
        }
        else if(event.getSource().equals(Vregcombo))
        {
            regComboEvent() ;
        }
    }

}