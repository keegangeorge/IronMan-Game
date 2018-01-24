class HeadsUpDisplay 
{


  // FIELDS:
  float bulletBar = 200;

  void render() {
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
    fill(#313030);
    rect(0, 0, 200, 25);
    fill(#FF4740);
    rect(0, 0, ironman.health, 25);
    popMatrix();

    // HEART ICON:
    pushMatrix();
    translate(978, 666);
    scale(0.8);
    fill(#FF4740);
    beginShape();
    vertex(50, 15); 
    bezierVertex(50, -5, 90, 5, 50, 40); 
    vertex(50, 15); 
    bezierVertex(50, -5, 10, 5, 50, 40); 
    endShape();
    popMatrix();

    // REPULSOR BAR:
    pushMatrix();
    scale(0.2);
    tint(255);
    image(repulsorIcon, 3457, 3332);
    popMatrix();
    rectMode(CORNER);
    noStroke();
    pushMatrix();
    translate(751, 673);
    fill(#313030);
    rect(0, 0, 200, 25);
    fill(214, 247, 255);
    rect(0, 0, bulletBar, 25);
    popMatrix();
    rectMode(CENTER);
    textFont(hudFont);
    textSize(24);
    fill(255);
    text(int(hudDead), 83, 693);
    pushMatrix();
    scale(0.4);
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