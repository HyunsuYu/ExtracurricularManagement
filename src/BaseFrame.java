import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.UUID;


public class BaseFrame extends JFrame
{
	// TODO : DB 연결 관련 데이터 Json Serialize 하고 AES 암호화 적용해서 Local에 저장하는 로직 추가 및 그 역도 구현
	public class DBConnCredential
	{
		public String URL, User, Password;
	}
	
	
	private Container m_contentPen;
	
	private Connection m_dbConn;
	private UUID m_loginID = null;
	
	private Base_SidePanel m_sidePanelInstance;
	private Base_UpperPanel m_upperPanelInstance;

	private JPanel m_layout_SidePanel;
	
	
	public BaseFrame()
	{
		setTitle("Extracurricular Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m_contentPen = this.getContentPane();
		m_contentPen.setLayout(null);

		Init();
		
		Connect2DB();
		
		this.setSize(1100, 700);
		this.setResizable(false);
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
	
	private void Init()
	{
		m_contentPen.setBackground(new Color(33, 37, 41));

		m_sidePanelInstance = new Base_SidePanel(this);
		m_upperPanelInstance = new Base_UpperPanel(this);
		
		m_contentPen.add(m_sidePanelInstance.GetMainLayout());
		//m_contentPen.add(m_upperPanelInstance.GetMainLayout());
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