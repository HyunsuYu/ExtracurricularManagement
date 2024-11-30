import javax.swing.JPanel;


public abstract class MajorLayoutBase
{
	protected BaseFrame m_baseFrame;
	
	protected JPanel m_layout_Main;
	
	
	public MajorLayoutBase(BaseFrame baseFrame)
	{
		m_baseFrame = baseFrame;
		
		Init();
		Link();
	}
	
	public JPanel GetMainLayout()
	{
		return m_layout_Main;
	}
	
	public abstract void Switch2LoginLayout();
	
	protected abstract void Init();
	protected abstract void Link();
	
}