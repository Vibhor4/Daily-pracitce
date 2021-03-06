#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <ctype.h>
#include "calc02.h"
#define BUFSIZE 100

char buf[2*BUFSIZE];
int bufp = 0;

int getch (void){
    return (bufp>0) ? buf[--bufp] : getNormal();
}

void ungetch (int c){
	if (bufp >= BUFSIZE)
	{
		printf("ungetch : too many characters\n");
	}
	else{
		buf[bufp++] = c;
	}
}

void ungets (char s[]) {
	int size = strlen(s);
	if (bufp+size >= BUFSIZE)
	{
		printf("ungets : too many characters\n");
	}
	else{
		ungetch(' ');//let to check
		while (size) {
			ungetch(s[--size]);
		}
	}
}

/*
  rule of priority: only the high priority can place on the low priority
  priority in stack: ):7 *:6 /:6 +:4 -:4 (:1 '\n':0
  priority out of stack: (:8 *:5 /:5 +:3 -:3 ):2 '\n':0
  return the state of input
*/
int spT = 0;
char stack[BUFSIZE];

int pushT(int c);
int popT();
int topT();
int flushToCharT(char *,char);
int sizeT();

int transform(char *inputStr, size_t len){
    //work as buffer to store the whole postfix expression
    char reversedBuf[2*len];
    int i = 0, j = 0;
    //transform the input to postfix order
    pushT('\n');
    char c;
    char topE;
    for(; i < len; ++i){
        c = inputStr[i];
        topE = topT();
        if (isdigit(c) || c == '.') {
            reversedBuf[j++] = inputStr[i];
        } else {
            reversedBuf[j++] = ' ';//in order to separate numbers
            if (c == '(') {
                pushT('(');
            }else if (c == ')'|| c == '\n' ) {
                j += flushToCharT(&reversedBuf[j], c);
            }else if(isspace(c)) {//ignore the space
            }
            //means c is sign
            else {//compare the priority of two operator
                while(topE == '*' || topE == '/'
                    || topE == c //means they are all + or -
                    || (topE == '+' && c == '-')
                    ||  (topE == '-' && c == '+')){
                    
                    reversedBuf[j++] = popT();
                    //update
                    topE = topT();
                }
                pushT(c);
            }
        }
    }
    if(sizeT() != 0){
        printf("You seems not input a complete expression: %s" , inputStr);
        puts("Want to input more?");
        fprintf(stderr, "something wrong in transform");
    } else {
        ungetch('\n');//assume that if the size is 0, the expression can be calculated, so ungetch '\n' to calculate
    }
    //copy to buf in reverse order
    
    i = --j;
    for(; i >= 0; --i){
        ungetch(reversedBuf[i]);
    }
    
    //    ungets(reversedBuf);//put to much char that after the useful char
    return 1;
}
int sizeT(){
    return spT;
}
/*
  parameter: char *: destination of elements; char: the symbol of end
  return: the state of function
*/
int flushToCharT(char *des, char c){
    int i = 0;
    if(c == ')'){
        c = '(';
    }
    while (topT() != c) {
        des[i++] = popT();
    }
    popT();//pop the '(' or '\n' in the stack
    return i;
}
int pushT(int c){
    if(spT < BUFSIZE){
        stack[spT++] = c;
        return c;
    }else{
        fprintf(stderr,"stack is full pushT");
        return -1;
    }
}
int popT(){
    if(spT > 0){
        return stack[--spT];
    }else{
        fprintf(stderr, "stack is empty in popT");
        return -1;
    }
}
int topT(){
    int temp = popT();
    pushT(temp);
    return temp;
}

/*
  method: getNormal
  params: nil
  function: fill the buffer and return a char
  date: 2014.9.29 / 11.8
*/
int getNormal() {
	size_t maxL = 100;
	char *inputStr;
    inputStr = malloc(maxL+1);
    int state;
    //the length include the delimiter '\n' but not '\0'
    size_t len = getline(&inputStr, &maxL , stdin);//how would getline behave if input is '1+2 EOF \n' --it seems EOF was ignored
	if (len == -1) {
		return EOF;
	}else {
		//make it reverse order
		state = transform(inputStr, len);
		if (!state){
            fprintf(stderr, "something wrong in transform: %s", inputStr);
			return '$';//flush the stack of previous input in order to start again
		}
		return getch();//buf[--bufp];//encapsulation
	}
}
