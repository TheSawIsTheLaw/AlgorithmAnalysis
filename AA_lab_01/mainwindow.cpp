#include "mainwindow.hpp"
#include "ui_mainwindow.h"

#include "QtDebug"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->label->setPixmap(QPixmap("../AA_lab_01/imgs/cyber.jpg"));
}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_DamerauRecursive_clicked()
{
    StringDialog stringWindow(nullptr);
    stringWindow.setModal(true);
    stringWindow.exec();
}
