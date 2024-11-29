import javax.swing.JPanel;


public abstract class MajorLayoutBase
{
	protected BaseFrame m_baseFrame;
	
	protected JPanel m_layout_Main;
	
	
	public MajorLayoutBase(BaseFrame baseFrame)
	{
		m_baseFrame = baseFrame;
	}
	
	public JPanel GetMainLayout()
	{
		return m_layout_Main;
	}
	
	protected abstract void Init();
	protected abstract void Link();
}