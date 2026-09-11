package com.Bevan.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;

    private Player player;
    private Fairy fairy;
    private Boss boss;

    private Item pointItem;
    private Item bonusItem;

    private List<GameObject> gameObjects;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();

        gameObjects = new ArrayList<>();

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
        pointItem = new Item(
            200,
            450,
            16,
            16,
            100f,
            "Point Item",
            1000L
        );

        bonusItem = new Item(
            320,
            500,
            16,
            16,
            120f,
            "Bonus Item",
            1500L
        );

        // Add all objects polymorphically
        gameObjects.add(player);
        gameObjects.add(fairy);
        gameObjects.add(boss);
        gameObjects.add(pointItem);
        gameObjects.add(bonusItem);
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        // Polymorphic update
        for (GameObject obj : gameObjects) {
            obj.update(delta);
        }

        // Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // Polymorphic render
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (GameObject obj : gameObjects) {
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
