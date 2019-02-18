#include "totalizer.h"

__declspec(dllexport)
long sum(int vals[], int size) {
    long r = 0;
    for (int i = 0; i < size; i++) {
        r += vals[i];
    }
    return r;
}
