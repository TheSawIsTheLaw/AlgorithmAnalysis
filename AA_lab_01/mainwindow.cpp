#include "mainwindow.hpp"
#include "ui_mainwindow.h"

#include <iostream>

#include "QtDebug"

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->label->setPixmap(QPixmap("../AA_lab_01/imgs/cyber.jpg"));
}

MainWindow::~MainWindow() { delete ui; }

bool comp(int a, int b) { return a < b; }

size_t MainWindow::damerauRecursive(QString fWord, QString sWord)
{
    qDebug() << "Current Words are: " << fWord << sWord;

    if (!fWord.size() || !sWord.size())
        return fWord.size() + sWord.size();

    return std::min({damerauRecursive(fWord, sWord.mid(0, sWord.size() - 1)) + 1,
    damerauRecursive(fWord.mid(0, fWord.size() - 1), sWord) + 1,
                     damerauRecursive(fWord.mid(0, fWord.size() - 1), sWord.mid(0, sWord.size() - 1)) +
    ((fWord.back() == sWord.back()) ? 0 : 1)});
}

void MainWindow::getTwoWords(QString &fWord_, QString &sWord_)
{
    StringDialog stringWindow(nullptr);
    stringWindow.setModal(true);
    stringWindow.exec();

    //    if (!stringWindow.areStringsValid())
    //        return;

    fWord_ = stringWindow.getFirstWord();
    sWord_ = stringWindow.getSecondString();

    qDebug() << "GOT IN MAIN:" << fWord_ << sWord_;
}

void MainWindow::on_DamerauRecursive_clicked()
{
    QString fWord, sWord;
    getTwoWords(fWord, sWord);
    qDebug() << "GOT IN DAMERAU RECURSIVE" << fWord << sWord;

    size_t answer = damerauRecursive(fWord, sWord);
    qDebug() << "READY IN RECURSIVE: " << answer;
}
