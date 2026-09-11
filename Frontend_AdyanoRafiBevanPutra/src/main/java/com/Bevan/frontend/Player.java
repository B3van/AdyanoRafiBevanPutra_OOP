package com.Bevan.frontend;

import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject {

    public String name;
    public int hp;
    public int power;
    public int spellCards;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 0, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public void shoot(Enemy target) {
        int damage = 10 + power;

        System.out.println(name + " shoots " + target.getName()
                + " dealing " + damage + " DMG!");

        target.takeDamage(damage);
    }

    public void addScore(long points) {
        if (points > 0) {
            score += points;
        }
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public int getSpellCards() {
        return spellCards;
    }

    public long getScore() {
        return score;
    }

    public void setHp(int hp) {
        this.hp = Math.max(hp, 0);
    }

    public void takeDamage(int damage) {
        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }
    }
}
