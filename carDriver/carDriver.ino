// Ferdi Kızıltoprak
// Aralık 2015
// Microprocessor Term Project

int rx = 0;
bool guc,left,right,forth,back,sidePower;

int pinSidePower = 8,
    pinSpeed = 9,
    pinForth = 10,
    pinBack  = 11,
    pinLeft  = 12,
    pinRight = 13;
void setup() {
  pinMode(pinSidePower, OUTPUT);
  pinMode(pinSpeed, OUTPUT);
  pinMode(pinForth, OUTPUT);
  pinMode(pinBack, OUTPUT);
  pinMode(pinLeft, OUTPUT);
  pinMode(pinRight, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  if ( Serial.available() > 0 ) {
      rx = Serial.read(); 
  } 
  if( rx == '0' ) {
    digitalWrite(pinSpeed, LOW);
    digitalWrite(pinForth, LOW);
    digitalWrite(pinBack, LOW);
  }
  if( rx == '1' ) {
    digitalWrite(pinSpeed, HIGH);
    digitalWrite(pinForth, HIGH); // ileri
    digitalWrite(pinBack, LOW);
  }
  if( rx == '2' ) {
    digitalWrite(pinSpeed, HIGH);
    digitalWrite(pinBack, HIGH); // Geri
    digitalWrite(pinForth, LOW);
  }
  if( rx == '3' ) {
    digitalWrite(pinSidePower, HIGH);
    digitalWrite(pinRight, LOW);
    digitalWrite(pinLeft, HIGH); // Sol
  }
  if( rx == '4' ) {
    digitalWrite(pinSidePower, HIGH);
    digitalWrite(pinLeft, LOW);
    digitalWrite(pinRight, HIGH); // Sağ 
  }
  if( rx == '5' ) {
    digitalWrite(pinLeft, LOW);
    digitalWrite(pinRight, LOW);
    digitalWrite(pinSidePower, LOW);
  }
}
