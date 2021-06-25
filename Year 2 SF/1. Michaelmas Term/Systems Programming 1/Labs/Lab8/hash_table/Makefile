TEST_LISTSET_CASES=1 2 3 4 5 6 7 8
TEST_HASHTABLE_CASES=1 2 3 4 5 6
pass=\\\e[32mPASSED\\\e[0m
partial_pass=\\\e[33mPASSED WITH NON ZERO EXIT CODE\\\e[0m
partial_compile=\\\e[33mPASSED WITH WARNINGS\\\e[0m
fail=\\\e[31mFAILED\\\e[0m

all: file_check build-prep listset hashtable run_all_listset_tests run_all_hashtable_tests

listset: test_files/listset-main.c listset.c listset.h
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Compiling calc"
	echo "gcc -Wall -O0 -g -o listset test_files/listset-main.c listset.c -I."
	echo -n "\e[0m"
	gcc -Wall -O0 -g -o listset test_files/listset-main.c listset.c -I. \
  && b=1 || b=0; \
	gcc -Wall -O0 -g -o listset test_files/listset-main.c listset.c -I. -Werror 2> /dev/null 1> /dev/null \
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

hashtable: test_files/hashtable-main.c hashtable.c hashtable.h listset.c listset.h
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Compiling calc"
	echo "gcc -Wall -O0 -g -o hashtable test_files/hashtable-main.c hashtable.c listset.c -I."
	echo -n "\e[0m"
	gcc -Wall -O0 -g -o hashtable test_files/hashtable-main.c hashtable.c listset.c -I. \
  && b=1 || b=0; \
	gcc -Wall -O0 -g -o hashtable test_files/hashtable-main.c hashtable.c listset.c -I. -Werror 2> /dev/null 1> /dev/null \
  && a=1 || a=0; \
	echo -n "\e[40m\e[K\e[1m"; \
  echo -n "Compilation ";\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 3.0 2 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_compile); \
    echo Test 3.0 2 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 3.0 0 >> results.txt; \
  fi; \
  fi ;"

run_all_listset_tests : $(foreach test,$(TEST_LISTSET_CASES),run_test_listset_$(test))

run_all_hashtable_tests : $(foreach test,$(TEST_HASHTABLE_CASES),run_test_hashtable_$(test))

run_test_listset_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running listset test $* "
	echo -n "./listset " `cat test_files/test_inputs/test_listset_$*.txt`
	echo "\e[0m"
	./listset `cat test_files/test_inputs/test_listset_$*.txt` > /dev/null \
  && a=1 || a=0;\
	./listset `cat test_files/test_inputs/test_listset_$*.txt` > test_files/build/test_listset_$*.txt ;\
  diff -eq test_files/test_outputs/test_listset_$*.txt test_files/build/test_listset_$*.txt > /dev/null \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run listset test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 2.$* 1 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial); \
    echo Test 2.$* 0.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 2.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_hashtable_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running hashtable test $* "
	echo -n "./hashtable " `cat test_files/test_inputs/test_hashtable_$*.txt`
	echo "\e[0m"
	./hashtable `cat test_files/test_inputs/test_hashtable_$*.txt` > /dev/null \
  && a=1 || a=0;\
	./hashtable `cat test_files/test_inputs/test_hashtable_$*.txt` > test_files/build/test_hashtable_$*.txt ;\
  diff -eq test_files/test_outputs/test_hashtable_$*.txt test_files/build/test_hashtable_$*.txt > /dev/null \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run hashtable test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 4.$* 1 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial); \
    echo Test 4.$* 0.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 4.$* 0 >> results.txt; \
  fi; \
  fi ;" \

file_check:
	echo "Filecheck listset.c "
	test -s listset.c
	echo "Filecheck hashtable.c "
	test -s hashtable.c
	echo "Filecheck listset.h "
	test -s listset.h
	echo "Filecheck hashtable.h "
	test -s hashtable.h
	echo "Filecheck test_files/listset-main.c "
	test -s test_files/listset-main.c
	echo "Filecheck test_files/hashtable-main.c "
	test -s test_files/hashtable-main.c

build-prep: clean
	mkdir -p test_files/build
	touch results.txt

clean:
	rm -rf test_files/build
	rm -rf results.txt
	rm -rf listset
	rm -rf hashtable

submission_archive:
	tar -cvf listset_submission.tar *.h *.c

.SILENT: 

