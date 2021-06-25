TEST_POSTFIX_CASES=1 2 3 4 5 6 7 8
TEST_INFIX_CASES=1 2 3 4 5 6 7 8
pass=\\\e[32mPASSED\\\e[0m
partial_pass=\\\e[33mPASSED WITH NON ZERO EXIT CODE\\\e[0m
partial_compile=\\\e[33mPASSED WITH WARNINGS\\\e[0m
fail=\\\e[31mFAILED\\\e[0m

all: file_check build-prep calc run_all_postfix_tests run_all_infix_tests

calc: test_files/main.c infix.c postfix.c stack.c infix.h postfix.h stack.h
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Compiling calc"
	echo "gcc -Wall -O0 -g -o calc test_files/main.c infix.c postfix.c stack.c -I. -lm"
	echo -n "\e[0m"
	gcc -Wall -O0 -g -o calc test_files/main.c infix.c postfix.c stack.c -I. -lm \
  && b=1 || b=0; \
	gcc -Wall -O0 -g -o calc test_files/main.c infix.c postfix.c stack.c -I. -lm -Werror 2> /dev/null 1> /dev/null \
  && a=1 || a=0; \
	echo -n "\e[40m\e[K\e[1m"; \
  echo -n "Compilation ";\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 1.0 4 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_compile); \
    echo Test 1.0 4 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 1.0 0 >> results.txt; \
  fi; \
  fi ;"

test_stack: test_files/test_stack.c stack.c stack.h
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Compiling test_stack"
	echo "gcc -Wall -O0 -g -o test_stack test_files/test_stack.c stack.c -I. -lm"
	echo -n "\e[0m"
	gcc -Wall -O0 -g -o test_stack test_files/test_stack.c stack.c -I. -lm \
  && b=1 || b=0; \
	gcc -Wall -O0 -g -o test_stack test_files/test_stack.c stack.c -I. -lm -Werror 2> /dev/null 1> /dev/null \
  && a=1 || a=0; \
	echo -n "\e[40m\e[K\e[1m"; \
  echo -n "Compilation ";\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_compile); \
  else \
    echo -e $(fail); \
  fi; \
  fi ;"

run_all_postfix_tests : $(foreach test,$(TEST_POSTFIX_CASES),run_test_postfix_$(test))

run_all_infix_tests : $(foreach test,$(TEST_INFIX_CASES),run_test_infix_$(test))

run_test_postfix_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running postfix test $* "
	echo -n "./calc postfix" `cat test_files/test_inputs/test_postfix_$*.txt`
	echo "\e[0m"
	./calc postfix `cat test_files/test_inputs/test_postfix_$*.txt` > /dev/null \
  && a=1 || a=0;\
	./calc postfix `cat test_files/test_inputs/test_postfix_$*.txt` | tail -n 1 > test_files/build/test_postfix_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_postfix_$*.txt test_files/build/test_postfix_output_$*.txt > /dev/null \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run postfix test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 2.$* 2 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial); \
    echo Test 2.$* 1.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 2.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_infix_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running infix test $* "
	echo -n "./calc infix" `cat test_files/test_inputs/test_infix_$*.txt`
	echo "\e[0m"
	./calc infix `cat test_files/test_inputs/test_infix_$*.txt` > /dev/null \
  && a=1 || a=0;\
	./calc infix `cat test_files/test_inputs/test_infix_$*.txt` | tail -n 1 > test_files/build/test_infix_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_infix_$*.txt test_files/build/test_infix_output_$*.txt > /dev/null \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run infix test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 3.$* 2 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial); \
    echo Test 3.$* 1.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 3.$* 0 >> results.txt; \
  fi; \
  fi ;" \

file_check:
	echo "Filecheck infix.c "
	test -s infix.c
	echo "Filecheck postfix.c "
	test -s postfix.c
	echo "Filecheck stack.c "
	test -s stack.c
	echo "Filecheck infix.h "
	test -s infix.h
	echo "Filecheck postfix.h "
	test -s postfix.h
	echo "Filecheck stack.h "
	test -s stack.h
	echo "Filecheck test_files/main.c "
	test -s test_files/main.c

build-prep: clean
	mkdir -p test_files/build
	touch results.txt

clean:
	rm -rf test_files/build
	rm -rf results.txt
	rm -rf calc
	rm -rf test_stack

submission_archive:
	tar -cvf calc_submission.tar *.h *.c

.SILENT: 

