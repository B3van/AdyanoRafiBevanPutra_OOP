package com.Bevan.frontend.objects;

import com.Bevan.frontend.objects.enemies.Enemy;
import com.Bevan.frontend.objects.items.Item;
import com.Bevan.frontend.objects.patterns.ShootingPattern;
import com.Bevan.frontend.systems.AssetManager;
import com.Bevan.frontend.systems.BulletManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject implements Collidable {

    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir = 0;

    private ShootingPattern shootingPattern;

    public Player(String name, int hp, int power, int spellCards) {
        this(280, 40, name, hp, power, spellCards);
    }

    public Player(float x, float y, String name, int hp,
                  int power, int spellCards) {
        super(x, y, 32, 48, 200f, Color.RED);

        this.name = name;
        this.hp = Math.max(0, hp);
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        float dx = 0f;
        float distance = getSpeed() * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W)
            || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setY(getY() + distance);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)
            || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setY(getY() - distance);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)
            || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setX(getX() - distance);
            dx -= distance;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)
            || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setX(getX() + distance);
            dx += distance;
        }

        updateAnimationState(dx);
    }

    private void updateAnimationState(float dx) {
        int nextDir;
        String animationKey;

        if (dx < 0f) {
            nextDir = -1;
            animationKey = "player_left";
        } else if (dx > 0f) {
            nextDir = 1;
            animationKey = "player_right";
        } else {
            nextDir = 0;
            animationKey = "player_idle";
        }

        if (nextDir != currentDir) {
            Animation<TextureRegion> animation =
                AssetManager.getInstance().getAnimation(animationKey);

            if (animation != null) {
                setAnimation(animation);
                currentDir = nextDir;
            }
        }
    }

    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) {
            return;
        }

        float muzzleX = getX() + getWidth() / 2f - 8f;
        float muzzleY = getY() + getHeight();

        if (shootingPattern != null) {
            shootingPattern.execute(
                muzzleX,
                muzzleY,
                bulletManager,
                true
            );
        } else {
            int damage = 10 + power;

            bulletManager.spawnPlayerBullet(
                muzzleX,
                muzzleY,
                0f,
                400f,
                damage
            );
        }
    }

    public void shoot(Enemy target) {
        if (target != null && target.isAlive()) {
            int damage = 10 + power;

            System.out.println(
                name + " shoots " + target.getName()
                    + " for " + damage + " damage!"
            );

            if (target.takeDamage(damage)) {
                addScore(target.getScoreValue());
            }
        }
    }

    public void takeDamage(int damage) {
        setHp(hp - damage);

        System.out.println(
            name + " took " + damage + " damage! HP: " + hp
        );
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void collectItem(Item item) {
        if (item != null && item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            score += points;
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(
            getX() + getWidth() / 2f - 4f,
            getY() + getHeight() / 2f - 4f,
            8f,
            8f
        );
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(
            getX(),
            getY(),
            getWidth(),
            getHeight()
        );
    }

    @Override
    public void onCollision(Collidable other) {
        // Damage and item collection are handled by other entities.
    }

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }

    public long getScore() {
        return score;
    }
}
