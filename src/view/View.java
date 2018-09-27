package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Função principal VIEW, seguindo o padrão MVC
 */
public final class View {

    private final GamePanel gamePanel;
    private final GameHeaderPanel gameHeaderPanel;
    private final ViewListener viewListener = new ViewListener();
    private final GameOverPanel gameOverPanel;
    private final NewGamePanel newGamePanel;
    private final DifficultyPanel difficultyPanel;
    private JFrame frame;
    private JPanel content;
    private final int scale;
    private final List<Image> icons = new ArrayList<>();

    public View(int width, int height, int scale, Deque<Point> snakeBody, Point apple) //Contrutor da Clase
    {
        gamePanel = new GamePanel(width, height, scale, snakeBody, apple);
        newGamePanel = new NewGamePanel(width, height, scale);
        gameOverPanel = new GameOverPanel(width, height, scale);
        difficultyPanel = new DifficultyPanel(width, height, scale);
        gameHeaderPanel = new GameHeaderPanel(width, height, scale);
        this.scale = scale;
        initIcons();
        initGridView();
    }

    /**
     * Inicializa o GUI.
     */
    private void initGridView() {
        frame = new JFrame("Snake");
        frame.addKeyListener(viewListener);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        content.setBackground(Color.black);
        content.add(gameHeaderPanel);
        content.add(gamePanel);
        frame.add(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        newGame();
        frame.setIconImages(icons);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateView(Deque<Point> snakeBody, Point apple, String difficulty, int highScore, int applesEaten) {
        gamePanel.setSnakeBody(snakeBody, apple);
        gameHeaderPanel.update(difficulty, highScore, applesEaten);
        gameHeaderPanel.repaint();
        gamePanel.repaint();
    }

    public void gameOver() {
        System.out.println("GAME OVER");
        viewListener.setGameOver(true);
        content.removeAll();
        content.add(gameOverPanel);
        content.validate();
        content.repaint();
    }

    public void newGame() {
        System.out.println("NEW GAME");
        viewListener.setNewGame(true);
        content.removeAll();
        content.add(newGamePanel);
        content.validate();
        content.repaint();
    }

    public void chooseDifficulty() 
    {
        System.out.println("SELEÇÃO DE FICICULDADE, Classe View");
        viewListener.setChoosingDifficulty(true);
        content.removeAll();
        content.add(difficultyPanel);
        content.validate();
        content.repaint();
    }
    
    public void continueGame() {
        System.out.println("CONTINUE GAME");
        viewListener.setGameOver(false);
        viewListener.setNewGame(false);
        viewListener.setChoosingDifficulty(false);
        content.removeAll();
        content.add(gameHeaderPanel);
        content.add(gamePanel);
        content.validate();
        content.repaint();
    }

    public void update(String difficulty, int applesEaten, int highScore) {
        gameOverPanel.update(difficulty, applesEaten, highScore);
    }

    private void initIcons() //Icone que aparece na barra de tarefas 
    {
        try {
            URL url = this.getClass().getClassLoader().getResource("icon/Cobrinha.png");
            icons.add((new ImageIcon(url)).getImage());
        } catch (Exception e) {
        }
    }

}
