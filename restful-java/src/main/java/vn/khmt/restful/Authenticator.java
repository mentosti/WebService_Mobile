/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.khmt.restful;

import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author hoangdo
 */
public class Authenticator {
    /**
     * authenticateUser from header string
     * @param authenticationHeader
     * @return authenticated User
     */
    static User authenticateUser(HttpHeaders headers) {
        try {
            String authorizationHeader = headers.getRequestHeader("Authorization").get(0);
            System.out.println(authorizationHeader);
            return new User();
        } catch(Exception e) {
            return new User();
        }
    }
}
