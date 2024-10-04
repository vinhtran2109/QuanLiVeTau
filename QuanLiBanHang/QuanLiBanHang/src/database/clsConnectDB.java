package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class clsConnectDB {
    // Chuỗi kết nối đến SQL Server
    private String connectionString = "jdbc:sqlserver://VinhTran:1433;databaseName=YourDatabase;user=YourUsername;password=YourPassword;\r\n"
    		+ "";
    
    private Connection conn;

    // Hàm khởi tạo, thực hiện việc kết nối CSDL
    public clsConnectDB() {
        try {
            // Nạp driver SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(connectionString);

            if (conn != null) {
                System.out.println("Kết nối CSDL SQL Server thành công!");
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Lỗi nạp driver: " + ex.toString());
        } catch (SQLException ex) {
            System.out.println("Lỗi kết nối CSDL: " + ex.toString());
        }
    }

    // Phương thức thực thi câu lệnh SELECT
    public ResultSet ExcuteQueryGetTable(String cauTruyVanSQL) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(cauTruyVanSQL);
        } catch (SQLException ex) {
            System.out.println("Lỗi thực thi truy vấn: " + ex.toString());
        }
        return null;
    }

    // Phương thức thực thi câu lệnh INSERT, UPDATE, DELETE
    public void ExcuteQueryUpdateDB(String cauTruyVanSQL) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(cauTruyVanSQL);
            System.out.println("Thực thi lệnh SQL thành công!");
        } catch (SQLException ex) {
            System.out.println("Lỗi thực thi cập nhật: " + ex.toString());
        }
    }

    // Phương thức đóng kết nối CSDL
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Đã đóng kết nối CSDL.");
            } catch (SQLException ex) {
                System.out.println("Lỗi khi đóng kết nối: " + ex.toString());
            }
        }
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng
        clsConnectDB db = new clsConnectDB();
        
        // Thực thi truy vấn SELECT
        ResultSet rs = db.ExcuteQueryGetTable("SELECT * FROM SanPham");
        try {
            while (rs != null && rs.next()) {
                System.out.println("Sản phẩm: " + rs.getString("TenSanPham"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đọc kết quả: " + e.toString());
        }

        // Đóng kết nối
        db.closeConnection();
    }
}
