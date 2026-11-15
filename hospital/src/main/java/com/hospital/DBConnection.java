package com.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "system"; // <-- Put your Oracle 10g password here

    private static boolean isTableCreated = false;

    private static final String CREATE_PATIENT_TABLE_SQL =
        "CREATE TABLE PATIENT ("
        + "PATIENT_ID NUMBER(5) PRIMARY KEY, "
        + "PATIENT_NAME VARCHAR2(100) NOT NULL, "
        + "EMAIL VARCHAR2(100), "
        + "PHONE VARCHAR2(15), "
        + "GENDER VARCHAR2(10), "
        + "DOB DATE, "
        + "BLOOD_GROUP VARCHAR2(5), "
        + "ADDRESS VARCHAR2(200), "
        + "DISEASE VARCHAR2(150), "
        + "DOCTOR_NAME VARCHAR2(100), "
        + "DEPARTMENT VARCHAR2(50), "
        + "ADMISSION_DATE DATE"
        + ")";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        if (!isTableCreated) {
            createTableIfNeeded(conn);
        }

        return conn;
    }

    private static synchronized void createTableIfNeeded(Connection conn) {
        if (isTableCreated) {
            return;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_PATIENT_TABLE_SQL);
            isTableCreated = true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 955) { // ORA-00955: table already exists
                isTableCreated = true;
            } else {
                e.printStackTrace();
            }
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}