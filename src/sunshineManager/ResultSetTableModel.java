package sunshineManager;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
//class for getting database stuff
public class ResultSetTableModel extends AbstractTableModel
    {
        private Connection con;
        private Statement st;
        private ResultSet rs;
        private ResultSetMetaData rsmd;
        private int rows;
        private boolean connectedToDatabase =false;
        public ResultSetTableModel(String driver,String url,String username,
                String password,String query)throws SQLException,ClassNotFoundException
        {
            Class.forName(driver);
            con=DriverManager.getConnection(url,username,password);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            connectedToDatabase=true;
            setQuery(query);
        }

    @Override
        public Class getColumnClass(int col)throws IllegalStateException
        {
            if(!connectedToDatabase)throw new IllegalStateException("Not connected");
            try
            {
                String className=rsmd.getColumnClassName(col+1);
                return Class.forName(className);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return Object.class;
        }

        public int getColumnCount()throws IllegalStateException
        {
            if(!connectedToDatabase)throw new IllegalStateException("Not connected to database");
            try
            {
                return rsmd.getColumnCount();
            }
            catch(SQLException sq)
            {
                sq.printStackTrace();

            }
            return 0;
        }
    @Override
        public String getColumnName(int c)throws IllegalStateException
        {
            if(!connectedToDatabase)throw new IllegalStateException("Not connected to database");
            try
            {
                return rsmd.getColumnName(c+1);
            }
            catch(SQLException sq)
            {
                sq.printStackTrace();
                return "";
            }
        }

        public Object getValueAt(int r,int c)
        {
            try
            {
                rs.absolute(r+1);
                return rs.getObject(c+1);
            }
            catch(SQLException sq)
            {
                sq.printStackTrace();
                return "";
            }
        }
        public int getRowCount()
        {
            try
            {
                return rows;
            }
            catch(IllegalStateException sq)
            {
                sq.printStackTrace();
                return 0;
            }
        }
        public void setQuery(String query)throws SQLException,IllegalStateException
        {
            if(!connectedToDatabase)new IllegalStateException("Not connected");
            rs=st.executeQuery(query);
            rsmd=rs.getMetaData();
            rs.last();
            rows=rs.getRow();
            fireTableStructureChanged();

        }
        public void disconnectedFromDatabase()
        {
           if(!connectedToDatabase)
               return;
           try
           {
               st.close();
               con.close();
           }
           catch(SQLException sq)
           {
            sq.printStackTrace();
           }
           finally
           {
               connectedToDatabase=false;
           }
        }
    }
