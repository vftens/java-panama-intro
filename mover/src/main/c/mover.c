#include "mover.h"

__declspec(dllexport)
void move(Point *point) {
    point->x = 4;
    point->y = 2;
}
