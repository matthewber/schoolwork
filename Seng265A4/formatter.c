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

int width = 256;
int buffer = 0;
int formatting = 0; /* on: 0   off: 1 */
int linespacing = 0;
int currLineSize = 0;


char **format_file(FILE *infile) {
  char *nc = (char *)malloc(sizeof(char));
  if(nc == NULL){
    fprintf(stderr, "Failed: Memory allocation\n");
  }
  char **currLines = (char *)malloc(sizeof(char *));
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
      currLines = realloc(currLines, sizeof(currLines) + sizeof(char));
      if(currLines == NULL){
        fprintf(stderr, "Failed: Memory reallocation\n");
      }
  }
  free(nc);
  char **lines = format_lines(currLines, lineCount);
  free(currLines);
  return lines;
}

int determineCommand(char currLine[]){
  char *command = (char *)malloc(sizeof(char)*1000);
  char *tokens;
  strcpy(command, currLine);
  tokens = strtok(command, " ");

  if(strcmp(tokens, ".LW") == 0){
    tokens = strtok(NULL, " ");
    int num = atoi(tokens);
    width = (num);
    return 1;
  }else if(strcmp(tokens, ".LM") == 0){
    tokens = strtok(NULL, " ");
    int num = atoi(tokens);
    buffer = (num);
    return 1;
  }else if(strcmp(tokens, ".FT") == 0){
    tokens = strtok(NULL, " ");
    if(strncmp(tokens, "on", 2) == 0){
      formatting = 0;
      return 1;
    }else if(strncmp(tokens, "off", 3) == 0){
      formatting = 1;
      return 1;
    }else{
      fprintf(stderr, "error: .FT command missing a correctly formatted argument");
      return 1;
    }
  }else if(strcmp(tokens, ".LS") == 0){
    tokens = strtok(NULL, " ");
    int num = atoi(tokens);
    linespacing = (num);
    return 1;
  }
  return 0;
}

char **format_lines(char **lines, int num_lines) {
  int num_result_lines = 1;
  char **result = malloc(sizeof(char *) * num_lines);
  if(result == NULL){
      fprintf(stderr, "Failed: Memory allocation");
  }
  char **line = result;
  *line = malloc(sizeof(char *));
  if(*line == NULL){
    fprintf(stderr, "Failed: Memory allocation");
  }
  char tempLine[width];
  for(int i = 0; i < num_lines; i++){
    if(determineCommand(lines[i])){
      continue;
    }else{
      if(formatting){
        *line = lines[i];
        line++;
        *line = malloc(sizeof(char) * width);
        num_result_lines++;
      }else{
        const char delim[3] = " \n";
        strcpy(tempLine, lines[i]);
        char *currWord = strtok(tempLine, delim);
        while(currWord != NULL){
          if(width <= strlen(currWord)+currLineSize){
            //printf("%d", width);
            line++;
            *line = malloc(sizeof(char)*width);
            strcat(*line, currWord);
            strncat(*line, " ", 1);
            currLineSize = (strlen(currWord) + 1);
            num_result_lines++;
          }else{
            strcat(*line, currWord);
            strncat(*line, " ", 1);
            currLineSize += (strlen(currWord) + 1);
          }

          currWord = strtok(NULL, delim);
        }
      }

  return result;
}
