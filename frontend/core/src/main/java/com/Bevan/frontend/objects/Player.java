package com.Bevan.frontend.objects;

import com.Bevan.frontend.objects.enemies.Enemy;
import com.Bevan.frontend.objects.items.Item;
import com.Bevan.frontend.objects.items.ItemType;
import com.Bevan.frontend.objects.patterns.ShootingPattern;
import com.Bevan.frontend.observers.GameObserver;
import com.Bevan.frontend.systems.AssetManager;
import com.Bevan.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject implements Collidable {
    private final List<GameObserver> observers = new ArrayList<>();

    private String name;
    private int hp;
    private int lives = 3;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir = 0;

    private ShootingPattern shootingPattern;

    private int grazeCount = 0;
    private boolean focused = false;
    private float shootTimer = 0.1f;
    private float shootCooldown = 0.1f;
    private float invulnerableTimer = 0f;

    public Player(String name, int hp, int power, int spellCards) {
        this(200, 50, name, hp, power, spellCards);
    }

    public Player(
        float x,
        float y,
        String name,
        int hp,
        int power,
        int spellCards
    ) {
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

        shootTimer += delta;

        if (invulnerableTimer > 0f) {
            invulnerableTimer -= delta;

            if (invulnerableTimer < 0f) {
                invulnerableTimer = 0f;
            }
        }
    }

    public void move(float dx, float dy) {
        setX(getX() + dx * getSpeed());
        setY(getY() + dy * getSpeed());

        clampToPlayfield();
    }

    private void clampToPlayfield() {
        float minX = 32f;
        float maxX = 416f - getWidth();
        float minY = 16f;
        float maxY = 560f - getHeight();

        setX(Math.max(minX, Math.min(getX(), maxX)));
        setY(Math.max(minY, Math.min(getY(), maxY)));
    }

    public void updateAnimationState(float dx) {
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

        if (shootTimer >= shootCooldown) {
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
                bulletManager.spawnPlayerBullet(
                    muzzleX,
                    muzzleY,
                    0f,
                    400f,
                    10 + power
                );
            }

            shootTimer = 0f;
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

    public void useBomb(
        BulletManager bulletManager,
        List<GameObject> entities
    ) {
        if (spellCards > 0) {
            spellCards--;
            notifySpellCardsChanged();
            invulnerableTimer = 5.0f;

            if (bulletManager != null) {
                bulletManager.clearEnemyBullets(entities);
            }

            System.out.println(
                name + " unleashes SPELL CARD (Fantasy Seal)! "
                    + "All enemy bullets cleared!"
            );
        } else {
            System.out.println("No Spell Cards (Bombs) remaining!");
        }
    }

    public void useBomb(BulletManager bulletManager) {
        useBomb(bulletManager, null);
    }

    public void addGraze() {
        grazeCount++;
        addScore(50);
        notifyGrazeChanged();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch == null || !active || isDestroyed()) {
            return;
        }

        if (invulnerableTimer > 0f) {
            boolean visible =
                ((int) (invulnerableTimer * 16)) % 2 == 0;

            if (!visible) {
                return;
            }
        }

        float previousColor = batch.getPackedColor();

        if (focused) {
            batch.setColor(1f, 1f, 1f, 0.5f);
        }

        super.render(batch);

        batch.setPackedColor(previousColor);
    }

    public void renderFocusIndicator(ShapeRenderer shapeRenderer) {
        if (focused && shapeRenderer != null
            && active && !isDestroyed()) {

            Rectangle core = getCoreHitbox();

            float centerX = core.x + core.width / 2f;
            float centerY = core.y + core.height / 2f;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(centerX, centerY, 4f);

            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(centerX, centerY, 2f);

            shapeRenderer.end();
        }
    }

    public void takeDamage(int damage) {
        takeDamage(damage, null, null);
    }

    public void takeDamage(
        int damage,
        BulletManager bulletManager,
        List<GameObject> entities
    ) {
        if (isInvulnerable() || isDestroyed()) {
            if (isInvulnerable()) {
                System.out.println(
                    name + " is INVULNERABLE! (0 damage taken)"
                );
            }

            return;
        }

        hp = Math.max(0, hp - damage);
        notifyHpChanged();

        System.out.println(
            name + " took " + damage
                + " damage! Remaining HP: " + hp
        );

        if (hp == 0) {
            System.out.println(
                name + " was defeated (Pichuun~)!"
            );

            loseLife(bulletManager, entities);
        }
    }

    public void loseLife(
        BulletManager bulletManager,
        List<GameObject> entities
    ) {
        lives--;
        notifyLivesChanged();
        =
        if (bulletManager != null) {
            bulletManager.clearEnemyBullets();
        }

        if (lives > 0) {
            System.out.println(
                "[Player] " + name
                    + " lost a life! Remaining Lives: " + lives
                    + ". Respawning at (200, 50)..."
            );

            respawn();
        } else {
            System.out.println(
                "[Player] GAME OVER! "
                    + name + " has run out of lives!"
            );

            destroy();
        }
    }

    public void respawn() {
        active = true;

        setX(200);
        setY(50);

        hp = 100;
        invulnerableTimer = 3.0f;
        power = Math.max(10, power - 16);
        spellCards = 3;
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();

        focused = false;
        shootTimer = shootCooldown;
        updateAnimationState(0f);
    }

    public void resetGame() {
        lives = 3;
        hp = 100;
        power = 15;
        spellCards = 3;
        score = 0;
        grazeCount = 0;

        notifyLivesChanged();
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
        notifyScoreChanged();
        notifyGrazeChanged();

        active = true;

        setX(200);
        setY(50);

        invulnerableTimer = 3.0f;
        focused = false;
        shootTimer = shootCooldown;
        updateAnimationState(0f);
    }

    public void collectItem(Item item) {
        if (item == null || item.isDestroyed()) {
            return;
        }

        ItemType type = item.getItemTypeEnum();

        if (type != null) {
            switch (type) {
                case POWER -> {
                    power += type.getPowerBonus();
                    notifyPowerChanged();
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected POWER item! "
                            + "Power increased to " + power
                    );
                }

                case POINT -> {
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected POINT item!"
                    );
                }

                case BOMB -> {
                    spellCards++;
                    notifySpellCardsChanged();
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected BOMB item! "
                            + "SpellCards: " + spellCards
                    );
                }

                case LIFE -> {
                    lives++;
                    hp = 100;
                    notifyLivesChanged();
                    notifyHpChanged();
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected LIFE item! "
                            + "Remaining Lives: " + lives
                    );
                }
            }
        } else {
            addScore(item.getScoreValue());

            System.out.println(
                name + " collected " + item.getItemType() + "!"
            );
        }

        item.destroy();
    }

    public void addScore(long points) {
        if (points > 0) {
            score += points;
            notifyScoreChanged();
        }
    }

    public boolean isAlive() {
        return hp > 0 && lives > 0 && !isDestroyed();
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public boolean isInvulnerable() {
        return invulnerableTimer > 0f;
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
        notifyHpChanged();

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
        notifyPowerChanged();
    }

    public int getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
        notifySpellCardsChanged();
    }

    public long getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        notifyLivesChanged();
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public int getGrazeCount() {
        return grazeCount;
    }

    public float getInvulnerableTimer() {
        return invulnerableTimer;
    }
    private void notifyLivesChanged() {
        for (GameObserver o : observers) o.onLivesChanged(lives);
    }
    private void notifyHpChanged() {
        for (GameObserver o : observers) o.onHpChanged(hp);
    }
    private void notifyPowerChanged() {
        for (GameObserver o : observers) o.onPowerChanged(power);
    }
    private void notifySpellCardsChanged() {
        for (GameObserver o : observers) o.onSpellCardsChanged(spellCards);
    }
    private void notifyScoreChanged() {
        for (GameObserver o : observers) o.onScoreChanged(score);
    }
    private void notifyGrazeChanged() {
        for (GameObserver o : observers) o.onGrazeChanged(grazeCount);
}
