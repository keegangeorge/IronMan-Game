class BossEnemy extends BasicEnemy 
{

  // FIELDS:
  ArrayList <EnemyProjectile> beam = new ArrayList<EnemyProjectile>();
  boolean newWall = false;
  float launchTimer;
  color tintColor = color(255);

  // CONSTRUCTOR:
  BossEnemy(PVector pos) 
  {
    super(pos);
    vel = new PVector(5, 0);
    size = new PVector(305, 330);
    reAlignImage = new PVector(-153, -164); // re-aligns image to draw inside hit-box
    launchTimer = random(10, 50);
  }


  // METHODS:
  void drawCharacter() 
  {
    tint(tintColor);
    image(bossEnemy, pos.x + reAlignImage.x, pos.y + reAlignImage.y);
  }

  void update() 
  {
    drawCharacter();
    moveCharacter();
    checkWalls();
    checkProjectiles();
    launchTimer--;

    // Check collision with player:
    if ((abs(pos.x - (ironman.pos.x + 127)) < size.x / 2 + ironman.size.x / 2 && abs(pos.y - (ironman.pos.y + 138)) < size.y / 2 + ironman.size.y / 2)) 
    {
      ironman.decreaseHealth(1);
    }

    if (launchTimer <= 0) 
    {
      fire(); 
      launchTimer = random(10, 50);
    }
  }

  void fire() 
  {
    pushMatrix();
    beam.add(new EnemyProjectile(new PVector(pos.x - 184, pos.y - 62), new PVector(-20, 0))); // adds bullet
    popMatrix();
  }

  void checkProjectiles() 
  {
    for (int i = 0; i < beam.size(); i++) 
    {
      EnemyProjectile t = beam.get(i); 
      t.update(); 
      t.drawProjectile();
      t.hit(ironman);
      if (!t.isAlive) beam.remove(i);
    }
  }


  void moveCharacter() 
  {
    pos.sub(vel);
  }

  void decreaseHealth(int damage) 
  {
    super.decreaseHealth(damage);
  }

  void checkWalls() 
  {
    if (pos.x <= 680) 
    {
      vel.x *= -1;
      vel.y = -1;
      newWall = true;
    }

    if (pos.y <= 80)
    {
      vel.y *= -1;
    }

    if (pos.y >= height - 100) 
    {
      vel.y *= -1;
    }

    if (newWall == true && pos.x >= width - 100) 
    {
      vel.x *= -1;
      vel.y *= random(3, -35);
    }
  }
} // class end