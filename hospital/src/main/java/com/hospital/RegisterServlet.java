package com.hospital;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String patientIdStr = request.getParameter("patientId");
        String patientName = request.getParameter("patientName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dob");
        String bloodGroup = request.getParameter("bloodGroup");
        String address = request.getParameter("address");
        String disease = request.getParameter("disease");
        String doctorName = request.getParameter("doctorName");
        String department = request.getParameter("department");
        String admissionDateStr = request.getParameter("admissionDate");

        Connection conn = null;
        PreparedStatement pstmt = null;

        out.println("<!DOCTYPE html><html><head><title>Registration Status</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; display: flex; justify-content: center; padding-top: 80px; }");
        out.println(".card { background: #fff; padding: 30px 40px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); text-align: center; max-width: 500px; }");
        out.println(".success { color: #198754; font-size: 22px; font-weight: bold; margin-bottom: 20px; }");
        out.println(".error { color: #dc3545; font-size: 20px; font-weight: bold; margin-bottom: 15px; }");
        out.println(".links a { display: inline-block; margin: 10px 8px 0; padding: 10px 18px; text-decoration: none; border-radius: 6px; font-weight: 600; }");
        out.println(".btn-primary { background: #0d6efd; color: #fff; }");
        out.println(".btn-secondary { background: #6c757d; color: #fff; }");
        out.println("</style></head><body><div class='card'>");

        try {
            int patientId = Integer.parseInt(patientIdStr);
            Date dob = Date.valueOf(dobStr);
            Date admissionDate = Date.valueOf(admissionDateStr);

            conn = DBConnection.getConnection();

            String sql = "INSERT INTO PATIENT (PATIENT_ID, PATIENT_NAME, EMAIL, PHONE, GENDER, "
                       + "DOB, BLOOD_GROUP, ADDRESS, DISEASE, DOCTOR_NAME, DEPARTMENT, ADMISSION_DATE) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            pstmt.setString(2, patientName);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, gender);
            pstmt.setDate(6, dob);
            pstmt.setString(7, bloodGroup);
            pstmt.setString(8, address);
            pstmt.setString(9, disease);
            pstmt.setString(10, doctorName);
            pstmt.setString(11, department);
            pstmt.setDate(12, admissionDate);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                out.println("<div class='success'>Patient Registered Successfully!</div>");
            } else {
                out.println("<div class='error'>Failed to register patient.</div>");
            }
        } catch (Exception e) {
            out.println("<div class='error'>Registration Error!</div>");
            out.println("<p style='color:#555; margin-bottom:15px;'>" + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        out.println("<div class='links'>");
        out.println("<a href='index.html' class='btn-secondary'>Register Another Patient</a>");
        out.println("<a href='DisplayServlet' class='btn-primary'>View All Patients</a>");
        out.println("</div></div></body></html>");
    }
}