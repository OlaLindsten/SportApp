/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bycrypttest;

/**
 *
 * @author albinarvidsson
 */
public class BycryptTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String password1 = "simon";
        String password2 = "nejhejhejhej";
        
        String hash = hashPassword(password1);// sparas i databasen
        
        if (BCrypt.checkpw(password1, hash)) {
            System.out.println(hash);
        }
        
    }
    
    public static String hashPassword(String password){
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password, salt);
        return hash;
    }
    
    
    
}
