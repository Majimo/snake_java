package fr.majimo.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

import fr.majimo.ihm.Window;

public class Snake extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public int length;
	Graphics g;
	public boolean play = true;
	public int nbApples = 3;
	Scanner sc;
	ArrayList<Ring> body = new ArrayList<Ring>();
	ArrayList<Apple> apples = new ArrayList<Apple>();
	public int direction = 4;
	
	public Snake(int length, Graphics g) {
		this.length = length;
		this.g = g;
		sc = new Scanner(System.in);
	}
	
	public void play() {
		createSnake();
		
		while(play == true) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Window.lengthWindow, Window.heigthWindow);
			
			createApples();
			drawApples();
			drawSnake();
			showScore();
			
			sleep(100);
			
			moveSnake();
			checkCollision();
		}
	}
	
	public void sleep(int time) {
		try {
			Thread.sleep(time);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void createApples() {
		int randX;
		int randY;
		boolean create;
		
		while(apples.size() < nbApples) {
			create = true;
			int heigthWin = ((Window.heigthWindow - 20) / 10) - 2;
			int lenghtWin = ((Window.lengthWindow - 20) / 10) - 2;
			
			randX = (int) ((Math.random() * lenghtWin) + 3);
			randY = (int) ((Math.random() * heigthWin) + 3);
			
			randX = randX * 10;
			randY = randY * 10;
			
			// Vérification si on est pas sur le serpent
			for (int i = 0; i < body.size(); i++) {
				Ring isTrue = body.get(i);
				
				if (randX == isTrue.posX && randY == isTrue.posY) {
					create = false;
				}
			}
			
			if (create == true) {
				apples.add(new Apple(randX, randY, Color.RED));
			}
		}
	}
	
	public void createSnake() {
		for (int i = 0; i < this.length; i++) {
			int heightSnake;
			heightSnake = ((int) Window.heigthWindow / 2) / 10;
			heightSnake = heightSnake * 10;
			
			if (i == 0) {
				body.add(new Ring((Window.lengthWindow / 2 + (i * 10)), heightSnake, Color.BLUE));
			}
			else {
				body.add(new Ring((Window.lengthWindow / 2 + (i * 10)), heightSnake, Color.GREEN));
			}
		}
	}
	
	public void drawApples() {
		for (int i = 0; i < apples.size(); i++) {
			Apple a = apples.get(i);
			
			g.setColor(a.color);
			g.fillOval(a.posX, a.posY, 10, 10);
		}
	}
	
	public void drawSnake() {
		for (int i = 0; i < body.size(); i++) {
			Ring r = body.get(i);
			
			g.setColor(r.color);
			g.fillOval(r.posX, r.posY, 10, 10);
		}
	}
	
	public void moveSnake() {
		int snakeX;
		int snakeY;
		
		for (int i = (body.size() - 1); i > 0; i--) {
			Ring r;
			r = body.get(i - 1);
			snakeX = r.posX;
			snakeY = r.posY;
			r = body.get(i);
			r.posX = snakeX;
			r.posY = snakeY;
		}
		Ring r1 = body.get(0);
		
		if(direction == 1) {
			r1.posY -= 10;
		}
		if(direction == 2) {
			r1.posX += 10;
		}
		if(direction == 3) {
			r1.posY += 10;
		}
		if(direction == 4) {
			r1.posX -= 10;
		}
	}
	
	public void checkCollision() {
		
		// Collecte d'une pomme
		for (int i = 0; i < apples.size(); i++) {
			Apple checkApple = apples.get(i);
			Ring checkSnake = body.get(0);
			Ring lastPosition = body.get(body.size() - 1);
			
			if (checkApple.posX == checkSnake.posX && checkApple.posY == checkSnake.posY) {
				apples.remove(i);
				body.add(new Ring(200 + ((lastPosition.posX) + 10), 0, Color.GREEN));
			}
		}
		
		// Collision du serpent sur lui-même

		for (int i = 1; i < body.size(); i++) {
			Ring bodySnake = body.get(i);
			Ring headSnake = body.get(0);
					
			if (bodySnake.posX == headSnake.posX && bodySnake.posY == headSnake.posY) {
				play = false;
			}
		}
		
		// Collision du serpent avec la fenêtre
		Ring headSnake = body.get(0);
		
		if (headSnake.posX < 10) {
			play = false;
		}
		if (headSnake.posX > (Window.lengthWindow - 20)) {
			play = false;
		}
		if (headSnake.posY < 30) {
			play = false;
		}
		if (headSnake.posY > (Window.heigthWindow - 20)) {
			play = false;
		}
	}
	
	public void showScore() {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.drawString(Integer.toString(body.size() - 4), 10, Window.heigthWindow - 10);
	}
	
}
