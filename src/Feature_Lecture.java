import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.UUID;
import java.util.Random;


public class Feature_Lecture extends MajorLayoutBase
{
    private UUID currentLoginID = null;
    private JButton currentSessionButton = null;
    private JButton currentModuleButton = null;

    public Feature_Lecture(BaseFrame baseFrame)
    {
        super(baseFrame);
    }

    @Override
    public void Switch2LoginLayout()
    {
        currentLoginID = m_baseFrame.GetLoginID();
        InitLectureLayout();
    }

    @Override
    protected void Init()
    {
        m_layout_Main = new JPanel();
        m_layout_Main.setLayout(null);
        m_layout_Main.setBounds(30, 240, 740, 400);
        m_layout_Main.setBackground(new Color(33, 37, 41));

        InitLectureLayout();
    }
    @Override
    protected void Link() {
      
    }

    private void InitLectureLayout() {
        m_layout_Main.removeAll();

        JPanel sessionPanel = new JPanel();
        sessionPanel.setLayout(null);
        sessionPanel.setBounds(0, 0, 200, 400);
        sessionPanel.setBackground(new Color(44, 48, 52));
        JPanel modulePanel = new JPanel();
        modulePanel.setLayout(null);
        modulePanel.setBounds(210, 0, 530, 400);
        modulePanel.setBackground(new Color(55, 59, 63));
        m_layout_Main.add(sessionPanel);
        m_layout_Main.add(modulePanel);

        if (currentLoginID == null) {
            System.err.println("CurrentLoginID is null.");
        } else {
            try {
                UUID.fromString(currentLoginID.toString());
                System.out.println("CurrentLoginID is valid UUID: " + currentLoginID.toString());

                Statement stmt = m_baseFrame.GetSQLStatement();

    
                String query = "SELECT si.session_id, si.session_order, si.session_name " +
                               "FROM session_infos si " +
                               "JOIN enrolled_student_infos ei ON si.lecture_id = ei.enrolled_lecture_id " +
                               "WHERE ei.enrolled_student_id = ? " +
                               "ORDER BY si.session_id";

                PreparedStatement pstmt = stmt.getConnection().prepareStatement(query);
                pstmt.setString(1, currentLoginID.toString());
                ResultSet rs = pstmt.executeQuery();

                int yOffset = 10;
                boolean firstSessionSelected = false;
                int makedSessionID = -1;
                while (rs.next()) {
                    int sessionId = rs.getInt("session_id");
                    int sessionOrder = rs.getInt("session_order");
                    String sessionName = rs.getString("session_name");
                    
                    if(makedSessionID == sessionId)
                    {
                    	continue;
                    }
                    else
                    {
                    	makedSessionID = sessionId;
                    }
                    
                    JButton sessionButton = new JButton("Ep." + sessionOrder + " " + sessionName);
                    sessionButton.setBounds(10, yOffset, 180, 30);
                    sessionButton.setBackground(new Color(44, 48, 52));
                    sessionButton.setForeground(Color.WHITE);
                    sessionButton.setFont(new Font("Arial", Font.PLAIN, 14));
                    sessionButton.setFocusPainted(false);
                    sessionButton.setBorderPainted(false);
                    sessionButton.setContentAreaFilled(false);
                    sessionButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (currentSessionButton != null) {
                                currentSessionButton.setForeground(Color.WHITE);
                            }
                            sessionButton.setForeground(Color.RED);
                            currentSessionButton = sessionButton;
                            ShowModules(sessionId, modulePanel);
                        }
                    });
                    sessionPanel.add(sessionButton);

                    if (!firstSessionSelected) {
                        sessionButton.doClick(); 
                        firstSessionSelected = true;
                    }

                    yOffset += 40;
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid UUID format: " + currentLoginID.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        m_layout_Main.revalidate();
        m_layout_Main.repaint();
    }

    private void ShowModules(int sessionId, JPanel modulePanel) {
        modulePanel.removeAll();

        JPanel moduleButtonsPanel = new JPanel();
        moduleButtonsPanel.setLayout(null);
        moduleButtonsPanel.setBounds(0, 0, 530, 50);
        moduleButtonsPanel.setBackground(new Color(44, 48, 52)); 

        JPanel moduleDetailsPanel = new JPanel();
        moduleDetailsPanel.setLayout(null);
        moduleDetailsPanel.setBounds(0, 50, 530, 350); 
        moduleDetailsPanel.setBackground(new Color(44, 48, 52)); 

        try {
            Statement stmt = m_baseFrame.GetSQLStatement();
            ResultSet rs = stmt.executeQuery("SELECT module_id, module_order, module_name, module_content, module_question FROM module_infos WHERE session_id = " + sessionId + " ORDER BY module_order");

            int xOffset = 10;
            boolean firstModuleSelected = false;

            while (rs.next()) {
                int moduleId = rs.getInt("module_id");
                int moduleOrder = rs.getInt("module_order");
                String moduleName = rs.getString("module_name");
                String moduleContent = rs.getString("module_content");
                String moduleQuestion = rs.getString("module_question");
                JButton moduleButton = new JButton("Q." + moduleOrder);
                moduleButton.setBounds(xOffset, 10, 60, 30);
                moduleButton.setBackground(new Color(44, 48, 52));
                moduleButton.setForeground(Color.WHITE);
                moduleButton.setFont(new Font("Arial", Font.PLAIN, 14));
                moduleButton.setFocusPainted(false);
                moduleButton.setBorderPainted(false);
                moduleButton.setContentAreaFilled(false);
                moduleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentModuleButton != null) {
                            currentModuleButton.setForeground(Color.WHITE);
                        }
                        moduleButton.setForeground(Color.RED);
                        currentModuleButton = moduleButton;
                        ShowModuleDetails(moduleOrder, moduleId, moduleName, moduleContent, moduleQuestion, moduleDetailsPanel);
                    }
                });
                moduleButtonsPanel.add(moduleButton);

                if (!firstModuleSelected) {
                    moduleButton.doClick();
                    firstModuleSelected = true;
                }

                xOffset += 70;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modulePanel.add(moduleButtonsPanel);
        modulePanel.add(moduleDetailsPanel);
        modulePanel.revalidate();
        modulePanel.repaint();
    }

    private void ShowModuleDetails(int moduleOrder, int moduleId, String moduleName, String moduleContent, String moduleQuestion, JPanel moduleDetailsPanel) {
        moduleDetailsPanel.removeAll();

        Color baseFrameBackgroundColor = new Color(33, 37, 41);
        JTextArea moduleNameArea = new JTextArea(moduleName);
        moduleNameArea.setFont(new Font("Arial", Font.BOLD, 16));
        moduleNameArea.setForeground(Color.WHITE);
        moduleNameArea.setBackground(baseFrameBackgroundColor);
        moduleNameArea.setLineWrap(true);
        moduleNameArea.setWrapStyleWord(true);
        moduleNameArea.setEditable(false);
        moduleNameArea.setBounds(10, 10, 510, 30);
        moduleNameArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        JScrollPane moduleNameScrollPane = new JScrollPane(moduleNameArea);
        moduleNameScrollPane.setBounds(10, 10, 510, 30);
        moduleNameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        moduleNameScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        moduleDetailsPanel.add(moduleNameScrollPane);

        JTextArea moduleContentArea = new JTextArea(moduleContent);
        moduleContentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        moduleContentArea.setForeground(Color.WHITE);
        moduleContentArea.setBackground(baseFrameBackgroundColor);
        moduleContentArea.setLineWrap(true);
        moduleContentArea.setWrapStyleWord(true);
        moduleContentArea.setEditable(false);
        moduleContentArea.setBounds(10, 50, 510, 140);
        moduleContentArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane moduleContentScrollPane = new JScrollPane(moduleContentArea);
        moduleContentScrollPane.setBounds(10, 50, 510, 140);
        moduleContentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        moduleContentScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        moduleDetailsPanel.add(moduleContentScrollPane);
        
        JTextArea moduleQuestionArea = new JTextArea(moduleQuestion);
        moduleQuestionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        moduleQuestionArea.setForeground(Color.WHITE);
        moduleQuestionArea.setBackground(baseFrameBackgroundColor);
        moduleQuestionArea.setLineWrap(true);
        moduleQuestionArea.setWrapStyleWord(true);
        moduleQuestionArea.setEditable(false);
        moduleQuestionArea.setBounds(10, 200, 510, 30);
        moduleQuestionArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        
        JScrollPane moduleQuestionScrollPane = new JScrollPane(moduleQuestionArea);
        moduleQuestionScrollPane.setBounds(10, 200, 510, 30);
        moduleQuestionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        moduleQuestionScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        moduleDetailsPanel.add(moduleQuestionScrollPane);

        JTextArea answerField = new JTextArea();
        answerField.setFont(new Font("Arial", Font.PLAIN, 14));
        answerField.setForeground(Color.WHITE);
        answerField.setBackground(baseFrameBackgroundColor);
        answerField.setLineWrap(true);
        answerField.setWrapStyleWord(true);
        answerField.setEditable(true);
        answerField.setBounds(10, 240, 510, 60);
        answerField.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        JScrollPane answerFieldScrollPane = new JScrollPane(answerField);
        answerFieldScrollPane.setBounds(10, 240, 510, 60);
        answerFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        answerFieldScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        moduleDetailsPanel.add(answerFieldScrollPane);

        JButton uploadButton = new JButton("Upload");
        uploadButton.setBounds(420, 310, 100, 30);
        uploadButton.setBackground(baseFrameBackgroundColor); 
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFont(new Font("Arial", Font.PLAIN, 14));
        uploadButton.setFocusPainted(false);
        uploadButton.setBorderPainted(false);
        uploadButton.setContentAreaFilled(true); 

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = answerField.getText();
                UploadAnswer(moduleId, answer);
            }
        });
        moduleDetailsPanel.add(uploadButton);

        moduleDetailsPanel.revalidate();
        moduleDetailsPanel.repaint();
    }

    private void UploadAnswer(int moduleId, String answer)
    {
        try
        {
            Statement stmt = m_baseFrame.GetSQLStatement();
            String enrolledInfoQuery = "SELECT enrolled_info_id FROM enrolled_student_infos WHERE enrolled_student_id = '" + currentLoginID + "'";
            ResultSet rs = stmt.executeQuery(enrolledInfoQuery);
            int enrolledInfoId = -1;
            if (rs.next()) {
                enrolledInfoId = rs.getInt("enrolled_info_id");
            }

            Random random = new Random();
            int score = 50 + random.nextInt(51); 

            String query = "INSERT INTO lecture_score_infos (enrolled_info_id, module_id, module_answer, score) VALUES (" +
                           enrolledInfoId + ", " + moduleId + ", '" + answer + "', " + score + ")";
            stmt.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}