package com.Bevan.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.Bevan.frontend.objects.enemies.Enemy;
import com.Bevan.frontend.objects.items.Item;
import com.Bevan.frontend.objects.items.ItemType;

public class Player extends GameObject {

    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(
        String name,
        int hp,
        int power,
        int spellCards
    ) {
        super(280, 40, 32, 32, 200f, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(
        float x,
        float y,
        String name,
        int hp,
        int power,
        int spellCards
    ) {
        super(x, y, 32, 32, 200f, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)
                || Gdx.input.isKeyPressed(Input.Keys.UP)) {

                setY(getY() + getSpeed() * delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S)
                || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

                setY(getY() - getSpeed() * delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)
                || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

                setX(getX() - getSpeed() * delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D)
                || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

                setX(getX() + getSpeed() * delta);
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Item) {
            System.out.println("Player touches items");
            collectItem((Item) other);
        }
    }

    public void takeDamage(int damage) {
        if (damage > 0) {
            setHp(getHp() - damage);

            System.out.println(
                name + " takes " + damage
                    + " damage. HP: " + hp
            );
        }
    }

    public void shoot(Enemy enemy, int damage) {
        if (enemy != null && damage > 0) {
            enemy.takeDamage(damage);

            System.out.println(
                name + " shoots " + enemy.getName()
                    + " for " + damage + " damage."
            );
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();

        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
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
                    this.spellCards += 1;
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected BOMB item! "
                            + "SpellCards: " + spellCards
                    );
                }

                case LIFE -> {
                    this.hp += 20;
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected LIFE item! "
                            + "HP: " + hp
                    );
                }
            }
        } else {
            addScore(item.getScoreValue());

            System.out.println(
                name + " collected "
                    + item.getItemType() + "!"
            );
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;

            System.out.println(
                name + " gained " + points
                    + " pts! Total Score: " + score
            );
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
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
