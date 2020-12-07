#include "segmentrasterizator.hpp"
#include "boost/date_time/posix_time/posix_time.hpp"

std::string now_str()
{
    const boost::posix_time::ptime now = boost::posix_time::microsec_clock::local_time();

    const boost::posix_time::time_duration td = now.time_of_day();

    const long hours = td.hours();
    const long minutes = td.minutes();
    const long seconds = td.seconds();
    const long milliseconds =
        td.total_milliseconds() - ((hours * 3600 + minutes * 60 + seconds) * 1000);

    char buf[40];
    sprintf(buf, "%02ld:%02ld:%02ld.%03ld", hours, minutes, seconds, milliseconds);

    return buf;
}

SegmentRasterizator::SegmentRasterizator(int xStart_, int yStart_, int xEnd_, int yEnd_)
{
    xStart = xStart_;
    yStart = yStart_;
    xEnd = xEnd_;
    yEnd = yEnd_;
    if (xStart == xEnd)
        xEnd += 1;
    else if (yStart == yEnd)
        yEnd += 1;

    image = NULL;
}

int sign(float num)
{
    return (num < -__FLT_EPSILON__) ? -1 : ((num > __FLT_EPSILON__) ? 1 : 0);
}

void SegmentRasterizator::prepareConstantsForRB(int index)
{
    std::printf(ANSI_BLUE_BRIGHT "From START worker: task %d BEGIN %s\n" ANSI_RESET, index, now_str().c_str());

    deltaX = xEnd - xStart;
    deltaY = yEnd - yStart;

    stepX = sign(deltaX);
    stepY = sign(deltaY);

    deltaX = std::abs(deltaX);
    deltaY = std::abs(deltaY);

    if (deltaX < deltaY)
    {
        std::swap(deltaX, deltaY);
        stepFlag = true;
    }
    else
        stepFlag = false;

    tngModule = deltaY / deltaX;
    mistake = tngModule - 0.5;

    std::printf(ANSI_BLUE_BRIGHT "From START worker: task %d ENDED %s\n" ANSI_RESET, index, now_str().c_str());
}

void SegmentRasterizator::rastSegment(int index)
{
    std::printf(ANSI_MAGENTA_BRIGHT "From MIDDLE worker: task %d BEGIN %s\n" ANSI_RESET, index, now_str().c_str());

    float curX = xStart, curY = yStart;
    for (int i = 0; i < deltaX; i++)
    {
        dotsOfSegment.push_back(std::pair<int, int>(curX, curY));
        if (stepFlag)
        {
            if (mistake >= 0)
                (curX += stepX, mistake--);
            curY += stepY;
        }
        else
        {
            if (mistake >= 0)
                (curY += stepY, mistake--);
            curX += stepX;
        }
        mistake += tngModule;
    }

    std::printf(ANSI_MAGENTA_BRIGHT "From MIDDLE worker: task %d ENDED %s\n" ANSI_RESET, index, now_str().c_str());
}

void SegmentRasterizator::createImg(int index)
{
    std::printf(ANSI_CYAN_BRIGHT"From END worker: task %d BEGIN %s\n" ANSI_RESET, index, now_str().c_str());

    if (image)
        delete image;
    image = new QImage(WIDTH, HEIGHT, QImage::Format_RGB32);
    image->fill(Qt::white);

    for (auto iter = dotsOfSegment.begin(); iter < dotsOfSegment.end(); iter++)
        image->setPixel(iter->first, iter->second, Qt::black);

    std::printf(ANSI_CYAN_BRIGHT " From END worker: task %d ENDED %s\n" ANSI_RESET, index, now_str().c_str());
}

std::vector<std::pair<int, int>> SegmentRasterizator::getDotsOfSegment()
{
    return dotsOfSegment;
}
