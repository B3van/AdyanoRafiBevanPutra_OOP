package com.Bevan.frontend.systems;

import com.Bevan.frontend.objects.GameObject;
import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.bullets.Bullet;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(
        Player player,
        List<GameObject> entities,
        BulletManager bulletManager
    ) {
        if (player == null
            || entities == null
            || bulletManager == null) {
            return;
        }

        List<GameObject> newDrops = new ArrayList<>();
        for (Bullet b : bulletManager.getActiveEnemyBullets()) {
            if (b.isDestroyed() || player.isDestroyed()) continue;

            if (b.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                if (!player.isInvulnerable()) {
                    player.takeDamage(100, bulletManager, entities);
                    b.destroy();
                    break;
                }
            } else if (!b.hasBeenGrazed() &&
                b.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                b.setGrazed(true);
                player.addGraze();
            }
        }

        entities.addAll(newDrops);
    }
}
