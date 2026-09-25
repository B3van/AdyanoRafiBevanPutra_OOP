package com.Bevan.frontend.objects.bullets;

import com.Bevan.frontend.objects.BulletType;
import com.Bevan.frontend.objects.Collidable;
import com.Bevan.frontend.objects.GameObject;
import com.Bevan.frontend.objects.enemies.Enemy;
import com.badlogic.gdx.graphics.Color;

public class Bullet extends GameObject implements Collidable {
    private BulletType bulletType;
    private int damage;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 8, 16, 400f, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
    }

    public Bullet(float x, float y, float speed,
                  BulletType bulletType, int damage) {
        super(x, y, 8, 16, speed, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        setY(getY() + getSpeed() * delta);
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            System.out.println(
                "Bullet hit " + enemy.getName()
                    + " for " + damage + " DMG!"
            );

            enemy.takeDamage(damage);
            destroy();
        }
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getDamage() {
        return damage;
    }
}
