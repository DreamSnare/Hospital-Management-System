package com.hospital;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList patientList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT PATIENT_ID, PATIENT_NAME, EMAIL, PHONE, GENDER, DOB, "
                       + "BLOOD_GROUP, ADDRESS, DISEASE, DOCTOR_NAME, DEPARTMENT, ADMISSION_DATE "
                       + "FROM PATIENT ORDER BY PATIENT_ID ASC";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map patient = new HashMap();
                String email = rs.getString("EMAIL");
                String dob = rs.getDate("DOB") != null ? sdf.format(rs.getDate("DOB")) : "-";
                String admDate = rs.getDate("ADMISSION_DATE") != null ? sdf.format(rs.getDate("ADMISSION_DATE")) : "-";

                patient.put("patientId", String.valueOf(rs.getInt("PATIENT_ID")));
                patient.put("patientName", rs.getString("PATIENT_NAME"));
                patient.put("email", email != null ? email : "-");
                patient.put("phone", rs.getString("PHONE"));
                patient.put("gender", rs.getString("GENDER"));
                patient.put("dob", dob);
                patient.put("bloodGroup", rs.getString("BLOOD_GROUP"));
                patient.put("address", rs.getString("ADDRESS"));
                patient.put("disease", rs.getString("DISEASE"));
                patient.put("doctorName", rs.getString("DOCTOR_NAME"));
                patient.put("department", rs.getString("DEPARTMENT"));
                patient.put("admissionDate", admDate);

                patientList.add(patient);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Database Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        request.setAttribute("patientList", patientList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("display.jsp");
        dispatcher.forward(request, response);
    }
}