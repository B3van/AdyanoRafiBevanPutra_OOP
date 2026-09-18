package com.Bevan.frontend.objects.items;

import com.Bevan.frontend.objects.Collidable;
import com.Bevan.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;
import com.Bevan.frontend.objects.GameObject;

public class Item extends GameObject {

    private String itemType;
    private long scoreValue;
    private ItemType itemTypeEnum;

    // Old Module 2 constructor
    public Item(float x, float y, String itemType) {
        super(x, y, 16, 16, 100f, Color.WHITE);

        this.itemType = itemType;
        this.scoreValue = 1000L;
    }

    // Old Module 2 constructor
    public Item(float x, float y, float width, float height,
                float speed, String itemType) {

        super(x, y, width, height, speed, Color.WHITE);

        this.itemType = itemType;
        this.scoreValue = 1000L;
    }

    // Old Module 2 constructor
    public Item(float x, float y, float width, float height,
                float speed, String itemType, long scoreValue) {

        super(x, y, width, height, speed, Color.WHITE);

        this.itemType = itemType;
        this.scoreValue = scoreValue;
    }

    // New Module 3 minimal constructor
    public Item(float x, float y, ItemType itemTypeEnum) {
        super(x, y, 16, 16, 100f, Color.WHITE);

        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
        this.scoreValue = itemTypeEnum.getScoreValue();
    }

    // New Module 3 full constructor
    public Item(float x, float y, float width, float height,
                float speed, ItemType itemTypeEnum, long scoreValue) {

        super(x, y, width, height, speed, Color.WHITE);

        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
        this.scoreValue = scoreValue;
    }

    @Override
    public void update(float delta) {
        this.y -= speed * delta;
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
    public void onCollision(Collidable other){
        if (other instanceof Player) {
            // Item collection is handled by Player
        }
    }
}
