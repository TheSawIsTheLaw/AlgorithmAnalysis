#ifndef SEGMENTRASTERIZATOR_HPP
#define SEGMENTRASTERIZATOR_HPP

#include <vector>
#include <math.h>

#include <QImage>

#include "config.hpp"

class SegmentRasterizator
{
public:
    SegmentRasterizator(int xStart_, int yStart_, int xEnd_, int yEnd_);

    void prepareConstantsForRB(); // Подготовка констант для алгоритма Брезенхема
    void rastSegment(); // Растеризация отрезка в форе
    void createImg(); // Создание QImage с изображённым отрезком

    std::vector<std::pair<int, int>> getDotsOfSegment();

private:
    float xStart, yStart, xEnd, yEnd;

    bool stepFlag;
    float deltaX, deltaY;
    int stepX, stepY;
    float tngModule;
    float mistake;

    std::vector<std::pair<int, int>> dotsOfSegment;

    QImage *image;
};


#endif // SEGMENTRASTERIZATOR_HPP
