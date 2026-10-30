package com.Bevan.frontend;

import com.Bevan.frontend.objects.GameObject;
import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.enemies.Boss;
import com.Bevan.frontend.objects.enemies.Fairy;
import com.Bevan.frontend.objects.items.Item;
import com.Bevan.frontend.objects.items.ItemType;
import com.Bevan.frontend.systems.AssetManager;
import com.Bevan.frontend.systems.BulletManager;
import com.Bevan.frontend.systems.EntityFactory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private List<GameObject> entities;

    private BulletManager bulletManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        entities = new ArrayList<>();
        fairy = new ArrayList<>();
        bulletManager = new BulletManager();

        AssetManager.getInstance().init();

        player = EntityFactory.createPlayer(
            280, 40, "Reimu Hakurei", 100, 15, 3
        );
        entities.add(player);

        fairy.add(EntityFactory.createFairy(
            150, 380, "Red Fairy", 20
        ));

        fairy.add(EntityFactory.createFairy(
            250, 380, "Blue Fairy", 20, "fairy_idle_blue"
        ));

        entities.addAll(fairy);

        boss = EntityFactory.createBoss(
            380, 400, "Rumia", 150
        );
        entities.add(boss);

        Item powerItem = EntityFactory.createItem(
            200, 450, ItemType.POWER
        );
        powerItem.setSpeed(80f);
        powerItem.setWidth(16);
        powerItem.setHeight(16);
        entities.add(powerItem);

        Item pointItem = EntityFactory.createItem(
            320, 480, ItemType.POINT
        );
        pointItem.setSpeed(120f);
        pointItem.setWidth(16);
        pointItem.setHeight(16);
        entities.add(pointItem);
    }

    public <T extends GameObject> void updateAndClean(
        List<T> list,
        float delta,
        float screenWidth,
        float screenHeight
    ) {
        Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()) {
            T entity = iterator.next();

            if (!entity.isDestroyed()) {
                entity.update(delta);
            }

            if (entity.isOffScreen(screenWidth, screenHeight)
                || entity.isDestroyed()) {
                iterator.remove();
            }
        }
    }

    private Rectangle getHitbox(GameObject entity) {
        Rectangle hitbox = entity.getCoreHitbox();

        if (hitbox != null) {
            return hitbox;
        }

        return new Rectangle(
            entity.getX(),
            entity.getY(),
            entity.getWidth(),
            entity.getHeight()
        );
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (player.isAlive()
            && !player.isDestroyed()
            && Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player.shootBullet(bulletManager);
        }

        updateAndClean(
            entities,
            delta,
            screenWidth,
            screenHeight
        );

        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                if (!a.isDestroyed()
                    && !b.isDestroyed()
                    && getHitbox(a).overlaps(getHitbox(b))) {
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();

        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }

        bulletManager.render(batch);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (GameObject entity : entities) {
            if (!entity.isDestroyed()
                && entity.getSprite() == null
                && entity.getAnimation() == null) {
                entity.render(shapeRenderer);
            }
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }

        AssetManager.getInstance().dispose();
    }
}
