#ifndef _ADDER_H
#define _ADDER_H

typedef struct {
    int x;
    int y;
} Point;

__declspec(dllexport) void move(Point*);

#endif
