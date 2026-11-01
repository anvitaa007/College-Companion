


package com.collegecompanion;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class CollegeCompanion extends JFrame {

    public CollegeCompanion() {

       
setTitle("College Companion | Student Dashboard");
setExtendedState(JFrame.MAXIMIZED_BOTH);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
setResizable(false);


        JLabel title = new JLabel(
                "COLLEGE COMPANION", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel subtitle = new JLabel(
    "Your Academic Partner", JLabel.CENTER);
        JLabel welcome = new JLabel(
    "Manage your timetable, assignments, attendance and notes",
    JLabel.CENTER);

welcome.setFont(new Font("Arial", Font.PLAIN, 13));
welcome.setForeground(new Color(90, 110, 140));

subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
subtitle.setForeground(new Color(90, 110, 140));
        title.setForeground(new Color(25, 45, 85));
       title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JButton timetable = new JButton("Timetable");
        JButton assignments = new JButton("Assignments");
        JButton viewAssignments = new JButton("View Assignments");
        JButton attendance = new JButton("Attendance");
        JButton notes = new JButton("Notes");
        JButton viewNotes = new JButton("View Saved Notes");
        JButton exit = new JButton("Exit");
        Color blue = new Color(35, 65, 120);
Color white = Color.WHITE;

JButton[] buttons = {
    timetable, assignments, viewAssignments, attendance, notes,viewNotes
};

for (JButton button : buttons) {
    button.setBackground(blue);
    button.setForeground(white);
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
    button.setOpaque(true);
}


exit.setBackground(new Color(180, 55, 65));
exit.setForeground(Color.WHITE);
exit.setFont(new Font("Arial", Font.BOLD, 15));
exit.setFocusPainted(false);
exit.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
exit.setOpaque(true);


        JPanel panel = new JPanel(new GridLayout(0, 2, 20, 20));
        panel.setBackground(new Color(225, 235, 248));
        panel.setBorder(BorderFactory.createEmptyBorder(
    30, 40, 30, 40));

        panel.add(timetable);
        panel.add(assignments);
        panel.add(viewAssignments);
        panel.add(attendance);
        panel.add(notes);
        panel.add(viewNotes);
        JPanel exitPanel = new JPanel();
exitPanel.setBackground(new Color(235, 242, 252));
exitPanel.add(exit);

panel.add(exitPanel);
       getContentPane().setBackground(new Color(225, 235, 248));
        JPanel heading = new JPanel(new GridLayout(2, 1));
heading.setBackground(new Color(235, 242, 252));
heading.add(title);
heading.add(subtitle);
heading.add(welcome);

add(heading, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        timetable.addActionListener(e -> showTimetable());
       assignments.addActionListener(e -> showAssignments());
       viewAssignments.addActionListener(e -> viewSavedAssignments());

notes.addActionListener(e -> manageNotes());
viewNotes.addActionListener(e -> viewSavedNotes());

        attendance.addActionListener(e -> calculateAttendance());
        exit.addActionListener(e -> System.exit(0));
    }
    
private void showAssignments() {
   JTextField titleField = new JTextField(25);
JTextField subjectField = new JTextField(25);
JTextField dateField = new JTextField(25);

titleField.setToolTipText("Enter assignment title");
subjectField.setToolTipText("Enter subject name");
dateField.setToolTipText("Enter date as YYYY-MM-DD");

    Object[] fields = {
        "Assignment title:", titleField,
        "Subject:", subjectField,
        "Due date (YYYY-MM-DD):", dateField
    };

    int choice = JOptionPane.showConfirmDialog(
        this,
        fields,
        "College Companion - Add Assignment",
        JOptionPane.OK_CANCEL_OPTION
    );

    if (choice == JOptionPane.OK_OPTION) {
        String title = titleField.getText().trim();
        String subject = subjectField.getText().trim();
        String dueDate = dateField.getText().trim();

        if (title.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please enter the title and subject."
            );
            return;
        }
          try {
    java.time.LocalDate.parse(dueDate);
} catch (Exception e) {
    JOptionPane.showMessageDialog(
        this,
        "Please enter the date as YYYY-MM-DD."
    );
    return;
}
        boolean saved = Assignment.addAssignment(
            title, subject, dueDate
        );

        if (saved) {
            JOptionPane.showMessageDialog(
                this, "Assignment saved successfully!"
            );
        } else {
            JOptionPane.showMessageDialog(
                this, "Could not save assignment. Check the Output window."
            );
        }
    }
}

    public static Connection connectDB() {
    try {
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/collegecompanion",
            "root",
            "Mango!River#4827"
        );

        System.out.println("Database connected successfully!");
        return con;

    } catch (Exception e) {
        System.out.println("Database connection failed!");
        e.printStackTrace();
        return null;
    }
}

    void showTimetable() {

        String schedule =
                "MONDAY - Java, Mathematics\n"
                + "TUESDAY - Data Structures, OOPS\n"
                + "WEDNESDAY - Digital Electronics\n"
                + "THURSDAY - Java Lab\n"
                + "FRIDAY - Mathematics, Data Structures";

        JOptionPane.showMessageDialog(
                this, schedule, "Timetable",
                JOptionPane.INFORMATION_MESSAGE);
    }

    void manageFile(String filename, String type) {

        String[] options = {"Add " + type, "View " + type + "s"};

        int choice = JOptionPane.showOptionDialog(
                this, "Choose an option", type + " Manager",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {

            String text = JOptionPane.showInputDialog(
                    this, "Enter " + type + ":");

            if (text != null && !text.trim().isEmpty()) {
                try (FileWriter writer =
                             new FileWriter(filename, true)) {

                    writer.write(text + System.lineSeparator());

                    JOptionPane.showMessageDialog(
                            this, type + " saved successfully!");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            this, "Error saving " + type);
                }
            }

        } else if (choice == 1) {

            StringBuilder content = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(filename))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                if (content.length() == 0) {
                    content.append("No ").append(type)
                            .append("s added yet.");
                }

            } catch (IOException ex) {
                content.append("No saved items found yet.");
            }

            JOptionPane.showMessageDialog(
                    this, content.toString(), type + "s",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void calculateAttendance() {

        try {
            String a = JOptionPane.showInputDialog(
                    this, "Enter classes attended:");

            if (a == null) return;

            String t = JOptionPane.showInputDialog(
                    this, "Enter total classes held:");

            if (t == null) return;

            int attended = Integer.parseInt(a);
            int total = Integer.parseInt(t);

            if (total <= 0 || attended < 0
                    || attended > total) {
                JOptionPane.showMessageDialog(
                        this, "Enter valid class numbers.");
                return;
            }
           String subject = JOptionPane.showInputDialog(
             this, "Enter subject name:");

        if (subject == null || subject.trim().isEmpty())
        {
             return;
         }
            double percentage =
                    (attended * 100.0) / total;
        
try {
    Connection con = Database.connectDB();

    if (con != null) {
        String sql = "INSERT INTO attendance "
                + "(subject, total_classes, attended_classes) "
                + "VALUES (?, ?, ?)";

        java.sql.PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(1, subject);
        ps.setInt(2, total);
        ps.setInt(3, attended);

        ps.executeUpdate();

        ps.close();
        con.close();

        System.out.println("Attendance saved successfully!");
    }
} catch (Exception e) {
    System.out.println("Error saving attendance!");
    e.printStackTrace();
}

            String status;

if (percentage >= 75) {
    status = "Eligible";
} else {
    status = "Below 75%";
}

JOptionPane.showMessageDialog(
    this,
    String.format(
        "SUBJECT: %s\nATTENDANCE: %.2f%%\nSTATUS: %s",
        subject, percentage, status
    ),
    "Attendance Report",
    JOptionPane.INFORMATION_MESSAGE
);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Please enter numbers only.");
        }
    }

    public static void main(String[] args) {
          Database.connectDB();
        SwingUtilities.invokeLater(() -> {
            new CollegeCompanion().setVisible(true);
        });
    }
    
private void manageNotes() {
    String title = JOptionPane.showInputDialog(
            this, "Enter note title:");

    if (title == null || title.trim().isEmpty()) {
        return;
    }

    String content = JOptionPane.showInputDialog(
            this, "Enter note content:");

    if (content == null) {
        return;
    }

    try (Connection con = Database.connectDB()) {
        if (con == null) {
            return;
        }

        String sql = "INSERT INTO notes (title, content) VALUES (?, ?)";

        java.sql.PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, content);
        ps.executeUpdate();
        ps.close();

        JOptionPane.showMessageDialog(
                this, "Note saved successfully!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this, "Error saving note: " + e.getMessage());
    }
}
private void viewSavedNotes() {
    String notes = "";

    String sql = "SELECT * FROM notes ORDER BY id DESC";

    try (Connection con = Database.connectDB();
         java.sql.Statement st = con.createStatement();
         java.sql.ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            notes += "Title: " + rs.getString("title")
                    + "\nContent: " + rs.getString("content")
                    + "\n--------------------------\n";
        }

        if (notes.isEmpty()) {
            notes = "No saved notes found.";
        }

    } catch (Exception e) {
        notes = "Could not load notes.";
        e.printStackTrace();
    }

    javax.swing.JTextArea area = new javax.swing.JTextArea(notes, 12, 30);
    area.setEditable(false);
    area.setLineWrap(true);
    area.setWrapStyleWord(true);

    javax.swing.JScrollPane scrollPane =
            new javax.swing.JScrollPane(area);

    JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Saved Notes",
            JOptionPane.INFORMATION_MESSAGE
    );
}

private void viewSavedAssignments() {
    String assignments = Assignment.viewAssignments();

    JTextArea area = new JTextArea(assignments, 12, 30);
    area.setEditable(false);
    area.setFont(new Font("Arial", Font.PLAIN, 14));

    JScrollPane scrollPane = new JScrollPane(area);

    JOptionPane.showMessageDialog(
        this,
        scrollPane,
        "Saved Assignments",
        JOptionPane.INFORMATION_MESSAGE
    );
}
}
