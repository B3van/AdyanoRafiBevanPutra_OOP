package com.Bevan.frontend.objects.items;

import com.Bevan.frontend.objects.Collidable;
import com.Bevan.frontend.objects.GameObject;
import com.Bevan.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Item extends GameObject implements Collidable {
    private String itemType;
    private long scoreValue;
    private ItemType itemTypeEnum;

    public Item(float x, float y, String itemType) {
        super(x, y, 16, 16, 100f, Color.WHITE);
        this.itemType = itemType;
        this.scoreValue = 1000L;
        this.itemTypeEnum = null;
    }

    public Item(float x, float y, float width, float height,
                float speed, String itemType) {
        super(x, y, width, height, speed, Color.WHITE);
        this.itemType = itemType;
        this.scoreValue = 1000L;
        this.itemTypeEnum = null;
    }

    public Item(float x, float y, float width, float height,
                float speed, String itemType, long scoreValue) {
        super(x, y, width, height, speed, Color.WHITE);
        this.itemType = itemType;
        this.scoreValue = scoreValue;
        this.itemTypeEnum = null;
    }

    public Item(float x, float y, ItemType itemTypeEnum) {
        super(x, y, 16, 16, 100f, Color.WHITE);
        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
        this.scoreValue = itemTypeEnum.getScoreValue();
    }

    public Item(float x, float y, float width, float height,
                float speed, ItemType itemTypeEnum) {
        super(x, y, width, height, speed, Color.WHITE);
        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
        this.scoreValue = itemTypeEnum.getScoreValue();
    }

    public Item(float x, float y, float width, float height,
                float speed, ItemType itemTypeEnum, long scoreValue) {
        super(x, y, width, height, speed, Color.WHITE);
        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
        this.scoreValue = scoreValue;
    }

    @Override
    public void update(float delta) {
        setY(getY() - getSpeed() * delta);
    }

    public String getItemType() {
        return itemType;
    }

    public long getScoreValue() {
        return scoreValue;
    }

    public ItemType getItemTypeEnum() {
        return itemTypeEnum;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            // Item collection is handled by Player
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
