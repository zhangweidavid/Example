package mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by wei.zw on 2017/6/15.
 */
public class JdbcExample {
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/demo";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String getName(int id){
        Connection connection=getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=connection.prepareStatement("select first_name from employees where employee_id=?");
           ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                return rs.getString("first_name");
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
        return null;

    }

    public static void main(String[] args){
        JdbcExample example=new JdbcExample();
        String name=example.getName(1);
        System.out.println(name);

    }
}
