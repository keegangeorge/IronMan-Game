class InstructionScreen
{
  color obColor = color(255);
  color coColor = color(255);

  //SCREEN-CODE(1)
  void mainRender() {
    rectMode(CORNER);
    surface.setTitle("IRON MAN: THE GAME > INSTRUCTIONS" + "mouseX: " + mouseX + "mouseY" + mouseY);
    background(235, 111, 101);

    // OBJECTIVE BUTTON
    noStroke();
    fill(obColor);
    rect(408, 251, 199, 57);
    fill(50);
    textFont(mainFont, (41));
    text("OBJECTIVE", 440, 293);

    // CONTROLS BUTTON
    fill(coColor);
    rect(731, 251, 199, 57);
    fill(50);
    textFont(mainFont, (41));
    text("CONTROLS", 770, 295);

    if (mouseY >= 255 && mouseY <= 312 && mouseX >= 409 && mouseX <= 609) {
      obColor = color(250, 232, 30);
      if (mousePressed) {
        screen = OBJECTIVE;
      }
    } else obColor = color(255);

    if (mouseY >= 255 && mouseY <= 312 && mouseX >= 734 && mouseX <= 930) {
      coColor = color(250, 232, 30);
      if (mousePressed) {
        screen = INSTRUCTION;
      }
    } else coColor = color(255);

    // BACK BUTTON:
    pushMatrix();
    translate(0, 20);
    tint(255);
    image(backButton, 0, 0);
    popMatrix();

    // HANDLES BACK BUTTON CLICK ACTION:
    if (mouseX >= 0 && mouseX <= 165 && mouseY >=19 && mouseY <= 72 && mousePressed)
    {
      screen = INTRO;
    }
    rectMode(CENTER);
  }

  void instructionRender()
  {
    rectMode(CORNER);
    surface.setTitle("IRON MAN: THE GAME > INSTRUCTIONS");
    background(255);
    image(instructionBackground, 0, 0);
    // BACK BUTTON:
    pushMatrix();
    translate(0, 20);
    tint(255);
    image(backButton, 0, 0);
    popMatrix();

    // HANDLES BACK BUTTON CLICK ACTION:
    if (mouseX >= 0 && mouseX <= 165 && mouseY >=19 && mouseY <= 72 && mousePressed)
    {
      screen = MAIN_INSTRUCTIONS;
    }
    rectMode(CENTER);
  }

  void objectiveRender() {
    rectMode(CORNER);
    surface.setTitle("IRON MAN: THE GAME > INSTRUCTIONS > OBJECTIVE");
    background(255);
    image(objective, 0, 0);
    // BACK BUTTON:
    pushMatrix();
    translate(0, 20);
    tint(255);
    image(backButton, 0, 0);
    popMatrix();

    if (mouseX >= 0 && mouseX <= 165 && mouseY >=19 && mouseY <= 72 && mousePressed)
    {
      screen = MAIN_INSTRUCTIONS;
    }
    rectMode(CENTER);
  }
} // class end