package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.AllOrdersDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AllOrdersDaoJDBC implements AllOrdersDao {
    private DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration();

    public AllOrdersDaoJDBC() {
    }

    AllOrdersDaoJDBC(DataBaseConfiguration dataBaseConfiguration) {
        this.dataBaseConfiguration = dataBaseConfiguration;
    }


    // LINE ITEMS:
    private void addLineItem(Order order, LineItem lineItem) {
        String query = "INSERT INTO line_items(product_id, quantity, orders_id) VALUES (?, ?, ?)";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, lineItem.getProductId());
            statement.setInt(1, lineItem.getQuantity());
            statement.setInt(1, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addLineItemList(Order order, List<LineItem> lineItemList) {
        for (LineItem lineItem : lineItemList) {
            addLineItem(order, lineItem);
        }
    }

    private List<LineItem> getLineItemsForOrder(int orderId) {
        String query =
                "SELECT ln.id as ln_id, ln.product_id, ln.quantity, ln.orders_id, " +
                        "p.id as prod_id, p.name, p.price, p.currency " +
                        "FROM line_items ln " +
                        "JOIN products p on ln.product_id = p.id " +
                        "WHERE ln.orders_id = ?";
        List<LineItem> lineItemsForOrder = new ArrayList<>();

        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LineItem lineItem =
                        new LineItem(resultSet.getInt("li_id"),
                                new Product(resultSet.getInt("prod_id"),
                                        resultSet.getString("prod_name"),
                                        resultSet.getFloat("price"),
                                        resultSet.getString("currency")),
                                resultSet.getInt("quantity"));
                lineItemsForOrder.add(lineItem);
            }
            return lineItemsForOrder;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // ORDER

    private Order addAndReturn(Order order) {
        String query =
                "INSERT INTO orders(data, customer_id) VALUES (DATE_TRUNC('minute', now()), ?)" +
                        "RETURNING id";

        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getCustomerId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                return find(orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order addFullOrderAndReturn(Order orderFromSession) {
        Order orderFormDatabase = addAndReturn(orderFromSession);
        addLineItemList(orderFormDatabase, orderFromSession.getLineItemList());
        return orderFormDatabase;
    }


    @Override
    public Order find(int orderId) {
        String query = "SELECT * FROM orders WHERE id = ?";
        List<LineItem> lineItemsForOrder = getLineItemsForOrder(orderId);
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createOrderFromDatabase(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getForCustomer(Customer customer) {
        List<Order> ordersForCustomer = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customer.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = createOrderFromDatabase(resultSet,
                        getLineItemsForOrder(resultSet.getInt("id")));
                ordersForCustomer.add(order);
            }
            return ordersForCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updatePayment(String paymentName, int orderId) {
        String query =
                "UPDATE orders SET payment_id = ? " +
                        "WHERE id = (SELECT p.id FROM payments p WHERE p.name = ?)";
        update(dataBaseConfiguration, query, paymentName, orderId);
    }

    @Override
    public void updateTotalPrice(float totalPrice, int orderId) {
        String query = "UPDATE orders SET total_price = ? WHERE id = ?";
        withStatement(dataBaseConfiguration, query,
                preparedStatement -> {
                    try {
                        preparedStatement.setFloat(1, totalPrice);
                        preparedStatement.setInt(2, orderId);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void updateStatus(String status, int orderId) {
        String query =
                "UPDATE orders SET status_id = ? " +
                        "WHERE id = (SELECT s.id FROM statuses s WHERE s.name = ?";
        update(dataBaseConfiguration, query, status, orderId);

    }

    // HELPERS

    private void withStatement(DataBaseConfiguration dataBaseConfiguration, String query, Consumer<PreparedStatement> consumer) {
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            consumer.accept(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void update(DataBaseConfiguration dataBaseConfiguration, String query, String parameter, int orderId) {
        withStatement(dataBaseConfiguration, query,
                preparedStatement -> {
                    try {
                        preparedStatement.setString(1, parameter);
                        preparedStatement.setInt(2, orderId);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    private Order createOrderFromDatabase(ResultSet resultSet) throws SQLException {
        return new Order(resultSet.getInt("id"),
                resultSet.getString("data"),
                resultSet.getInt("customer_id"),
                resultSet.getString("pay_name"),
                resultSet.getFloat("total_price"),
                resultSet.getString("stat_name"), null);
    }

    private Order createOrderFromDatabase(ResultSet resultSet, List<LineItem> lineItemList) throws SQLException {
        return new Order(resultSet.getInt("id"),
                resultSet.getString("data"),
                resultSet.getInt("customer_id"),
                resultSet.getString("pay_name"),
                resultSet.getFloat("total_price"),
                resultSet.getString("stat_name"),
                lineItemList);
    }

    //    private <T> T withStatement(DataBaseConfiguration dataBaseConfiguration, String query, Function<PreparedStatement, T> function) {
//        try (Connection connection = dataBaseConfiguration.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            return function.apply(statement);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
