/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.khmt.restful;

import vn.khmt.db.ConnectToSQL;

/**
 *
 * @author hoangdo
 */
public class DBConfig {
    public static final String TYPE = "POSTGRESQL";
    public static final String HOST = "ec2-54-227-253-228.compute-1.amazonaws.com:5432";
    public static final String DBNAME = "d8viikojj42e3b";
    public static final String DBUSER = "uzufecmqojhnyx";
    public static final String DBPWD = "WPJGueUbd3npLKslU2BEUOmMHx";
    public static final Integer ADMIN_STATUS = 3;
    
    public static ConnectToSQL quickConnect() {
        return new ConnectToSQL(TYPE, HOST, DBNAME, DBUSER, DBPWD);
    }
}
