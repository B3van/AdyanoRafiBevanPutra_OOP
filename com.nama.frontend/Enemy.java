package com.nama.frontend;

public class Enemy {
    String name;
    int hp, maxHP;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
    }

    public void takeDamage(int damage) {
        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }

        System.out.println(name + " took " + damage + " damage! HP: " + hp + "/" + maxHP);

        if (hp == 0) {
            System.out.println(name + " was defeated!");
        }
    }

    public void attack(Player target, int damage) {
        System.out.println(name + " attacks " + target.name + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }

    public boolean isAlive() {
        return hp > 0;
    }
}