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
color strokeColor = color(125, 3, 10);
color mainColor = color(222, 30, 41);
color lightColor = color(255);
color accentColor = color(246, 186, 54);

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
void setup()
{
  size(1280, 720); // screen size
  smooth();

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
void draw()
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
void keyPressed()
{
  // FOR MOVEMENT OF CHARACTER:
  if (key == CODED && keyCode == RIGHT || key == 'd' || key == 'D') right = true;
  if (key == CODED && keyCode == LEFT  || key == 'a' || key == 'A') left = true;
  if (key == CODED && keyCode == UP    || key == 'w' || key == 'W') up = true;
  if (key == CODED && keyCode == DOWN  || key == 's' || key == 'S') down = true;
}

void keyReleased()
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

void mousePressed()
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

void mouseClicked()
{
  demoScreen.mouseClicked();
}