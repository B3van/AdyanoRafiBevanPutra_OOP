package com.Bevan.frontend.objects.enemies;

import com.Bevan.frontend.objects.Collidable;
import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.patterns.ShootingPattern;
import com.Bevan.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.Bevan.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy {

    private ShootingPattern shootingPattern =
        new SpreadShot(200f, 3, 30f);

    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) {
            return;
        }

        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(
                getX() + getWidth() / 2f - 8f,
                getY(),
                bulletManager,
                false
            );
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            System.out.println("Player touches boss");
        }
    }

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }
}
