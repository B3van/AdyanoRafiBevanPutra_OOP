package com.Bevan.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.Bevan.frontend.objects.BulletType;
import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.bullets.Bullet;
import com.Bevan.frontend.objects.enemies.Boss;
import com.Bevan.frontend.objects.enemies.Fairy;
import com.Bevan.frontend.objects.items.Item;
import com.Bevan.frontend.objects.items.ItemType;


public class EntityFactory {

    public static Player createPlayer(
        float x, float y, String name,
        int hp, int power, int spellCards
    ) {
        Player player = new Player(
            x, y, name, hp, power, spellCards
        );
        player.setAnimation(
            AssetManager.getInstance().getAnimation("player_idle")
        );
        return player;
    }

    public static Boss createBoss(
        float x, float y, String name, int hp
    ) {
        Boss boss = new Boss(x, y, name, hp);
        boss.setWidth(64);
        boss.setHeight(64);
        boss.setAnimation(
            AssetManager.getInstance().getAnimation("boss_idle")
        );
        return boss;
    }

    public static Fairy createFairy(
        float x, float y, String name, int hp
    ) {
        Fairy fairy = new Fairy(x, y, name, hp);
        fairy.setWidth(32);
        fairy.setHeight(32);

        Animation<TextureRegion> idleAnim =
            AssetManager.getInstance().getAnimation("fairy_idle_red");

        fairy.setAnimation(idleAnim);
        return fairy;
    }

    public static Fairy createFairy(float x, float y, String name,int hp, String keyString) {
        Fairy fairy = new Fairy(x, y, name, hp);
        fairy.setWidth(32);
        fairy.setHeight(32);

        Animation<TextureRegion> idleAnim =
            AssetManager.getInstance().getAnimation(keyString);

        fairy.setAnimation(idleAnim);
        return fairy;


    }

    public static Item createItem(
        float x, float y, ItemType itemType
    ) {
        Item item = new Item(x, y, itemType);

        String spriteKey;
        switch (itemType) {
            case POWER:
                spriteKey = "item_power";
                break;
            case POINT:
                spriteKey = "item_point";
                break;
            case BOMB:
                spriteKey = "item_bomb";
                break;
            case LIFE:
                spriteKey = "item_life";
                break;
            default:
                throw new IllegalArgumentException(
                    "Unsupported item type: " + itemType
                );
        }

        item.setSprite(
            AssetManager.getInstance().getTextureRegion(spriteKey)
        );
        return item;
    }

    public static Bullet createEnemyBullet(
        float x, float y, int damage
    ) {
        Bullet bullet = new Bullet(
            x, y, 0f, BulletType.DANMAKU, damage
        );
        bullet.setWidth(16);
        bullet.setHeight(16);
        bullet.setSprite(
            AssetManager.getInstance()
                .getTextureRegion("bullet_danmaku")
        );
        return bullet;
    }
}
