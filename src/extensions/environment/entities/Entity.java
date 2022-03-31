package extensions.environment.entities;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import extensions.environment.EnvironmentVisitor;
import graphics.shapes.Shape;
import graphics.shapes.ShapeVisitor;

public class Entity extends Shape {
	
	//Quelques attributs et fonctions possiblement nécessaires
	
	/*private final float GRAVITY; 
	
	private Boolean onGround;
	private Boolean onRoof;
	private Point2D.Float velocity;
	public abstract void move();*/
	
	private BufferedImage sprite;
	private Point loc;
	
	public Entity(Point loc) {
		try {
		    this.sprite = ImageIO.read(new File("assets/Sprites/ptitbonhome.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.loc = loc;
	}
	
	@Override
	public Point getLoc() {
		return this.loc;
	}

	@Override
	public void setLoc(Point point) {
		this.loc = point;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.getLoc().x,this.getLoc().y,sprite.getWidth(),sprite.getHeight());
	}
	
	public void accept(ShapeVisitor visitor) {
		((EnvironmentVisitor) visitor).visitEntity(this);
	}

	public BufferedImage getSprite() {
		return sprite;
	}
}