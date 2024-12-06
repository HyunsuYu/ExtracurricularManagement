import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class Feature_Analyze extends MajorLayoutBase
{
	private JPanel m_scoreLayout;
	private JPanel m_sessions;
	private JPanel m_modules;
	private JPanel m_answers;
	
	private JLabel m_label_ScoreTitle;
	private JLabel[] m_label_ScoreNums;
	private ArrayList<ArrayList<Integer>> m_scores;
	
	private JLabel m_label_SessionTitle;
	private JPanel m_panel_SessionContent;
	private JScrollPane m_scrollPane_Session;
	private ArrayList<JButton> m_button_SessionBtns;
	private int m_curSessionID;
	
	private JLabel m_label_ModuleTitle;
	private JPanel m_panel_ModuleContent;
	private JScrollPane m_scrollPane_Module;
	private ArrayList<JButton> m_button_ModuleBtns;
	private int m_curModuleIDOffset;
	private int m_curModuleID;
	
	private JLabel m_label_AnswerTitle;
	private JLabel m_label_Answer;
	private ArrayList<Integer> m_curEnrolledInfoIDs;
	
	
	public Feature_Analyze(BaseFrame baseFrame)
	{
		super(baseFrame);
	}
	
	@Override
	public void Switch2LoginLayout()
	{
		RenderSession();
	}

	@Override
	protected void Init()
	{
		Init_ScoreGraph();
		Init_SelectSession();
		Init_SelectModule();
		Init_ShowAnswer();
	}
	
	private void Init_ScoreGraph()
	{
		// Score
		m_scoreLayout = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				g.setColor(new Color(60, 60, 60));
				
				for(int count = 0; count < 6; count++)
				{
					g.drawLine(50, 60 + count * 23, 420, 60 + count * 23);	
				}
				
				if(m_scores == null)
				{
					return;
				}
				
				g.setColor(Color.RED);
				for(int index = 0; index < m_scores.size(); index++)
				{
					if(index > 0)
					{
						g.setColor(Color.GRAY);
					}
					
					for(int scoreIndex = 0; scoreIndex < m_scores.get(index).size() - 1; scoreIndex++)
					{
						g.drawLine(55 + scoreIndex * 20,
								   (int)(60 + 115 - 115 * (m_scores.get(index).get(scoreIndex) / 100.0f)),
								   55 + (scoreIndex + 1) * 20,
								   (int)(60 + 115 - 115 * (m_scores.get(index).get(scoreIndex + 1) / 100.0f)));
					}
				}
			}
		};
		m_scoreLayout.setLayout(null);
		m_scoreLayout.setBounds(0, 0, 435, 195);
		m_scoreLayout.setBackground(new Color(44, 47, 50));
		
		m_label_ScoreTitle = new JLabel("Score");
		m_label_ScoreTitle.setBounds(20, 20, 240, 13);
		m_label_ScoreTitle.setFont(new Font("", Font.BOLD, 13));
		m_label_ScoreTitle.setForeground(Color.WHITE);
		m_label_ScoreTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_ScoreNums = new JLabel[6];
		for(int index = 0; index < 6; index++)
		{
			m_label_ScoreNums[index] = new JLabel(Integer.toString(100 - index * 20));
			m_label_ScoreNums[index].setBounds(20, 55 + index * 23, 20, 10);
			m_label_ScoreNums[index].setFont(new Font("", Font.PLAIN, 10));
			m_label_ScoreNums[index].setForeground(new Color(60, 60, 60));
			m_label_ScoreNums[index].setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
		m_curEnrolledInfoIDs = new ArrayList<Integer>();
	}
	private void Init_SelectSession()
	{
		m_sessions = new JPanel();
		m_sessions.setLayout(null);
		m_sessions.setBounds(445, 0, 295, 195);
		m_sessions.setBackground(new Color(44, 47, 50));
		
		m_label_SessionTitle = new JLabel("Sessions");
		m_label_SessionTitle.setBounds(20, 20, 240, 13);
		m_label_SessionTitle.setFont(new Font("", Font.BOLD, 13));
		m_label_SessionTitle.setForeground(Color.WHITE);
		m_label_SessionTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_panel_SessionContent = new JPanel();
		m_panel_SessionContent.setLayout(new BoxLayout(m_panel_SessionContent, BoxLayout.Y_AXIS));
		m_panel_SessionContent.setBackground(new Color(44, 47, 50));
		
		m_scrollPane_Session = new JScrollPane(m_panel_SessionContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_scrollPane_Session.setBounds(20, 50, 255, 130);
		m_scrollPane_Session.setBorder(BorderFactory.createEmptyBorder());
		
		m_button_SessionBtns = new ArrayList<JButton>();
	}
	private void Init_SelectModule()
	{
		m_modules = new JPanel();
		m_modules.setLayout(null);
		m_modules.setBounds(0, 205, 295, 195);
		m_modules.setBackground(new Color(44, 47, 50));
		
		m_label_ModuleTitle = new JLabel("Modules");
		m_label_ModuleTitle.setBounds(20, 20, 240, 13);
		m_label_ModuleTitle.setFont(new Font("", Font.BOLD, 13));
		m_label_ModuleTitle.setForeground(Color.WHITE);
		m_label_ModuleTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_panel_ModuleContent = new JPanel();
		m_panel_ModuleContent.setLayout(new BoxLayout(m_panel_ModuleContent, BoxLayout.Y_AXIS));
		m_panel_ModuleContent.setBackground(new Color(44, 47, 50));
		
		m_scrollPane_Module = new JScrollPane(m_panel_ModuleContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_scrollPane_Module.setBounds(20, 50, 255, 130);
		m_scrollPane_Module.setBorder(BorderFactory.createEmptyBorder());
		
		m_button_ModuleBtns = new ArrayList<JButton>();
	}
	private void Init_ShowAnswer()
	{
		m_answers = new JPanel();
		m_answers.setLayout(null);
		m_answers.setBounds(305, 205, 435, 195);
		m_answers.setBackground(new Color(44, 47, 50));
		
		m_label_AnswerTitle = new JLabel("Answer");
		m_label_AnswerTitle.setBounds(20, 20, 240, 13);
		m_label_AnswerTitle.setFont(new Font("", Font.BOLD, 13));
		m_label_AnswerTitle.setForeground(Color.WHITE);
		m_label_AnswerTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_Answer = new JLabel("None");
		m_label_Answer.setBounds(20, 45, 390, 140);
		m_label_Answer.setFont(new Font("", Font.PLAIN, 10));
		m_label_Answer.setForeground(Color.WHITE);
		m_label_Answer.setVerticalAlignment(SwingConstants.TOP);
		m_label_Answer.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	private void RenderScore()
	{
		m_scores = new ArrayList<ArrayList<Integer>>();
		
		java.sql.Statement query = null;
		try
		{
			query = m_baseFrame.GetSQLStatement();
			
			for(int index = 0; index < m_curEnrolledInfoIDs.size(); index++)
			{
				String queryContent = String.format("SELECT * FROM extracurricularmanagement.lecture_score_infos"
						+ " WHERE enrolled_info_id = \"%d\" ORDER BY module_id;",
						m_curEnrolledInfoIDs.get(index));

						ResultSet result = query.executeQuery(queryContent);

						m_scores.add(new ArrayList<Integer>());
						while(result.next())
						{
							m_scores.get(index).add(result.getInt("score"));

						}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private void RenderSession()
	{
		UUID curLoginID = m_baseFrame.GetLoginID();
		m_curSessionID = 1;
		try
		{
			// m_curEnrolledInfoID
			Statement query = m_baseFrame.GetSQLStatement();
			
			String queryContent = String.format("SELECT * FROM extracurricularmanagement.enrolled_student_infos"
            									+ " WHERE enrolled_student_id = \"%s\";",
            									curLoginID.toString());
            
            ResultSet result = query.executeQuery(queryContent);
            
            m_curEnrolledInfoIDs.clear();
            while(result.next())
            {
            	m_curEnrolledInfoIDs.add(result.getInt("enrolled_info_id"));
            }
			
			// Session
            queryContent = "SELECT si.session_id, si.session_order, si.session_name " +
                           "FROM session_infos si " +
                           "JOIN enrolled_student_infos ei ON si.lecture_id = ei.enrolled_lecture_id " +
                           "WHERE ei.enrolled_student_id = ? " +
                           "ORDER BY si.session_id";

            PreparedStatement pstmt = query.getConnection().prepareStatement(queryContent);
            pstmt.setString(1, curLoginID.toString());
            ResultSet rs = pstmt.executeQuery();

            boolean firstSessionSelected = false;
            int precSessionOrder = -1;
            while (rs.next())
            {
                int sessionOrder = rs.getInt("session_order");
                String sessionName = rs.getString("session_name");
                
                if(precSessionOrder == sessionOrder)
                {
                	continue;
                }
                else
                {
                	precSessionOrder = sessionOrder;
                }
            	
            	JButton sessionButton = new JButton("Ep." + sessionOrder + " " + sessionName);
            	sessionButton.setPreferredSize(new Dimension(200, 50));
                sessionButton.setBackground(new Color(44, 48, 52));
                sessionButton.setForeground(Color.WHITE);
                sessionButton.setFont(new Font("Arial", Font.PLAIN, 14));
                sessionButton.setFocusPainted(false);
                sessionButton.setBorderPainted(false);
                sessionButton.setContentAreaFilled(false);
                sessionButton.setHorizontalAlignment(SwingConstants.LEFT);
                sessionButton.addActionListener(new ActionListener()
                		{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								int curBtnIndex = m_button_SessionBtns.indexOf((JButton)e.getSource());
								m_curSessionID = curBtnIndex + 1;
								
								for(int index = 0; index < m_button_SessionBtns.size(); index++)
								{
									m_button_SessionBtns.get(index).setForeground(Color.WHITE);
								}
								m_button_SessionBtns.get(m_curSessionID - 1).setForeground(Color.RED);
								
								RenderScore();
								RenderModule();
							}
                		});
                m_button_SessionBtns.add(sessionButton);
                
                m_panel_SessionContent.add(sessionButton);
                
                if (!firstSessionSelected)
                {
                    sessionButton.doClick(); 
                    firstSessionSelected = true;
                }
            }
        }
		catch (Exception e)
		{
			e.printStackTrace();
        }
	}
	private void RenderModule()
	{
		System.out.println("session - " +m_curSessionID);
		try
		{
            Statement stmt = m_baseFrame.GetSQLStatement();
            ResultSet rs = stmt.executeQuery("SELECT module_id, module_order FROM module_infos WHERE session_id = " +
            								m_curSessionID +
            								" ORDER BY module_order");

            boolean firstModuleSelected = false;
            m_curModuleID = 1;
            m_button_ModuleBtns.clear();
            m_panel_ModuleContent.removeAll();
            while (rs.next())
            {
            	int moduleOrder = rs.getInt("module_order");
            	
            	JButton moduleButton = new JButton("Q." + moduleOrder);
            	moduleButton.setPreferredSize(new Dimension(200, 50));
            	moduleButton.setBackground(new Color(44, 48, 52));
            	moduleButton.setForeground(Color.WHITE);
            	moduleButton.setFont(new Font("Arial", Font.PLAIN, 14));
            	moduleButton.setFocusPainted(false);
            	moduleButton.setBorderPainted(false);
            	moduleButton.setContentAreaFilled(false);
            	moduleButton.addActionListener(new ActionListener()
                		{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								int curBtnIndex = m_button_ModuleBtns.indexOf((JButton)e.getSource());
								m_curModuleID = curBtnIndex + 1 + m_curModuleIDOffset;
								
								for(int index = 0; index < m_button_ModuleBtns.size(); index++)
								{
									m_button_ModuleBtns.get(index).setForeground(Color.WHITE);
								}
								m_button_ModuleBtns.get(curBtnIndex).setForeground(Color.RED);
								
								RenderAnswer();
							}
                		});
                m_button_ModuleBtns.add(moduleButton);
                
                m_panel_ModuleContent.add(moduleButton);
                
                if (!firstModuleSelected)
                {
                	m_curModuleIDOffset = rs.getInt("module_id") - 1;
                	
                	moduleButton.doClick(); 
                    firstModuleSelected = true;
                }
            }
        }
		catch (SQLException e)
		{
            e.printStackTrace();
        }
	}
	private void RenderAnswer()
	{
		java.sql.Statement query = null;
		try
		{
			query = m_baseFrame.GetSQLStatement();
			
			// start day
			String queryContent = String.format("SELECT * FROM extracurricularmanagement.lecture_score_infos"
												+ " WHERE enrolled_info_id = \"%d\" and module_id = \"%d\";",
												m_curEnrolledInfoIDs.getFirst(), m_curModuleID);
			
			ResultSet result = query.executeQuery(queryContent);
			while(result.next())
			{
				m_label_Answer.setText(result.getString("module_answer"));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	protected void Link()
	{
		m_layout_Main = new JPanel();
		m_layout_Main.setLayout(null);
		m_layout_Main.setBounds(30, 240, 740, 400);
		m_layout_Main.setBackground(new Color(33, 37, 41));
		
		m_scoreLayout.add(m_label_ScoreTitle);
		for(int index = 0; index < 6; index++)
		{
			m_scoreLayout.add(m_label_ScoreNums[index]);
		}
		
		m_sessions.add(m_label_SessionTitle);
		m_sessions.add(m_scrollPane_Session);
		
		m_modules.add(m_label_ModuleTitle);
		m_modules.add(m_scrollPane_Module);
		
		m_answers.add(m_label_AnswerTitle);
		m_answers.add(m_label_Answer);
		
		m_layout_Main.add(m_scoreLayout);
		m_layout_Main.add(m_sessions);
		m_layout_Main.add(m_modules);
		m_layout_Main.add(m_answers);
	}
}