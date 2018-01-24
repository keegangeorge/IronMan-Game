class Player extends MainCharacter 
{

  // FIELDS:
  float shake, shakeVel;
  float bodyRotation, bodyRotationVel;
  ArrayList <Projectile> bullet = new ArrayList<Projectile>();
  PVector armPosition;
  float projAdditX, projAdditY;
  float projectionAngle;
  PVector bulletpos;
  float acc = 5;
  PVector upAcc = new PVector(0, -acc);
  PVector downAcc = new PVector(0, acc);
  PVector leftAcc = new PVector(-acc, 0);
  PVector rightAcc = new PVector(acc, 0);

  // CONSTRUCTOR:
  Player(PVector pos) 
  {
    super(pos);
    shakeVel = 1;
    armPosition = pos;
    bulletpos = new PVector(186, 200);
    projAdditX = 186;
    projAdditY = 200;
    projectionAngle = 5;
    health = 200;
  }

  // METHODS:
  void fire() 
  {
    pushMatrix();
    bullet.add(new Projectile(new PVector(armPosition.x + bulletpos.x, armPosition.y + bulletpos.y), new PVector(20, projectionAngle))); // adds bullet
    popMatrix();
  }

  void decreaseHealth(int damage) {
    super.decreaseHealth(damage);
  }

  void update() 
  {
    super.update();
    bulletAngle();
    checkProjectiles();
    if (bulletCount <= 0) {
      recharge();
    }
    if (health <= 0) screen = GAME_OVER;

    if (keyPressed) {
      if (keyCode == UP || key == 'w' || key == 'W') 
      {
        image(thruster, pos.x + 87, pos.y + 255); // shows thruster-flames coming out of legs of character
      }

      if (keyCode == DOWN || key == 's' || key == 'S')
      {
        shake += shakeVel;
      }
      if (keyCode == LEFT || key == 'a' || key == 'A')
      {
        shake = 0;
      }
      if (keyCode == RIGHT || key == 'd' || key == 'D')
      {
        shake = 0;
      }
    }

    if (shake >= 4) 
    {
      shakeVel *= -1;
    }

    if (shake <= -4) 
    {
      shakeVel *= -1;
    }

    // Player Keyboard Controls:
    if (up) ironman.accelerate(upAcc);
    if (down) ironman.accelerate(downAcc);
    if (left) ironman.accelerate(leftAcc);
    if (right) ironman.accelerate(rightAcc);

    if (demoStateMove == true) 
    {
      if (up) demoMan.accelerate(upAcc);
      if (down) demoMan.accelerate(downAcc);
      if (left) demoMan.accelerate(leftAcc);
      if (right) demoMan.accelerate(rightAcc);
    }
  }

  void checkProjectiles() 
  {
    for (int i = 0; i < bullet.size(); i++) 
    {
      Projectile b = bullet.get(i); 
      b.update(); 
      b.drawProjectile();

      // cycle through each enemy for collision with bullet

      // Basic Enemy:
      for (int j = 0; j < alien.size(); j++) 
      {
        BasicEnemy enemy = alien.get(j);
        b.hit(enemy);
      }

      // removes bullet from ArrayList after hit
      if (!b.isAlive) bullet.remove(i);
    }
  }






  void bulletAngle() 
  {
    // Projects bullets at angle based on mouse press
    if (mouseY > 0 && mouseY < 76) 
    {
      bulletpos.y = 122;
      bulletpos.x = 200;
      projectionAngle = -5;
    } else if (mouseY > 76 && mouseY < 166) {
      bulletpos.y = 140;
      bulletpos.x = 200;
      projectionAngle = -4;
    } else if (mouseY > 166 && mouseY < 180) {
      bulletpos.y = 150;
      bulletpos.x = 200;
      projectionAngle = -3;
    } else if (mouseY > 180 && mouseY < 260) {
      bulletpos.y = 160;
      bulletpos.x = 220;
      projectionAngle = -2;
    } else if (mouseY > 260 && mouseY < 338) {
      bulletpos.x = 220;
      bulletpos.y = 160;
      projectionAngle = 0;
    } else if (mouseY > 338 && mouseY < 452) {
      bulletpos.y= 175;
      bulletpos.x = 210;
      projectionAngle = 1;
    } else if (mouseY > 452 && mouseY < 573) {
      bulletpos.y = 200;
      bulletpos.x = 210;
      projectionAngle = 3;
    } else  if (mouseY > 573 && mouseY < 601) {
      bulletpos.y = 220;
      bulletpos.x = 210;
      projectionAngle = 5;
    } else {
      bulletpos.y = 240;
      bulletpos.x = 190;
      projectionAngle = 5;
    }
  }

  void drawCharacter() 
  {
    float armPivot = map(mouseY, 0, height, -50, 70);
    pushMatrix();
    translate(pos.x, pos.y);

    // LEFT ARM:
    pushMatrix();
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    rectMode(CORNER);
    translate(95, 160);
    rotate(radians(120));
    rect(0, 0, 65, 20, 7);
    fill(accentColor);
    rect(0, 0, 35, 20, 7);
    popMatrix();

    // LEFT LEG:
    pushMatrix();
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    rectMode(CENTER);
    translate(103, 220);
    rotate(radians(shake));
    rect(0, 0, 30, 50, 5);
    fill(accentColor);
    rect(0, 0, 30, 15, 5);
    fill(mainColor);
    translate(0, 8);
    ellipse(0, 0, 10, 10);
    popMatrix();

    // LEFT FOOT:
    pushMatrix();
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    rectMode(CENTER);
    translate(103, 248);
    rotate(radians(shake));
    rect(0, 0, 25, 10, 10);
    popMatrix();

    // RIGHT ARM:
    pushMatrix();
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    rectMode(CORNER);
    translate(144, 149);
    //translate(armPosition.x, armPosition.y);
    rotate(radians(armPivot)); // pivots arm
    rect(0, 0, 65, 20, 7);
    fill(accentColor);
    rect(0, 0, 35, 20, 7);
    popMatrix();

    // RIGHT LEG:
    pushMatrix();
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    rectMode(CENTER);
    translate(133, 220);
    rotate(radians(shake));
    rect(0, 0, 30, 50, 5);
    fill(accentColor);
    rect(0, 0, 30, 15, 5);
    fill(mainColor);
    translate(0, 8);
    ellipse(0, 0, 10, 10);
    popMatrix();

    // RIGHT FOOT:
    pushMatrix();
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    rectMode(CENTER);
    translate(134, 248);
    rotate(radians(shake));
    rect(0, 0, 25, 10, 10);
    popMatrix();

    // TORSO:
    fill(mainColor);
    strokeWeight(2);
    stroke(strokeColor);
    pushMatrix();
    rectMode(CENTER);
    translate(118, 156);
    rect(0, 0, 60, 110, 5);
    translate(2, 30);
    line(-10, 0, 10, 0);
    translate(0, 5);
    line(-10, 0, 10, 0);
    translate(0, 5);
    line(-10, 0, 10, 0);
    popMatrix();

    // CHEST LIGHT:
    pushMatrix();
    fill(lightColor);
    translate(120, 170);
    stroke(strokeColor);
    strokeWeight(2);
    ellipse(0, 0, 20, 20); 
    popMatrix();

    // RIGHT EAR:
    pushMatrix();
    translate(184, 105);
    fill(mainColor);
    strokeWeight(5);
    rectMode(CENTER);
    pushMatrix();
    rotate(radians(10));
    rect(0, 0, 10, 40, 255);
    popMatrix();
    translate(-128, 0);

    // LEFT EAR:
    pushMatrix();
    rotate(radians(-10));
    strokeWeight(5);
    rect(0, 0, 10, 40, 255);
    popMatrix();

    // HEAD:
    translate(64, -18);
    strokeWeight(5);
    //rotate(radians(mouseX)); rotates head
    rect(0, 0, 128, 128, 255);
    popMatrix();

    // FACE:
    pushMatrix();
    beginShape();
    fill(accentColor);
    strokeWeight(2);
    vertex(69, 56);
    vertex(98, 45);
    vertex(110, 74);
    vertex(128, 75);
    vertex(141, 45);
    vertex(172, 55);
    vertex(176, 88);
    vertex(171, 119);
    vertex(157, 127);
    vertex(149, 146);
    vertex(120, 154);
    vertex(92, 148);
    vertex(84, 128);
    vertex(69, 120);
    vertex(64, 96);
    vertex(64, 80);
    vertex(68, 56);
    endShape();
    popMatrix();

    // MOUTH:
    pushMatrix();
    beginShape();
    stroke(strokeColor);
    noFill();
    vertex(99, 151);
    vertex(102, 142);
    vertex(135, 142);
    vertex(139, 150);
    endShape();
    popMatrix();

    // MASK CONNETOR:
    pushMatrix();
    stroke(strokeColor);
    curve(103, 60, 103, 57, 135, 56, 135, 60);
    popMatrix();

    // LEFT EYE:
    pushMatrix();
    translate(90, 107);
    strokeWeight(2);

    fill(lightColor);
    pushMatrix();
    rotate(radians(12));
    arc(0, 0, 40, 30, radians(0), radians(190));
    popMatrix();

    // RIGHT EYE:
    translate(60, 0);
    rotate(radians(-12));
    strokeWeight(2);
    arc(0, 0, 40, 30, radians(-10), radians(180));
    popMatrix();

    // EYEBROW:
    pushMatrix();
    stroke(strokeColor);
    strokeWeight(2);
    noFill();
    curve(70, -3, 70, 98, 169, 98, 169, -3);
    popMatrix();
    popMatrix();
  }


  void recharge() {
    hud.bulletBar += 0.5;
    rechargeText = true;
    if (hud.bulletBar >= 195) 
    {
      hud.bulletBar = 200;
      bulletCount = 10;
    }
  }
} // class end