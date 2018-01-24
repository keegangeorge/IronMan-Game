class EnemyProjectile 
{

  // FIELDS:
  PVector pos, vel, dim;
  boolean isAlive;


  // CONSTRUCTOR:
  EnemyProjectile(PVector pos, PVector vel) 
  {
    this.pos = pos;
    this.vel = vel;
    isAlive = true;
    dim = new PVector(10, 5);
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
    if (removeBeam == false) 
    {
      pushMatrix();
      fill(5, 249, 255);
      translate(pos.x, pos.y);
      rect(0, 0, 80, dim.x, dim.y);
      popMatrix();
    }
  }

  void move() 
  {
    if (removeBeam == false) 
    {
      pos.add(vel);
    }
  }

  void checkWalls() 
  {
    if (pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height) 
    {
      isAlive = false;
    }
  }

  void hit(Player ironman) 
  {

    if ((abs(pos.x - (ironman.pos.x + 127)) < dim.x / 2 + ironman.size.x / 2 && abs(pos.y - (ironman.pos.y + 138)) < dim.y / 2 + ironman.size.y / 2))
    {
      ironman.decreaseHealth(50);
      isAlive = false;
      //if (enemy.health <= -3) 
      //{
      //  enemy.vel.add(-5, -5);
      //}
    }
  }
} // class end