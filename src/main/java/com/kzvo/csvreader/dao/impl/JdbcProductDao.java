package com.kzvo.csvreader.dao.impl;

import com.kzvo.csvreader.dao.ProductDao;
import com.kzvo.csvreader.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String connectionUrl;

    @Override
    public void saveProduct(Product product) {
        try {
            Class.forName(driver);

            Properties dbProperties = new Properties();
            dbProperties.put("user", user);
            dbProperties.put("password", password);

            Connection connection = DriverManager
                    .getConnection(connectionUrl, dbProperties);

            String sql = "INSERT INTO products (name, price, type) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setObject(2, product.getPrice());
            statement.setInt(3, product.getType());

            statement.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to the DB", e);
        }
    }
}
