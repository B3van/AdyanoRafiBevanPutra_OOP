package com.Bevan.frontend.commands;

import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}
