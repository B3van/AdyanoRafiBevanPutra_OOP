package com.Bevan.frontend.objects.patterns;

import com.Bevan.frontend.systems.BulletManager;

public interface ShootingPattern {

    void execute(float originX, float originY,
                 BulletManager bulletManager,
                 boolean isPlayer);
}
