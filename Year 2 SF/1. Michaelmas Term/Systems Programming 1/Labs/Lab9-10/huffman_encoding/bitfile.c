
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>


#include "bitfile.h"
#include <limits.h>




struct bitfile *  bitfile_new()
{
	struct bitfile *p;
	p= malloc(sizeof(struct bitfile));
	p->file = NULL;
	p->buffer = 0;
	p->index = 0;
	p->is_read_mode = 0;
	p-> is_EOF = 0;
	
	return p;
}


// open a bit file in "r" (read) mode or "w" (write) mode
struct bitfile * bitfile_open(char * filename, char * mode)
{
	FILE * file;
	struct bitfile * fp = bitfile_new();


  file = fopen(filename, mode);
  
  if ( file == NULL ) {		
    printf("FATAL: Error opening file %s. Aborting program.\n", filename);
    exit(1);
  }

	fp->file = file;
	if(strcmp(mode, "r")==0)		//check to see if file is in read mode
	{
		fp->buffer = fgetc(file);
		fp->is_read_mode = 1;
	}
	else
	{
		fp->is_read_mode = 0;
	}
	
  return fp;
}

// write a bit to a file; the file must have been opened in write mode
void bitfile_write_bit(struct bitfile * this, int bit)
{
	if(this->index>=8)		//at end of buffer
	{
		fputc(this->buffer, this->file);		//write to file
		this->buffer = 0;				//reset buffer
		this->index = 0;				//reset buffer index
	}
	this->buffer = this->buffer | (bit<<this->index);
	this->index++;
}

// read a bit from a file; the file must have been opened in read mode
// returns the bit (that is 0 or 1), or -1 if at end of file
int bitfile_read_bit(struct bitfile * this)
{
	int bit = this->buffer & (1<<this->index);
	this->index++;
	if(this->index>=8)		//at end of buffer
	{
		this->buffer = fgetc(this->file);		//get more bits
		if (feof(this->file)) {
			this->is_EOF = 1;
		}
		this->index = 0;				//reset buffer index
	}
	/*if(feof(this->file))			//if at end of file
	{
		this->is_EOF = 1;
	}*/
	if(bit !=0)
	{
	return 1;
	}
	else
	{
	return 0;
	}
}

// close a bitfile; flush any partially-filled buffer if file is open
// in write mode
void bitfile_close(struct bitfile * this)
{
	if(this->index > 0 && this->is_read_mode == 0)
	{
		fputc(this->buffer, this->file);		//write to file
	}
	fclose(this->file);
}

// return true if we have read the last bit of the file, and
// then tried and failed to read a subsequent bit
int bitfile_end_of_file(struct bitfile * this)
{
	
	return this->is_EOF;
}
