package sunshineManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import javax.swing.JTable;
public class Status extends JFrame 
{
    static final String driver="com.mysql.jdbc.Driver";
    private ResultSetTableModel model;
    static final String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
    static final String username="root";
    static final String password="";
    JTable t;
    Connection conn=null;
    Statement st=null;
    Box box=Box.createVerticalBox();
    ButtonGroup gr=new ButtonGroup();
    JLabel t1,t2,t3,t4;
    public Status()
    {        
        t1=new JLabel("List of allocated employees");
        t2=new JLabel("List of UnAllocated Employees");
        t3=new JLabel("List of Allocated Vehicles");
        t4=new JLabel("List of UnAllocated Vehicles");
        add(box,BorderLayout.NORTH);
        
        //allocated.addActionListener(this);
        //unallocated.addActionListener(this);
        setVisible(true);
        setSize(500,300);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
    //allocting the employees
   public void allocated()
        {
           try
            {
               box.add(t1);
               t1.setForeground(Color.red);
                //t2.setVisible(false);
                         String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
                         String user="root";
                         String pw="";
                         conn=DriverManager.getConnection(url,user,pw);
                         String q="select Emp_PF,name,drive_class,vehicle_No from employee_details where status='true'";

               model=new ResultSetTableModel(driver,url,username,password,q);
               Box tbox=Box.createVerticalBox();

               t=new JTable(model);
               add(tbox,BorderLayout.SOUTH);
               add(new JScrollPane(t),BorderLayout.CENTER);


               setVisible(true);
               setSize(500, 300);
            }
            catch(Exception ex)
            {
                System.out.println("Error");
            }
 
        }
   //deallocating the employeeess
   public void unallocated()
   {
         try
            {
             box.add(t2);
             t2.setForeground(Color.red);
                t1.setVisible(false);
                         String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
                         String user="root";
                         String pw="";
                         conn=DriverManager.getConnection(url,user,pw);
                         String q="select EMP_PF,NAME,drive_class from employee_details where status='false'";
                         model=new ResultSetTableModel(driver,url,username,password,q);
                           t=new JTable(model);


                           Box tbox=Box.createVerticalBox();
//                           t.setBackground(Color.LIGHT_GRAY);
                           add(tbox,BorderLayout.SOUTH);
                           add(new JScrollPane(t),BorderLayout.CENTER);

                           setVisible(true);

                           setSize(500, 300);
            }
            catch(Exception ex)
            {
                System.out.println("Error");
            }
   }
   //code for viewing the allocated vehicles
   public void allocatedV()
   {
     try
            {
               box.add(t3);
               t3.setForeground(Color.red);
                //t2.setVisible(false);
                         String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
                         String user="root";
                         String pw="";
                         conn=DriverManager.getConnection(url,user,pw);
                         String q="select vehicle_regNo,make,model,drive_class from vehicle_details where status='true'";

               model=new ResultSetTableModel(driver,url,username,password,q);
               Box tbox=Box.createVerticalBox();

               t=new JTable(model);
               add(tbox,BorderLayout.SOUTH);
               add(new JScrollPane(t),BorderLayout.CENTER);


               setVisible(true);
               setSize(500, 300);
            }
            catch(Exception ex)
            {
                System.out.println("Error");
            }
   }
   //code for viewing the un allocated vehicles
   public void unallocatedV()
   {
         try
            {
             box.add(t4);
             t4.setForeground(Color.red);
                t1.setVisible(false);
                         String url="jdbc:mysql://localhost/sunshineVehicleManagementDB";
                         String user="root";
                         String pw="";
                         conn=DriverManager.getConnection(url,user,pw);
                         String q="select vehicle_regNo,make,model,drive_class from vehicle_details where status='false'";
                         model=new ResultSetTableModel(driver,url,username,password,q);
                           t=new JTable(model);

                           Box tbox=Box.createVerticalBox();
//                           t.setBackground(Color.LIGHT_GRAY);
                           add(tbox,BorderLayout.SOUTH);
                           add(new JScrollPane(t),BorderLayout.CENTER);

                           setVisible(true);

                           setSize(500, 300);
            }
            catch(Exception ex)
            {
                System.out.println("Error");
            }
   }
}