package com.Bevan.frontend;

import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.bullets.Bullet;

public class Test {
    public static void main(String[] args) {
        Player reimu = new Player("Reimu Hakurei", 100, 15, 3);
        Bullet bullet = reimu.shootBullet();

        System.out.println("Bullet created at: ("
            + bullet.getX() + ", " + bullet.getY()
            + ") | Damage: " + bullet.getDamage());

        bullet.update(0.1f);
        System.out.println("Bullet Y after 0.1s: " + bullet.getY());
        System.out.println("Is bullet off screen? "
            + bullet.isOffScreen(640, 480));
    }
}
