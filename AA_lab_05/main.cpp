#include <iostream>
#include <queue>

#include "QDebug"

#include "windows.h"

#include "segmentrasterizator.hpp"
#include "director.hpp"

std::queue<SegmentRasterizator> getStartQueue(int numOfSegments)
{
    srand(time(0));
    std::queue<SegmentRasterizator> queue;
    for (int i = 0; i < numOfSegments; i++)
    {
        int x = rand() % (WIDTH - 500) + 1;
        int y = rand() % (HEIGHT - 500) + 1;
        queue.push(SegmentRasterizator(x, y, x + rand() % 500 + 1, y + rand() % 500 + 1));
    }
    return queue;
}

int main()
{
    std::queue<SegmentRasterizator> startQueue = getStartQueue(NUM_OF_SEGMENTS);

    Director dir(startQueue);
    dir.initWork();

    auto result = dir.getFinal();
//    for (int i = 0; i < (int)result.size(); i++)
//        qDebug() << result.at(i).getDotsOfSegment();

    return 0;
}
