/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author albinarvidsson
 */

@Stateless
public class SportsBean {
    
    public JsonArray getGames(){
        
        try {
            Connection connection = ConnectionFactory.make("testServer");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM matcher";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {                
                int id = data.getInt("id");
                int hl = data.getInt("hemmalag");
                int bl = data.getInt("bortalag");
                int hp = data.getInt("poanghemma");
                int bp = data.getInt("poangbort");
                
                jsonArrayBuilder.add(Json.createObjectBuilder()
                            .add("id",id)
                            .add("hemmalag",hl)
                            .add("bortalag",bl)
                            .add("poanghemma",hp)
                            .add("poangborta",bp).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
