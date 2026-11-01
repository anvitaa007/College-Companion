/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.collegecompanion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class Assignment {
     public static boolean addAssignment(
            String title, String subject, String dueDate) {

        String sql = "INSERT INTO assignments "
                   + "(title, subject, due_date) VALUES (?, ?, ?)";

        try (Connection con = Database.connectDB();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, subject);

            if (dueDate == null || dueDate.trim().isEmpty()) {
                ps.setNull(3, java.sql.Types.DATE);
            } else {
                ps.setDate(3, Date.valueOf(dueDate));
            }

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error saving assignment!");
            e.printStackTrace();
            return false;
        }
    }
     public static String viewAssignments() {
    String result = "";

    String sql = "SELECT * FROM assignments ORDER BY id DESC";

    try (Connection con = Database.connectDB();
         java.sql.Statement st = con.createStatement();
         java.sql.ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            result += "ASSIGNMENT\n"
        + "Title: " + rs.getString("title")
        + "\nSubject: " + rs.getString("subject")
        + "\nDue Date: " + rs.getString("due_date")
        + "\n--------------------------------\n";
        }

        if (result.isEmpty()) {
            result = "No assignments found.";
        }

    } catch (Exception e) {
        result = "Could not load assignments.";
        e.printStackTrace();
    }

    return result;
}
}

