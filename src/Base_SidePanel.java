import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Base_SidePanel extends MajorLayoutBase
{
	public class NewsItem
	{
		private JPanel m_layout_Main;
		
		private JLabel m_label_NewsTitle;
		private JButton m_button_OpenNews;
		
		private URI m_uri;
		
		
		public NewsItem()
		{
			Init();
			Link();
		}
		
		public JPanel GetMainLayout()
		{
			return m_layout_Main;
		}
		
		public void SetNewsTitle(String newsTitle)
		{
			m_label_NewsTitle.setText(newsTitle);
		}
		public void SetUrl(String url)
		{
			try
			{
				m_uri = new URI(url);
			}
			catch (URISyntaxException e)
			{
				e.printStackTrace();
			}
		}
		
		private void Init()
		{
			try
			{
				m_uri = new URI("https://hyunsuyu.github.io/Projects/ExtracurricularManagement/ExtracurricularManagement.html");
			}
			catch (URISyntaxException e)
			{
				e.printStackTrace();
			}
			
			m_label_NewsTitle = new JLabel("Default");
			m_label_NewsTitle.setBounds(20, 20, 70, 20);
			m_label_NewsTitle.setFont(new Font("", Font.PLAIN, 10));
			m_label_NewsTitle.setForeground(Color.WHITE);
			
			BufferedImage urlImage = null;
			try
			{
				urlImage = ImageIO.read(new File("./src/imgs/url.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			Image dimg = urlImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(dimg);
			m_button_OpenNews = new JButton(icon);
			m_button_OpenNews.setBounds(180, 15, 20, 20);
			m_button_OpenNews.setBackground(new Color(33, 37, 41));
			m_button_OpenNews.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							if (!m_uri.getPath().isBlank() &&
								Desktop.isDesktopSupported() &&
								Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
							{
								try
								{
									Desktop.getDesktop().browse(m_uri);
								}
								catch (IOException e1)
								{
									e1.printStackTrace();
								}
							}
						}
					});
		}
		private void Link()
		{
			m_layout_Main = new JPanel();
			m_layout_Main.setLayout(null);
			m_layout_Main.setBackground(new Color(33, 37, 41));
			m_layout_Main.setPreferredSize(new Dimension(240, 50));
			m_layout_Main.setVisible(true);
			
			m_layout_Main.add(m_label_NewsTitle);
			m_layout_Main.add(m_button_OpenNews);
		}
	}
	
	
	// Profile Elements
	private JLabel m_label_Name;
	private JLabel m_label_Roll;
	private JButton m_button_ProfileIcon;
	
	// SignIn Elements
	private JPanel m_layout_SignIn;
	private JLabel m_label_SignIn;
	
	private JLabel m_label_ID;
	private JTextField m_textField_ID;
	
	private JLabel m_label_Password;
	private JTextField m_textField_Password;
	
	private JButton m_button_Process;
	private JButton m_button_SignUp;
	
	// LogIn Elements
	private JPanel m_layout_LogIn;
	
	private JLabel m_label_CourseName;
	private JLabel m_label_Course;
	
	private JLabel m_label_StartDayTitle;
	private JLabel m_label_StartDay;
	
	private JLabel m_label_EndDayTitle;
	private JLabel m_label_EndDay;
	
	// News
	private JPanel m_layout_News;
	
	private JLabel m_label_News;
	
	private JPanel m_panel_NewsContent;
	private JScrollPane m_scrollPane_News;
	
	
	public Base_SidePanel(BaseFrame baseFrame)
	{
		super(baseFrame);
		
		Init();
		
		Link();
	}
	
	@Override
	protected void Init()
	{
		Init_Profile();
		Init_SignIn();
		Init_LogIn();
		Init_News();
	}
	private void Init_Profile()
	{
		m_label_Name = new JLabel("Name");
		m_label_Name.setBounds(100, 10, 100, 30);
		m_label_Name.setFont(new Font("", Font.BOLD, 20));
		m_label_Name.setForeground(Color.WHITE);
		m_label_Name.setHorizontalAlignment(SwingConstants.RIGHT);
		
		m_label_Roll = new JLabel("User");
		m_label_Roll.setBounds(100, 30, 100, 30);
		m_label_Roll.setFont(new Font("", Font.PLAIN, 13));
		m_label_Roll.setForeground(Color.WHITE);
		m_label_Roll.setHorizontalAlignment(SwingConstants.RIGHT);
		
		BufferedImage profileImage = null;
		try
		{
			profileImage = ImageIO.read(new File("./src/imgs/ProfileIcon.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Image dimg = profileImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(dimg);
		
		m_button_ProfileIcon = new JButton(icon);
		m_button_ProfileIcon.setBounds(220, 10, 50, 50);
		m_button_ProfileIcon.setBackground(new Color(44, 47, 50));
		m_button_ProfileIcon.setForeground(Color.WHITE);
		m_button_ProfileIcon.setBorderPainted(false);
		m_button_ProfileIcon.setFocusPainted(false);
		m_button_ProfileIcon.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if(m_baseFrame.GetLoginID() == null)
						{
							return;
						}
						
						JPanel panel = new JPanel();
						JLabel label = new JLabel("Enter New Name :");
						JTextField textField = new JTextField(45);
						
						panel.add(label);
						panel.add(textField);
						
						int selection = JOptionPane.showConfirmDialog(null, panel, "Would you like to change your name?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

						if (selection == JOptionPane.OK_OPTION)
						{
							java.sql.Statement query = null;
							try
							{
								query = m_baseFrame.GetSQLStatement();
								
								String queryContent = String.format("UPDATE `extracurricularmanagement`.`student_infos`"
																	+ " SET `name` = '%s'"
																	+ " WHERE (`student_uid` = '%s');",
																	textField.getText(),
																	m_baseFrame.GetLoginID().toString());
								
								query.executeUpdate(queryContent);
								
								RenderLoginState(textField.getText());
							}
							catch (SQLException ex)
							{
								ex.printStackTrace();
							}
						}
					}
				});
	}
	private void Init_SignIn()
	{
		m_label_SignIn = new JLabel("Sign In");
		m_label_SignIn.setBounds(30, 30, 100, 30);
		m_label_SignIn.setFont(new Font("", Font.BOLD, 20));
		m_label_SignIn.setForeground(Color.WHITE);
		m_label_SignIn.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_ID = new JLabel("ID");
		m_label_ID.setBounds(30, 60, 100, 30);
		m_label_ID.setFont(new Font("", Font.PLAIN, 13));
		m_label_ID.setForeground(Color.WHITE);
		m_label_ID.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_textField_ID = new JTextField(50);
		m_textField_ID.setBounds(30, 90, 230, 25);
		m_textField_ID.setFont(new Font("", Font.PLAIN, 13));
		m_textField_ID.setBackground(new Color(39, 43, 47));
		m_textField_ID.setForeground(Color.WHITE);
		m_textField_ID.setHorizontalAlignment(SwingConstants.LEFT);
		m_textField_ID.setBorder(BorderFactory.createEmptyBorder());
		
		m_label_Password = new JLabel("Password");
		m_label_Password.setBounds(30, 120, 100, 30);
		m_label_Password.setFont(new Font("", Font.PLAIN, 13));
		m_label_Password.setForeground(Color.WHITE);
		m_label_Password.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_textField_Password = new JTextField(50);
		m_textField_Password.setBounds(30, 150, 230, 25);
		m_textField_Password.setFont(new Font("", Font.PLAIN, 13));
		m_textField_Password.setBackground(new Color(39, 43, 47));
		m_textField_Password.setForeground(Color.WHITE);
		m_textField_Password.setHorizontalAlignment(SwingConstants.LEFT);
		m_textField_Password.setBorder(BorderFactory.createEmptyBorder());
		
		m_button_Process = new JButton("Process");
		m_button_Process.setBounds(40, 190, 100, 30);
		m_button_Process.setBackground(new Color(33, 37, 41));
		m_button_Process.setForeground(Color.WHITE);
		m_button_Process.setBorderPainted(false);
		m_button_Process.setFocusPainted(false);
		m_button_Process.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						java.sql.Statement query = null;
						try
						{
							query = m_baseFrame.GetSQLStatement();
							
							ResultSet result = query.executeQuery("select * from student_infos");
							
							while(result.next())
							{
								if(result.getString(3).equals(m_textField_ID.getText()) &&
									result.getString(4).equals(m_textField_Password.getText()))
								{
									m_baseFrame.SetLoginID(UUID.fromString(result.getString(1)));
									Switch2LoginLayout(result.getString(2));
									
									break;
								}
							}
						}
						catch (SQLException ex)
						{
							ex.printStackTrace();
						}
					}
				});
		
		m_button_SignUp = new JButton("Sign Up");
		m_button_SignUp.setBounds(150, 190, 100, 30);
		m_button_SignUp.setBackground(new Color(33, 37, 41));
		m_button_SignUp.setForeground(Color.WHITE);
		m_button_SignUp.setBorderPainted(false);
		m_button_SignUp.setFocusPainted(false);
		m_button_SignUp.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						java.sql.Statement query = null;
						try
						{
							query = m_baseFrame.GetSQLStatement();
							
							String queryContent = String.format("INSERT INTO `extracurricularmanagement`.`student_infos`"
																+ "(`student_uid`, `id`, `password`)"
																+ "VALUES ('%s', '%s', '%s');",
																UUID.randomUUID().toString(), m_textField_ID.getText(), m_textField_Password.getText());
						
							query.executeUpdate(queryContent);
							
							m_textField_ID.setText("");
							m_textField_Password.setText("");
						}
						catch (SQLException ex)
						{
							ex.printStackTrace();
							return;
						}
						
						m_textField_ID.setText("");
						m_textField_Password.setText("");
					}
				});
	}
	private void Init_LogIn()
	{
		m_label_CourseName = new JLabel("Test Builder");
		m_label_CourseName.setBounds(30, 30, 240, 30);
		m_label_CourseName.setFont(new Font("", Font.BOLD, 25));
		m_label_CourseName.setForeground(Color.WHITE);
		m_label_CourseName.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_Course = new JLabel("Course");
		m_label_Course.setBounds(30, 60, 240, 30);
		m_label_Course.setFont(new Font("", Font.BOLD, 13));
		m_label_Course.setForeground(Color.GRAY);
		m_label_Course.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_label_StartDayTitle = new JLabel("Start Day");
		m_label_StartDayTitle.setBounds(50, 110, 70, 20);
		m_label_StartDayTitle.setFont(new Font("", Font.PLAIN, 10));
		m_label_StartDayTitle.setForeground(Color.WHITE);
		
		m_label_StartDay = new JLabel("00 / 00");
		m_label_StartDay.setBounds(45, 140, 70, 20);
		m_label_StartDay.setFont(new Font("", Font.BOLD, 17));
		m_label_StartDay.setForeground(Color.WHITE);
		
		m_label_EndDayTitle = new JLabel("End Day");
		m_label_EndDayTitle.setBounds(190, 110, 70, 20);
		m_label_EndDayTitle.setFont(new Font("", Font.PLAIN, 10));
		m_label_EndDayTitle.setForeground(Color.WHITE);
		
		m_label_EndDay = new JLabel("00 / 00");
		m_label_EndDay.setBounds(185, 140, 70, 20);
		m_label_EndDay.setFont(new Font("", Font.BOLD, 17));
		m_label_EndDay.setForeground(Color.WHITE);
	}
	private void Init_News()
	{
		m_label_News = new JLabel("News");
		m_label_News.setBounds(30, 300, 240, 30);
		m_label_News.setFont(new Font("", Font.BOLD, 25));
		m_label_News.setForeground(Color.WHITE);
		m_label_News.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_panel_NewsContent = new JPanel();
		m_panel_NewsContent.setLayout(new BoxLayout(m_panel_NewsContent, BoxLayout.Y_AXIS));
		m_panel_NewsContent.setBackground(new Color(33, 37, 41));
		
		m_scrollPane_News = new JScrollPane(m_panel_NewsContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_scrollPane_News.setBounds(0, 0, 230, 300);
		m_scrollPane_News.setBorder(BorderFactory.createEmptyBorder());
	}
	
	@Override
	protected void Link()
	{
		// Profile
		m_layout_Main = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(new Color(60, 60, 60));
				g.drawLine(20, 70, 270, 70);
			}
		};
		m_layout_Main.setLayout(null);
		m_layout_Main.setBounds(800, 0, 300, 700);
		m_layout_Main.setBackground(new Color(44, 47, 50));

		m_layout_Main.add(m_label_Name);
		m_layout_Main.add(m_label_Roll);
		m_layout_Main.add(m_button_ProfileIcon);
		
		// SignIn
		m_layout_SignIn = new JPanel();
		m_layout_SignIn.setLayout(null);
		m_layout_SignIn.setBounds(0, 70, 300, 250);
		m_layout_SignIn.setBackground(new Color(44, 47, 50));
		
		m_layout_SignIn.add(m_label_SignIn);
		m_layout_SignIn.add(m_label_ID);
		m_layout_SignIn.add(m_textField_ID);
		m_layout_SignIn.add(m_label_Password);
		m_layout_SignIn.add(m_textField_Password);
		m_layout_SignIn.add(m_button_Process);
		m_layout_SignIn.add(m_button_SignUp);
		
		m_layout_Main.add(m_layout_SignIn);
		
		// LogIn
		m_layout_LogIn = new JPanel();
		m_layout_LogIn.setLayout(null);
		m_layout_LogIn.setBounds(0, 70, 300, 200);
		m_layout_LogIn.setBackground(new Color(44, 47, 50));
		
		m_layout_LogIn.add(m_label_CourseName);
		m_layout_LogIn.add(m_label_Course);
		m_layout_LogIn.add(m_label_StartDayTitle);
		m_layout_LogIn.add(m_label_StartDay);
		m_layout_LogIn.add(m_label_EndDayTitle);
		m_layout_LogIn.add(m_label_EndDay);
		
		m_layout_Main.add(m_layout_LogIn);
		m_layout_LogIn.setVisible(false);
		
		// News
		m_layout_News = new JPanel();
		m_layout_News.setLayout(null);
		m_layout_News.setBounds(30, 340, 230, 300);
		m_layout_News.setBackground(new Color(33, 37, 41));
		
		m_layout_News.add(m_scrollPane_News);
		
		m_layout_Main.add(m_label_News);
		m_label_News.setVisible(false);
		
		//for(int count = 0; count < 7; count++)
		//{
		//	m_panel_NewsContent.add(new NewsItem().GetMainLayout());
		//}
		
		m_layout_Main.add(m_layout_News);
	}
	
	private void Switch2LoginLayout(String name)
	{
		m_layout_SignIn.setVisible(false);
		
		m_layout_LogIn.setVisible(true);
		m_label_News.setVisible(true);
		
		RenderLoginState(name);
		RenderNewsState();
	}
	private void RenderLoginState(String name)
	{
		m_label_Name.setText(name);
		if(name.equals("admin"))
		{
			m_label_Roll.setText("Teacher");
		}
		else
		{
			m_label_Roll.setText("Student");
		}
	}
	private void RenderNewsState()
	{
		java.sql.Statement query = null;
		try
		{
			query = m_baseFrame.GetSQLStatement();
			
			String queryContent = String.format("SELECT * FROM extracurricularmanagement.recommended_resources"
												+ " WHERE student_uid = \"%s\";",
												m_baseFrame.GetLoginID().toString());
			
			ResultSet result = query.executeQuery(queryContent);

			var newsItemComps = m_panel_NewsContent.getComponents();
			for(var item : newsItemComps)
			{
				m_panel_NewsContent.remove(item);
			}
			
			while(result.next())
			{
				NewsItem newsItem = new NewsItem();
				newsItem.SetNewsTitle(result.getString(4));
				newsItem.SetUrl(result.getString(3));
				
				m_panel_NewsContent.add(newsItem.GetMainLayout());
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}