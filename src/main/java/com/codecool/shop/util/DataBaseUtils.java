package com.codecool.shop.util;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataBaseUtils {

    public static Object withStatement(DataBaseConfiguration dataBaseConfiguration, String query, Function<PreparedStatement, Object> function) {
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return function.apply(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void withStatementNoReturn(DataBaseConfiguration dataBaseConfiguration, String query, Consumer<PreparedStatement> consumer) {
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            consumer.accept(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
