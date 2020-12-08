#include "director.hpp"

#include "QDebug"

Director::Director(std::queue<SegmentRasterizator> &startQueue_)
{
    startQueue = startQueue_;
}

void Director::processPrepare()
{
    int i = 0;
    for (SegmentRasterizator curSeg(startQueue.front()); startQueue.size();
         startQueue.pop(), curSeg = startQueue.front())
    {

        curSeg.prepareConstantsForRB(i++);
        middleQueue.push(curSeg);
    }
}

void Director::processRast()
{
    int i = 0;
    while (startQueue.size() || middleQueue.size())
    {
        if (middleQueue.empty())
            continue;
        SegmentRasterizator curSeg(middleQueue.front());

        curSeg.rastSegment(i++);

        endQueue.push(curSeg);
        middleQueue.pop();
    }
}

void Director::processCreate()
{
    int i = 0;
    while (startQueue.size() || middleQueue.size() || endQueue.size())
    {
        if (endQueue.empty())
            continue;
        SegmentRasterizator curSeg(endQueue.front());

        curSeg.createImg(i++);
        endQueue.pop();
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
