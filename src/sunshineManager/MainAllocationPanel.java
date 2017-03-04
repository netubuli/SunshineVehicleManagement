
package sunshineManager;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainAllocationPanel extends JFrame implements ActionListener
{

    private  JMenuItem vehicle;
    private  JMenuItem employee;
    private  JMenuItem allocate;
    private  JMenuItem appItem;
    private  JMenuItem deAllocate;
    private  JMenuItem status;
    private JMenuItem allocatedE,unallocatedE,allocatedV,unallocatedV;
    private  JButton help;
   //class constuctor
    public MainAllocationPanel()
    {
        JPanel pan=new JPanel();
        pan.setLayout(new FlowLayout());
        pan.setBackground(Color.LIGHT_GRAY);
        add(pan);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.setBackground(Color.pink);

        JMenu status = new JMenu("STATUS");
        JMenu emp=new JMenu("EMPLOYEES");
        emp.add(allocatedE=new JMenuItem("Allocated"));
        emp.add(unallocatedE=new JMenuItem("UnAllocated"));
        JMenu veh=new JMenu("VEHICLES");
        veh.add(allocatedV=new JMenuItem("Allocated"));
        veh.add(unallocatedV=new JMenuItem("Unallocated"));
        menuBar.add(status);
        status.add(emp);
        status.add(veh);
        allocatedE.addActionListener(this);
        unallocatedE.addActionListener(this);
        allocatedV.addActionListener(this);
        unallocatedV.addActionListener(this);
        status.setForeground(Color.BLUE);

        JMenu   allocateMenu= new JMenu("ALLOCATION");
        allocateMenu.setForeground(Color.BLUE);
         allocateMenu.setMnemonic('L');
        allocateMenu.add(allocate = new JMenuItem("ALLOCATE VEHICLE",'C'));
        allocateMenu .add(new JSeparator());
        allocate.setForeground(Color.BLUE);
        allocate.addActionListener(this);
        allocateMenu.add(deAllocate = new JMenuItem("DEALLOCATE VEHICLE",'D'));
        deAllocate.addActionListener(this);
        deAllocate.setForeground(Color.BLUE);
         menuBar.add(allocateMenu);

        JMenu   viewmenu= new JMenu("DETAILS");
        viewmenu.setForeground(Color.BLUE);
        viewmenu.setMnemonic('V');
        viewmenu.add(employee = new JMenuItem("EMPLOYEES DETAILS", 'E'));
        menuBar.add(viewmenu);
        employee.addActionListener(this);
        employee.setForeground(Color.BLUE);
        viewmenu.add(vehicle = new JMenuItem("VEHICLE DETAILS",'A'));
        viewmenu.add(new JSeparator());
        vehicle.addActionListener(this);
        vehicle.setForeground(Color.BLUE);

         JMenu   about= new JMenu("HELP");
         about.setForeground(Color.BLUE);
         about.add( help=new JButton("Help Contents"));
         about.add(appItem = new JMenuItem("About ",'H'));
        //allocateMenu .add(new JSeparator());

         appItem.setForeground(Color.BLUE);
         appItem.addActionListener(this);
           menuBar.add(about);

       help.setBounds(200,50,20, 14);
       help.setForeground(Color.BLUE);
       help.addActionListener(this);

         setResizable(false);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         //setLocationRelativeTo(null);
        setSize(500,300);

    }
        public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainAllocationPanel().setVisible(true);
            }
        });
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(vehicle))
        {
            Vehicle vh=new Vehicle();
            vh.setVisible(true);
        }
        else if(e.getSource().equals(employee))
        {
            Employee emp=new Employee();
            emp.setVisible(true);

        }
        else if(e.getSource().equals(appItem))
        {
           JOptionPane.showMessageDialog(null, "This program is all about allocating,deallocating" +
                                   "and viewing vehicle details together with Employee Details ");

        }
        else if (e.getSource().equals(allocate))
        {
           Allocation aform=new  Allocation();
           aform.setVisible(true);
        }
         else if (e.getSource().equals(deAllocate))
        {
           DeAllocation dform=new  DeAllocation();
           dform.setVisible(true);
        }
         else if(e.getSource()==allocatedE)
         {
             Status s=new Status();
             s.allocated();
             s.setVisible(true);
         }
         else if(e.getSource()==unallocatedE)
         {
             Status s=new Status();
             s.unallocated();
             s.setVisible(true);
         }
         else if(e.getSource()==allocatedV)
         {
             Status s=new Status();
             s.allocatedV();
             s.setVisible(true);
         }
         else if(e.getSource()==unallocatedV)
         {
             Status s=new Status();
             s.unallocatedV();
             s.setVisible(true);
         }
         else if(e.getSource().equals(help))
         {
             JOptionPane.showMessageDialog(null, "Select on the Menu the action you want to perform and " +
                     "follow the steps.","Message",JOptionPane.INFORMATION_MESSAGE);
         }

    }
}
