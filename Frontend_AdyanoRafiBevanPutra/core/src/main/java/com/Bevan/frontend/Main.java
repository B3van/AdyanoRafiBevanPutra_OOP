package com.Bevan.frontend;

import com.Bevan.frontend.objects.items.ItemType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


import com.Bevan.frontend.objects.GameObject;
import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.enemies.Boss;
import com.Bevan.frontend.objects.enemies.Fairy;
import com.Bevan.frontend.objects.items.Item;

import com.Bevan.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;

    private Player player;
    private Fairy fairy;
    private Boss boss;

    private Item powerItem;
    private Item pointItem;

    private List<GameObject> entities;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();

        // Player - Red square at (280, 40)
        player = new Player(
            280,
            40,
            "Reimu Hakurei",
            100,
            15,
            3
        );

        // Fairy - Pink square at (150, 380)
        fairy = new Fairy(
            150,
            380,
            "Stage 1 Fairy",
            20
        );

        // Boss - Blue square at (380, 400)
        boss = new Boss(
            380,
            400,
            "Cirno (Stage 2 Boss)",
            150
        );

        // Items - White squares moving downward
       powerItem = new Item (
           200, 450, 16, 16,
           80f, ItemType.POWER, 500L
       );

       pointItem = new Item(
           320, 480, 12, 12,
           120f, ItemType.POINT, 1000L
       );
        // Add all objects polymorphically
        entities.add(player);
        entities.add(fairy);
        entities.add(boss);
        entities.add(pointItem);
        entities.add(powerItem);
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        // Polymorphic update
        for (GameObject obj : entities) {
            obj.update(delta);
        }

        // AABB collision detection
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                if (a.getCoreHitbox(). overlaps(b.getCoreHitbox())) {
                    a.onCollision(b);
                    b.onCollision(a);

                    }
                }
            }

            ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);



        // Polymorphic rendering
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (GameObject obj : entities) {
            obj.render(shapeRenderer);
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
