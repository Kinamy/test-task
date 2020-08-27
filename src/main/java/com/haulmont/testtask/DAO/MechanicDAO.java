package com.haulmont.testtask.DAO;

import com.haulmont.testtask.ConnectionManager;
import com.haulmont.testtask.model.DTO.StatisticDTO;
import com.haulmont.testtask.model.entity.Mechanic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MechanicDAO {

    public List<Mechanic> findAllMechanic() {
        String request = "SELECT * FROM MECHANIC";
        List<Mechanic> mechanicList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Mechanic mechanic = new Mechanic(resultSet.getLong("ID"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("PATRONYMIC"),
                        resultSet.getDouble("SALARY"));
                mechanicList.add(mechanic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return mechanicList;
    }

    public void addMechanic(Mechanic mechanic) {
        String request = "INSERT INTO MECHANIC (FIRST_NAME, LAST_NAME, PATRONYMIC, SALARY) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setString(1, mechanic.getFirstName());
            statement.setString(2, mechanic.getLastName());
            statement.setString(3, mechanic.getPatronymic());
            statement.setDouble(4, mechanic.getSalary());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMechanic(Mechanic mechanic) {
        String request = "UPDATE MECHANIC SET FIRST_NAME=?, LAST_NAME=?, PATRONYMIC=?, SALARY=? WHERE ID=?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setString(1, mechanic.getFirstName());
            statement.setString(2, mechanic.getLastName());
            statement.setString(3, mechanic.getPatronymic());
            statement.setDouble(4, mechanic.getSalary());
            statement.setLong(5, mechanic.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeMechanic(Mechanic mechanic) {
        String request = "DELETE FROM MECHANIC WHERE ID=?";
        boolean isRemoved = true;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setLong(1, mechanic.getId());

            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            isRemoved = false;
        } catch (SQLException e) {
            e.printStackTrace();
            isRemoved = false;
        }
        return isRemoved;
    }

    public StatisticDTO getStatistic(Mechanic mechanic) {
        String request = "SELECT COUNT(*) AS AMOUNT FROM MECHANIC INNER JOIN ORDERS ON MECHANIC.ID=ORDERS.MECHANIC WHERE MECHANIC.ID=?";

        StatisticDTO statisticDTO = new StatisticDTO();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {

            statement.setLong(1, mechanic.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                statisticDTO.setAmount(resultSet.getInt("AMOUNT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statisticDTO;
    }
}
