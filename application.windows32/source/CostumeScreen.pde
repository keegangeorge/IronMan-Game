class CostumeScreen 
{ // SCREEN-CODE(3)


  // METHODS:
  void render() {
    surface.setTitle("IRON MAN: THE GAME > SUIT SELECTION");
    background(255);
    image(costumeBackground, 0, 0);

    // COSTUME 1 (MARK II):
    if  (mouseX >= 0 && mouseX <= 444 && mouseY >= 72) 
    {
      image(mark2Select, 0, 0);
      if (mousePressed) 
      {
        strokeColor = color(50);
        mainColor = color(211);
        lightColor = color(230);
        accentColor = color(100);
        pushMatrix();
        scale(0.1);
        translate(2000, 5800);
        image(checkmark, 0, 0);
        popMatrix();
      }

      // COSTUME 2 (MARK III):
    } else if (mouseX > 444 && mouseX <= 835) 
    {
      image(mark3Select, 0, 0);
      if (mousePressed) 
      {
        strokeColor = color(125, 3, 10);
        mainColor = color(222, 30, 41);
        lightColor = color(255);
        accentColor = color(246, 186, 54);
        pushMatrix();
        scale(0.1);
        translate(5800, 5800);
        image(checkmark, 0, 0);
        popMatrix();
      }

      // COSTUME 3 (WAR MACHINE):
    } else if (mouseX > 835) 
    {
      image(warSelect, 0, 0);
      if (mousePressed) 
      {
        strokeColor = color(50);
        mainColor = color(46, 103, 255);
        lightColor = color(214, 247, 255);
        accentColor = color(227);
        pushMatrix();
        scale(0.1);
        translate(9750, 5800);
        image(checkmark, 0, 0);
        popMatrix();
      }
    }

    // BACK BUTTON:
    pushMatrix();
    translate(0, 20);
    image(backButton, 0, 0); // draws back button image
    popMatrix();

    // HANDLES CLICK ACTION OF BUTTON:
    if (mouseX >= 0 && mouseX <= 165 && mouseY >=19 && mouseY <= 72 && mousePressed) 
    {
      screen = INTRO;
    }
  }
}// class end