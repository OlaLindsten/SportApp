/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import com.mysql.jdbc.Connection;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author albinarvidsson
 */
@Stateless
public class SportsBean {

    public JsonArray getGames() {

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
                        .add("id", id)
                        .add("hemmalag", hl)
                        .add("bortalag", bl)
                        .add("poanghemma", hp)
                        .add("poangborta", bp).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean addGame(String body) {
        JsonReader jsonReader = Json.createReader((new StringReader(body)));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        int hl = data.getInt("hemmalag");
        int bl = data.getInt("bortalag");
        int ph = data.getInt("poanghemma");
        int pb = data.getInt("poangborta");

        if (ph + pb == 3 && (hl > 0 && hl <= 10) && (bl > 0 && bl <= 10)) {
            try {
                Connection connection = ConnectionFactory.make("testServer");
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO matcher VALUE(NULL,?,?,?,?)");
                stmt.setInt(1, hl);
                stmt.setInt(2, bl);
                stmt.setInt(3, ph);
                stmt.setInt(4, pb);
                stmt.executeUpdate();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean deleteGame(int id) {
        try {
            Connection connection = ConnectionFactory.make("testServer");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM matcher WHERE id=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public boolean updateGame(String body) {
        System.out.println("1");
        JsonReader jsonReader = Json.createReader((new StringReader(body)));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        System.out.println("2");
        int id = data.getInt("id");
        int hl = data.getInt("hemmalag");
        int bl = data.getInt("bortalag");
        int ph = data.getInt("poanghemma");
        int pb = data.getInt("poangborta");
        System.out.println("3");
        if (ph + pb == 3 && (hl > 0 && hl <= 10) && (bl > 0 && bl <= 10)) {
            try {
                System.out.println("4");
                Connection connection = ConnectionFactory.make("testServer");
                PreparedStatement stmt = connection.prepareStatement("UPDATE matcher SET hemmalag=?, bortalag=?, poanghemma=?, poangbort=? WHERE id=?");
                System.out.println("har skickat sql fråga 5");
                stmt.setInt(1, hl);
                stmt.setInt(2, bl);
                stmt.setInt(3, ph);
                stmt.setInt(4, pb);
                stmt.setInt(5, id);
                stmt.executeUpdate();
                connection.close();
                System.out.println("6");
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }

    }

    public JsonArray showTable() {
        try {
            Connection connection = ConnectionFactory.make("testServer");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM tabellen";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String lagnamn = data.getString("lagnamn");
                int poang = data.getInt("p");
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("lagnamn", lagnamn)
                        .add("p", poang).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JsonArray showTeams() {
        try {
            Connection connection = ConnectionFactory.make("testServer");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM lag";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                int id = data.getInt("id");
                String lagnamn = data.getString("namn");
                
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", id)
                        .add("lagnamn", lagnamn).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JsonArray getTeam(int id) {
        try {
            Connection connection = ConnectionFactory.make("testServer");
            String sql = "SELECT tabellen.lagnamn, tabellen.p FROM tabellen, lag WHERE lag.id = ? AND lag.namn = tabellen.lagnamn";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet data = stmt.executeQuery();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()){
                int poang = data.getInt("p");
                String lagnamn = data.getString("lagnamn");
                
                jsonArrayBuilder.add(Json.createObjectBuilder()
                                    .add("lagnamn",lagnamn)
                                    .add("poang",poang)
                );
                
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
