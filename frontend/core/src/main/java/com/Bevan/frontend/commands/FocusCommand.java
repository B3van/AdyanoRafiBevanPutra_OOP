package com.Bevan.frontend.commands;

import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.systems.BulletManager;

public class FocusCommand implements Command {

    private boolean focused;

    public FocusCommand(boolean focused) {
        this.focused = focused;
    }

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.setFocused(focused);
        }
    }
}
