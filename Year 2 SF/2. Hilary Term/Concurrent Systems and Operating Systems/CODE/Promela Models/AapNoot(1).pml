int x;

proctype Noot()
{
  printf("I am Noot!\n");
  x=x+1;
}


proctype Aap()
{
  int y=1;
  skip;
  run Noot();
  x=2;
  x>2 && y==1;
  skip;
  printf("! Aap !\n");
}

init{
  run Aap()
}
