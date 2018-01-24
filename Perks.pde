class Perks 
{
  PVector pos;
  PVector vel;
  PVector dim;

  Perks(PVector pos, PVector vel) 
  {
    this.pos = pos;
    this.vel = vel;
    dim = new PVector(55, 55);
  }


  void healthUp() 
  {
    pos.add(vel);

    pushMatrix();

    translate(pos.x, pos.y);

    pushMatrix();
    fill(145);
    ellipse(0, 0, dim.x, dim.y);
    popMatrix();

    noStroke();
    pushMatrix();
    translate(-50, -20);
    fill(#FF4740);
    beginShape();
    vertex(50, 15); 
    bezierVertex(50, -5, 90, 5, 50, 40); 
    vertex(50, 15); 
    bezierVertex(50, -5, 10, 5, 50, 40); 
    endShape();
    popMatrix();
    popMatrix();
    fill(#FF4740);
  }

  void checkHealthCollision() 
  {

    if ((abs(pos.x - (ironman.pos.x + 127)) < dim.x / 2 + ironman.size.x && abs(pos.y - (ironman.pos.y + 138)) < dim.y / 2 + ironman.size.y / 2)) 
    {
      if (ironman.health < 180) 
      {
        ironman.health += 20;
      }
      healthBump.remove(this);

      if (pos.y > height) 
      {
        healthBump.remove(this);
      }
    }
  }



  void repulsorIncrease() 
  {
    pos.add(vel);
    pushMatrix();
    translate(pos.x, pos.y);
    pushMatrix();
    scale(0.25);
    image(repulsorIcon, 0, 0);
    popMatrix();
    popMatrix();
  }


  void checkRepulsorCollision() 
  {

    if ((abs(pos.x - (ironman.pos.x + 127)) < dim.x / 2 + ironman.size.x && abs(pos.y - (ironman.pos.y + 138)) < dim.y / 2 + ironman.size.y / 2)) 
    {
      hud.bulletBar = 200;
      bulletCount = 10;
      repulsorCharge.remove(this);
    }

    if (pos.y > height) 
    {
      repulsorCharge.remove(this);
    }
  }
} // class end