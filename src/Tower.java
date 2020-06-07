import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class Tower extends Sprite{
    private final double radius;
    private final int damage;
    private final List<Projectile> projectiles;
    private final String name;
    private boolean hasFinished = false;

    public boolean getHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public Tower(Point p, String imageFile, double radius, int damage, String name) {
        super(p,imageFile);
        this.radius = radius;
        this.damage = damage;
        this.projectiles = new ArrayList<>();
        this.name = name;
    }
    public abstract void attack(Input input);
    public double getRadius() {
        return radius;
    }

    public int getDamage() {
        return damage;
    }

    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public String getName() {
        return name;
    }
    public void render(Input input){
        super.update(input);
    }

    public void updateProjectiles(Input input){
        for (int i=projectiles.size()-1;i>=0;i--){
            Projectile p = projectiles.get(i);
            p.render(input);
            if (p.getHasFinished()){
                projectiles.remove(i);
            }
        }
    }

}
