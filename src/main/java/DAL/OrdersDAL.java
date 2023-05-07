package DAL;

import models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class OrdersDAL implements DAL<Order> {
    private long lastAddedOrderId = -1;

    /**
     * Method that gets order by id
     *
     * @param id id of order
     * @return order
     */
    @Override
    public Optional<Order> get(long id) {
        Order result = null;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("SELECT * FROM orders WHERE id = ?");
            dbManager.prepareStatement();
            dbManager.getPstmt().setLong(1, id);

            try (ResultSet res = dbManager.executeQuery()) {
                List<Order> extractProducts = extractOrders(res);
                if (extractProducts.size() > 0)
                    result = extractProducts.get(0);
            }

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return Optional.ofNullable(result);
    }

    /**
     * Method that gets all orders from database
     *
     * @return list of orders
     */
    @Override
    public List<Order> getAll() {
        List<Order> orders = new LinkedList<>();
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("SELECT * FROM orders");
            dbManager.prepareStatement();

            try (ResultSet res = dbManager.executeQuery()) {
                orders = extractOrders(res);
            }
        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return orders;
    }

    /**
     * Method that adds order to database
     *
     * @param order order to add
     * @return true if order was added successfully, false otherwise
     */
    @Override
    public boolean add(Order order) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            //Sql query that add product to the database
            dbManager.setQuery("INSERT INTO orders" +
                    " (id, userId, creationDate, userFirstName, userLastName, address1, address2, country, state, zip, totalSum, status)" +
                    " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            dbManager.prepareStatementWithKey();
            //Setting parameters for the query
            dbManager.getPstmt().setLong(1, order.getUserId());
            Date creatingDate = order.getCreationDate();
            java.sql.Date sqlDate = new java.sql.Date(creatingDate.getTime());
            dbManager.getPstmt().setDate(2, sqlDate);
            dbManager.getPstmt().setString(3, order.getUserFirstName());
            dbManager.getPstmt().setString(4, order.getUserLastName());
            dbManager.getPstmt().setString(5, order.getAddress1());
            dbManager.getPstmt().setString(6, order.getAddress2());
            dbManager.getPstmt().setString(7, order.getCountry());
            dbManager.getPstmt().setString(8, order.getState());
            dbManager.getPstmt().setInt(9, order.getZip());
            dbManager.getPstmt().setDouble(10, order.getTotalSum());
            dbManager.getPstmt().setString(11, order.getStatus());



            //Executing query
            int rowsCount = dbManager.executeUpdate();

            //Getting generated id
            try (ResultSet generatedKeys = dbManager.getPstmt().getGeneratedKeys()) {
                if (generatedKeys.next())
                    lastAddedOrderId = generatedKeys.getInt(1);
                else {
                    success = false;
                    throw new SQLException("Creating order failed, no ID obtained.");
                }

            }

            if (rowsCount > 0) {
                success = true;
            }

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return success;
    }

    /**
     * Method that adds order to the database with products
     *
     * @param order       order to add
     * @param productsIds list of products ids
     * @return true if order was added successfully, false otherwise
     */
    public boolean addWithProducts(Order order, List<Integer> productsIds) {
        boolean success;
        success = add(order);
        if (!success) return false;


        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            for (Integer productId : productsIds) {
                dbManager.setQuery("INSERT INTO orders_products" +
                        " (orderId, productId)" +
                        " VALUES (?, ?) ");
                dbManager.prepareStatement();
                //Setting parameters for the query
                dbManager.getPstmt().setLong(1, lastAddedOrderId);
                dbManager.getPstmt().setInt(2, productId);
                int rowsCount = dbManager.executeUpdate();
                if (rowsCount == 0)
                    success = false;
            }


        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return success;
    }


    //Todo implement update
    @Override
    public boolean update(int id, String[] params) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("UPDATE orders SET" +
                    " userId = ?, creationDate = ?, userFirstName = ? , userLastName = ?, address1 = ?," +
                    " address2 = ?, country = ?, state = ?, zip = ?, totalSum = ?, status = ? WHERE id = ?");

            dbManager.prepareStatement();
            //Setting parameters for the query
            dbManager.getPstmt().setLong(1, Long.parseLong(params[0])); //userId
            java.sql.Date date = new java.sql.Date(Long.parseLong(params[1])); //creationDate
            dbManager.getPstmt().setDate(2, date);
            dbManager.getPstmt().setString(3, params[3]); //userFirstName
            dbManager.getPstmt().setString(4, params[4]); //userLastName
            dbManager.getPstmt().setString(5, params[5]); //address1
            dbManager.getPstmt().setString(6, params[6]); //address2
            dbManager.getPstmt().setString(7, params[7]); //country
            dbManager.getPstmt().setString(8, params[8]); //state
            dbManager.getPstmt().setInt(9, Integer.parseInt(params[9])); //zip
            dbManager.getPstmt().setDouble(10, Double.parseDouble(params[10])); //totalSum
            dbManager.getPstmt().setString(11, params[11]); //status
            dbManager.getPstmt().setInt(12, id); //id

            int rowsCount = dbManager.executeUpdate();
            if (rowsCount > 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }

        return success;
    }

    public boolean update(int id, Order order) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("UPDATE orders SET" +
                    " userId = ?, creationDate = ?, userFirstName = ? , userLastName = ?, address1 = ?," +
                    " address2 = ?, country = ?, state = ?, zip = ?, totalSum = ?, status = ? WHERE id = ?");

            dbManager.prepareStatement();
            //Setting parameters for the query
            dbManager.getPstmt().setLong(1, order.getUserId()); //userId
            java.sql.Date date = new java.sql.Date(order.getCreationDate().getTime()); //creationDate
            dbManager.getPstmt().setDate(2, date);
            dbManager.getPstmt().setString(3, order.getUserFirstName()); //userFirstName
            dbManager.getPstmt().setString(4, order.getUserLastName()); //userLastName
            dbManager.getPstmt().setString(5, order.getAddress1()); //address1
            dbManager.getPstmt().setString(6, order.getAddress2()); //address2
            dbManager.getPstmt().setString(7, order.getCountry()); //country
            dbManager.getPstmt().setString(8, order.getState()); //state
            dbManager.getPstmt().setInt(9, order.getZip()); //zip
            dbManager.getPstmt().setDouble(10, order.getTotalSum()); //totalSum
            dbManager.getPstmt().setString(11, order.getStatus()); //status
            dbManager.getPstmt().setInt(12, id); //id

            int rowsCount = dbManager.executeUpdate();
            if (rowsCount > 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }

        return success;
    }

    /**
     * Method that deletes order from database
     *
     * @param order order to delete
     * @return true if order was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(Order order) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            //Sql query that deletes product from the database
            dbManager.setQuery("DELETE FROM orders WHERE id = ?");
            dbManager.prepareStatement();
            //Setting parameters for the query
            dbManager.getPstmt().setLong(1, order.getId());

            //Executing query
            int rowsCount = dbManager.executeUpdate();
            if (rowsCount > 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }

        return success;
    }

    /**
     * Method that searches for orders in the database
     *
     * @param filter predicate to apply
     * @return list of orders that satisfy the predicate
     */
    @Override
    public List<Order> search(Predicate<Order> filter) {

        List<Order> results = new ArrayList<>();

        for (Order order : getAll()) {
            if (filter.test(order)) {
                results.add(order);
            }
        }
        return results;

    }


    private List<Order> extractOrders(ResultSet res) throws SQLException {
        List<Order> orders = new LinkedList<>();
        while (res.next()) {
            //Getting orders from the result set
            long id = res.getLong("id");
            int userId = res.getInt("userId");
            Date creationDate = res.getDate("creationDate");
            String userFirstName = res.getString("userFirstName");
            String userLastName = res.getString("userLastName");
            String address1 = res.getString("address1");
            String address2 = res.getString("address2");
            String country = res.getString("country");
            String state = res.getString("state");
            int zip = res.getInt("zip");
            double totalSum = res.getDouble("totalSum");
            String status = res.getString("status");

            //Adding order to list of orders
            Order order = new Order(id, userId, creationDate, userFirstName, userLastName,
                    address1, address2, country, state, zip, totalSum, status);
            orders.add(order);
        }
        return orders;
    }
}
