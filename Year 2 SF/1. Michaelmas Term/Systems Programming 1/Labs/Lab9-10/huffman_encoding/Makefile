TEST_SHOWCODES_CASES=1 2 3 4
TEST_ENCODE_CASES=1 2
TEST_DECODE_CASES=1 2
pass=\\\e[32mPASSED\\\e[0m
partial_pass=\\\e[33mPASSED WITH NON ZERO EXIT CODE\\\e[0m
partial_compile=\\\e[33mPASSED WITH WARNINGS\\\e[0m
fail=\\\e[31mFAILED\\\e[0m

all: file_check build-prep huff run_all_showcodes run_all_encode run_all_decode

huff: test_files/main.c huff.c huff.h
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Compiling huff"
	echo "gcc -Wall -O0 -g -o huff test_files/main.c huff.c -I. "
	echo -n "\e[0m"
	gcc -Wall -O0 -g -o huff test_files/main.c huff.c -I. \
  && b=1 || b=0; \
	gcc -Wall -O0 -g -o huff test_files/main.c huff.c -I. -Werror 2> /dev/null 1> /dev/null \
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

run_all_showcodes : $(foreach test,$(TEST_SHOWCODES_CASES),run_test_showcodes_$(test))

run_all_encode : $(foreach test,$(TEST_ENCODE_CASES),run_test_encode_$(test))

run_all_decode : $(foreach test,$(TEST_DECODE_CASES),run_test_decode_$(test))

run_test_showcodes_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running showcodes test $* "
	echo -n "./huff showcodes test_files/test_inputs/test_showcodes_$*.txt"
	echo "\e[0m"
	./huff showcodes test_files/test_inputs/test_showcodes_$*.txt > /dev/null \
  && a=1 || a=0; \
  ./huff showcodes test_files/test_inputs/test_showcodes_$*.txt | tail -n 256 > test_files/build/test_showcodes_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_showcodes_$*.txt test_files/build/test_showcodes_output_$*.txt > /dev/null \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run showcodes test $* " ;\
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

run_test_encode_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running encode test $* "
	echo -n "./huff encode test_files/test_inputs/test_showcodes_1.txt test_files/test_inputs/test_encode_$*.txt test_files/build/test_encode_output_$*"
	echo "\e[0m"
	./huff encode test_files/test_inputs/test_showcodes_1.txt test_files/test_inputs/test_encode_$*.txt test_files/build/test_encode_output_$* \
  && a=1 || a=0; \
  diff -eq test_files/test_outputs/test_encode_$* test_files/build/test_encode_output_$* > /dev/null \
  && b=1 || b=0; \
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run encode test $* " ;\
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
  fi ;"

run_test_decode_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Running decode test $* "
	echo -n "./huff decode test_files/test_inputs/test_showcodes_1.txt test_files/test_inputs/test_decode_$* test_files/build/test_decode_output_$*.txt"
	echo "\e[0m"
	timeout 120 ./huff decode test_files/test_inputs/test_showcodes_1.txt test_files/test_inputs/test_decode_$* test_files/build/test_decode_output_$*.txt 2> /dev/null 1> /dev/null \
  && a=1 || a=0; \
  diff -eq test_files/test_outputs/test_decode_$*.txt test_files/build/test_decode_output_$*.txt > /dev/null \
  && b=1 || b=0; \
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "Run decode test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo Test 4.$* 2 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial); \
    echo Test 4.$* 1.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo Test 4.$* 0 >> results.txt; \
  fi; \
  fi ;"

file_check:
	echo "Filecheck huff.c "
	test -s huff.c
	echo "Filecheck huff.h "
	test -s huff.h
	echo "Filecheck test_files/main.c "
	test -s test_files/main.c

build-prep: clean
	mkdir -p test_files/build
	touch results.txt

clean:
	rm -rf test_files/build
	rm -rf results.txt
	rm -rf huff

submission_archive:
	tar -cvf huffman_submission.tar *.h *.c

.SILENT: 
