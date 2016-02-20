package vn.khmt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import vn.khmt.restful.User;

/**
 *
 * @author LUONG The Nhan
 * @version 0.1
 */
public class ConnectToSQL {

    public static final String ERROR = "Error";
    public static final String NOTMATCH = "NotMatch";
    public static final String SQLSERVER = "sqlserver";
    public static final String SQLSERVERDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String MYSQL = "mysql";
    public static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
    public static final String POSTGRESQL = "postgresql";
    public static final String POSTGRESQLDRIVER = "org.postgresql.Driver";

    Connection dbConnection = null;

    public ConnectToSQL(String type, String host, String dbname, String user, String pwd) {
        this.dbConnection = getDBConnection(type, host, dbname, user, pwd);
    }

    private Connection getDBConnection(String type, String host, String dbname, String user, String pwd) {
        if (type != null && !type.isEmpty()) {
            try {
                if (type.equalsIgnoreCase(SQLSERVER)) {
                    Class.forName(SQLSERVERDRIVER);
                    dbConnection = DriverManager.getConnection(host + ";database=" + dbname + ";sendStringParametersAsUnicode=true;useUnicode=true;characterEncoding=UTF-8;", user, pwd);
                } else if (type.equalsIgnoreCase(MYSQL)) {
                    Class.forName(MYSQLDRIVER);
                    dbConnection = DriverManager.getConnection(host + "/" + dbname, user, pwd);
                } else if (type.equalsIgnoreCase(POSTGRESQL)) {
                    Class.forName(POSTGRESQLDRIVER);
                    dbConnection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + dbname + "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", user, pwd);
                }
                return dbConnection;
            } catch (ClassNotFoundException | SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return dbConnection;
    }
    
    public User getUser(int id) {
        try {
            String SQL = "SELECT * FROM public.user t WHERE t.id = " + id + ";";
            Statement stmt = this.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.  
            if (rs.next()) {
                System.out.println("User exists");
                User u = new User();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setStatus(rs.getInt(5));
                u.setName(rs.getString(6));
                return u;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return null;
    }
    
    /*
     *  
     */
    public User getUser(String username, String password) {
        try {
            String SQL = "SELECT * FROM public.user t WHERE t.username = '" + username + "' AND t.password = '" + password + "';";
            Statement stmt = this.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.  
            if (rs.next()) {
                System.out.println("User exists");
                User u = new User();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setStatus(rs.getInt(5));
                u.setName(rs.getString(6));
                return u;
            } else {
                System.out.println("User not exists");
                return new User();
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return null;
    }
    
    public String renameUser(int id, String name) {
        try {
            String SQL = "UPDATE public.user SET name = '" + name + "' WHERE id = " + id + ";";
            this.dbConnection.setAutoCommit(false);
            Statement stmt = this.dbConnection.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            this.dbConnection.commit();
            return "Success";
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            return "Failed";
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
    }
    
    public ArrayList<User> getAllUsers() {
        try {
            String SQL = "SELECT * FROM public.user;";
            Statement stmt = this.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<User> us = new ArrayList<User>();
            // Iterate through the data in the result set and display it.  
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setStatus(rs.getInt(5));
                u.setName(rs.getString(6));
                us.add(u);
            }
            return us;
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return null;
    }
    
    public String addUser(int id, String username, String password, String email, int status, String name) {
        try {
            
            String check = checkUserExist(username, id);
            if (!check.equals(NOTMATCH)) return "Existed";
            this.dbConnection.setAutoCommit(false);
            Statement stmt = this.dbConnection.createStatement();
//            String SQL = "INSERT INTO public.user(id, username, password, email, status, name) SELECT MAX(t.id) + 1, '" + username 
//                        + "', '" + password + "', '" + email + "', 1, '" + name + "' FROM public.user t;";
            String SQL = "INSERT INTO public.user(id, username, password, email, status, name) VALUES(" + id + ", '" + username + "', '"
                            + password + "', '" + email + "', " + status + ", '" + name + "');";
            stmt.executeUpdate(SQL);
            stmt.close();
            this.dbConnection.commit();
            return "Success";
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            return "Failed";
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
    }
    
    public String checkUser(String username, String password) {
        try {
            if (username != null && password != null) {
                String SQL = "SELECT u.name, u.status FROM user u WHERE u.username = '" + username + "' AND u.password = '" + password + "';";
                Statement stmt = this.dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                // Iterate through the data in the result set and display it.  
                if (rs.next()) {
                    if (rs.getInt(2) == 1) {
                        return rs.getString(1);
                    } else {
                        return ERROR;
                    }
                } else {
                    return NOTMATCH;
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return null;
    }
    
    public String checkUserExist(String username, int id) {
        try {
            if (username != null) {
                String SQL = "SELECT 1 FROM public.user u WHERE u.username = '" + username + "';";
                Statement stmt = this.dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                // Iterate through the data in the result set and display it.  
                if (rs.next()) {
                    return "Existed";
                } else {
                    SQL = "SELECT 1 FROM public.user u WHERE u.id = '" + id + "';";
                    rs = stmt.executeQuery(SQL);
                    if (rs.next()) return "Existed";
                    else return NOTMATCH;
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return null;
    }

    private static Timestamp getTimeStampOfDate(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    public boolean executeSQL(String sql) {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = this.dbConnection.prepareStatement(sql);
            // execute insert SQL stetement
            if (preparedStatement != null) {
                int res = preparedStatement.executeUpdate();
                return res == 1;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return false;
    }

}
