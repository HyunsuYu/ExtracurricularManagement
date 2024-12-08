import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.*;


//<<<<<<< Updated upstream
//public class Feature_Overview extends MajorLayoutBase 
//{
//	public Feature_Overview(BaseFrame baseFrame)
//	{
//		super(baseFrame);
//	}
//	
//	@Override
//	public void Switch2LoginLayout()
//	{
//		m_baseFrame.GetLoginID().toString();
//	}
//=======
//>>>>>>> Stashed changes

public class Feature_Overview extends MajorLayoutBase {

    public Feature_Overview(BaseFrame baseFrame) {
        super(baseFrame);
    }

    @Override
    public void Switch2LoginLayout() {
        if (m_baseFrame.GetLoginID() != null) {
            RefreshLayout();
        }
    }

    @Override
    protected void Init() {
        m_layout_Main = CreateMainPanel();
        RenderLayout();
    }

    @Override
    protected void Link() {}

    private JPanel CreateMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(30, 240, 740, 400);
        panel.setBackground(new Color(33, 37, 41));
        return panel;
    }

    private void RenderLayout() {
        if (m_layout_Main == null) {
            m_layout_Main = CreateMainPanel();
        } else {
            m_layout_Main.removeAll();
        }

        boolean isLoggedIn = m_baseFrame.GetLoginID() != null;

        AddBookRecommendationBox(0, isLoggedIn);
        AddLinkBox("./src/imgs/Microsoft.png", "Microsoft Document", "https://learn.microsoft.com/ko-kr/docs/",
                "./src/imgs/Stack_Overflowicon.png", "Stack Overflow", "https://stackoverflow.com/", 0, isLoggedIn);
        AddLinkBox("./src/imgs/githubicon.png", "Github", "https://github.com/",
                "./src/imgs/googleicon.png", "Google", "https://www.google.co.uk/", 140, isLoggedIn);
        AddLinkBox("./src/imgs/W3schoolsicon.png", "W3Schools", "https://www.w3schools.com/",
                "./src/imgs/RedditProgrammingicon.png", "Reddit Programming", "https://www.reddit.com/r/programming/", 280, isLoggedIn);

        m_layout_Main.revalidate();
        m_layout_Main.repaint();
    }

    public void RefreshLayout() {
        RenderLayout();
    }

    private void AddBookRecommendationBox(int yPosition, boolean isLoggedIn) {
        JPanel m_bookBox = new JPanel();
        m_bookBox.setLayout(null);
        m_bookBox.setBounds(0, yPosition, 460, 400);
        m_bookBox.setBackground(new Color(44, 47, 50));
        m_bookBox.setOpaque(true);
        
        JLabel m_titleLabel = CreateLabel("Recommended Books", 20, 5, 200, 20, SwingConstants.LEFT, Color.WHITE);
        m_titleLabel.setFont(new Font("", Font.BOLD, 13)); 
        m_bookBox.add(m_titleLabel);

        if (isLoggedIn) {
            AddBookCard(m_bookBox, "./src/imgs/book1.png", "Head First Java", "Kathy Sierra & Bert Bates",
                    "https://search.shopping.naver.com/book/catalog/32465535089", 20, 30);
            AddBookCard(m_bookBox, "./src/imgs/book2.png", "Effective Java 3/E", "Joshua Bloch",
                    "https://search.shopping.naver.com/book/catalog/32436239326", 240, 30);
            AddBookCard(m_bookBox, "./src/imgs/book3.png", "Clean Code", "Robert C. Martin",
                    "https://search.shopping.naver.com/book/catalog/32474195676", 20, 215);
            AddBookCard(m_bookBox, "./src/imgs/book4.png", "Optimizing Java", "Benjamin J.Evans",
                    "https://search.shopping.naver.com/book/catalog/32436011847", 240, 215);
        }

        m_layout_Main.add(m_bookBox);
    }

    private void AddBookCard(JPanel parent, String imgPath, String title, String author, String link, int x, int y) {
        JPanel m_bookCard = new JPanel();
        m_bookCard.setLayout(null);
        m_bookCard.setBounds(x, y, 200, 175);
        m_bookCard.setBackground(new Color(60, 63, 65));
        m_bookCard.setOpaque(true);

        JButton m_bookButton = CreateBookButton(imgPath, link);
        m_bookCard.add(m_bookButton);

        JLabel m_bookTitle = CreateLabel(title, 25, 130, 150, 20, SwingConstants.CENTER, Color.LIGHT_GRAY);
        m_bookCard.add(m_bookTitle);

        JLabel m_bookAuthor = CreateLabel(author, 25, 155, 150, 15, SwingConstants.CENTER, Color.GRAY);
        m_bookCard.add(m_bookAuthor);

        parent.add(m_bookCard);
    }

    private JButton CreateBookButton(String imgPath, String link) {
        JButton m_bookButton = new JButton(new ImageIcon(LoadImage(imgPath, 90, 110)));
        m_bookButton.setBounds(50, 10, 100, 120);
        m_bookButton.setBackground(new Color(60, 63, 65));
        m_bookButton.setBorderPainted(false);
        m_bookButton.setFocusPainted(false);
        m_bookButton.setContentAreaFilled(false);
        m_bookButton.addActionListener(e -> OpenLink(link));
        return m_bookButton;
    }

    private JLabel CreateLabel(String text, int x, int y, int width, int height, int alignment, Color color) {
        JLabel m_label = new JLabel(text);
        m_label.setBounds(x, y, width, height);
        m_label.setHorizontalAlignment(alignment);
        m_label.setForeground(color);
        return m_label;
    }

    private void AddLinkBox(String iconPath1, String name1, String url1, String iconPath2, String name2, String url2, int yPosition, boolean isLoggedIn) {
        JPanel m_linkBox = new JPanel();
        m_linkBox.setLayout(null);
        m_linkBox.setBounds(470, yPosition, 270, 120);
        m_linkBox.setBackground(new Color(44, 47, 50));
        m_linkBox.setOpaque(true);

        AddIconAndLabel(m_linkBox, iconPath1, name1, 10, url1, isLoggedIn);

        JSeparator m_separator = new JSeparator();
        m_separator.setBounds(30, 60, 220, 1);
        m_separator.setForeground(Color.DARK_GRAY);
        m_linkBox.add(m_separator);

        AddIconAndLabel(m_linkBox, iconPath2, name2, 70, url2, isLoggedIn);

        m_layout_Main.add(m_linkBox);
    }

    private void AddIconAndLabel(JPanel panel, String iconPath, String name, int yPosition, String url, boolean isLoggedIn) {
        JLabel m_iconLabel = new JLabel(new ImageIcon(LoadImage(iconPath, 40, 40)));
        m_iconLabel.setBounds(20, yPosition, 40, 40);
        panel.add(m_iconLabel);

        JLabel m_nameLabel = CreateLabel(name, 70, yPosition, 150, 40, SwingConstants.LEFT, Color.LIGHT_GRAY);
        panel.add(m_nameLabel);

        JButton m_linkButton = CreateLinkButton(url, isLoggedIn);
        m_linkButton.setBounds(225, yPosition + 10, 25, 25);
        panel.add(m_linkButton);
    }

    private JButton CreateLinkButton(String url, boolean isLoggedIn) {
        JButton m_linkButton = new JButton(new ImageIcon(LoadImage("./src/imgs/icon.png", 20, 20)));
        m_linkButton.setBackground(new Color(44, 47, 50));
        m_linkButton.setBorderPainted(false);
        m_linkButton.setFocusPainted(false);

        if (isLoggedIn) {
            m_linkButton.addActionListener(e -> OpenLink(url));
        } else {
            m_linkButton.setEnabled(false);
        }
        return m_linkButton;
    }

    private Image LoadImage(String path, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void OpenLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}


