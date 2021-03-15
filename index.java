 //Line Race
/*Nathan Rowbottom
Game for students to manipulate linear equations.
Match you line to a random line.
Times are tracked
Modified November 28th 2019 to go on Repl.it live as processing.js
Show visual representation of y-intercept
Added buttons for languages
Added language support


TODO
Sound
Adding different modes as progression
Show y intercept as point notation


ISSUES
frameSkip does nothing so remove it or implement it
increase difficulty???
change representation of grid.
*/

//global variables
//states example------------------------------------------------------
int state = 0;
int gameStart = 0;
int gameOn = 1;
int gameWin = 2;
int gameOver = 3;
int chooseGame = 4;
int numGames = 0;
//animated image example-----------------------------------------------
//pimage arrays to store the frames of animation
PImage [] banana;//for win state
PImage [] title;//for startscreen
PImage [] cactus;//for new best time
PImage [] game_over; //for gameover

String [] eng = new String[]{"LINE RACE", "Instructions", "Use arrows to move your flashing line to match the red line", "UP/DOWN Arrow Keys to change the y-intercept","LEFT/RIGHT Arrow Keys to change the slope", "You get 5 chances to get the best time.","Press e for English, m for Mohawk, c for Cayuga to continue","Target Line:","Your Line:","Time: ", "You Got The New Best Time", "Old Best Time ", "Your New Best Time ", "You Did it", "Turns Left ", "Best Time", "Your Time", "Your Times"};

String [] cay= new String[]{"CAYUGA TITLE", "InstructionsCay", "Use arrows to move your flashing line to match the red lineCay", "UP/DOWN Arrow Keys to change the y-interceptCay","LEFT/RIGHT Arrow Keys to change the slopeCay", "You get 5 chances to get the best time.Cay","Press e for English, m for Mohawk, c for Cayuga to continueCay","Target Line:Cay","Your Line:Cay","Time: Cay", "You Got The New Best TimeCay", "Old Best Time Cay", "Your New Best Time Cay", "You Did itCay", "Turns Left Cay", "Best TimeCay", "Your TimeCay", "Your Times"};

String [] moh = new String[]{"MOHAWK TITLE", "MohInstructions", "Moh2Use arrows to move your flashing line to match the red line", "Moh3UP/DOWN Arrow Keys to change the y-intercept","Moh4LEFT/RIGHT Arrow Keys to change the slope", "Moh5You get 5 chances to get the best time.","Moh6Press e for English, m for Mohawk, c for Cayuga to continue","Moh7Target Line:","Moh8Your Line:","Moh9Time: ", "Moh10You Got The New Best Time", "Moh11Old Best Time ", "12Your New Best Time ", "Moh13You Did it", "Moh14Turns Left ", "Moh15Best Time", "Moh16Your Time", "Moh17Your Times"}; 

String [] lang = eng;
int frames = 0;//int used to indicate which frame of banana we are on
int frameSkip = 2;

//drawgrid example-----------------------------------------------------
final float SLOPE_RESTRICTION = 5; 
final float SLOPE_INCREMENT = 0.5;
final float YINT_INCREMENT = 10;
//global variables

//global variables
long startTime = 0;
float currentTime = 0;
float [] times; 
float bestTime = 0;
int turnsLeft = 4;

float m =-1; //user slope
float b = 0;//user y int
float randM = 0;//the random target slope
float randB = 0;//the random target y int

PVector mohPos;
PVector engPos;
PVector cayPos;
PVector buttonSize;

boolean targetLineVisible = true;
boolean myLineVisible = true;
boolean yint = true;//to hold whether you can see the y int or not

void setup() {
  size(900, 600);
  frameRate(15);
  textAlign(CENTER);
  imageMode(CENTER);
  rectMode(CENTER);
  mohPos = new PVector(width*0.25, height*0.4);
  engPos = new PVector(width*0.5, height*0.4);
  cayPos = new PVector(width*0.75, height*0.4);
  buttonSize = new PVector(width*0.18, height*0.12);
  init();
  
}

void init() {

  state = gameStart;   

  banana = new PImage[8];
  title = new PImage[11];
  game_over = new PImage[10];
  cactus = new PImage[13];

  //for each image in the array
  for (int i = 0; i < banana.length; i++) {
    //load the images into to the array
    banana[i] = loadImage("/data/banana"+i+".png");
  }
  for (int i = 0; i < title.length; i++) {
    //load the images into to the array
    title[i] = loadImage("/data/title"+i+".png");
  }
  for (int i = 0; i < game_over.length; i++) {
    //load the images into to the array
    game_over[i] = loadImage("/data/game_over"+i+".png");
  }
  for (int i = 0; i < cactus.length; i++) {
    //load the images into to the array
    cactus[i] = loadImage("/data/cactus"+i+".gif");
  }
  numGames = 0
  //print ("leaving init");
    reset();
}

void reset() {
  turnsLeft = 5;
  times = new float[turnsLeft];
  startTime = millis();
  //print (startTime);
  getRandomValues();
  if (numGames == 0){
    targetLineVisible = true;
    myLineVisible = true;
    yint = true;
  }
  else if (numGames == 1){
    targetLineVisible =false;
    myLineVisible = true;
    yint = true;

  }
  else if (numGames == 2){
    targetLineVisible =true;
    myLineVisible = false;
    yint = true;
  }
  else {
    targetLineVisible = true;
    myLineVisible = true;
    yint = true;
  }
  numGames++;
}


void getRandomValues() {
  int max = (int)SLOPE_RESTRICTION; 
  int min = -(int)SLOPE_RESTRICTION;
  int range = max - min;
  float inc = SLOPE_INCREMENT;
  int states = 0;

  do {
    //loop until the slope is not near zero
    states = (int)(random(range / inc + 1));

    randM = states *inc - (int)(range/2);
  } while (abs(randM)< inc) ;

  //gets rid of the stupid floating point errors, credit to Emma
  randM *=10;
  randM = round(randM);
  randM /= 10;

  //get random b values
  max = height/2; 
  min = -height/2;
  range = max - min;
  inc = YINT_INCREMENT;
  states = floor(random(range / inc + 1));
  randB = states *inc- (int)(range/2);
  startTime = millis();
  //print("leaving random values");
}


void mouseClicked() {
  if (state == gameStart) {
    //if over instruction button
    //state = instructions
    //else if over stateGame
    //reset();
    startTime = millis();
    state = gameOn;
  } else if (state == gameWin) {
    turnsLeft --;
    if (turnsLeft < 1) {
      state = gameOver;
    } else {
      getRandomValues();
      state = gameOn;
    }
  } else if (state == gameOver) {
    reset();
    state = gameStart;
    yint = true;
  } else {
   // print("WTF "+state, 100, 100);
  }
}


//custom function
void drawText(String msg, int x, int y, int siz) {
  textSize(siz*0.5);

  fill(150, 100, 20);
  text(msg, x-1, y-1, siz);
  fill(250, 250, 250);
  text(msg, x, y, siz);
}

//custom function
void drawText(String msg, int x, int y, int siz, color col) {
  textSize(siz*0.5);

  fill(150);
  text(msg, x-1, y-1, siz);
  fill(col, 200);
  text(msg, x, y, siz);
 fill(150, 100, 20);
}

void drawCoolLine(float x1, float y1, float x2, float y2, color c) { 
  //want the frameCount to make the thickness grow and it to fade
  stroke(c, 127*sin(frameCount/1/PI-PI/2)+200);
  // println(""+(127*sin(frameCount/1/PI)+200));
  strokeWeight(2*sin(frameCount/1/PI)+4);
  line (x1, y1, x2, y2);
}

void checkKeys() {
  //print("checkKeys");
  //using if statements check to see if the keys are pressed
  if (keyCode == UP && keyPressed) {
    //dec y-intercept variable b (the line will go up) 
    b += YINT_INCREMENT;
  } else if (keyCode == DOWN && keyPressed) {
    //inc y-intercept variable b (the line will go down)
    b -= YINT_INCREMENT;
  }
  if (keyCode == LEFT && keyPressed) {
    //increase the slope of the line (rotation of line?) 
    m +=  SLOPE_INCREMENT;
  } else if (keyCode == RIGHT && keyPressed) {
    //decrease the slope of the line (rotation of line?)
    m -=  SLOPE_INCREMENT;
  } else if (key == 'e' && keyPressed) {
    
      lang = eng;
      //print("switching to eng");
  } else if (key == 'm' && keyPressed) {
      lang = moh;
      //print("switching to moh");
  } else if (key == 'c' && keyPressed) {
      lang = cay;
      //print("switching to cay");
  }
  
}

void checkBounds() {
  //check to see if |b| > 300
  //if so , set back to +/- 300
  if (b < -height/2) {
    b = -height/2;
  } else if (b > height/2) {
    b = height/2;
  }

  //check upper and lower bounds for m
  if (m < -SLOPE_RESTRICTION) {
    m = -SLOPE_RESTRICTION;
  } else if (m > SLOPE_RESTRICTION) {
    m = SLOPE_RESTRICTION;
  }
  //handles a slope that is too shallow
  if (m > -SLOPE_INCREMENT&&m<0) {
    m =  SLOPE_INCREMENT;
  } else if (m <  SLOPE_INCREMENT &&m>0) {
    m = - SLOPE_INCREMENT;
  }
}

void checkLines() {
  //if the slopes and yint atre the same do something
  if (b == randB && m == randM) {
    background(200, 200, 0); 
    state = gameWin;
    frames = 0;
    times[turnsLeft-1] = currentTime;
  }
}

void drawStats() {
  //between rounds draw a rectangle over the axis and place the stats on it
  //list the best time
  //list the present time
  //text("Turns remaining "+turnsLeft, 
  //list the turns left
}

void gg() {
  frames ++;
  if (frames >= game_over.length) {
    //set frames back to 0
    frames = 0;
  }
 
  textAlign(CENTER);
  image(game_over[frames], width/2, height/2, width, height);
  drawText("***"+lang[15]+"***", width/2, (int)(height*0.25f), 80);
  drawText(""+bestTime, width/2, (int)(height*0.35f), 50);
  drawText(lang[17], width/2, (int)(height*0.55f), 30);
  drawText("--------------------------------------------", width/2, (int)(height*0.60f), 30);
  for (int i = 0; i < times.length; i++) {
    drawText(""+(i+1)+": "+times[i], width/2, (int)(height*(0.65f+(i*0.05f))), 30);
  }
  
}

void drawAxis() { 
  //draw lines 600 long at width /2 and height /2
  //in a for loop increasing by 10 
  //draw horizontal ticks 10 apart
  //draw the horizontal labels 10 apart 
  //draw vertical ticks 10 apart
  //draw the vertical labels 10 apart

  strokeWeight(2);
  stroke(0);
  textAlign(CENTER);
  fill(50, 0, 0);
  textSize(10);
  int tickDim = 2;
  //draw lines 600 long at width /2 and height /2
  line(0, height/2, width, height/2);//hor line
  line(width/2, 0, width/2, height);//vert line
  textAlign(CENTER);
  //Horizontal tick s and labels
  //in a for loop increasing by 10 
  for (int i = 0; i <= width; i+=10) {
    //draw the thick lines and labels every 50

    if (i == width/2) {//this is where the zeros are drawn on  the grid
      //do nothing  because  zeros look awkward
    } else if (i % 50 == 0) {//if i is a multiple of 50
      strokeWeight(2);//thicker line
      line(i, height/2- tickDim, i, height/2+tickDim);//tick      
      text((i- width/2)+"", i, height/2+8*tickDim);//label
    } else {
      //thinker unlabelled ticks
      strokeWeight(0.8);
      line(i, height/2- tickDim, i, height/2+tickDim);//thinner ticks
    }
  }//end for loop

  textAlign(LEFT);
  //Vertical tick s and labels
  //in a for loop increasing by 10 
  for (int i = 0; i <= height; i+=10) {
    //draw the thick lines and labels every 50
    if (i == height/2) {
      //do nothing  because  zeros look awkward
    } else if (i % 50 == 0) {//if i is a multiple of 50
      strokeWeight(2);//thicker line
      line(width/2- tickDim, i, width/2+tickDim, i );//tick      
      text(-1*(i- height/2)+"", width/2+5, i+5 );//label
    } else {
      //thinker unlabelled ticks
      strokeWeight(0.8);
      line(width/2- tickDim, i, width/2+tickDim, i);
    }
  }//end for loop
}

void newBestTime(float newTime) {
  frames ++;
  if (frames >= cactus.length) {
    //set frames back to 0
    frames = 0;
  }
  image(cactus[frames], width/2, height/2, width, height);
  drawText("***"+lang[10]+"!!!***", width/2, (int)(height*0.25f), 80);
  drawText(lang[11]+bestTime, width/2, (int)(height*0.35f), 50);
  drawText(lang[12]+newTime, width/2, (int)(height*0.45f), 50);
  bestTime = newTime;
}

void drawWin(float lowestTime) {

  if (lowestTime <= bestTime||bestTime ==0) {

    frameSkip = 20;
    newBestTime(lowestTime);
  } else {
    frameSkip = 2;
    //check to see if frames is equal to banana.length
    if (frames >= banana.length) {
      //set frames back to 0
      frames = 0;
    }
    background(10, 10, 150);
    //draw the image
    textAlign(CENTER);
    image(banana[frames], width/2, height/2, height, height);
    //rect(buttonX+10*sin(0.5*frameCount), buttonY+20*pow(sin(0.25*frameCount+PI/2),2)-15, buttonW, buttonH);
 
    drawText(lang[13], width/2, (int)(height*0.25f), 80);
    drawText(lang[15]+bestTime, width/2, (int)(height*0.35f), 50);
    drawText(lang[16]+times[turnsLeft-1], width/2, (int)(height*0.45f), 50);
    drawText(lang[14]+turnsLeft, width/2, (int)(height*0.55f), 50);
 
  }
}


void drawLine(float _m, float _b, color col, boolean cool) {
  strokeWeight(5);
  float x_i = -width/2;//left most x value
  float x_f = width/2;//right most x values
  //given the slope and y int values, solve for y if x = -width/2
  float y_i  = -_m * x_i - _b;
  //repeat for x = width/2
  float y_f  = -_m * x_f - _b;
  //draw line from first point to final point
  if (cool) {
    drawCoolLine( x_i+width/2, y_i+height/2, x_f+width/2, y_f+height/2, col);
    
  } else {
    stroke(col);
    line(x_i+width/2, y_i+height/2, x_f+width/2, y_f+height/2);
  }
  if (yint){
    
    ellipse(width/2, height/2 -_b, 15, 15);
    drawText("(0, "+_b+")", width/2 + 20, height/2 -_b, 20);
  }
}

boolean checkButton(PVector pos, PVector siz, String lbl){
  boolean hover = (abs(mouseX - pos.x)< siz.x/2)&&(abs(mouseY - pos.y)< siz.y/2);
  if (hover){
    fill(80,10,100);
  }
  else {
    fill(0,200,200);
  }
  strokeWeight(height/80);
  rect(pos.x, pos.y-height/100., siz.x, siz.y, height/50);
  drawText(lbl, pos.x, pos.y, height/12.);
  return hover;
}



void langButtons(){
  /*fill(0,200,200);
  strokeWeight(5);
  rect(mohPos.x, mohPos.y-4, 100, 50, 8);
  drawText("MOHAWK", mohPos.x, mohPos.y, 30);*/
  if (checkButton(mohPos, buttonSize, "MOHAWK")){
    lang = moh;
  }
  if (checkButton(engPos, buttonSize, "ENGLISH")){
    lang = eng;
  }
  if (checkButton(cayPos, buttonSize, "CAYUGA")){
    lang = cay;
  }
  
  /*
  fill(0,200,200);
  strokeWeight(5);
  rect(engPos.x, engPos.y-4, 100, 50, 8);
  drawText("ENGLISH", engPos.x, engPos.y, 30);
  fill(0,200,200);
  strokeWeight(5);
  rect(cayPos.x, cayPos.y-4, 100, 50, 8);
  drawText("CAYUGA", cayPos.x, cayPos.y, 30);
  */
}



void startScreen() {
  image(title[frames], width/2, height/2, width, height);

    // songs[0].loop();
    // songs[0].play();
  langButtons();

  drawText("***"+lang[0]+"***", width/2, (int)(height*0.25), 80);
  drawText(lang[1], width/2, (int)(height*0.55), 30);
  drawText("--------------------------------------------", width/2, (int)(height*0.60), 30);
  drawText(lang[2], width/2, (int)(height*0.65), 30);
  drawText(lang[3], width/2, (int)(height*0.70), 30);
  drawText(lang[4], width/2, (int)(height*0.75), 30);
  drawText(lang[5], width/2, (int)(height*0.80), 30);
  //drawText(lang[6], width/2, (int)(height*0.40), 40);

}

void gameScreen() {
  background(200);
  //draw new frame
  drawAxis();
  yourCol = color(0, 0, 200);
  targetCol = color(200, 0, 0);
  if (myLineVisible){
    drawLine(m, b, yourCol, true);
  }
  
  if (targetLineVisible){
    drawLine(randM, randB, targetCol, false);
  }
  drawText(lang[7]+" y = "+randM+"x+"+randB, width/2 + 30, 40, 30, targetCol);
  drawText(lang[8]+" y = "+m+"x+"+b, 30, 40, 30, yourCol);
  currentTime = (int)((millis() - startTime)/1);
  currentTime /= 1000.;
  textAlign(CENTER);
  drawText(lang[9]+currentTime, width/2, 80, 30);
  
}

void draw() {
  
  background(0);
  
  //update stuff
  if (state == gameOn) {
    //update the new frames variables
    checkKeys();
  } else if (state == gameWin || state == gameStart) {
    //update (inc ) frames
    if (frameCount % frameSkip == 0) {//every 5th frame only, update frames
      frames ++;
    }
  } else if (state == gameOver) {
  } else { 
   // print ("WTF");
  }  

  //check stuff
  if (state == gameStart) {
    frameSkip = 1;
    if (frames >= title.length) {
      //set frames back to 0
      frames = 0;
    }
  } else if (state == gameOn) {
    //check all conditions
    checkBounds();
    checkLines();
  } else if (state == gameWin) {
    
  } else if (state == gameOver) {
  } else {
  }

  //drawstuff

  if (state == gameStart) {
    startScreen();
  } else if (state == gameOn) {
    gameScreen();
  } else if (state == gameWin) {
    drawWin(currentTime);
  } else if (state == gameOver) {
    //go thru the tiems and look to see if one beats it
    float lowestTime = 9999;
    for (int i = 0; i < times.length; i++) { 
      if (times[i]< lowestTime) {
        lowestTime = times[i];
      }
    }  

    frameSkip = 3;
    gg();

    //   background(15,0,0);
    // text("I'm in the game over state!!!",width/2, height/2);
  } else {
    background(150, 0, 0);
    text("Oh no! Somehow I am in an incorrect state!", width/2, height/2);
  }
  
  stroke(255);
  //}
  
}

/*
*/

/*
void draw() {
 drawTarget(width*0.25, height*0.4, 200, 4);
 drawTarget(width*0.5, height*0.5, 300, 10);
 drawTarget(width*0.75, height*0.3, 120, 6);
 }
 
 void drawTarget(float xloc, float yloc, int size, int num) {
 float grayvalues = 255/num;
 float steps = size/num;
 for (int i = 0; i < num; i++) {
 fill(i*grayvalues);
 ellipse(xloc, yloc, size - i*steps, size - i*steps);
 }
 }
 */