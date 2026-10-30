package com.Bevan.frontend.systems;

import com.Bevan.frontend.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {

        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        entities.addALL(newDrops);

    }
}
