OBJS = swtch.o proc.o main.o\

sim : $(OBJS)
	cc -o sim $(OBJS)

%.o: %.c %.h
	cc -c -o $@ $<

clean :
	rm sim $(OBJS)
