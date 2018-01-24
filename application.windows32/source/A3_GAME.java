import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class A3_GAME extends PApplet {

// ARRAY LISTS:
ArrayList <BasicEnemy> alien = new ArrayList <BasicEnemy>();
ArrayList <Perks> healthBump = new ArrayList <Perks>();
ArrayList <Perks> repulsorCharge = new ArrayList <Perks>();

// FONTS:
PFont mainFont, subFont, hudFont;

// IMAGES:
PImage startBackground, costumeBackground, instructionBackground, bg1, bg2, objective; // screen background images
PImage startButtonCostume, startButtonPlay, startButtonInstructions; // main menu button images
PImage mark2Select, mark3Select, warSelect; // costume images
PImage loader, checkmark, thruster, repulsorIcon, deadAlien; // small sized images
PImage backButton; // button images
PImage basicEnemy, bossEnemy; // enemy images
PImage nickFury, captain, explosion; // cut scene images
PImage icon;

// FLOATS:
float numAliens = 2;
float numRepulsorPerk = 2;
float numHealthPerk = 3;
float bulletCount = 5;
float deathCount = 0;
float hudDead = 0;
float bossDeath = 240;
float achieveTimer = 60;


// BOOLEANS:
boolean gameState = true;
boolean left, right, up, down; // key coding
boolean demoStateFire = false;
boolean demoStateMove = false;
boolean removeBeam = false;
boolean bossDeathTimerStart = false;
boolean achievementState = false;
boolean rechargeText = false;

// COSTUME COLORS:
int strokeColor = color(125, 3, 10);
int mainColor = color(222, 30, 41);
int lightColor = color(255);
int accentColor = color(246, 186, 54);

// GAME SCREENS:
final int SPLASH = -1;
final int INTRO = 0;
final int INSTRUCTION = 1;
final int GAME = 2;
final int COSTUME = 3;
final int COSTUME_SELECTED = 4;
final int DEMO = 5;
final int INTRO_CUT_SCENE = 6;
final int GAME_OVER = 7;
final int BOSS_CUT_SCENE = 8;
final int BOSS_GAME = 9;
final int WINNER = 10;
final int ACHIEVEMENT = 11;
final int MAIN_INSTRUCTIONS = 12;
final int OBJECTIVE = 13;
int screen = 0; // sets first screen to appear

// CLASS INITIALIZATIONS:
SplashScreen loading;
StartScreen introScreen;
GameMode gameScreen;
GameOver gameFail;
InstructionScreen instructions;
CostumeScreen selectCostume;
SideButtons miniMenu;
Projectile repulsor;
Player ironman;
Player demoMan;
DemoMode demoScreen;
CutScene scene;
HeadsUpDisplay hud;
BossEnemy loki;
Winner win;


// SETUP CALLBACK METHOD:
public void setup()
{
   // screen size
  

  // LOADS BIG SIZED IMAGES (Request Image):
  startBackground = requestImage("startScreen.jpg");
  startButtonCostume = requestImage("button1.png");
  startButtonPlay = requestImage("button2.png");
  startButtonInstructions = requestImage("button3.png");
  costumeBackground = requestImage("costumeBackground.jpg");
  mark2Select = requestImage("mark2.jpg");
  mark3Select = requestImage("mark3.jpg");
  warSelect = requestImage("warmachine.jpg");
  basicEnemy = requestImage("easyvillain.png");
  bossEnemy = requestImage("loki.png");
  nickFury = requestImage("fury.png");
  captain = requestImage("Captain.png");
  explosion = requestImage("explosion.png");
  instructionBackground = requestImage("instructionScreen.png");
  objective = requestImage("objective.png");
  bg1 = requestImage("bg1.png");// screen = GAME background // image retrieved from: https://s-media-cache-ak0.pinimg.com/originals/46/4f/b3/464fb34c03ae4cdddad335b4aff2a26b.jpg
  bg2 = requestImage("bg2.png"); // screen = BOSS_GAME background // image retrieved from: https://s-media-cache-ak0.pinimg.com/originals/46/4f/b3/464fb34c03ae4cdddad335b4aff2a26b.jpg

  // LOADS SMALL SIZED IMAGES (Load Image):
  loader = loadImage("ironFace.png");
  backButton = loadImage("backButton.png");
  checkmark = loadImage("selected.png");
  thruster = loadImage("thrusters.png");
  repulsorIcon = loadImage("repulsorIcon.png");
  deadAlien = loadImage("deadAlien.png");
  icon = loadImage("icon.png");

  // LOAD FONTS:
  mainFont = loadFont("MAINFONT.vlw");
  subFont = loadFont("subFont.vlw");
  hudFont = loadFont("Consolas-48.vlw");
  // CLASS CONSTRUCTORS:
  loading = new SplashScreen();
  introScreen = new StartScreen();
  gameScreen = new GameMode();
  gameFail = new GameOver();
  instructions = new InstructionScreen();
  selectCostume = new CostumeScreen();
  miniMenu = new SideButtons();
  ironman = new Player(new PVector(100, 270));
  demoMan = new Player(new PVector(500, height / 2 - 50));
  demoScreen = new DemoMode();
  scene = new CutScene();
  hud = new HeadsUpDisplay();
  loki = new BossEnemy(new PVector(width, 350));
  win = new Winner();

  for (int i = 0; i < numRepulsorPerk; i++)
  { //                                        Pos: x,  y                                         Vel: x, y
    repulsorCharge.add(new Perks(new PVector(random(300, 1000), random(-300, -500)), new PVector(0, random(1, 2))));
  }

  for (int i = 0; i < numHealthPerk; i++)
  { //                                        Pos: x,  y           Vel: x, y
    healthBump.add(new Perks(new PVector(random(100, width), -500), new PVector(0, random(1, 2))));
  }


  for (int i = 0; i < numAliens; i++)
  {
    alien.add(new BasicEnemy(new PVector(random(width, width + 1000), random(500))));
  }
}

// DRAW CALL BACK METHOD:
public void draw()
{
  surface.setIcon(icon);
  //println("BOSS DEATH: " + bossDeath);
  if (bossDeathTimerStart == true) {
    bossDeath--;
  }

  if (bossDeath <= 0) {
    screen = WINNER;
    loki.tintColor = color(255, 0, 0);
  }

  if (hud.bulletBar == 200) {
    rechargeText = false;
  }

  //println("FPS: " + frameRate);
  // SCREEN SETTINGS:
  if (millis() < 2000) // loader screens
  {
    loading.render();
  } else {
    if (screen == INTRO) // main menu screen
    {
      introScreen.render();
    } else if (screen == MAIN_INSTRUCTIONS) // game instructions screen
    {
      instructions.mainRender();
    } else if (screen == OBJECTIVE)
    {
      instructions.objectiveRender();
    } else if (screen == INSTRUCTION)
    {
      instructions.instructionRender();
    } else if (screen == GAME) // game-play screen
    {
      gameScreen.basicRender();
      miniMenu.update();
    } else if (screen == COSTUME) // costume selection screen
    {
      selectCostume.render();
    } else if (screen == DEMO) {
      demoScreen.render();
      miniMenu.update();
    } else if (screen == INTRO_CUT_SCENE) {
      scene.introCutScene();
    } else if (screen == BOSS_CUT_SCENE) {
      scene.bossScene();
    } else if (screen == GAME_OVER) {
      gameFail.render();
    } else if (screen == BOSS_GAME) {
      gameScreen.bossRender();
      miniMenu.update();
    } else if (screen == WINNER) {
      rectMode(CORNER);
      win.render();
      rectMode(CENTER);
      miniMenu.update();
    } else if (screen == ACHIEVEMENT) {
      scene.achievement();
    }
  }
}

// INTERACTIVITY CALL BACK METHODS:
public void keyPressed()
{
  // FOR MOVEMENT OF CHARACTER:
  if (key == CODED && keyCode == RIGHT || key == 'd' || key == 'D') right = true;
  if (key == CODED && keyCode == LEFT  || key == 'a' || key == 'A') left = true;
  if (key == CODED && keyCode == UP    || key == 'w' || key == 'W') up = true;
  if (key == CODED && keyCode == DOWN  || key == 's' || key == 'S') down = true;
}

public void keyReleased()
{
  // FOR MOVEMENT OF CHARACTER:
  if (key == CODED && keyCode == RIGHT || key == 'd' || key == 'D') right = false;
  if (key == CODED && keyCode == LEFT  || key == 'a' || key == 'A') left = false;
  if (key == CODED && keyCode == UP    || key == 'w' || key == 'W') up = false;
  if (key == CODED && keyCode == DOWN  || key == 's' || key == 'S') down = false;

  // RETURNS TO GAME AFTER PAUSE:
  if (key == 'r')
  {
    loop();
    gameState = true;
  }
}

public void mousePressed()
{

  // FOR FIRING OF PROJECTILE:
  if (mouseButton == LEFT && screen == GAME || mouseButton == LEFT && screen == BOSS_GAME)
  {
    if (bulletCount > 0)
    {
      ironman.fire();
    }
    bulletCount--;
    if (bulletCount < 5) {
      if (hud.bulletBar > 10)
      {
        hud.bulletBar -= 40;
      }
    }
  }
  if (demoStateFire == true)
  {
    demoMan.fire();
  }
}

public void mouseClicked()
{
  demoScreen.mouseClicked();
}
class BasicEnemy extends MainCharacter 
{

  // FIELDS:
  PVector size; 
  PVector reAlignImage;
  float rockingShip, rockVelocity;
  float rotate;
  float deathTimer;


  // CONSTRUCTOR:
  BasicEnemy(PVector pos) 
  {
    super(pos);
    vel = new PVector(3, 0);
    size = new PVector(410, 358);
    reAlignImage = new PVector(-205, -180); // re-aligns image to draw inside hit-box
    deathTimer = 60;
  }

  // METHODS:
  public void drawCharacter() 
  {
    image(basicEnemy, pos.x + reAlignImage.x, pos.y + reAlignImage.y);
  }

  public void update() 
  {
    drawCharacter();
    moveCharacter();
    checkWalls();

    // Checks collision with player
    if ((abs(pos.x - (ironman.pos.x + 127)) < size.x / 2 + ironman.size.x / 2 && abs(pos.y - (ironman.pos.y + 138)) < size.y / 2 + ironman.size.y / 2)) 
    {
      ironman.decreaseHealth(1);
    }
  }

  public void moveCharacter() 
  {
    pos.sub(vel);
    if (health <= -3) deathTimer--;
  }

  public void respawn() 
  {
    alien.add(new BasicEnemy(new PVector(random(width, width + 1000), random(500))));
  }

  public void checkWalls() 
  {
    if (pos.x < 0) {
      pos.x = random(width, width + 1000);
      killed();
      respawn();
    }
  }

  public void decreaseHealth(int damage) 
  {
    super.decreaseHealth(damage);
  }

  public void killed() 
  {
    alien.remove(this); // alien - global variable of ArrayList
  }
} // class end
class BossEnemy extends BasicEnemy 
{

  // FIELDS:
  ArrayList <EnemyProjectile> beam = new ArrayList<EnemyProjectile>();
  boolean newWall = false;
  float launchTimer;
  int tintColor = color(255);

  // CONSTRUCTOR:
  BossEnemy(PVector pos) 
  {
    super(pos);
    vel = new PVector(5, 0);
    size = new PVector(305, 330);
    reAlignImage = new PVector(-153, -164); // re-aligns image to draw inside hit-box
    launchTimer = random(10, 50);
  }


  // METHODS:
  public void drawCharacter() 
  {
    tint(tintColor);
    image(bossEnemy, pos.x + reAlignImage.x, pos.y + reAlignImage.y);
  }

  public void update() 
  {
    drawCharacter();
    moveCharacter();
    checkWalls();
    checkProjectiles();
    launchTimer--;

    // Check collision with player:
    if ((abs(pos.x - (ironman.pos.x + 127)) < size.x / 2 + ironman.size.x / 2 && abs(pos.y - (ironman.pos.y + 138)) < size.y / 2 + ironman.size.y / 2)) 
    {
      ironman.decreaseHealth(1);
    }

    if (launchTimer <= 0) 
    {
      fire(); 
      launchTimer = random(10, 50);
    }
  }

  public void fire() 
  {
    pushMatrix();
    beam.add(new EnemyProjectile(new PVector(pos.x - 184, pos.y - 62), new PVector(-20, 0))); // adds bullet
    popMatrix();
  }

  public void checkProjectiles() 
  {
    for (int i = 0; i < beam.size(); i++) 
    {
      EnemyProjectile t = beam.get(i); 
      t.update(); 
      t.drawProjectile();
      t.hit(ironman);
      if (!t.isAlive) beam.remove(i);
    }
  }


  public void moveCharacter() 
  {
    pos.sub(vel);
  }

  public void decreaseHealth(int damage) 
  {
    super.decreaseHealth(damage);
  }

  public void checkWalls() 
  {
    if (pos.x <= 680) 
    {
      vel.x *= -1;
      vel.y = -1;
      newWall = true;
    }

    if (pos.y <= 80)
    {
      vel.y *= -1;
    }

    if (pos.y >= height - 100) 
    {
      vel.y *= -1;
    }

    if (newWall == true && pos.x >= width - 100) 
    {
      vel.x *= -1;
      vel.y *= random(3, -35);
    }
  }
} // class end
class CostumeScreen 
{ // SCREEN-CODE(3)


  // METHODS:
  public void render() {
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
        scale(0.1f);
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
        scale(0.1f);
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
        scale(0.1f);
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
class CutScene {
  int sky = color(52, 184, 214);
  int mediumBuilding = color(84);
  int lightBuilding = color(125);
  int darkBuilding = color(43);
  int sun = color(255, 222, 0);
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


  public void introCutScene() 
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

  public void bossScene() 
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

  public void skip() 
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

  public void skipBoss() 
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

  public void achievement() 
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

  public void render() 
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


  public void mouseClicked() 
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
class EnemyProjectile 
{

  // FIELDS:
  PVector pos, vel, dim;
  boolean isAlive;


  // CONSTRUCTOR:
  EnemyProjectile(PVector pos, PVector vel) 
  {
    this.pos = pos;
    this.vel = vel;
    isAlive = true;
    dim = new PVector(10, 5);
  }

  public void update() 
  {
    drawProjectile();
    move();
    checkWalls();
  }

  // METHODS:
  public void drawProjectile() 
  {
    if (removeBeam == false) 
    {
      pushMatrix();
      fill(5, 249, 255);
      translate(pos.x, pos.y);
      rect(0, 0, 80, dim.x, dim.y);
      popMatrix();
    }
  }

  public void move() 
  {
    if (removeBeam == false) 
    {
      pos.add(vel);
    }
  }

  public void checkWalls() 
  {
    if (pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height) 
    {
      isAlive = false;
    }
  }

  public void hit(Player ironman) 
  {

    if ((abs(pos.x - (ironman.pos.x + 127)) < dim.x / 2 + ironman.size.x / 2 && abs(pos.y - (ironman.pos.y + 138)) < dim.y / 2 + ironman.size.y / 2))
    {
      ironman.decreaseHealth(50);
      isAlive = false;
      //if (enemy.health <= -3) 
      //{
      //  enemy.vel.add(-5, -5);
      //}
    }
  }
} // class end
class GameMode
{
  // SCREEN-CODE(2)

  // FIELDS:

  public void basicRender() {
    surface.setTitle("IRON MAN: THE GAME > GAMEPLAY");
    background(255);
    image(bg1, 0, 0);
    ironman.update();
    hud.render();



    for (int i = 0; i < healthBump.size(); i++)
    {
      Perks moreHealth = healthBump.get(i);
      noStroke();
      moreHealth.healthUp();
      moreHealth.checkHealthCollision();
    }

    for (int i = 0; i < repulsorCharge.size(); i++)
    {
      Perks moreBullets = repulsorCharge.get(i);
      noStroke();
      moreBullets.repulsorIncrease();
      moreBullets.checkRepulsorCollision();
    }

    for (int i = 0; i < alien.size(); i++)
    {
      BasicEnemy enemy = alien.get(i);
      enemy.update();

      if (deathCount >= 10) {
        enemy.vel.x = 6;
        achievementState = false;
      }

      if (deathCount == 9) {
        screen = ACHIEVEMENT;
        achievementState = true;
      } else {
        achievementState = false;
      }
    }

    if (alien.size() <= 2) {
      alien.add(new BasicEnemy(new PVector(random(width, width + 1000), random(500))));
    }

    if (healthBump.size() <= 0) {
      healthBump.add(new Perks(new PVector(random(100, width), -1000), new PVector(0, random(0.9f, 2))));
    }

    if (repulsorCharge.size() <= 0)
    {
      repulsorCharge.add(new Perks(new PVector(random(300, 1000), random(-300, -500)), new PVector(0, random(1, 2))));
    }

    if (deathCount >= 20)
    {
      screen = BOSS_CUT_SCENE;
    }
  }

  public void bossRender()
  {
    background(255);
    image(bg2, 0, 0);
    surface.setTitle("GAME" + "X   " + mouseX + "    Y    " + mouseY);

    loki.update();
    ironman.update();
    hud.render();
    for (int i = 0; i < healthBump.size(); i++)
    {
      Perks moreHealth = healthBump.get(i);
      noStroke();
      moreHealth.healthUp();
      moreHealth.checkHealthCollision();
    }

    for (int i = 0; i < repulsorCharge.size(); i++)
    {
      Perks moreBullets = repulsorCharge.get(i);
      noStroke();
      moreBullets.repulsorIncrease();
      moreBullets.checkRepulsorCollision();
    }

    if (repulsorCharge.size() <= 0)
    {
      repulsorCharge.add(new Perks(new PVector(random(100, width), -500), new PVector(0, random(1, 2))));
    }

    if (healthBump.size() <= 0)
    {
      healthBump.add(new Perks(new PVector(random(100, width), -1000), new PVector(0, random(0.9f, 2))));
    }
  }

  public void pauseGame()
  {
    noLoop();
    gameState = false;
    pushMatrix();
    fill(0, 0, 0, 90);
    noStroke();
    rect(0, 0, 1280 * 2, 720 * 2);
    fill(246, 9, 81);
    translate(width / 2 - 100, height / 2);
    text(" GAME PAUSED ", 0, 0);
    textFont(hudFont);
    textSize(24);
    text("Press 'R' to resume", -50, 70);
    popMatrix();
  }

  public void restartGame() {
    screen = GAME;
    deathCount = 0;
    hudDead = 0;
    for (int i = 0; i < alien.size(); i++)
    {
      BasicEnemy enemy = alien.get(i);
      enemy.pos.x = -500;
    }
    ironman.health = 200;
    hud.bulletBar = 200;
    bulletCount = 5;

    for (int i = 0; i < healthBump.size(); i++)
    {
      Perks moreHealth = healthBump.get(i);
      moreHealth.pos.y = -100;
    }

    for (int i = 0; i < repulsorCharge.size(); i++)
    {
      Perks moreBullets = repulsorCharge.get(i);
      moreBullets.pos.y = height + 100;
    }
  }
} // class end
class GameOver 
{
  //SCREEN-CODE(7)

  public void render() 
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
class HeadsUpDisplay 
{


  // FIELDS:
  float bulletBar = 200;

  public void render() {
    fill(0, 0, 0, 180);
    rectMode(CORNER);
    noStroke();
    rect(0, 650, width, 120);
    rectMode(CENTER);

    // HEALTH BAR:
    rectMode(CORNER);
    noStroke();
    pushMatrix();
    translate(1054, 673);
    fill(0xff313030);
    rect(0, 0, 200, 25);
    fill(0xffFF4740);
    rect(0, 0, ironman.health, 25);
    popMatrix();

    // HEART ICON:
    pushMatrix();
    translate(978, 666);
    scale(0.8f);
    fill(0xffFF4740);
    beginShape();
    vertex(50, 15); 
    bezierVertex(50, -5, 90, 5, 50, 40); 
    vertex(50, 15); 
    bezierVertex(50, -5, 10, 5, 50, 40); 
    endShape();
    popMatrix();

    // REPULSOR BAR:
    pushMatrix();
    scale(0.2f);
    tint(255);
    image(repulsorIcon, 3457, 3332);
    popMatrix();
    rectMode(CORNER);
    noStroke();
    pushMatrix();
    translate(751, 673);
    fill(0xff313030);
    rect(0, 0, 200, 25);
    fill(214, 247, 255);
    rect(0, 0, bulletBar, 25);
    popMatrix();
    rectMode(CENTER);
    textFont(hudFont);
    textSize(24);
    fill(255);
    text(PApplet.parseInt(hudDead), 83, 693);
    pushMatrix();
    scale(0.4f);
    tint(255);
    image(deadAlien, 57, 1661);
    tint(255);
    popMatrix();

    if (rechargeText == true) {
      fill(random(0, 255));
      textFont(hudFont);
      textSize(12);
      text("Recharging", 870, 690);
    }
  }
} // class end
class InstructionScreen
{
  int obColor = color(255);
  int coColor = color(255);

  //SCREEN-CODE(1)
  public void mainRender() {
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

  public void instructionRender()
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

  public void objectiveRender() {
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
class MainCharacter 
{

  // FIELDS:
  PVector pos;
  PVector vel;
  float health;
  float wid, hght;
  float dampener = 0.8f;
  PVector size = new PVector(163, 234);

  // CONSTRUCTOR:
  MainCharacter(PVector pos) 
  {
    this.pos = pos;
    vel = new PVector();
  }

  // METHODS:
  public void moveCharacter() 
  {
    vel.mult(dampener); // multiplies velocity by dampening factor
    pos.add(vel);
  }

  public void accelerate(PVector acc) 
  {
    vel.add(acc);
  }

  public void drawCharacter() 
  {
    pushMatrix();
    fill(mainColor);
    translate(pos.x, pos.y);
    rect(0, 0, 155, 300);
    popMatrix();
  }

  // CHECKS COLLISION:
  public boolean hitCharacter(MainCharacter other) {
    if (abs(pos.x - other.pos.x) < size.x / 2 + other.size.x / 2 && abs(pos.y - other.pos.y) < size.y / 2 + other.size.y / 2) 
    {
      return true;
    }
    return false;
  }

  public void decreaseHealth(int damage) 
  {
    health -= damage;
  }

  public void checkWalls() 
  {
    if (pos.x > width)         pos.x = width - 400;
    if (pos.x < -200)          pos.x = 0;
    if (pos.y + 200 > height)  pos.y = 380;
    if (pos.y < 0)             pos.y = 0;
  }

  public void update() 
  {
    drawCharacter();
    moveCharacter();
    checkWalls();
  }
} // class end
class Perks 
{
  PVector pos;
  PVector vel;
  PVector dim;

  Perks(PVector pos, PVector vel) 
  {
    this.pos = pos;
    this.vel = vel;
    dim = new PVector(55, 55);
  }


  public void healthUp() 
  {
    pos.add(vel);

    pushMatrix();

    translate(pos.x, pos.y);

    pushMatrix();
    fill(145);
    ellipse(0, 0, dim.x, dim.y);
    popMatrix();

    noStroke();
    pushMatrix();
    translate(-50, -20);
    fill(0xffFF4740);
    beginShape();
    vertex(50, 15); 
    bezierVertex(50, -5, 90, 5, 50, 40); 
    vertex(50, 15); 
    bezierVertex(50, -5, 10, 5, 50, 40); 
    endShape();
    popMatrix();
    popMatrix();
    fill(0xffFF4740);
  }

  public void checkHealthCollision() 
  {

    if ((abs(pos.x - (ironman.pos.x + 127)) < dim.x / 2 + ironman.size.x && abs(pos.y - (ironman.pos.y + 138)) < dim.y / 2 + ironman.size.y / 2)) 
    {
      if (ironman.health < 180) 
      {
        ironman.health += 20;
      }
      healthBump.remove(this);

      if (pos.y > height) 
      {
        healthBump.remove(this);
      }
    }
  }



  public void repulsorIncrease() 
  {
    pos.add(vel);
    pushMatrix();
    translate(pos.x, pos.y);
    pushMatrix();
    scale(0.25f);
    image(repulsorIcon, 0, 0);
    popMatrix();
    popMatrix();
  }


  public void checkRepulsorCollision() 
  {

    if ((abs(pos.x - (ironman.pos.x + 127)) < dim.x / 2 + ironman.size.x && abs(pos.y - (ironman.pos.y + 138)) < dim.y / 2 + ironman.size.y / 2)) 
    {
      hud.bulletBar = 200;
      bulletCount = 10;
      repulsorCharge.remove(this);
    }

    if (pos.y > height) 
    {
      repulsorCharge.remove(this);
    }
  }
} // class end
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
  public void fire() 
  {
    pushMatrix();
    bullet.add(new Projectile(new PVector(armPosition.x + bulletpos.x, armPosition.y + bulletpos.y), new PVector(20, projectionAngle))); // adds bullet
    popMatrix();
  }

  public void decreaseHealth(int damage) {
    super.decreaseHealth(damage);
  }

  public void update() 
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

  public void checkProjectiles() 
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






  public void bulletAngle() 
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

  public void drawCharacter() 
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


  public void recharge() {
    hud.bulletBar += 0.5f;
    rechargeText = true;
    if (hud.bulletBar >= 195) 
    {
      hud.bulletBar = 200;
      bulletCount = 10;
    }
  }
} // class end
class Projectile 
{

  // FIELDS:
  PVector pos, vel;
  boolean isAlive;
  float bulletSize;

  // CONSTRUCTOR:
  Projectile(PVector pos, PVector vel) 
  {
    this.pos = pos;
    this.vel = vel;
    bulletSize = 20;
    isAlive = true;
  }

  public void update() 
  {
    drawProjectile();
    move();
    checkWalls();
  }

  // METHODS:
  public void drawProjectile() 
  {
    pushMatrix();
    fill(214, 247, 255);
    stroke(214, 247, 255);
    translate(pos.x, pos.y);
    ellipse(0, 0, bulletSize, bulletSize);
    popMatrix();
  }


  public void move() 
  {
    pos.add(vel);
  }

  public void checkWalls() 
  {
    if (pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height) 
    {
      isAlive = false;
    }
  }

  public void hit(BasicEnemy enemy) 
  {
    if (abs(pos.x - enemy.pos.x) < bulletSize / 2 + enemy.size.x / 2 && abs(pos.y - enemy.pos.y) < bulletSize / 2 + enemy.size.y / 2)
    {
      enemy.decreaseHealth(1);
      isAlive = false;
      if (enemy.health <= -3) 
      {
        enemy.vel.add(-5, -5);
      }
    }

    if (enemy.deathTimer <= 0) 
    {
      enemy.killed();
      deathCount++;
      hudDead += 1;
      enemy.deathTimer = 60;
    }

    if (abs(pos.x - loki.pos.x) < bulletSize / 2 + loki.size.x / 2 && abs(pos.y - loki.pos.y) < bulletSize / 2 + loki.size.y / 2 &&  screen == BOSS_GAME)
    {
      loki.decreaseHealth(1);
      isAlive = false;
    }

    if (loki.health <= -16 && screen == BOSS_GAME) 
    {
      bossDeathTimerStart = true;
      loki.tintColor = color(255, 0, 0);
      loki.vel.x = 0;
      loki.vel.y = -0.5f;
      removeBeam = true;
    }

    if (bossDeath <= 0) 
    {
      screen = WINNER;
      loki.tintColor = color(255, 0, 0);
    }
  }
} // class end
class SideButtons
{

  // FIELDS:
  int buttonMain = color(232);
  int buttonRibbon = color(231, 0, 0);
  int buttonText = color(57);
  PVector buttonPos = new PVector(0, 0);


  public void render()
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

  public void move()
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

  public void buttonAction()
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


  public void update()
  {
    render();
    move();
    buttonAction();
  }
} // class end
class SplashScreen // loading screen
{
  // SCREEN-CODE(-1)

  // FIELDS:
  float angle = 0;

  // METHODS:
  public void render() 
  {
    surface.setTitle("IRON MAN: THE GAME > LOADING");
    angle+= 2;
    background(230);
    pushMatrix();
    translate(width / 2, height / 2 - 50);
    rotate(radians(angle));
    scale(0.35f);
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
class StartScreen
{
  // SCREEN-CODE(0)

  // FIELDS:
  int buttonCostumeColor = color(255);
  int buttonPlayColor = color(255);
  int buttonInstructionsColor = color(255);

  // METHODS:
  public void render()
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
class Winner extends CutScene {
  String capText;

  Winner() {
    furyPos = new PVector(-662, 247);
    furyVel = new PVector(5, 0);
    capPos = new PVector(1262, 230);
    capVel = new PVector(-5, 0);
    capText = "Great Work Tony, we did it!";
  }


  public void render() {
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
  public void settings() {  size(1280, 720);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "A3_GAME" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
