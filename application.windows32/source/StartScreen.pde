class StartScreen
{
  // SCREEN-CODE(0)

  // FIELDS:
  color buttonCostumeColor = color(255);
  color buttonPlayColor = color(255);
  color buttonInstructionsColor = color(255);

  // METHODS:
  void render()
  {
    surface.setTitle("IRON MAN: THE GAME > MAIN MENU");
    background(startBackground);
    tint(buttonCostumeColor);
    image(startButtonCostume, 0, 0);
    tint(buttonPlayColor);
    image(startButtonPlay, 0, 0);
    tint(buttonInstructionsColor);
    image(startButtonInstructions, 0, 0);

    // HANDLES MOUSE CLICK ACTIONS OF BUTTONS:
    if (mouseX >= 97 && mouseX <= 413 && mouseY >= 404 && mouseY <= 683)
    {
      buttonCostumeColor = color(255, 0, 0);
      if (mousePressed)
      {
        screen = COSTUME;
      }
    } else
    {
      buttonCostumeColor = color(255);
    }

    if (mouseX >= 480 && mouseX <= 796 && mouseY >= 404 && mouseY <= 683)
    {
      buttonPlayColor = color(255, 0, 0);
      if (mousePressed)
      {
        screen = DEMO;
      }
    } else
    {
      buttonPlayColor = color(255);
    }

    if (mouseX >= 862 && mouseX <= 1177 && mouseY >= 404 && mouseY <= 683)
    {
      buttonInstructionsColor = color(255, 0, 0);
      if (mousePressed)
      {
        screen = MAIN_INSTRUCTIONS;
      }
    } else
    {
      buttonInstructionsColor = color(255);
    }
  }
} // class end