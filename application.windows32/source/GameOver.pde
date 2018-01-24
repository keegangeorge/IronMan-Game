class GameOver 
{
  //SCREEN-CODE(7)

  void render() 
  {
    surface.setTitle("IRON MAN: THE GAME > GAMEOVER");
    background(0);
    fill(255);
    textFont(mainFont);
    textSize(41);
    text("GAME OVER!", width / 2 - 64, height / 2);
    textFont(hudFont);
    textSize(24);
    text("Press the spacebar to restart", width / 2 - 172, height / 2 - -72);

    if (screen == GAME_OVER && keyPressed && key == ' ') {
      gameScreen.restartGame();
    }
  }
} // class end