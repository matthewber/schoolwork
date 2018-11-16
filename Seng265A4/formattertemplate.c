/*
 * UVic SENG 265, Summer 2018,  A#4
 *
 * This will contain the bulk of the work for the fourth assignment. It
 * provide similar functionality to the class written in Python for
 * assignment #3.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "formatter.h"

#define DEFAULT_BUFLEN 80


char **format_file(FILE *infile) {

  char *nc = (char *)malloc(sizeof(char));
  if(nc == NULL){
    fprintf(stderr, "Failed: Memory allocation\n");
  }
  char *currLines = (char *)malloc(sizeof(char));
  if(currLines == NULL){
    fprintf(stderr, "Failed: Memory allocation\n");
  }
  int lineCount = 0;
  for(;;){ //adds inFile to currLines char by char and counts lines
      *nc = (char)fgetc(infile);
      if(feof(infile)){
        break;
      }
      if(*nc == '\n'){
        lineCount++;
      }
      strncat(currLines, nc, 1);
      currLines = (char *)realloc(currLines, sizeof(currLines) + sizeof(char));
      if(currLines == NULL){
        fprintf(stderr, "Failed: Memory allocation\n");
      }
  }
  free(nc);
  return format_lines(currLines, lineCount);
}


char **format_lines(char **lines, int num_lines) {
        char **result = NULL;
        printf("%s", *lines);
        return result;
}
