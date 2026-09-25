package com.Bevan.frontend.objects;

import com.Bevan.frontend.objects.bullets.Bullet;
import com.Bevan.frontend.objects.enemies.Enemy;
import com.Bevan.frontend.objects.items.Item;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject implements Collidable {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        this(280, 40, name, hp, power, spellCards);
    }

    public Player(float x, float y, String name, int hp,
                  int power, int spellCards) {
        super(x, y, 32, 32, 0f, Color.RED);
        this.name = name;
        this.hp = Math.max(0, hp);
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    @Override
    public void update(float delta) {
        // Player movement can be added later.
    }

    public Bullet shootBullet() {
        int damage = 10 + power;
        System.out.println(name + " shoots bullet dealing "
            + damage + " DMG!");

        return new Bullet(
            getX() + getWidth() / 2f - 4,
            getY() + getHeight(),
            BulletType.AMULET,
            damage
        );
    }

    // Keep the older shooting method from Modules 1–3.
    public void shoot(Enemy target) {
        if (target != null && target.isAlive()) {
            int damage = 10 + power;
            System.out.println(name + " shoots " + target.getName()
                + " for " + damage + " damage!");

            if (target.takeDamage(damage)) {
                addScore(target.getScoreValue());
            }
        }
    }

    public void takeDamage(int damage) {
        setHp(hp - damage);
        System.out.println(name + " took " + damage
            + " damage! HP: " + hp);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void collectItem(Item item) {
        if (item != null && item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            score += points;
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
        // Collision behavior can be added later.
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }

    public long getScore() {
        return score;
    }
}
