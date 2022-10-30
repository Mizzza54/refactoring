package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Michael Gerasimov
 */
public class ProductDao {
    private final Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getAllProducts() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT")) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("name"),
                            rs.getInt("price")
                    ));
                }

                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(String name, long price) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> getProductWithMaxPrice() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1")) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("name"),
                            rs.getInt("price")
                    ));
                }

                return products.stream().findFirst();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> getProductWithMinPrice() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1")) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("name"),
                            rs.getInt("price")
                    ));
                }

                return products.stream().findFirst();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getProductsCount() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM PRODUCT")) {
                List<Integer> counts = new ArrayList<>();
                while (rs.next()) {
                    counts.add(rs.getInt(1));
                }

                return counts.stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("add error message"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getProductsSummaryPrice() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT SUM(price) FROM PRODUCT")) {
                List<Long> counts = new ArrayList<>();
                while (rs.next()) {
                    counts.add(rs.getLong(1));
                }

                return counts.stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("add error message"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
