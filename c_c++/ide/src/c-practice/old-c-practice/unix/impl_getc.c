#define NULL  0
#define EOF   (-1)
#define BUFSIZE  1024
#define OPEN_MAX 20

enum _flags {
	_READ = 01,
	_WRITE = 02,
	_UNBUF = 04,
	_EOF = 010,
	_ERR = 020
};

typedef struct _iobuf {
	int cnt;
	char *ptr;
	char *base;
	int flag;
	int fd;
} FILE;
FILE _iob[OPEN_MAX] = {
	{0, (char *)0, (char *)0, _READ, 0},
	{0, (char *)0, (char *)0, _WRITE, 1},
	{0, (char *)0, (char *)0, _WRITE | _UNBUF, 2}
};

#define stdin (&_iob[0])
#define stdout (&_iob[1])
#define stderr (&_iob[2])

int _fillbuf(FILE *);
int _flushbuf(int, FILE *);

#define _feof(p)   (((p)->flag & _EOF) != 0)
#define _ferror(p)  (((p)->flag & _ERR) != 0)
#define fileno(p)  ((p)->flag)

#define getc(p) (--(p)->cnt >= 0 \
		? (unsigned char) *(p)->ptr++ : _fillbuf(p)
//may be wrong
//#define putc(p) 

#define getchar getc(stdin)
#define putchar(x) putc((x), stdout)

