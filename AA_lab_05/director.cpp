#include "director.hpp"

Director::Director(std::queue<SegmentRasterizator> &startQueue_)
{
    startQueue = startQueue_;
}

void Director::processPrepare()
{
    for (SegmentRasterizator curSeg(startQueue.front()); startQueue.size();
         startQueue.pop())
    {
        curSeg.prepareConstantsForRB();
        middleQueue.push(curSeg);
    }
}

void Director::processRast()
{
    for (SegmentRasterizator curSeg(middleQueue.front());
         startQueue.size() || middleQueue.size(); middleQueue.pop())
    {
        curSeg.rastSegment();
        endQueue.push(curSeg);
    }
}

void Director::processCreate()
{
    for (SegmentRasterizator curSeg(endQueue.front());
         startQueue.size() || middleQueue.size() || endQueue.size(); endQueue.pop())
    {
        curSeg.rastSegment();
        final.push_back(curSeg);
    }
}

void Director::initWork()
{
    workers[0] = std::thread(&Director::processPrepare, this);
    workers[1] = std::thread(&Director::processRast, this);
    workers[2] = std::thread(&Director::processCreate, this);

    workers[0].join();
    workers[1].join();
    workers[2].join();
}

std::vector<SegmentRasterizator> Director::getFinal() { return final; }
