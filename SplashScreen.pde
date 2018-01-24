class SplashScreen // loading screen
{
  // SCREEN-CODE(-1)

  // FIELDS:
  float angle = 0;

  // METHODS:
  void render() 
  {
    surface.setTitle("IRON MAN: THE GAME > LOADING");
    angle+= 2;
    background(230);
    pushMatrix();
    translate(width / 2, height / 2 - 50);
    rotate(radians(angle));
    scale(0.35);
    image(loader, -250, -250);
    popMatrix();
    fill(0);
    text("LOADING...", width / 2 - 35, height / 2 + 100);
  }
} // class end


//void moveCharacter() {
//  if (keyPressed) {
//    if (key == 'd'|| key == 'D' || keyCode == RIGHT) {
//      pos.x = vel.x + 1;
//      shake = 0;

//    }

//    if (key == 'a' || key == 'A' || keyCode == LEFT) {
//      pos.x = vel.x - 1;

//      //x -= dx;
//      shake = 0;

//    }

//    if (key == 'w' || key == 'W' || keyCode == UP) {
//      //y -= dy;
//      pos.y = vel.y - 1;

//      shake = 0;

//      image(thruster, pos.x + 87, pos.y + 255);
//    }

//    if (key == 's' || key == 'S' || keyCode == DOWN) {
//      //y += dy;
//      pos.y = vel.y + 1;

//      

//    }



//  }
//}