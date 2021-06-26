bool turn;
bool flag[2];
byte cnt;

active [2] proctype mutex()
{    pid i, j;

    i = _pid;
    j = 1 - _pid;

again:

      flag[i] = true;
    	do	/* can be 'if' - says Doran&Thomas */
    	:: flag[j] ->
      		if
      		:: turn == j ->
        			flag[i] = false;
        			!(turn == j);
        			flag[i] = true
      		:: else
      		fi
    	:: else ->
    		break
    	od;

      printf("P%d in..\n",_pid);
      cnt++;
      assert(cnt == 1);    /* critical section */
      cnt--;
      printf("P%d out!\n",_pid);

      turn = j;
      flag[i] = false;

      goto again
  }
