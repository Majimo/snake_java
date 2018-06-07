package fr.majimo.ihm;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.majimo.snake.Snake;

public class Window extends JFrame implements KeyListener{

	private static final long serialVersionUID = 1L;
	
	JPanel container = new JPanel();
	public static int lengthWindow = 300;
	public static int heigthWindow = 300;
	Snake snake;
	
	JLabel gameOver = new JLabel();
	
	public Window() {
		this.setTitle("Snake");
		this.setSize(lengthWindow, heigthWindow);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		container.setBackground(Color.BLACK);
		
		gameOver.setBounds((Window.lengthWindow / 2) - 60, 0, 200, 20);
		
		this.addKeyListener(this);
		
		this.setContentPane(container);
		this.setVisible(true);
		
		snake = new Snake(4, this.getGraphics());
		
	}
	
	public void startGame() {
		snake.play();
		gameOver();
	}
	
	public void gameOver() {
		gameOver.setForeground(Color.WHITE);
		gameOver.setText("Perdu, tu es mauvais !");
		container.add(gameOver);
		container.repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_UP && snake.direction != 3) {
			snake.direction = 1;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN && snake.direction != 1) {
			snake.direction = 3;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT && snake.direction != 4) {
			snake.direction = 2;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT && snake.direction != 2) {
			snake.direction = 4;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
