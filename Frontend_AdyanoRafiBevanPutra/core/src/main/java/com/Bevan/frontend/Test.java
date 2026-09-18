package com.Bevan.frontend;

import com.badlogic.gdx.math.Rectangle;

import com.Bevan.frontend.objects.BulletType;
import com.Bevan.frontend.objects.Collidable;
import com.Bevan.frontend.objects.Player;
import com.Bevan.frontend.objects.enemies.Boss;
import com.Bevan.frontend.objects.enemies.Enemy;
import com.Bevan.frontend.objects.enemies.Fairy;
import com.Bevan.frontend.objects.items.Item;
import com.Bevan.frontend.objects.items.ItemType;

public class Test {

    public static void main(String[] args) {

        System.out.println(
            "=== TOUHOU OOP PRACTICUM - MODULE 1: "
                + "BASIC CLASSES & OBJECT INTERACTION ==="
        );

        Player reimu = new Player(
            "Reimu Hakurei",
            100,
            15,
            3
        );

        Enemy fairyBoss = new Enemy(
            "Cirno (Stage 2 Boss)",
            50
        );

        System.out.println("\n--- Initial Battle State ---");

        System.out.println(
            "Player: " + reimu.getName()
                + " | HP: " + reimu.getHp()
                + " | Power: " + reimu.getPower()
                + " | SpellCards: " + reimu.getSpellCards()
        );

        System.out.println(
            "Enemy: " + fairyBoss.getName()
                + " | HP: " + fairyBoss.getHp()
        );

        System.out.println("\n--- Turn 1: Player Shoots Enemy ---");
        reimu.shoot(fairyBoss, 25);

        System.out.println("\n--- Turn 2: Enemy Counter-attacks ---");
        fairyBoss.attack(reimu, 30);

        System.out.println(
            "\n--- Turn 3: Player Shoots Enemy Finishing Blow ---"
        );
        reimu.shoot(fairyBoss, 25);

        System.out.println(
            "\n--- Turn 4: Enemy Deals Fatal Damage to Reimu ---"
        );
        fairyBoss.attack(reimu, 80);

        System.out.println("\n=== Battle Simulation Complete ===");


        // ==================================================
        // MODULE 2: ENCAPSULATION, INHERITANCE & SCORE SYSTEM
        // ==================================================

        System.out.println(
            "\n\n=== TOUHOU OOP PRACTICUM - MODULE 2: "
                + "ENCAPSULATION, INHERITANCE & SCORE SYSTEM ==="
        );

        Player reimu2 = new Player(
            "Reimu Hakurei",
            100,
            15,
            3
        );

        Fairy fairy = new Fairy(
            "Stage 1 Fairy",
            20
        );

        Boss cirno = new Boss(
            "Cirno (Stage 2 Boss)",
            150
        );

        Item pointItem = new Item(
            200,
            450,
            12,
            12,
            120f,
            "Point Item",
            1000L
        );

        System.out.println(
            "\n--- Testing Encapsulation & Inheritance ---"
        );

        System.out.println(
            "Player: " + reimu2.getName()
                + " | Position: (" + reimu2.getX()
                + ", " + reimu2.getY() + ")"
        );

        System.out.println(
            "Fairy: " + fairy.getName()
                + " | Defeat Worth: "
                + fairy.getScoreValue() + " pts"
        );

        System.out.println(
            "Boss: " + cirno.getName()
                + " | Defeat Worth: "
                + cirno.getScoreValue()
                + " pts | Size: "
                + cirno.getWidth()
                + "x" + cirno.getHeight()
        );

        System.out.println(
            "Item: " + pointItem.getItemType()
                + " | Value: "
                + pointItem.getScoreValue()
                + " pts | Speed: "
                + pointItem.getSpeed()
        );

        System.out.println("\n--- Testing Item Movement Update ---");

        System.out.println(
            "Initial Item Y: " + pointItem.getY()
        );

        pointItem.update(0.5f);

        System.out.println(
            "Item Y after 0.5s update: "
                + pointItem.getY()
                + " (linear downward movement)"
        );

        System.out.println("\n--- Testing Scoring System ---");

        System.out.println(
            "Initial Score: " + reimu2.getScore()
        );

        reimu2.shoot(fairy, 25);
        reimu2.collectItem(pointItem);
        reimu2.shoot(cirno, 25);

        System.out.println(
            "Final Score: " + reimu2.getScore() + " pts"
        );

        System.out.println(
            "\n=== Module 2 Test Completed Successfully ==="
        );


        // ===============================================
        // MODULE 3: ABSTRACTION, INTERFACE, ENUM & HITBOXES
        // ===============================================

        System.out.println(
            "\n\n=== TOUHOU OOP PRACTICUM - MODULE 3: "
                + "POLYMORPHISM & ABSTRACTION ==="
        );

        System.out.println("\n--- Testing Collidable Interface ---");

        Collidable collidablePlayer = reimu2;

        Rectangle coreHitbox =
            collidablePlayer.getCoreHitbox();

        Rectangle grazeHitbox =
            collidablePlayer.getGrazeHitbox();

        System.out.println(
            "Core Hitbox: "
                + "x=" + coreHitbox.x
                + ", y=" + coreHitbox.y
                + ", width=" + coreHitbox.width
                + ", height=" + coreHitbox.height
        );

        System.out.println(
            "Graze Hitbox: "
                + "x=" + grazeHitbox.x
                + ", y=" + grazeHitbox.y
                + ", width=" + grazeHitbox.width
                + ", height=" + grazeHitbox.height
        );

        System.out.println("\n--- Testing ItemType Enum ---");

        Item powerItem = new Item(
            300,
            300,
            ItemType.POWER
        );

        Item lifeItem = new Item(
            350,
            300,
            ItemType.LIFE
        );

        System.out.println(
            "Power Item Type: "
                + powerItem.getItemTypeEnum()
        );

        System.out.println(
            "Power Item String: "
                + powerItem.getItemType()
        );

        System.out.println(
            "Power Item Score: "
                + powerItem.getScoreValue()
        );

        System.out.println(
            "Power Bonus: "
                + ItemType.POWER.getPowerBonus()
        );

        System.out.println(
            "Life Item Type: "
                + lifeItem.getItemTypeEnum()
        );

        System.out.println(
            "Life Item Score: "
                + lifeItem.getScoreValue()
        );

        System.out.println("\n--- Testing BulletType Enum ---");

        BulletType selectedBullet = BulletType.AMULET;

        System.out.println(
            "Selected Bullet Type: " + selectedBullet
        );

        System.out.println(
            "\n=== Module 3 Test Completed Successfully ==="
        );
    }
}
