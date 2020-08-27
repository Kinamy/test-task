package com.haulmont.testtask.DAO;

import com.haulmont.testtask.ConnectionManager;
import com.haulmont.testtask.model.entity.Client;
import com.haulmont.testtask.model.entity.Mechanic;
import com.haulmont.testtask.model.entity.Order;
import com.haulmont.testtask.model.entity.OrderStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> findAllOrder() {
        String request = "SELECT * FROM ORDERS INNER JOIN CLIENT ON ORDERS.CLIENT = CLIENT.ID INNER JOIN MECHANIC ON ORDERS.MECHANIC = MECHANIC.ID";
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return orderList;
    }

    private Order createOrder(ResultSet resultSet) throws SQLException {
        Client client = new Client(resultSet.getLong("CLIENT.ID"),
                resultSet.getString("CLIENT.FIRST_NAME"),
                resultSet.getString("CLIENT.LAST_NAME"),
                resultSet.getString("CLIENT.PATRONYMIC"),
                resultSet.getString("CLIENT.PHONE"));

        Mechanic mechanic = new Mechanic(resultSet.getLong("MECHANIC.ID"),
                resultSet.getString("MECHANIC.FIRST_NAME"),
                resultSet.getString("MECHANIC.LAST_NAME"),
                resultSet.getString("MECHANIC.PATRONYMIC"),
                resultSet.getDouble("MECHANIC.SALARY"));

        Order order = new Order(resultSet.getLong("ORDERS.ID"),
                resultSet.getString("ORDERS.DESCRIPTION"),
                client,
                mechanic,
                resultSet.getObject("ORDERS.CREATE_DATE", LocalDate.class),
                resultSet.getObject("ORDERS.END_DATE", LocalDate.class),
                resultSet.getDouble("ORDERS.PRICE"),
                OrderStatus.valueOf(resultSet.getString("ORDERS.STATUS").toUpperCase())
        );

        return order;
    }

    public void addOrder(Order order) {
        String request = "INSERT INTO ORDERS (DESCRIPTION, CLIENT, MECHANIC, CREATE_DATE, END_DATE, PRICE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setString(1, order.getDescription());
            statement.setLong(2, order.getClient().getId());
            statement.setLong(3, order.getMechanic().getId());
            statement.setObject(4, order.getCreateDate());
            statement.setObject(5, order.getEndDate());
            statement.setDouble(6, order.getPrice());
            statement.setString(7, order.getStatus().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> findByDescription(String description) {
        String request = "SELECT * FROM ORDERS INNER JOIN CLIENT ON ORDERS.CLIENT = CLIENT.ID INNER JOIN MECHANIC ON ORDERS.MECHANIC = MECHANIC.ID WHERE LOWER(DESCRIPTION) LIKE LOWER(?)";
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, "%" + description + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public List<Order> findByStatus(OrderStatus orderStatus) {
        String request = "SELECT * FROM ORDERS INNER JOIN CLIENT ON ORDERS.CLIENT = CLIENT.ID INNER JOIN MECHANIC ON ORDERS.MECHANIC = MECHANIC.ID WHERE STATUS LIKE ?";
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, orderStatus.toString());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public List<Order> findByClient(Client client) {
        String request = "SELECT * FROM ORDERS INNER JOIN CLIENT ON ORDERS.CLIENT = CLIENT.ID INNER JOIN MECHANIC ON ORDERS.MECHANIC = MECHANIC.ID WHERE CLIENT.ID = ?";
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(1, client.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void updateOrder(Order order) {
        String request = "UPDATE ORDERS SET DESCRIPTION=?, CLIENT=?, MECHANIC=?, CREATE_DATE=?, END_DATE=?, PRICE=?, STATUS=? WHERE ID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setString(1, order.getDescription());
            statement.setLong(2, order.getClient().getId());
            statement.setLong(3, order.getMechanic().getId());
            statement.setObject(4, order.getCreateDate());
            statement.setObject(5, order.getEndDate());
            statement.setDouble(6, order.getPrice());
            statement.setString(7, order.getStatus().toString());
            statement.setLong(8, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeOrder(Order order) {
        String request = "DELETE FROM ORDERS WHERE ID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setLong(1, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
