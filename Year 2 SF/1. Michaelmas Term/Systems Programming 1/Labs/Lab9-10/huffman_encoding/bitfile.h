// header file for a file ADT where we can read a single bit at a
// time, or write a single bit at a time

#ifndef BITFILE_H
#define BITFILE_H

struct bitfile {
  FILE * file;
  unsigned char buffer;
  int index;
  int is_read_mode;
  int is_EOF;
};

//new bitfile
struct bitfile *  bitfile_new();

// open a bit file in "r" (read) mode or "w" (write) mode
struct bitfile * bitfile_open(char * filename, char * mode);

// write a bit to a file; the file must have been opened in write mode
void bitfile_write_bit(struct bitfile * this, int bit);

// read a bit from a file; the file must have been opened in read mode
// returns the bit (that is 0 or 1), or -1 if at end of file
int bitfile_read_bit(struct bitfile * this);

// close a bitfile; flush any partially-filled buffer if file is open
// in write mode
void bitfile_close(struct bitfile * this);

// return true if we have read the last bit of the file, and
// then tried and failed to read a subsequent bit
int bitfile_end_of_file(struct bitfile * this);


#endif // BITFILE_H