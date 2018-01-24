class Projectile 
{

  // FIELDS:
  PVector pos, vel;
  boolean isAlive;
  float bulletSize;

  // CONSTRUCTOR:
  Projectile(PVector pos, PVector vel) 
  {
    this.pos = pos;
    this.vel = vel;
    bulletSize = 20;
    isAlive = true;
  }

  void update() 
  {
    drawProjectile();
    move();
    checkWalls();
  }

  // METHODS:
  void drawProjectile() 
  {
    pushMatrix();
    fill(214, 247, 255);
    stroke(214, 247, 255);
    translate(pos.x, pos.y);
    ellipse(0, 0, bulletSize, bulletSize);
    popMatrix();
  }


  void move() 
  {
    pos.add(vel);
  }

  void checkWalls() 
  {
    if (pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height) 
    {
      isAlive = false;
    }
  }

  void hit(BasicEnemy enemy) 
  {
    if (abs(pos.x - enemy.pos.x) < bulletSize / 2 + enemy.size.x / 2 && abs(pos.y - enemy.pos.y) < bulletSize / 2 + enemy.size.y / 2)
    {
      enemy.decreaseHealth(1);
      isAlive = false;
      if (enemy.health <= -3) 
      {
        enemy.vel.add(-5, -5);
      }
    }

    if (enemy.deathTimer <= 0) 
    {
      enemy.killed();
      deathCount++;
      hudDead += 1;
      enemy.deathTimer = 60;
    }

    if (abs(pos.x - loki.pos.x) < bulletSize / 2 + loki.size.x / 2 && abs(pos.y - loki.pos.y) < bulletSize / 2 + loki.size.y / 2 &&  screen == BOSS_GAME)
    {
      loki.decreaseHealth(1);
      isAlive = false;
    }

    if (loki.health <= -16 && screen == BOSS_GAME) 
    {
      bossDeathTimerStart = true;
      loki.tintColor = color(255, 0, 0);
      loki.vel.x = 0;
      loki.vel.y = -0.5;
      removeBeam = true;
    }

    if (bossDeath <= 0) 
    {
      screen = WINNER;
      loki.tintColor = color(255, 0, 0);
    }
  }
} // class end