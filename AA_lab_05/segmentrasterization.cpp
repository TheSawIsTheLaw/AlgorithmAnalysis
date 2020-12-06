#include "segmentrasterization.hpp"

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
}

void SegmentRasterizator::prepareConstantsForRB()
{
    deltaX = xEnd - xStart;
    deltaY = yEnd - yStart;

    stepX = int(signbit(deltaX) ? deltaX : -deltaX);
    stepY = int(signbit(deltaY) ? deltaY : -deltaY);

    deltaX = std::abs(deltaX);
    deltaY = std::abs(deltaY);

    if (deltaX < deltaY)
    {
        std::swap(deltaX, deltaY);
        stepFlag = true;
    }
    else
        stepFlag = false;

    accConst = deltaY / deltaX - 0.5;
}

void SegmentRasterizator::rastSegment()
{

}

void SegmentRasterizator::createImg()
{

}
