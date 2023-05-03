package DAL;

import data.DbProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbManager {

    //region Private fields
    private Connection conn;
    private ResultSet res;
    private PreparedStatement pstmt;
    private String query;
    //endregion

    //region Getter / Setters

    public Connection getConnection() {

        return conn;
    }


    public ResultSet getRes() {
        return res;
    }

    public void setRes(ResultSet res) {
        this.res = res;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    //endregion

    //region Constructors
    public DbManager() {
    }
    //endregion

    //region public methods
    public void createConnection() throws SQLException, ClassNotFoundException {
        conn = DbProvider.getMySqlConnection();
    }
    public void prepareStatement() throws SQLException {
        this.pstmt = conn.prepareStatement(query);
    }

    public ResultSet executeQuery() throws SQLException {
        res = pstmt.executeQuery();
        return res;
    }
    public int executeUpdate() throws SQLException {
        return pstmt.executeUpdate();
    }

    public void closeAllConnections() {
        try {
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
            if (res != null) {
                res.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //endregion
}
