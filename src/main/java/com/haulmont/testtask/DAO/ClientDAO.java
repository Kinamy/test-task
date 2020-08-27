package com.haulmont.testtask.DAO;

import com.haulmont.testtask.ConnectionManager;
import com.haulmont.testtask.model.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public List<Client> findAllClient() {
        String request = "SELECT * FROM CLIENT";
        List<Client> clientList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("ID"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("PATRONYMIC"),
                        resultSet.getString("PHONE"));
                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return clientList;
    }

    public void addClient(Client client) {
        String request = "INSERT INTO CLIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getPatronymic());
            statement.setString(4, client.getPhone());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClient(Client client) {
        String request = "UPDATE CLIENT SET FIRST_NAME=?, LAST_NAME=?, PATRONYMIC=?, PHONE=? WHERE ID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getPatronymic());
            statement.setString(4, client.getPhone());
            statement.setLong(5, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeClient(Client client) {
        String request = "DELETE FROM CLIENT WHERE ID=?";
        boolean isRemoved = true;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setLong(1, client.getId());

            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            isRemoved = false;
        } catch (SQLException e) {
            e.printStackTrace();
            isRemoved = false;
        }
        return isRemoved;
    }
}
