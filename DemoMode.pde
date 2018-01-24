class DemoMode 
{
  //SCREEN-CODE(5)
  int tutScreen = 1;
  final int TUT1 = 1;
  final int TUT2 = 2;
  final int TUT3 = 3;
  boolean displayCircle = true;
  float timer = 5;
  float timerOther = 5;

  void render() 
  {
    surface.setTitle("IRON MAN: THE GAME > TUTORIAL MODE");
    background(100);
    if (tutScreen == TUT2) {
      timer--;
    }

    if (tutScreen == TUT3) {
      timerOther--;
    }

    if (keyPressed) {
      displayCircle = false;
    }

    if (tutScreen == TUT1 || tutScreen == TUT2 || tutScreen == TUT3) 
    {
      pushMatrix();
      fill(miniMenu.buttonMain);
      translate(770, 650);
      translate(170, 0);
      rect(0, 0, 150, 50); // next button
      translate(170, 0);
      rect(0, 0, 150, 50); // skip all button
      popMatrix();
      textFont(mainFont);
      textSize(41);
      fill(255, 0, 0);
      text("Tutorial", 50, 50);
      textFont(subFont);
      textSize(24);
      fill(255);
      text("Get a feel for the controls", 50, 80);
      textFont(mainFont);
      textSize(33);
      fill(10);
      text("NEXT", 917, 663);
      text("SKIP TUTORIAL", 1042, 663);
    }

    if (tutScreen == TUT2 || tutScreen == TUT3) 
    {
      pushMatrix();
      translate(770, 650);
      fill(miniMenu.buttonMain);
      rect(0, 0, 150, 50); // back button
      popMatrix();
      textFont(mainFont);
      textSize(33);
      fill(10);
      text("PREVIOUS", 720, 663);
    }

    if (tutScreen == TUT1) 
    {
      fill(255);
      textFont(subFont);
      textSize(24);
      text("Move the mouse up\n and down to move\n   Iron Man's arm.", 800, 480);
      fill(10, 50);
      ellipse(700, 500, 100, 100);
      demoStateFire = false;
      demoStateMove = false;
      demoMan.pos.x = 500;
      demoMan.pos.y = height / 2 - 50;
    }

    if (tutScreen == TUT2) 
    {
      fill(255);
      textFont(subFont);
      textSize(24);
      text("Left-click to \nlaunch thruster.", 350, 480);
      fill(10, 50);
      ellipse(700, 500, 50, 50);
      demoStateFire = true;
      demoStateMove = false;
      demoMan.pos.x = 500;
      demoMan.pos.y = height / 2 - 50;
    }

    if (tutScreen == TUT3) 
    {
      fill(255);
      textFont(subFont);
      textSize(24);
      text("Use the Arrow Keys or \nW-A-S-D keys to fly around.", 800, 480);
      fill(10, 50);
      if (displayCircle == true) 
      {
        ellipse(610, 400, 400, 400);
      } 
      demoStateFire = true;
      demoStateMove = true;
    }
    demoMan.update();
    println(timer);



    if (mousePressed && tutScreen == TUT1 || mousePressed && tutScreen == TUT2 || mousePressed && tutScreen == TUT3) 
    {
      if (mouseY > 624 && mouseY < 676 && mouseX > 1034 && mouseX < 1185) // skip button
      {
        screen = INTRO_CUT_SCENE;
      }
    }
  }


  void mouseClicked() 
  {

    // NEXT BUTTONS:
    if (mouseY > 624 && mouseY < 676 && mouseX > 865 && mouseX < 1015 && tutScreen == TUT1) {
      tutScreen = TUT2;
    }

    if (mouseY > 624 && mouseY < 676 && mouseX > 865 && mouseX < 1015 && tutScreen == TUT2 && tutScreen != TUT1 && timer < 0) {
      tutScreen = TUT3;
    }

    if (mouseY > 624 && mouseY < 676 && mouseX > 865 && mouseX < 1015 && tutScreen == TUT3 && tutScreen != TUT1 && timerOther < 0) {
      screen = INTRO_CUT_SCENE;
    }


    // PREVIOUS BUTTONS:
    if (mouseY > 624 && mouseY < 676 && mouseX > 695 && mouseX < 845 && tutScreen == TUT2) {
      tutScreen = TUT1;
      timer = 5;
      timerOther = 5;
    }

    if (mouseY > 624 && mouseY < 676 && mouseX > 695 && mouseX < 845 && tutScreen == TUT3) {
      tutScreen = TUT2;
      timer = 5;
      timerOther = 5;
    }
  }
} // class end