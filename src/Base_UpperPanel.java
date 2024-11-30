import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Base_UpperPanel extends MajorLayoutBase
{
	// App Infos Elements
	private JLabel m_label_AppTitle;
	private JLabel m_label_AppSubTitle;
	
	// Dashboard Elements
	private JLabel m_label_Dashboard;
	
	
	public Base_UpperPanel(BaseFrame baseFrame)
	{
		super(baseFrame);
	}
	
	@Override
	public void Switch2LoginLayout()
	{

	}

	@Override
	protected void Init()
	{
		m_label_AppTitle = new JLabel("An Extracurricular Management App");
		m_label_AppTitle.setBounds(30, 20, 800, 40);
		m_label_AppTitle.setFont(new Font("", Font.BOLD, 30));
		m_label_AppTitle.setForeground(Color.WHITE);
		m_label_AppTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_AppSubTitle = new JLabel("Scheduling and Analyze");
		m_label_AppSubTitle.setBounds(30, 60, 800, 30);
		m_label_AppSubTitle.setFont(new Font("", Font.BOLD, 20));
		m_label_AppSubTitle.setForeground(new Color(219, 59, 54));
		m_label_AppSubTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_Dashboard = new JLabel("Dashboard");
		m_label_Dashboard.setBounds(30, 130, 800, 30);
		m_label_Dashboard.setFont(new Font("", Font.BOLD, 30));
		m_label_Dashboard.setForeground(Color.WHITE);
		m_label_Dashboard.setHorizontalAlignment(SwingConstants.LEFT);
	}

	@Override
	protected void Link()
	{
		m_layout_Main = new JPanel();
		m_layout_Main.setLayout(null);
		m_layout_Main.setBounds(0, 0, 800, 180);
		m_layout_Main.setBackground(new Color(33, 37, 41));
		
		m_layout_Main.add(m_label_AppTitle);
		m_layout_Main.add(m_label_AppSubTitle);
		m_layout_Main.add(m_label_Dashboard);
	}
}