#ifndef SEGMENTRASTERIZATION_HPP
#define SEGMENTRASTERIZATION_HPP

#include <QtGui/qimage.h>
#include <QtGui/qrgb.h>
#include <vector>
#include <math.h>

class SegmentRasterizator
{
public:
    SegmentRasterizator(int xStart_, int yStart_, int xEnd_, int yEnd_);

    void prepareConstantsForRB(); // Подготовка констант для алгоритма Брезенхема
    void rastSegment(); // Растеризация отрезка в форе
    void createImg(); // Создание QImage с изображённым отрезком

private:
    float xStart, yStart, xEnd, yEnd;

    bool stepFlag;
    float deltaX, deltaY;
    int stepX, stepY;
    float accConst;

    std::vector<std::pair<int, int>> dotsOfSegment;

    QImage *image = nullptr;
};

#endif // SEGMENTRASTERIZATION_HPP
