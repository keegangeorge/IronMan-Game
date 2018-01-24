class GameMode
{
  // SCREEN-CODE(2)

  // FIELDS:

  void basicRender() {
    surface.setTitle("IRON MAN: THE GAME > GAMEPLAY");
    background(255);
    image(bg1, 0, 0);
    ironman.update();
    hud.render();



    for (int i = 0; i < healthBump.size(); i++)
    {
      Perks moreHealth = healthBump.get(i);
      noStroke();
      moreHealth.healthUp();
      moreHealth.checkHealthCollision();
    }

    for (int i = 0; i < repulsorCharge.size(); i++)
    {
      Perks moreBullets = repulsorCharge.get(i);
      noStroke();
      moreBullets.repulsorIncrease();
      moreBullets.checkRepulsorCollision();
    }

    for (int i = 0; i < alien.size(); i++)
    {
      BasicEnemy enemy = alien.get(i);
      enemy.update();

      if (deathCount >= 10) {
        enemy.vel.x = 6;
        achievementState = false;
      }

      if (deathCount == 9) {
        screen = ACHIEVEMENT;
        achievementState = true;
      } else {
        achievementState = false;
      }
    }

    if (alien.size() <= 2) {
      alien.add(new BasicEnemy(new PVector(random(width, width + 1000), random(500))));
    }

    if (healthBump.size() <= 0) {
      healthBump.add(new Perks(new PVector(random(100, width), -1000), new PVector(0, random(0.9, 2))));
    }

    if (repulsorCharge.size() <= 0)
    {
      repulsorCharge.add(new Perks(new PVector(random(300, 1000), random(-300, -500)), new PVector(0, random(1, 2))));
    }

    if (deathCount >= 20)
    {
      screen = BOSS_CUT_SCENE;
    }
  }

  void bossRender()
  {
    background(255);
    image(bg2, 0, 0);
    surface.setTitle("GAME" + "X   " + mouseX + "    Y    " + mouseY);

    loki.update();
    ironman.update();
    hud.render();
    for (int i = 0; i < healthBump.size(); i++)
    {
      Perks moreHealth = healthBump.get(i);
      noStroke();
      moreHealth.healthUp();
      moreHealth.checkHealthCollision();
    }

    for (int i = 0; i < repulsorCharge.size(); i++)
    {
      Perks moreBullets = repulsorCharge.get(i);
      noStroke();
      moreBullets.repulsorIncrease();
      moreBullets.checkRepulsorCollision();
    }

    if (repulsorCharge.size() <= 0)
    {
      repulsorCharge.add(new Perks(new PVector(random(100, width), -500), new PVector(0, random(1, 2))));
    }

    if (healthBump.size() <= 0)
    {
      healthBump.add(new Perks(new PVector(random(100, width), -1000), new PVector(0, random(0.9, 2))));
    }
  }

  void pauseGame()
  {
    noLoop();
    gameState = false;
    pushMatrix();
    fill(0, 0, 0, 90);
    noStroke();
    rect(0, 0, 1280 * 2, 720 * 2);
    fill(246, 9, 81);
    translate(width / 2 - 100, height / 2);
    text(" GAME PAUSED ", 0, 0);
    textFont(hudFont);
    textSize(24);
    text("Press 'R' to resume", -50, 70);
    popMatrix();
  }

  void restartGame() {
    screen = GAME;
    deathCount = 0;
    hudDead = 0;
    for (int i = 0; i < alien.size(); i++)
    {
      BasicEnemy enemy = alien.get(i);
      enemy.pos.x = -500;
    }
    ironman.health = 200;
    hud.bulletBar = 200;
    bulletCount = 5;

    for (int i = 0; i < healthBump.size(); i++)
    {
      Perks moreHealth = healthBump.get(i);
      moreHealth.pos.y = -100;
    }

    for (int i = 0; i < repulsorCharge.size(); i++)
    {
      Perks moreBullets = repulsorCharge.get(i);
      moreBullets.pos.y = height + 100;
    }
  }
} // class end