
package sunshineManager;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Employee extends javax.swing.JFrame
{
    private JTextField classfield;
    private JTextField namefield;
    private JTextField  vehregnofield;
    private JTextField allocationStatusfield;
    private JLabel personalFileNo;
    private JProgressBar progressBar;
    private JComboBox combo;
    private final JLabel namelabel;
    private final JLabel driveLabel;
    private final JLabel vehicleLabel;
    private final JLabel allocationStatusLabel;
    private String selectedguipfnumber;
//    private JButton helpbutton;
    private final JButton helpButton;
    Connection conn=null;
    Statement stmnt=null;
    

    public Employee()
    {
       Container cont=getContentPane();
        cont.setLayout(new FlowLayout());

        personalFileNo = new JLabel("PF Number");
        cont.add( personalFileNo);

        combo = new JComboBox();
        combo.setModel(new DefaultComboBoxModel(new String[] { "  ","101", "102", "103", "104", "105", "106",
                                                                  "107", "108", "109", "110", "111", " " }));
        cont.add(new JLabel("                "));

        cont.add(combo);
         combo.addItemListener (new ItemListener()
           {
              public void itemStateChanged( ItemEvent event )
           {
              comboEvent();
            }
            });
            cont.add(new JLabel("                 "));
        cont.add(namelabel=new JLabel("Name:"));
        namefield = new JTextField(20);
        cont.add(namefield);


        cont.add(driveLabel=new JLabel("DRIVE CLASS:"));
        classfield = new JTextField(20);
        classfield.setEditable(false);
        cont.add(classfield);

        cont.add(allocationStatusLabel=new JLabel("Allocated:"));
        cont.add(allocationStatusfield=new JTextField(20));

        cont.add(vehicleLabel=new JLabel("Vehicle RegNumber:"));

         vehregnofield=new JTextField(20);
           cont.add(vehregnofield);
         //vehregnofield.setVisible(true);
       helpButton=new JButton("HELP");
       helpButton.setBounds(300,50,14, 14);
       cont.add(new JLabel("                                         "));
       cont.add(helpButton);
        helpButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
               JOptionPane.showMessageDialog(null, "Select the employee number to get details "
                       ,"Message",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        inactivateComponents();

        progressBar=new JProgressBar();
        progressBar.setPreferredSize(new java.awt.Dimension(140,10));
        progressBar.setBackground(Color.getHSBColor(200, 50, 50));
        progressBar.setForeground(Color.getHSBColor(10, 200, 200));
        progressBar.setVisible(false);

        cont.add(progressBar);
        setSize(250,300);
        setLocation(380,380);
        setResizable(false);
      //  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cont.setBackground(Color.getHSBColor(100, 100, 250));
    }
    public void inactivateComponents()
    {
            namelabel.setVisible(false);
            namefield.setVisible(false);
            driveLabel.setVisible(false);
             classfield.setVisible(false);
             vehicleLabel.setVisible(false);
            vehregnofield.setVisible(false);
            allocationStatusLabel.setVisible(false);
            allocationStatusfield.setVisible(false);
            helpButton.setVisible(true);
    }

     public void activateComponents()
     {
        namelabel.setVisible(true);
            namefield.setVisible(true);
            driveLabel.setVisible(true);
             classfield.setVisible(true);
             vehicleLabel.setVisible(true);
            vehregnofield.setVisible(true);
            allocationStatusLabel.setVisible(true);
            allocationStatusfield.setVisible(true);
            helpButton.setVisible(false);
     }
public void comboEvent()
    {
         selectedguipfnumber=combo.getSelectedItem().toString();
     try
     {
         Class.forName("com.mysql.jdbc.Driver");
         String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
         String user="root";
         String pw="";
         
         conn=DriverManager.getConnection(url, user, pw);
                   stmnt = conn.createStatement();

                   ResultSet rs=stmnt.executeQuery("Select * FROM employee_details where Emp_PF='"+ selectedguipfnumber+"'");
              
                   while(rs.next())
                   {

                      Thread progress=new Thread(new ProgressBar());
                      progress.start();
                      progressBar.setVisible(true);

                    namefield.setText(rs.getString("Name"));
                    classfield.setText(rs.getString("drive_class"));
                    allocationStatusfield.setText(rs.getString("status"));
                   
                    if(rs.getString("vehicle_No").equals(" "))
                    {
                      vehregnofield.setText("None");
                    }
                    else
                    {
                        vehregnofield.setText(rs.getString("vehicle_No"));
                    }
                   
    }

     }
        catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
     catch (ClassNotFoundException ex) {
         
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
     
activateComponents();
}
 private class ProgressBar implements Runnable
   {
        @Override
        public void run() {
            int i = 0;

            progressBar.setVisible(true);
            while (i < 10000)
            {
                progressBar.setValue(i);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                i += 2;
            }
            progressBar.setValue(progressBar.getMinimum());
        }

    }
}