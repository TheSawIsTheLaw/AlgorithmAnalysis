TEMPLATE = app

QT += gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
        main.cpp \
        segmentrasterization.cpp

HEADERS += \
    segmentrasterization.hpp
