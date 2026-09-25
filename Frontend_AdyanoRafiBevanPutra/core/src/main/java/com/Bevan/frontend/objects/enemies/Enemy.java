package com.Bevan.frontend.objects.enemies;

import com.Bevan.frontend.objects.Collidable;
import com.Bevan.frontend.objects.GameObject;
import com.Bevan.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends GameObject implements Collidable {
    private String name;
    private int hp;
    private int maxHp;
    private long scoreValue;

    public Enemy(String name, int hp) {
        this(0, 0, 24, 24, Color.PINK, name, hp, 100L);
    }

    public Enemy(float x, float y, int width, int height, Color color,
                 String name, int hp, long scoreValue) {
        super(x, y, width, height, 0f, color);
        this.name = name;
        this.hp = Math.max(0, hp);
        this.maxHp = this.hp;
        this.scoreValue = scoreValue;
    }

    @Override
    public void update(float delta) {
        // Enemy movement can be added later.
    }

    public boolean takeDamage(int damage) {
        boolean wasAlive = isAlive();
        this.hp -= damage;

        if (this.hp < 0) {
            this.hp = 0;
        }

        System.out.println(name + " took " + damage
            + " damage! HP: " + this.hp + "/" + this.maxHp);

        if (wasAlive && this.hp == 0) {
            System.out.println(name + " was defeated!");
            destroy();
            return true;
        }

        return false;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void attack(Player player, int damage) {
        if (isAlive() && player != null) {
            System.out.println(name + " attacks " + player.getName()
                + " for " + damage + " damage!");
            player.takeDamage(damage);
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return null;
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return null;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            System.out.println("Player touches enemy!");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public long getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(long scoreValue) {
        this.scoreValue = scoreValue;
    }
}
