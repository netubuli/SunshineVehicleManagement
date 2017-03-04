package sunshineManager;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class DeAllocation extends JFrame
{
    private JComboBox pfcombo;
    private JLabel regLabel, pfNumberlabel;
    private JButton actionButton,helpButton;
     private JProgressBar progressValidation;
    Connection conn;
    Statement stmnt;
    PreparedStatement psinsert1,psinsert2;
    ResultSet rs;
    private String selectedguipfnumber;
    private String deregnumber;
    private String checkIfAllocated;

    public DeAllocation()
    {
         Container cont=getContentPane();
        cont.setLayout(new FlowLayout());

        pfNumberlabel = new JLabel("Employee Personal File Number");
        cont.add( pfNumberlabel);

        pfcombo=new JComboBox();
        pfcombo.setModel(new DefaultComboBoxModel(new String[] {"  ", "101", "102", "103", "104", "105", "106",
                                                                  "107", "108", "109", "110", "111", " " }));
        cont.add(pfcombo);
       pfcombo.addItemListener(new ItemListener()
       {

         public void itemStateChanged( ItemEvent event )
        {

              try
            {
                  helpButton.setVisible(false);
                  selectedguipfnumber=pfcombo.getSelectedItem().toString();
                    Class.forName("com.mysql.jdbc.Driver");
                   conn = DriverManager.getConnection("jdbc:mysql://localhost/sunshineVehicleManagementDB", "root", "");
                   stmnt = conn.createStatement();

                   ResultSet rs=stmnt.executeQuery("Select * FROM employee_details where Emp_PF='"+ selectedguipfnumber+"'");

                   while(rs.next())
                   {

                     deregnumber=rs.getString("Vehicle_No");
                     checkIfAllocated=rs.getString("status");
                   }

                     if(checkIfAllocated.equals("true"))
                     {
                     regLabel.setText("DeAllocate Vehicle \" "+deregnumber+"\" from Employee\""+selectedguipfnumber+"\"");
                     regLabel.setForeground(Color.BLUE);
                     actionButton.setVisible(true);
                     }
                     else if(checkIfAllocated.equals("false"))
                     {
                        regLabel.setText("Employee is currently not allocated any vehicle");
                        regLabel.setForeground(Color.BLUE);
                        actionButton.setVisible(false);
                     }

              }
              catch(Exception ex){}
              }
         });
        cont.add(regLabel=new JLabel(""));
        cont.add(actionButton=new JButton("DeAllocate the Vehicle"));
        actionButton.setVisible(false);
        actionButton.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent event)
        {

            try
                  {
                  selectedguipfnumber=pfcombo.getSelectedItem().toString();
                  Class.forName("com.mysql.jdbc.Driver");
                   conn = DriverManager.getConnection("jdbc:mysql://localhost/sunshineVehicleManagementDB", "root", "");
                   stmnt = conn.createStatement();
                   psinsert1=conn.prepareStatement("UPDATE  employee_details  SET status= 'false',vehicle_No='' WHERE Emp_PF='"+ selectedguipfnumber+"'");
                   psinsert1.executeUpdate();

                 psinsert2=conn.prepareStatement("UPDATE  vehicle_details  SET status= 'false' WHERE vehicle_regNo='"+deregnumber+"'"  );
                 psinsert2.executeUpdate();
                 Thread progress=new Thread(new ProgressBar());
                  progress.start();
                  progressValidation.setVisible(true);
                JOptionPane.showMessageDialog(null,"De-Allocation Recorded in Vehicle_Details and Employee_Details tables");
                 progressValidation.setVisible(false);


                  }catch(Exception ex){}
         }
       });

       helpButton=new JButton("Help");
       helpButton.setBounds(200,50,14, 14);
       cont.add(new JLabel("                                         "));
       cont.add(helpButton);
       helpButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
               JOptionPane.showMessageDialog(null, "Select the number and follow instuctions");

            }
        });
        progressValidation=new JProgressBar();
        cont.add(progressValidation);
        progressValidation.setPreferredSize(new java.awt.Dimension(140,10));
        progressValidation.setBackground(Color.ORANGE);
        progressValidation.setForeground(Color.blue);
        progressValidation.setVisible(false);
       setSize(400,300);
      setLocation(380,380);
    cont.setBackground(Color.LIGHT_GRAY);

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
}