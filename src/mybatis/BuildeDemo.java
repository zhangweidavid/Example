package mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Created by wei.zw on 2017/6/15.
 */
public class BuildeDemo {

    public static void main(String[] args) throws  Exception{


        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(BuildeDemo.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));
        String name=(String)sessionFactory.openSession().selectOne("getEmployeeName",1);
        System.out.println(name);
    }
}
