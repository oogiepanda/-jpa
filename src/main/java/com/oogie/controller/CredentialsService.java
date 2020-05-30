package com.oogie.controller;

import com.oogie.Credentials;
import com.oogie.model.CredentialsEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class CredentialsService extends BaseService{

    public CredentialsService(Connection conn) {
        super(conn);
    }

    public void create (CredentialsEntity ce) {
        Statement stmt = null;
        try {

        } catch (Exception e) {

        }
    }

    public Credentials retrieve (Credentials credentials) {
        Statement stmt = null;
        Credentials retval = new Credentials();
        retval.setId(credentials.getId());
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM credentials WHERE id = '"
                    + retval.getId() + "'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                retval.setId(rs.getInt("id"));
                retval.setUsername(rs.getString("username"));
                retval.setPassword(rs.getString("password"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
        return retval;
    }

    public void update (CredentialsEntity ce) {

    }

    public void delete (CredentialsEntity ce) {

    }

}
