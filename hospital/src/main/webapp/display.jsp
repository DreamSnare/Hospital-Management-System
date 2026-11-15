<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registered Patient Records</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background-color: #f0f4f8;
            padding: 30px 20px;
            color: #333;
        }
        .container {
            max-width: 1300px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .header {
            background: #0d6efd;
            color: #ffffff;
            padding: 20px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            font-size: 22px;
        }
        .header a {
            background: #ffffff;
            color: #0d6efd;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-weight: 600;
            font-size: 14px;
        }
        .table-wrapper {
            overflow-x: auto;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }
        th, td {
            border: 1px solid #dee2e6;
            padding: 10px 12px;
            text-align: left;
            white-space: nowrap;
        }
        th {
            background-color: #e9ecef;
            color: #212529;
            font-weight: 600;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #eef2f7;
        }
        .empty-msg, .error-msg {
            text-align: center;
            padding: 30px;
            font-size: 16px;
        }
        .error-msg {
            color: #dc3545;
            font-weight: 600;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>Registered Patient Records</h1>
        <a href="index.html">+ Register New Patient</a>
    </div>

    <div class="table-wrapper">
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <div class="error-msg"><%= errorMessage %></div>
        <%
            }
            List patientList = (List) request.getAttribute("patientList");

            if (patientList == null || patientList.isEmpty()) {
        %>
            <div class="empty-msg">No patient records found. Click "+ Register New Patient" to add one.</div>
        <%
            } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th>Patient ID</th>
                        <th>Patient Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Gender</th>
                        <th>DOB</th>
                        <th>Blood Group</th>
                        <th>Address</th>
                        <th>Disease</th>
                        <th>Doctor Name</th>
                        <th>Department</th>
                        <th>Admission Date</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Object obj : patientList) {
                            Map p = (Map) obj;
                    %>
                    <tr>
                        <td><%= p.get("patientId") %></td>
                        <td><%= p.get("patientName") %></td>
                        <td><%= p.get("email") %></td>
                        <td><%= p.get("phone") %></td>
                        <td><%= p.get("gender") %></td>
                        <td><%= p.get("dob") %></td>
                        <td><%= p.get("bloodGroup") %></td>
                        <td><%= p.get("address") %></td>
                        <td><%= p.get("disease") %></td>
                        <td><%= p.get("doctorName") %></td>
                        <td><%= p.get("department") %></td>
                        <td><%= p.get("admissionDate") %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>
</div>

</body>
</html>