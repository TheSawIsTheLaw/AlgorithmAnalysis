#include <iostream>

#include "segmentrasterizator.hpp"

int main()
{
    SegmentRasterizator sr(1, 1, 4, 5);
    sr.prepareConstantsForRB();
    sr.rastSegment();
    sr.createImg();

    return 0;
}
