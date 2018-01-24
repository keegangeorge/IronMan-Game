class BasicEnemy extends MainCharacter 
{

  // FIELDS:
  PVector size; 
  PVector reAlignImage;
  float rockingShip, rockVelocity;
  float rotate;
  float deathTimer;


  // CONSTRUCTOR:
  BasicEnemy(PVector pos) 
  {
    super(pos);
    vel = new PVector(3, 0);
    size = new PVector(410, 358);
    reAlignImage = new PVector(-205, -180); // re-aligns image to draw inside hit-box
    deathTimer = 60;
  }

  // METHODS:
  void drawCharacter() 
  {
    image(basicEnemy, pos.x + reAlignImage.x, pos.y + reAlignImage.y);
  }

  void update() 
  {
    drawCharacter();
    moveCharacter();
    checkWalls();

    // Checks collision with player
    if ((abs(pos.x - (ironman.pos.x + 127)) < size.x / 2 + ironman.size.x / 2 && abs(pos.y - (ironman.pos.y + 138)) < size.y / 2 + ironman.size.y / 2)) 
    {
      ironman.decreaseHealth(1);
    }
  }

  void moveCharacter() 
  {
    pos.sub(vel);
    if (health <= -3) deathTimer--;
  }

  void respawn() 
  {
    alien.add(new BasicEnemy(new PVector(random(width, width + 1000), random(500))));
  }

  void checkWalls() 
  {
    if (pos.x < 0) {
      pos.x = random(width, width + 1000);
      killed();
      respawn();
    }
  }

  void decreaseHealth(int damage) 
  {
    super.decreaseHealth(damage);
  }

  void killed() 
  {
    alien.remove(this); // alien - global variable of ArrayList
  }
} // class end