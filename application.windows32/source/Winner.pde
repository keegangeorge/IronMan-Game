class Winner extends CutScene {
  String capText;

  Winner() {
    furyPos = new PVector(-662, 247);
    furyVel = new PVector(5, 0);
    capPos = new PVector(1262, 230);
    capVel = new PVector(-5, 0);
    capText = "Great Work Tony, we did it!";
  }


  void render() {
    surface.setTitle("IRON MAN: THE GAME > GAME COMPLETE");

    background(sky);
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
    image(captain, capPos.x, capPos.y);

    capPos.add(capVel);


    if (capPos.x <= 800) {
      capVel.x = 0;
      pushMatrix();
      translate(1010, 115);
      fill(255);
      ellipse(0, 0, 428, 188);
      ellipse(59, 136, 60, 43);
      fill(0);
      textFont(subFont);
      textSize(24);
      text(capText, -162, -3);
      popMatrix();
      furyPos.add(furyVel);
    }

    if (furyPos.x >= 30) {
      furyVel.x = 0;
      pushMatrix();
      translate(216, 160);
      fill(255);
      ellipse(0, 0, 315, 222);
      ellipse(104, 136, 68, 60);
      fill(0);
      textFont(subFont);
      textSize(24);
      text("Well, it looks like you didn't \nscrew things up this time!", -134, -3);
      popMatrix();
      capText = "Now, Let's go get some schwarma!";
    }
  }
} // class end