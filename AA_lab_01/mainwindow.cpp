#include "mainwindow.hpp"
#include "ui_mainwindow.h"

#include <iostream>

#include "QtDebug"
#include <windows.h>

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->label->setPixmap(QPixmap("../AA_lab_01/imgs/cyber.jpg"));
}

MainWindow::~MainWindow() { delete ui; }

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

size_t MainWindow::damerauRecursiveMatrix(
QString fWord, QString sWord, std::vector<std::vector<int>> &matrix)
{
    qDebug() << "Current Words are: " << fWord << sWord;

    if (matrix[sWord.size()][fWord.size()] != std::numeric_limits<int>().max())
        return matrix[sWord.size()][fWord.size()];

    matrix[sWord.size()][fWord.size()] =
    std::min({damerauRecursiveMatrix(fWord.mid(0, fWord.size() - 1), sWord, matrix) + 1,
    damerauRecursiveMatrix(fWord, sWord.mid(0, sWord.size() - 1), matrix) + 1,
    damerauRecursiveMatrix(
    fWord.mid(0, fWord.size() - 1), sWord.mid(0, sWord.size() - 1), matrix) +
    ((fWord.back() == sWord.back()) ? 0 : 1)});

    return matrix[sWord.size()][fWord.size()];
}

size_t MainWindow::damerauNonRecursiveMatrix(
QString fWord, QString sWord, std::vector<std::vector<int>> &matrix)
{
    for (int i = 1; i <= sWord.size(); i++)
        for (int j = 1; j <= fWord.size(); j++)
            matrix[i][j] = std::min({matrix[i - 1][j] + 1, matrix[i][j - 1] + 1,
            matrix[i - 1][j - 1] +
            (((fWord.mid(0, j)).back() == sWord.mid(0, i).back()) ? 0 : 1)});

    return matrix[sWord.size()][fWord.size()];
}

bool canBeTranspose(QString fStr, QString sStr, size_t i, size_t j)
{
    return fStr.at(j - 1) == sStr.at(i - 2) && fStr.at(j - 2) == sStr.at(i - 1);
}

size_t MainWindow::damerauLev(
QString fWord, QString sWord, std::vector<std::vector<int>> &matrix)
{
    for (int i = 1; i <= sWord.size(); i++)
        for (int j = 1; j <= fWord.size(); j++)
        {
            int temp = std::min({matrix[i - 1][j] + 1, matrix[i][j - 1] + 1,
            matrix[i - 1][j - 1] +
            (((fWord.mid(0, j)).back() == sWord.mid(0, i).back()) ? 0 : 1)});
            if (i > 1 && j > 1 && canBeTranspose(fWord, sWord, i, j))
                temp = std::min(temp, matrix[i - 2][j - 2] + 1);
            matrix[i][j] = temp;
        }
    return matrix[sWord.size()][fWord.size()];
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

void MainWindow::on_DamerauRecursiveMatrix_clicked()
{
    QString fWord, sWord;
    getTwoWords(fWord, sWord);
    qDebug() << "GOT IN DAMERAU RECURSIVE MAT" << fWord << sWord;
    std::vector<std::vector<int>> matrix;
    for (int i = 0; i <= sWord.size(); i++)
        matrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < matrix.size(); i++)
        for (size_t j = 0; j < matrix[0].size(); j++)
            matrix[i][j] = std::numeric_limits<int>().max();

    for (size_t i = 0; i < matrix.size(); i++) matrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) matrix[0][i] = i;

    for (size_t i = 0; i < matrix.size(); i++)
    {
        for (size_t j = 0; j < matrix[0].size(); j++) std::cout << matrix[i][j];
        std::cout << "\n";
    }

    size_t answer = damerauRecursiveMatrix(fWord, sWord, matrix);
    qDebug() << "READY IN DAMERAU RECURSIVE MAT" << answer;
    std::cout << "Matrix: \n";
    for (size_t i = 0; i < matrix.size(); i++)
    {
        for (size_t j = 0; j < matrix[0].size(); j++) std::cout << matrix[i][j];
        std::cout << "\n";
    }
}

void MainWindow::on_DamerauNonRecursiveMatrix_clicked()
{
    QString fWord, sWord;
    getTwoWords(fWord, sWord);
    qDebug() << "GOT IN DAMERAU NON-RECURSIVE MAT" << fWord << sWord;
    std::vector<std::vector<int>> matrix;
    for (int i = 0; i <= sWord.size(); i++)
        matrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < matrix.size(); i++) matrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) matrix[0][i] = i;

    for (size_t i = 0; i < matrix.size(); i++)
    {
        for (size_t j = 0; j < matrix[0].size(); j++) std::cout << matrix[i][j];
        std::cout << "\n";
    }

    size_t answer = damerauNonRecursiveMatrix(fWord, sWord, matrix);
    qDebug() << "READY IN DAMERAU NON-RECURSIVE MAT" << answer;
    std::cout << "Matrix: \n";
    for (size_t i = 0; i < matrix.size(); i++)
    {
        for (size_t j = 0; j < matrix[0].size(); j++) std::cout << matrix[i][j];
        std::cout << "\n";
    }
}

void MainWindow::on_DamerauLevenshtein_clicked()
{
    QString fWord, sWord;
    getTwoWords(fWord, sWord);
    std::vector<std::vector<int>> matrix;
    for (int i = 0; i <= sWord.size(); i++)
        matrix.push_back(std::vector<int>(fWord.size() + 1));

    qDebug() << "max i = " << matrix.size() << "max j: " << matrix[0].size();

    for (size_t i = 0; i < matrix.size(); i++) matrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) matrix[0][i] = i;

    LARGE_INTEGER li;
    LARGE_INTEGER notli;
    QueryPerformanceCounter(&li);
    __int64 counterStart = li.QuadPart;
    size_t answer = damerauLev(fWord, sWord, matrix);
    QueryPerformanceCounter(&notli);
    __int64 result = notli.QuadPart - counterStart;
    qDebug() << result;

    qDebug() << "READY IN DAMERAU-LEV" << answer;

}
