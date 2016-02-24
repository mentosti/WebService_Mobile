/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.khmt.helper;

import vn.khmt.entity.User;
import java.util.Base64;
import javax.ws.rs.core.HttpHeaders;
import vn.khmt.db.ConnectToSQL;

/**
 *
 * @author hoangdo
 */
public class AuthorizationChecker {

    /**
     * authenticateUser from header string
     * @param headers
     * @return authenticated User
     */
    public static User checkFromHeaders(HttpHeaders headers) {
        try {
            String authorizationHeader = headers.getRequestHeader("Authorization").get(0);
            authorizationHeader = authorizationHeader.replaceAll("Basic ", "");
            String decoded = new String(Base64.getDecoder().decode(authorizationHeader));
            System.out.println(decoded);
            String[] credentials  = decoded.split(":");
            
            try {
                ConnectToSQL conn = DBConfig.quickConnect();
                return conn.getUser(credentials[0], credentials[1]);
            } catch(Exception e) {
                //return empty User object
                return new User();
            }
        } catch(Exception e) {
            //return empty User object
            return new User();
        }
    }
}
