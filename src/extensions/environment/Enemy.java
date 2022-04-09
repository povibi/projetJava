package extensions.environment;

import extensions.environment.entities.Entity;
import extensions.environment.ui.Sprite;

import java.awt.*;
import java.awt.geom.Point2D;

public class Enemy extends Entity {
    private final double GRAVITY = 1000;
    private GameModel gameModel;
    private Point2D.Double velocity = new Point2D.Double(0,0);

    public Enemy(Point2D.Double loc,GameModel gameModel) {
        super(loc, new Sprite("assets/Sprites/ptitbonhome_inverted.png"));
        this.gameModel = gameModel;
    }

    @Override
    public void applyPhysics(TileMap tileMap, double dt) {
        Point2D.Double doubleLoc = this.getDoubleLoc();
        if (!onGround(tileMap, doubleLoc))
            velocity.setLocation(velocity.x, velocity.y+GRAVITY*dt);
        Point2D.Double nextLoc = nextLoc(dt);
        if (velocity.y > 0) {
            if (!onGround(tileMap,nextLoc)) {
                this.setDoubleLoc(nextLoc);
            }
            else {
                this.velocity.y = 0;
                this.setDoubleLoc(new Point2D.Double(doubleLoc.x,Math.ceil(doubleLoc.y)));
                doubleLoc = this.getDoubleLoc();
                while (!onGround(tileMap,doubleLoc))
                    this.DoubleTranslate(0.0, 1.0);
            }
        }
        else if (velocity.y < 0) {
            if (!onRoof(tileMap,nextLoc)) {
                this.setDoubleLoc(nextLoc);
            }
            else {
                this.velocity.y = 0;
                this.setDoubleLoc(new Point2D.Double(doubleLoc.x,Math.ceil(doubleLoc.y)));
                doubleLoc = this.getDoubleLoc();
                while (!onRoof(tileMap,doubleLoc))
                    this.DoubleTranslate(0.0, -1.0);
            }
        }
    }

    public Point2D.Double nextLoc(double dt) {
        Point2D.Double doubleLoc = getDoubleLoc();
        return new Point2D.Double(doubleLoc.x + this.velocity.x * dt, doubleLoc.y + this.velocity.y * dt);
    }

    public Boolean onGround(TileMap tileMap, Point2D.Double doubleLoc)
    {
        try {
            return tileMap.getTextureMap()[(int) ((doubleLoc.y+this.getSprite().getHeight())/tileMap.TILEWIDTH)][(int) (doubleLoc.x/tileMap.TILEWIDTH)] >= 0;
        } catch (Exception e) {
            this.die();
            return true;
        }
    }

    public Boolean onRoof(TileMap tileMap, Point2D.Double doubleLoc)
    {
        try {
            return tileMap.getTextureMap()[(int) ((doubleLoc.y+this.getSprite().getHeight())/tileMap.TILEWIDTH)-1][(int) (doubleLoc.x/tileMap.TILEWIDTH)] >= 0;
        } catch (Exception e) {
            this.die();
            return true;
        }
    }

    @Override
    public void die() {
        //System.out.println(this.toString()+" est mort!");
        //System.exit(2);

    }
}