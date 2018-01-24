class CutScene {
  color sky = color(52, 184, 214);
  color mediumBuilding = color(84);
  color lightBuilding = color(125);
  color darkBuilding = color(43);
  color sun = color(255, 222, 0);
  PVector furyPos = new PVector(-760, 167);
  PVector furyVel = new PVector(5, 0);
  PVector fury2Pos = new PVector(-760, 167);
  PVector fury2Vel = new PVector(8, 0);
  PVector capPos = new PVector(635, 594);
  PVector capVel = new PVector(0, -5);
  float timerIntro, startTime;
  float bossTimer, bossStartTime;

  CutScene() 
  {
    startTime = 600;
    timerIntro = startTime;
    bossStartTime = 400;
    bossTimer = bossStartTime;
  }


  void introCutScene() 
  {
    surface.setTitle("IRON MAN: THE GAME");
    background(sky);
    timerIntro--;
    //println(timerIntro);
    rectMode(CORNER);
    if (mouseX > 50 && mouseX < 214 && mouseY > 50 && mouseY < 100 && mouseButton == LEFT && mousePressed) screen = GAME;

    if (timerIntro <= 0) 
    {
      timerIntro = startTime;
      furyPos.x = -760;
      screen = GAME;
    }

    // Dark Coloured Building:
    pushMatrix();
    fill(darkBuilding);
    noStroke();
    translate(0, 156);
    rect(0, 0, 520, 564);
    popMatrix();

    // Medium Coloured Building:
    fill(mediumBuilding);
    noStroke();
    pushMatrix();
    translate(0, 68);
    rect(0, 0, 305, 655);
    popMatrix();

    // Light Coloured Building:
    pushMatrix();
    translate(106, 413);
    fill(lightBuilding);
    noStroke();
    rect(0, 0, 950, 311);
    popMatrix();

    // Sun:
    pushMatrix();
    translate(1268, 0);
    fill(sun);
    noStroke();
    ellipse(0, 0, 545, 544);
    popMatrix();

    // Window:
    noFill();
    stroke(0);
    strokeWeight(61);
    rect(0, 0, 1280, 720); // window frame
    pushMatrix();
    translate(377, 0);
    fill(0);
    noStroke();
    rect(0, 0, 25, height); // window border left
    popMatrix();
    pushMatrix();
    translate(847, 0);
    rect(0, 0, 25, height); // window border right
    popMatrix();
    image(nickFury, furyPos.x, furyPos.y);
    skip();
    furyPos.add(furyVel);

    if (furyPos.x >= 200) 
    {
      furyVel.x = 0;
      pushMatrix();
      translate(995, 198);
      fill(255);
      ellipse(0, 0, 381, 238);
      ellipse(-143, 142, 63, 64);
      fill(0);
      textFont(subFont);
      textSize(24);
      text("Tony, there is an influx of Loki's\n alien things on the \nEast side! Get over there and \ntake them out!", -145, -40);
      popMatrix();
    }
  }

  void bossScene() 
  {
    surface.setTitle("IRON MAN: THE GAME");
    background(explosion);
    image(captain, capPos.x, capPos.y);
    capPos.add(capVel);
    rectMode(CORNER);
    if (capPos.y <= 265) 
    {
      capVel.y = 0;
      pushMatrix();
      translate(348, 372);
      fill(255);
      ellipse(0, 0, 381, 238);
      ellipse(199, 106, 67, 65);
      fill(0);
      textFont(subFont);
      textSize(24);
      text("Tony, Loki's going crazy with the \nTesseract, you've got to stop him!", -158, -3);
      popMatrix();
    }
    noStroke();
    skipBoss();
    bossTimer--;

    if (bossTimer <= 0) 
    {
      screen = BOSS_GAME;
    }
  }

  void skip() 
  {
    if (mouseX > 50 && mouseX < 214 && mouseY > 50 && mouseY < 100 && mouseButton == LEFT && mousePressed) screen = GAME;


    pushMatrix();
    fill(miniMenu.buttonMain);
    translate(50, 50);
    rect(0, 0, 164, 51);
    fill(miniMenu.buttonRibbon);
    translate(0, 0);
    rect(0, 0, 6, 51);
    popMatrix();
    textFont(mainFont);
    textSize(41);
    fill(0);
    text("SKIP", 106, 89);
  }

  void skipBoss() 
  {
    if (mouseX > 50 && mouseX < 214 && mouseY > 50 && mouseY < 100 && mouseButton == LEFT && mousePressed) screen = BOSS_GAME;


    pushMatrix();
    fill(miniMenu.buttonMain);
    translate(50, 50);
    rect(0, 0, 164, 51);
    fill(miniMenu.buttonRibbon);
    translate(0, 0);
    rect(0, 0, 6, 51);
    popMatrix();
    textFont(mainFont);
    textSize(41);
    fill(0);
    text("SKIP", 106, 89);
  }

  void achievement() 
  {
    if (achievementState == true) 
    {
      background(sky);
      rectMode(CORNER);
      // Dark Coloured Building:
      pushMatrix();
      fill(darkBuilding);
      noStroke();
      translate(0, 156);
      rect(0, 0, 520, 564);
      popMatrix();

      // Medium Coloured Building:
      fill(mediumBuilding);
      noStroke();
      pushMatrix();
      translate(0, 68);
      rect(0, 0, 305, 655);
      popMatrix();

      // Light Coloured Building:
      pushMatrix();
      translate(106, 413);
      fill(lightBuilding);
      noStroke();
      rect(0, 0, 950, 311);
      popMatrix();

      // Sun:
      pushMatrix();
      translate(1268, 0);
      fill(sun);
      noStroke();
      ellipse(0, 0, 545, 544);
      popMatrix();

      // Window:
      noFill();
      stroke(0);
      strokeWeight(61);
      rect(0, 0, 1280, 720); // window frame
      pushMatrix();
      translate(377, 0);
      fill(0);
      noStroke();
      rect(0, 0, 25, height); // window border left
      popMatrix();
      pushMatrix();
      translate(847, 0);
      rect(0, 0, 25, height); // window border right
      popMatrix();
      image(nickFury, fury2Pos.x, fury2Pos.y);
      fury2Pos.add(fury2Vel);

      if (fury2Pos.x >= 200) {
        fury2Vel.x = 0;
        achieveTimer--;
        pushMatrix();
        translate(995, 198);
        fill(255);
        ellipse(0, 0, 381, 238);
        ellipse(-143, 142, 63, 64);
        fill(0);
        textFont(subFont);
        textSize(24);
        text("Great Work Tony, Keep it Up!", -145, -40);
        popMatrix();
      }

      if (achieveTimer <= 0) 
      {
        achievementState = false;
        screen = GAME;
        deathCount = 10;
      }
      rectMode(CENTER);
    }
  }
} // class end