package eventDrivenSimulation;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class MyParticle extends Particle {

    boolean visitedCenter;

    public MyParticle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
        super(rx, ry, vx, vy, radius, mass, color);
    }

    public MyParticle() {
        super();
    }

    public void draw() {

//        if(color.getRed() != 255 && color.getGreen() != 255 && color.getBlue() != 255) {
        if(color != Color.WHITE){

            if (!visitedCenter) {
                double d = Math.pow(Math.abs(0.5 - rx), 2) + Math.pow(Math.abs(0.5 - ry), 2);
                d = Math.sqrt(d);
                d = Math.min(d, 0.5);
                int alpha = (int) ((255 * d) / 0.5);
                alpha = Math.abs(alpha - 255);
//                alpha = 255;
                Color myColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                StdDraw.setPenColor(myColor);

                if (alpha == 255) {
                    visitedCenter = true;
                }

            } else {
                StdDraw.setPenColor(color);
            }

            StdDraw.filledCircle(rx, ry, radius);
        }
    }

    @Override
    public void bounceOff(Particle that) {
        super.bounceOff(that);

        if(that.color == Color.BLACK || this.color == Color.BLACK){
            if(this.color == Color.RED){
                that.color = Color.pink;
            }else if (that.color == Color.RED){
                this.color = Color.pink;
            }

            if(this.color == Color.GREEN){
                this.mass += that.mass;
                this.vx *= 1.05;
                this.vy *= 1.05;
                that.color = Color.yellow;
            }else if(that.color == Color.GREEN){
                this.color = Color.yellow;
                that.mass += this.mass;
                that.vx *= 1.05;
                that.vy *= 1.05;

            }
        }

        if((this.color == Color.pink && that.color == Color.pink) ||
                (this.color == Color.yellow && that.color == Color.yellow)) {

            double newRadius = Math.pow(this.radius, 2) + Math.pow(that.radius, 2);
            newRadius = Math.sqrt(newRadius);
            if(this.mass > that.mass){
                this.mass += that.mass;
                this.radius = newRadius;
                this.vx *= 1.01;
                this.vy *= 1.01;
                that.color = Color.WHITE;
            }else {
                that.mass += this.mass;
                that.radius = newRadius;
                that.vx *= 1.01;
                that.vy *= 1.01;
                this.color = Color.WHITE;
            }
        }
    }
}
