%%{
  machine common;

  action tok {
    tok = p;
  }

  SP        = ' ';
  CHAR      = [0-9a-zA-Z\-_:.];
  DB_QUOTE = '"';


}%%