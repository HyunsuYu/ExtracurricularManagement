import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;


public class BaseFrame extends JFrame
{
	// TODO : DB 연결 관련 데이터 Json Serialize 하고 AES 암호화 적용해서 Local에 저장하는 로직 추가 및 그 역도 구현
	public class DBConnCredential
	{
		public String URL, User, Password;
	}
	
	public class DashboardButton
	{
		private BaseFrame m_baseFrame;
		
		private JPanel m_layout_Main;
		
		private JButton m_button_Dashboard;
		private JPanel m_panel_ActiveState;
		
		private int m_index;
		
		
		public DashboardButton(BaseFrame baseFrame, int index, String buttonName)
		{
			m_baseFrame = baseFrame;
			
			m_index = index;
			
			Init(buttonName);
			Link();
		}
		
		public JPanel GetMainLayout()
		{
			return m_layout_Main;
		}
		public int GetIndex()
		{
			return m_index;
		}
		
		public void SetActiveState(boolean bisActive)
		{
			if(bisActive)
			{
				m_button_Dashboard.setForeground(Color.WHITE);
				m_panel_ActiveState.setVisible(true);
			}
			else
			{
				m_button_Dashboard.setForeground(Color.GRAY);
				m_panel_ActiveState.setVisible(false);
			}
		}
		
		private void Init(String buttonName)
		{
			m_button_Dashboard = new JButton(buttonName);
			m_button_Dashboard.setBounds(0, 0, 100, 30);
			m_button_Dashboard.setBackground(new Color(33, 37, 41));
			m_button_Dashboard.setForeground(Color.GRAY);
			m_button_Dashboard.setBorderPainted(false);
			m_button_Dashboard.setFocusPainted(false);
			m_button_Dashboard.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							m_baseFrame.OpenDashboardPanel(m_index);
						}
					});
			
			m_panel_ActiveState = new JPanel();
			m_panel_ActiveState.setBounds(35, 35, 30, 1);
			m_panel_ActiveState.setBackground(new Color(219, 59, 54));
			m_panel_ActiveState.setVisible(false);
			if(m_index == 0)
			{
				m_button_Dashboard.setForeground(Color.WHITE);
				m_panel_ActiveState.setVisible(true);
			}
		}
		private void Link()
		{
			m_layout_Main = new JPanel();
			m_layout_Main.setLayout(null);
			m_layout_Main.setBackground(new Color(33, 37, 41));
			m_layout_Main.setBounds(20 + m_index * 100, 180, 100, 50);
			m_layout_Main.setVisible(true);
			
			m_layout_Main.add(m_button_Dashboard);
			m_layout_Main.add(m_panel_ActiveState);
		}
	}
	
	
	private Container m_contentPen;
	
	private Connection m_dbConn;
	private UUID m_loginID = null;
	
	private Base_SidePanel m_sidePanelInstance;
	private Base_UpperPanel m_upperPanelInstance;
	
	private DashboardButton m_button_Overview, m_button_Lecture, m_button_Analyze;
	private JPanel[] m_panel_DashboardPanels;
	
	
 	public BaseFrame()
	{
		setTitle("Extracurricular Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m_contentPen = getContentPane();
		m_contentPen.setLayout(null);
		
		Connect2DB();

		Init();
		Link();
		
		setSize(1100, 700);
		setResizable(false);
		setVisible(true);
	}
	
	public java.sql.Statement GetSQLStatement() throws SQLException
	{
		return m_dbConn.createStatement();
	}
	
	public UUID GetLoginID()
	{
		return m_loginID;
	}
	public void SetLoginID(UUID id)
	{
		m_loginID = id;
	}
	
	public void OpenDashboardPanel(int targetIndex)
	{
		//for(int dashboardIndex = 0; dashboardIndex < 3; dashboardIndex++)
		//{
		//	m_panel_DashboardPanels[dashboardIndex].setVisible(false);
		//	
		//	if(targetIndex == dashboardIndex)
		//	{
		//		m_panel_DashboardPanels[dashboardIndex].setVisible(true);
		//	}
		//}
		
		switch(targetIndex)
		{
		case 0:
			m_button_Overview.SetActiveState(true);
			m_button_Lecture.SetActiveState(false);
			m_button_Analyze.SetActiveState(false);
			break;
			
		case 1:
			m_button_Overview.SetActiveState(false);
			m_button_Lecture.SetActiveState(true);
			m_button_Analyze.SetActiveState(false);
			break;
			
		case 2:
			m_button_Overview.SetActiveState(false);
			m_button_Lecture.SetActiveState(false);
			m_button_Analyze.SetActiveState(true);
			break;
		}
	}
	
	private void Init()
	{
		m_contentPen.setBackground(new Color(33, 37, 41));

		m_sidePanelInstance = new Base_SidePanel(this);
		m_upperPanelInstance = new Base_UpperPanel(this);
		
		m_contentPen.add(m_sidePanelInstance.GetMainLayout());
		m_contentPen.add(m_upperPanelInstance.GetMainLayout());
		
		// Dashboard
		m_panel_DashboardPanels = new JPanel[3];
		m_button_Overview = new DashboardButton(this, 0, "Overview");
		m_button_Lecture = new DashboardButton(this, 1, "Lecture");
		m_button_Analyze = new DashboardButton(this, 2, "Analyze");
		
		m_contentPen.add(m_button_Overview.GetMainLayout());
		m_contentPen.add(m_button_Lecture.GetMainLayout());
		m_contentPen.add(m_button_Analyze.GetMainLayout());
	}
	private void Link()
	{
		
	}
	
	private void Connect2DB()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
	  	}
	   	catch(Exception e)
	   	{
	    	e.printStackTrace();
	  	}
	      
	  	try
	   	{
	     	String url = "jdbc:mysql://univ-project.mysql.database.azure.com:3306/extracurricularmanagement?useSSL=false&requireSSL=false";
	     	m_dbConn = DriverManager.getConnection(url, "HyunsuYu", "LiOiE6478++");
	   	}
	  	catch(SQLException e)
	  	{
	      	e.printStackTrace();
	   	}
	}
}