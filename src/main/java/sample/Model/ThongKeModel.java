package main.java.sample.Model;

import java.sql.*;
import java.util.*;

public class ThongKeModel {
    private Connection connection;

    public ThongKeModel(Connection connection) {
        this.connection = connection;
    }

    // Lấy danh sách phí chưa thanh toán
    public List<Map<String, Object>> getUnpaidFees() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = """
            SELECT h.household_id, h.room_number, r.name as resident_name, f.name as fee_name, 
                   hf.amount, hf.month
            FROM household h
            JOIN resident r ON h.owned_by = r.resident_id
            JOIN household_fees hf ON h.household_id = hf.household_id
            JOIN fees f ON hf.fees_id = f.fees_id
            LEFT JOIN (
                SELECT household_id, SUM(amount) as paid, payment_date
                FROM household_payments
                GROUP BY household_id, payment_date
            ) hp ON h.household_id = hp.household_id AND hf.month = hp.payment_date
            WHERE hp.paid IS NULL OR hp.paid < hf.amount
            ORDER BY hf.month DESC, h.room_number
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("household_id", rs.getInt("household_id"));
                row.put("room_number", rs.getString("room_number"));
                row.put("resident_name", rs.getString("resident_name"));
                row.put("fee_name", rs.getString("fee_name"));
                row.put("amount", rs.getDouble("amount"));
                row.put("month", rs.getDate("month"));
                result.add(row);
            }
        }
        return result;
    }

    // Lấy thống kê cư dân theo hộ
    public List<Map<String, Object>> getResidentsByHousehold() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = """
            SELECT h.household_id, h.room_number, r.name as resident_name,
                   (SELECT COUNT(*) FROM temporary_absent ta WHERE ta.resident_id = r.resident_id AND (ta.end_at IS NULL OR ta.end_at > CURRENT_DATE)) as temp_absent_count,
                   (SELECT COUNT(*) FROM temporary_residence tr WHERE tr.temp_resident_id = r.resident_id AND (tr.end_at IS NULL OR tr.end_at > CURRENT_DATE)) as temp_resident_count
            FROM household h
            JOIN resident r ON h.household_id = r.household_id
            ORDER BY h.room_number
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("household_id", rs.getInt("household_id"));
                row.put("room_number", rs.getString("room_number"));
                row.put("resident_name", rs.getString("resident_name"));
                row.put("temp_absent_count", rs.getInt("temp_absent_count"));
                row.put("temp_resident_count", rs.getInt("temp_resident_count"));
                result.add(row);
            }
        }
        return result;
    }

    // Lấy thống kê tạm vắng và tạm trú
    public Map<String, Integer> getTemporaryStats() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        String query = """
            SELECT 
                (SELECT COUNT(DISTINCT resident_id) FROM temporary_absent WHERE end_at IS NULL OR end_at > CURRENT_DATE) as temp_absent_count,
                (SELECT COUNT(DISTINCT temp_resident_id) FROM temporary_residence WHERE end_at IS NULL OR end_at > CURRENT_DATE) as temp_resident_count
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                stats.put("temporary_absent", rs.getInt("temp_absent_count"));
                stats.put("temporary_resident", rs.getInt("temp_resident_count"));
            }
        }
        return stats;
    }

    // Lấy doanh thu theo tháng của một năm
    public List<Map<String, Object>> getMonthlyRevenue(int year) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = """
            SELECT 
                DATE_FORMAT(payment_date, '%Y-%m') as month,
                SUM(amount) as total_amount
            FROM household_payments
            WHERE YEAR(payment_date) = ?
            GROUP BY DATE_FORMAT(payment_date, '%Y-%m')
            ORDER BY month
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("month", java.sql.Date.valueOf(rs.getString("month") + "-01"));
                    row.put("amount", rs.getDouble("total_amount"));
                    result.add(row);
                }
            }
        }
        return result;
    }
} 