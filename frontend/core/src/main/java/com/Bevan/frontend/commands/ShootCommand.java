package com.Bevan.frontend.commands;

import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.systems.BulletManager;

public class ShootCommand implements Command {

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.shootBullet(bulletManager);
        }
    }
}
