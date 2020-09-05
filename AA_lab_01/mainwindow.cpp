#include "mainwindow.hpp"
#include "ui_mainwindow.h"

#include "QtDebug"

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->label->setPixmap(QPixmap("../AA_lab_01/imgs/cyber.jpg"));
}

MainWindow::~MainWindow() { delete ui; }

void MainWindow::getTwoWords(QString &fWord_, QString &sWord_)
{
    StringDialog stringWindow(nullptr);
    stringWindow.setModal(true);
    stringWindow.exec();

    if (!stringWindow.areStringsValid())
        return;

    fWord_ = stringWindow.getFirstWord();
    sWord_ = stringWindow.getSecondString();

    qDebug() << "GOT IN MAIN:" << fWord_ << sWord_;
}

void MainWindow::on_DamerauRecursive_clicked()
{
    QString fWord, sWord;
    getTwoWords(fWord, sWord);
    if (fWord.size() == 0 || sWord.size() == 0)
        return;
    qDebug() << "GOT IN DAMERAU RECURSIVE" << fWord << sWord;

    std::vector<std::vector<size_t>> matrix(fWord.size(), std::vector<size_t>(sWord.size()));
    qDebug() << "START MATRIX" << matrix;
}
