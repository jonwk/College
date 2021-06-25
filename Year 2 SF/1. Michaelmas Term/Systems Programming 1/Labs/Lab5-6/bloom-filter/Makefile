TEST_BITSET_CREATE_CASES=1 2
TEST_BITSET_UNION_CASES=1 2
TEST_BITSET_INTERSECT_CASES=1 2
TEST_BITSET_REMOVE_CASES=1 2
TEST_BITSET_CARDINALITY_CASES=1 2
TEST_BLOOM_CASES=1 2
pass=\\\e[32mPASSED\\\e[0m
partial_pass=\\\e[33mPASSED WITH NON ZERO EXIT CODE\\\e[0m
partial_compile=\\\e[33mPASSED WITH WARNINGS\\\e[0m
fail=\\\e[31mFAILED\\\e[0m

all: file_check build-prep filter run_all_bitset_create run_all_bitset_union run_all_bitset_intersect run_all_bitset_remove run_all_bitset_cardinality run_all_bloom

filter: test_files/main.c bitset.c bitset.h bloom.c bloom.h
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo "Compiling filter"
	echo "gcc -Wall -Og -g -o filter test_files/main.c bitset.c bloom.c -I. -lm"
	echo -n "\e[0m"
	gcc -Wall -O0 -g -o filter test_files/main.c bitset.c bloom.c -I. -lm \
  && b=1 || b=0; \
	gcc -Wall -O0 -g -o filter test_files/main.c bitset.c bloom.c -I. -lm -Werror 2> /dev/null 1> /dev/null \
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

run_all_bitset_create : $(foreach test,$(TEST_BITSET_CREATE_CASES),run_test_bitset_create_$(test))

run_all_bitset_union : $(foreach test,$(TEST_BITSET_UNION_CASES),run_test_bitset_union_$(test))

run_all_bitset_intersect : $(foreach test,$(TEST_BITSET_INTERSECT_CASES),run_test_bitset_intersect_$(test))

run_all_bitset_remove : $(foreach test,$(TEST_BITSET_REMOVE_CASES),run_test_bitset_remove_$(test))

run_all_bitset_cardinality : $(foreach test,$(TEST_BITSET_CARDINALITY_CASES),run_test_bitset_cardinality_$(test))

run_all_bloom : $(foreach test,$(TEST_BLOOM_CASES),run_test_bloom_$(test))

run_test_bitset_create_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo -n "run bitset create test $* "
	echo "\e[0m"
	./filter bitset_create test_files/test_inputs/test_bitset_create_$*.txt > /dev/null \
  && a=1 || a=0;\
	./filter bitset_create test_files/test_inputs/test_bitset_create_$*.txt | tail -n 1 > test_files/build/test_bitset_create_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_bitset_create_$*.txt test_files/build/test_bitset_create_output_$*.txt \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "run bitset create test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo test 2.$* 2 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_pass); \
    echo test 2.$* 1.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo test 2.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_bitset_union_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo -n "run bitset union test $* "
	echo "\e[0m"
	./filter bitset_union test_files/test_inputs/test_bitset_union_$*.txt > /dev/null \
  && a=1 || a=0;\
	./filter bitset_union test_files/test_inputs/test_bitset_union_$*.txt | tail -n 1 > test_files/build/test_bitset_union_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_bitset_union_$*.txt test_files/build/test_bitset_union_output_$*.txt \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "run bitset union test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo test 3.$* 1 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_pass); \
    echo test 3.$* 0.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo test 3.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_bitset_intersect_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo -n "run bitset intersect test $* "
	echo "\e[0m"
	./filter bitset_intersect test_files/test_inputs/test_bitset_intersect_$*.txt > /dev/null \
  && a=1 || a=0;\
	./filter bitset_intersect test_files/test_inputs/test_bitset_intersect_$*.txt | tail -n 1 > test_files/build/test_bitset_intersect_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_bitset_intersect_$*.txt test_files/build/test_bitset_intersect_output_$*.txt \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "run bitset intersect test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo test 4.$* 1 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_pass); \
    echo test 4.$* 0.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo test 4.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_bitset_remove_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo -n "run bitset remove test $* "
	echo "\e[0m"
	./filter bitset_remove test_files/test_inputs/test_bitset_remove_$*.txt > /dev/null \
  && a=1 || a=0;\
	./filter bitset_remove test_files/test_inputs/test_bitset_remove_$*.txt | tail -n 1 > test_files/build/test_bitset_remove_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_bitset_remove_$*.txt test_files/build/test_bitset_remove_output_$*.txt \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "run bitset remove test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo test 5.$* 1 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_pass); \
    echo test 5.$* 0.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo test 5.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_bitset_cardinality_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo -n "run bitset cardinality test $* "
	echo "\e[0m"
	./filter bitset_cardinality test_files/test_inputs/test_bitset_cardinality_$*.txt > /dev/null \
  && a=1 || a=0;\
	./filter bitset_cardinality test_files/test_inputs/test_bitset_cardinality_$*.txt | tail -n 1 > test_files/build/test_bitset_cardinality_output_$*.txt ;\
  diff -eq test_files/test_outputs/test_bitset_cardinality_$*.txt test_files/build/test_bitset_cardinality_output_$*.txt \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "run bitset cardinality test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo test 6.$* 1 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_pass); \
    echo test 6.$* 0.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo test 6.$* 0 >> results.txt; \
  fi; \
  fi ;" \

run_test_bloom_%:
	echo
	echo -n "\e[40m\e[K\e[2m"
	echo -n "run bloom test $* "
	echo "\e[0m"
	./filter bloom test_files/test_inputs/test_bloom_$*.txt | tail -n 1 > test_files/build/test_bloom_output_$*.txt \
  && a=1 || a=0;\
  diff -eq test_files/test_outputs/test_bloom_$*.txt test_files/build/test_bloom_output_$*.txt \
  && b=1 || b=0;\
	echo -n "\e[40m\e[K\e[1m" ;\
	echo -n "run bloom test $* " ;\
  bash -c \
 "if [ $$b == 1 -a $$a == 1 ]; \
  then \
    echo -e $(pass); \
    echo test 7.$* 2 >> results.txt; \
  else if [ $$b == 1 ]; \
  then \
    echo -e $(partial_pass); \
    echo test 7.$* 1.5 >> results.txt; \
  else \
    echo -e $(fail); \
    echo test 7.$* 0 >> results.txt; \
  fi; \
  fi ;" \

file_check:
	echo "Filecheck bitset.c "
	test -s bitset.c
	echo "Filecheck bitset.h "
	test -s bitset.h
	echo "Filecheck bloom.c "
	test -s bloom.c
	echo "Filecheck bloom.h "
	test -s bloom.h
	echo "Filecheck test_files/main.c "
	test -s test_files/main.c

build-prep: clean
	mkdir -p test_files/build
	touch results.txt

clean:
	rm -rf test_files/build
	rm -rf results.txt
	rm -rf filter

submission_archive:
	tar -cvf filter_submission.tar *.h *.c

.SILENT: 
