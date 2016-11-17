/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.support;

import java.util.Base64;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author albinarvidsson
 */
public class User {
    public static boolean authoricate(HttpHeaders httpHeaders){
        try {
            List<String> authHeader = httpHeaders.getRequestHeader(httpHeaders.AUTHORIZATION);
            String header = authHeader.get(0);
            header = header.substring(header.indexOf(" ")+1);
            byte[] decode = Base64.getDecoder().decode(header);
            String userPass = new String(decode);
            System.out.println(userPass);
            //plocka ut anv och lösenord
            String username = userPass.substring(0,userPass.indexOf(":"));
            String password = userPass.substring(userPass.indexOf(":")+1);
            System.out.println(username);
            System.out.println(password);
            if(username.equals("Albin") && password.equals("123")){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

