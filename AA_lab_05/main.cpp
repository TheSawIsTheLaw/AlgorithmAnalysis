#include <iostream>
#include <thread>
#include <queue>

#include "QDebug"

#include "segmentrasterizator.hpp"

void startPrepareConstantsForRB(SegmentRasterizator *sr) { sr->prepareConstantsForRB(); }

void startRastSegment(SegmentRasterizator *sr) { sr->rastSegment(); }

void startCreateImg(SegmentRasterizator *sr) { sr->createImg(); }

std::queue<SegmentRasterizator> getStartQueue(int numOfSegments)
{
    std::queue<SegmentRasterizator> queue;
    for (int i = 0; i < numOfSegments; i++)
        queue.push(SegmentRasterizator(rand() % WIDTH + 1, rand() % HEIGHT + 1, rand() % WIDTH + 1, rand() % HEIGHT + 1));
    return queue;
}

int main()
{
    std::queue<SegmentRasterizator> startQueue = getStartQueue(NUM_OF_SEGMENTS);

    for (SegmentRasterizator curSeg(startQueue.front()); startQueue.size(); startQueue.pop())
    {
        curSeg.prepareConstantsForRB();
        curSeg.rastSegment();
        curSeg.createImg();
    }

    return 0;
}
