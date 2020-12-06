#include <iostream>
#include <queue>

#include "QDebug"

#include "windows.h"

#include "boost/date_time/posix_time/posix_time.hpp"

#include "segmentrasterizator.hpp"
#include "director.hpp"

std::string now_str()
{
    const boost::posix_time::ptime now =
        boost::posix_time::microsec_clock::local_time();

    const boost::posix_time::time_duration td = now.time_of_day();

    const long hours        = td.hours();
    const long minutes      = td.minutes();
    const long seconds      = td.seconds();
    const long milliseconds = td.total_milliseconds() -
                              ((hours * 3600 + minutes * 60 + seconds) * 1000);

    char buf[40];
    sprintf(buf, "%02ld:%02ld:%02ld.%03ld",
        hours, minutes, seconds, milliseconds);

    return buf;
}

void startPrepareConstantsForRB(SegmentRasterizator *sr) { sr->prepareConstantsForRB(); }

void startRastSegment(SegmentRasterizator *sr) { sr->rastSegment(); }

void startCreateImg(SegmentRasterizator *sr) { sr->createImg(); }

std::queue<SegmentRasterizator> getStartQueue(int numOfSegments)
{
    std::queue<SegmentRasterizator> queue;
    for (int i = 0; i < numOfSegments; i++)
    {
        int x = rand() % (WIDTH - 50) + 1;
        int y = rand() % (HEIGHT - 50) + 1;
        queue.push(SegmentRasterizator(x, y, x + 50, y + 50));
    }
    return queue;
}

int main()
{
    std::queue<SegmentRasterizator> startQueue = getStartQueue(NUM_OF_SEGMENTS);

    Director dir(startQueue);
    dir.initWork();

    auto result = dir.getFinal();
    for (int i = 0; i < (int)result.size(); i++)
        qDebug() << result.at(i).getDotsOfSegment();

    std::cout << now_str();

    return 0;
}
