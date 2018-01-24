class SideButtons
{

  // FIELDS:
  color buttonMain = color(232);
  color buttonRibbon = color(231, 0, 0);
  color buttonText = color(57);
  PVector buttonPos = new PVector(0, 0);


  void render()
  {
    pushMatrix();
    translate(buttonPos.x, buttonPos.y);
    noStroke();
    fill(buttonMain);
    rect(-76, 325, 164, 51); // main menu button
    rect(-76, 380, 164, 51); // main menu button
    rect(-76, 435, 164, 51); // main menu button
    rect(-76, 490, 164, 51); // main menu button

    fill(buttonRibbon);
    rect(3, 325, 6, 51); // main menu button ribbon
    rect(3, 380, 6, 51); // pause button ribbon
    rect(3, 435, 6, 51); // restart button ribbon
    rect(3, 490, 6, 51); // instructions button ribbon

    textFont(mainFont);
    fill(buttonText);
    text("MAIN MENU", -140, 340);
    text("PAUSE", -125, 395);
    text("RESTART", -132, 450);
    textSize(33);
    text("INSTRUCTIONS", -145, 503);
    popMatrix();
  }

  void move()
  {
    if (mouseX > 0 && mouseX <= 135 && mouseY >= 300 && mouseY <= 516)
    {
      buttonPos.x += 10;
    } else {
      buttonPos.x = 0;
    }

    if (buttonPos.x >= 158) {
      buttonPos.x = 158;
    }
  }

  void buttonAction()
  {
    // SIDE MENU BUTTONS CLICK ACTION:
    if (mouseX > 0 && mouseX < 135 && mouseY >= 300)
    {
      if (mousePressed)
      {
        if (mouseY >= 300 && mouseY < 352)
        {
          screen = INTRO;
        }

        if (mouseY > 355 && mouseY <= 406)
        {
          gameScreen.pauseGame();
        }

        if (mouseY >= 409 && mouseY < 461)
        {
          //setup(); // fix
          gameScreen.restartGame();
        }

        if (mouseY >= 465 && mouseY <= 516)
        {
          screen = MAIN_INSTRUCTIONS;
        }
      }
    }
  }


  void update()
  {
    render();
    move();
    buttonAction();
  }
} // class end