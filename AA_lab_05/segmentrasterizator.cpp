#include "segmentrasterizator.hpp"

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

void SegmentRasterizator::prepareConstantsForRB()
{
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
}

void SegmentRasterizator::rastSegment()
{
    float curX = xStart, curY = yStart;
    for (int i = 0; i < deltaX; i++)
    {
        dotsOfSegment.push_back(std::pair<int, int>(curX, curY));
        if (stepFlag)
        {
            if (mistake >= 0) (curX += stepX, mistake--);
            curY += stepY;
        }
        else
        {
            if (mistake >= 0) (curY += stepY, mistake--);
            curX += stepX;
        }
        mistake += tngModule;
    }
}

void SegmentRasterizator::createImg()
{
    if (image)
        delete image;
    image = new QImage(WIDTH, HEIGHT, QImage::Format_RGB32);
    image->fill(Qt::white);

    for (auto iter = dotsOfSegment.begin(); iter < dotsOfSegment.end(); iter++)
        image->setPixel(iter->first, iter->second, Qt::black);
}
