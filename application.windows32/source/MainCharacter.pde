class MainCharacter 
{

  // FIELDS:
  PVector pos;
  PVector vel;
  float health;
  float wid, hght;
  float dampener = 0.8;
  PVector size = new PVector(163, 234);

  // CONSTRUCTOR:
  MainCharacter(PVector pos) 
  {
    this.pos = pos;
    vel = new PVector();
  }

  // METHODS:
  void moveCharacter() 
  {
    vel.mult(dampener); // multiplies velocity by dampening factor
    pos.add(vel);
  }

  void accelerate(PVector acc) 
  {
    vel.add(acc);
  }

  void drawCharacter() 
  {
    pushMatrix();
    fill(mainColor);
    translate(pos.x, pos.y);
    rect(0, 0, 155, 300);
    popMatrix();
  }

  // CHECKS COLLISION:
  boolean hitCharacter(MainCharacter other) {
    if (abs(pos.x - other.pos.x) < size.x / 2 + other.size.x / 2 && abs(pos.y - other.pos.y) < size.y / 2 + other.size.y / 2) 
    {
      return true;
    }
    return false;
  }

  void decreaseHealth(int damage) 
  {
    health -= damage;
  }

  void checkWalls() 
  {
    if (pos.x > width)         pos.x = width - 400;
    if (pos.x < -200)          pos.x = 0;
    if (pos.y + 200 > height)  pos.y = 380;
    if (pos.y < 0)             pos.y = 0;
  }

  void update() 
  {
    drawCharacter();
    moveCharacter();
    checkWalls();
  }
} // class end