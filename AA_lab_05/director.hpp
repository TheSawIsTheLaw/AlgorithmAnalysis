#ifndef DIRECTOR_HPP
#define DIRECTOR_HPP

#include <thread>
#include <queue>
#include "segmentrasterizator.hpp"

class Director
{
public:
    Director(std::queue<SegmentRasterizator> &startQueue_);

    void processPrepare();
    void processRast();
    void processCreate();

    void initWork();

    std::vector<SegmentRasterizator> getFinal();

private:
    std::queue<SegmentRasterizator> startQueue, middleQueue, endQueue;
    std::thread workers[3];

    std::vector<SegmentRasterizator> final;
};

#endif // DIRECTOR_HPP
