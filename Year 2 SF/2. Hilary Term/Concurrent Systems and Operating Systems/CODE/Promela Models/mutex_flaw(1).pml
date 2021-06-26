byte count;
byte x, y, z;
active [2] proctype user()
{	byte me = _pid + 1;	/* me is 1 or 2 */
L1:	x = me;
L2:	if
	:: (y != 0 && y != me) -> goto L1
	:: (y == 0 || y == me)
	fi;
L3:	z = me;
L4:	if
	:: (x != me)  -> goto L1
	:: (x == me)
	fi;
L5:	y = me;
L6:	if
	:: (z != me) -> goto L1
	:: (z == me)
	fi;

L7:	/* success: enter critical section */
	printf("P%d in..\n",me);
  count++;
	assert(count == 1);
	count--;
  printf("P%d out!\n",me)
	goto L1
}
