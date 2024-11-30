import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Feature_Overview extends MajorLayoutBase 
{
	public Feature_Overview(BaseFrame baseFrame)
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
		
	}

	@Override
	protected void Link()
	{
		m_layout_Main = new JPanel();
		m_layout_Main.setLayout(null);
		m_layout_Main.setBounds(30, 240, 740, 400);
		m_layout_Main.setBackground(new Color(33, 37, 41));
		
		// 아래 부분은 예시이므로 실제 구현 시 삭제하고 작업할 것
		JLabel temp = new JLabel("Overview");
		temp.setBounds(30, 60, 100, 30);
		temp.setFont(new Font("", Font.PLAIN, 13));
		temp.setForeground(Color.WHITE);
		temp.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_layout_Main.add(temp);
	}
}