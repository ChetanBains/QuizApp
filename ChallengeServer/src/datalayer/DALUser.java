package datalayer;

import java.sql.*;
import components.User;
import javax.swing.JOptionPane;

public class DALUser {

    static Connection con = null;
    /// Keep This until Firebase Doesnt work
    public DALUser() {
        try {
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-GOFF4ES\\chetan:49821;Database=projectDB;userName=sa;password=1100");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void closeConnection() {
        try {
            con.close();
            con = null;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void register(User user) {
        try {

            PreparedStatement ps = con.prepareStatement("insert into Users values(?,?,?)");
            ps.setString(1, user.Name);
            ps.setString(2, user.UserId);
            ps.setString(3, user.Password);
            ps.executeUpdate();
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /// Keep This until Firebase Doesnt work

    public User authentication(User user) {
        User obj = new User();
        try {
            PreparedStatement ps = con.prepareStatement("select * from users where UserId=? and Password=?");
            ps.setString(1, user.UserId);
            ps.setString(2, user.Password);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == true) {
                obj.Name = rs.getString("Name");
                obj.UserId = rs.getString("UserId");
                obj.Password = rs.getString("Password");
                obj.UserNo = rs.getInt("UserNo");

            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return obj;
    }
/// Keep This until Firebase Doesnt work

    public void changePassword(User usr) {
        try {
            PreparedStatement ps = con.prepareStatement("update Users set Password=? where UserNo=?");
            ps.setString(1, usr.Password);
            ps.setInt(2, usr.UserNo);
            ps.executeUpdate();

        } catch (Exception e) {
        }

    }

}
