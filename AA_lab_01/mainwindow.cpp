#include "mainwindow.hpp"
#include "ui_mainwindow.h"

#include <iostream>

#include "QtDebug"
#include <windows.h>

#include <fstream>

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->label->setPixmap(QPixmap("../AA_lab_01/imgs/cyber.jpg"));
}

MainWindow::~MainWindow() { delete ui; }

void createCSVfileForMatrix(const char *fileName, QString fWord, QString sWord,
std::vector<std::vector<int>> &matrix, size_t timeResult, size_t answer)
{
    std::ofstream outFile;
    outFile.open(fileName);
    outFile << "Result of operation;" << answer << std::endl;
    outFile << "Time result(ticks);" << timeResult << std::endl;
    outFile << "Result Matrix:\n";
    outFile << ";";
    for (int i = 0; i < fWord.size(); i++) outFile << ";" << fWord.toUtf8().at(i);
    outFile << std::endl;
    for (size_t i = 0; i < matrix.size(); i++)
    {
        if (i)
            outFile << sWord.toUtf8().at(i - 1);
        for (size_t j = 0; j < matrix[0].size(); j++) outFile << ";" << matrix[i][j];
        outFile << std::endl;
    }

    outFile.close();
}

void createCSVfileForRecursive(const char *fileName, size_t timeResult, size_t answer)
{
    std::ofstream outFile;
    outFile.open(fileName);
    outFile << "Result of operation;" << answer << std::endl;
    outFile << "Time result(ticks);" << timeResult << std::endl;

    outFile.close();
}

bool MainWindow::getTwoWords(QString &fWord_, QString &sWord_)
{
    StringDialog stringWindow(nullptr);
    stringWindow.setModal(true);
    stringWindow.exec();

    if (!stringWindow.areStringsValid())
        return true;

    fWord_ = stringWindow.getFirstWord();
    sWord_ = stringWindow.getSecondString();

    qDebug() << "GOT IN MAIN:" << fWord_ << sWord_;
    return false;
}

size_t MainWindow::levenshteinRecursive(QString fWord, QString sWord)
{
    //    qDebug() << "Current Words are: " << fWord << sWord;
    if (!fWord.size() || !sWord.size())
        return fWord.size() + sWord.size();

    return std::min({levenshteinRecursive(fWord, sWord.mid(0, sWord.size() - 1)) + 1,
    levenshteinRecursive(fWord.mid(0, fWord.size() - 1), sWord) + 1,
    levenshteinRecursive(fWord.mid(0, fWord.size() - 1), sWord.mid(0, sWord.size() - 1)) +
    ((fWord.back() == sWord.back()) ? 0 : 1)});
}

void MainWindow::on_levenshteinRecursive_clicked()
{
    QString fWord /* = "VxgtUsx"*/, sWord /* = "jRMFAyC"*/;
    if (getTwoWords(fWord, sWord))
        return;
    qDebug() << "GOT IN levenshtein RECURSIVE" << fWord << sWord;

    LARGE_INTEGER li;
    LARGE_INTEGER notli;
    QueryPerformanceCounter(&li);

    __int64 counterStart = 0;
    __int64 result = 0;

    size_t answer = levenshteinRecursive(fWord, sWord);
    counterStart = li.QuadPart;
    levenshteinRecursive(fWord, sWord);
    QueryPerformanceCounter(&notli);
    result = notli.QuadPart - counterStart;
    qDebug() << result;

    createCSVfileForRecursive("result.csv", result, answer);

    qDebug() << "READY IN RECURSIVE: " << answer;
}

size_t MainWindow::levenshteinRecursiveMatrix(
QString fWord, QString sWord, std::vector<std::vector<int>> &matrix)
{
    //    qDebug() << "Current Words are: " << fWord << sWord;
    if (matrix[sWord.size()][fWord.size()] != std::numeric_limits<int>().max())
        return matrix[sWord.size()][fWord.size()];

    matrix[sWord.size()][fWord.size()] =
    std::min({levenshteinRecursiveMatrix(fWord.mid(0, fWord.size() - 1), sWord, matrix) + 1,
    levenshteinRecursiveMatrix(fWord, sWord.mid(0, sWord.size() - 1), matrix) + 1,
    levenshteinRecursiveMatrix(
    fWord.mid(0, fWord.size() - 1), sWord.mid(0, sWord.size() - 1), matrix) +
    ((fWord.back() == sWord.back()) ? 0 : 1)});

    return matrix[sWord.size()][fWord.size()];
}

void MainWindow::on_levenshteinRecursiveMatrix_clicked()
{
    QString
    fWord /*= "VxgtUsx2u39dtX81sxy8GInrYeVNmJvvG7WkaA7Qjs82qP6bJGOoryez5fYpJWcPRhm7"
            "TEjeUoD49M26XDtCJrGtjJXf3aZ9La9nshv3cAbwuAJuKc00ndp6EWNHQcArjwXQzAtd"
            "pnHs2uOF1kfhWjzXUS44zKnHVNCaeLyzBlce3RCdGwbJx8s2SlfvYoyBZsKrN1cX"*/
    ,
    sWord /*= "jRMFAyCfiVxyhmILtGMG4IVZTjPQ7laMIEG6xv9zbdXq9WcJY2G4J0JV1XP8ecmHkTYd"
            "Y1uzSm8WFY3KjgGggAw3GrPISl76Mzb1f3ElDEyOeorQGS6CxLWS3lH8sNgZta9vSDML"
            "vnbPaXP24H5dYkBXLRruvzSlLs1T8hyezy0U3awz65ctATEclCBG4H1pC9mMusWF"*/
    ;
    if (getTwoWords(fWord, sWord))
        return;
    qDebug() << "GOT IN levenshtein RECURSIVE MAT" << fWord << sWord;

    LARGE_INTEGER li;
    LARGE_INTEGER notli;
    QueryPerformanceCounter(&li);

    __int64 counterStart = 0;
    __int64 result = 0;

    std::vector<std::vector<int>> matrix;
    for (int i = 0; i <= sWord.size(); i++)
        matrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < matrix.size(); i++)
        for (size_t j = 0; j < matrix[0].size(); j++)
            matrix[i][j] = std::numeric_limits<int>().max();

    for (size_t i = 0; i < matrix.size(); i++) matrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) matrix[0][i] = i;

    size_t answer = levenshteinRecursiveMatrix(fWord, sWord, matrix);

    std::vector<std::vector<int>> newMatrix;
    for (int i = 0; i <= sWord.size(); i++)
        newMatrix.push_back(std::vector<int>(fWord.size() + 1));
    for (size_t i = 0; i < newMatrix.size(); i++)
        for (size_t j = 0; j < newMatrix[0].size(); j++)
            newMatrix[i][j] = std::numeric_limits<int>().max();

    for (size_t i = 0; i < matrix.size(); i++) newMatrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) newMatrix[0][i] = i;
    counterStart = li.QuadPart;
    levenshteinRecursiveMatrix(fWord, sWord, newMatrix);
    QueryPerformanceCounter(&notli);
    result = notli.QuadPart - counterStart;
    newMatrix.erase(newMatrix.begin(), newMatrix.end());
    qDebug() << result;

    createCSVfileForMatrix("result.csv", fWord, sWord, matrix, result, answer);

    //    qDebug() << "READY IN levenshtein RECURSIVE MAT" << answer;
}

size_t MainWindow::levenshteinNonRecursiveMatrix(
QString fWord, QString sWord, std::vector<std::vector<int>> &matrix)
{
    for (int i = 1; i <= sWord.size(); i++)
        for (int j = 1; j <= fWord.size(); j++)
            matrix[i][j] = std::min({matrix[i - 1][j] + 1, matrix[i][j - 1] + 1,
            matrix[i - 1][j - 1] +
            (((fWord.mid(0, j)).back() == sWord.mid(0, i).back()) ? 0 : 1)});

    return matrix[sWord.size()][fWord.size()];
}

void MainWindow::on_levenshteinNonRecursiveMatrix_clicked()
{
    QString
    fWord /* = "VxgtUsx2u39dtX81sxy8GInrYeVNmJvvG7WkaA7Qjs82qP6bJGOoryez5fYpJWcPRhm7"
             "TEjeUoD49M26XDtCJrGtjJXf3aZ9La9nshv3cAbwuAJuKc00ndp6EWNHQcArjwXQzAtd"
             "pnHs2uOF1kfhWjzXUS44zKnHVNCaeLyzBlce3RCdGwbJx8s2SlfvYoyBZsKrN1cX"*/
    ,
    sWord /* = "jRMFAyCfiVxyhmILtGMG4IVZTjPQ7laMIEG6xv9zbdXq9WcJY2G4J0JV1XP8ecmHkTYd"
             "Y1uzSm8WFY3KjgGggAw3GrPISl76Mzb1f3ElDEyOeorQGS6CxLWS3lH8sNgZta9vSDML"
             "vnbPaXP24H5dYkBXLRruvzSlLs1T8hyezy0U3awz65ctATEclCBG4H1pC9mMusWF"*/
    ;
    if (getTwoWords(fWord, sWord))
        return;
    qDebug() << "GOT IN levenshtein NON-RECURSIVE MAT" << fWord << sWord;

    LARGE_INTEGER li;
    LARGE_INTEGER notli;
    QueryPerformanceCounter(&li);

    __int64 counterStart = 0;
    __int64 result = 0;

    std::vector<std::vector<int>> matrix;
    for (int i = 0; i <= sWord.size(); i++)
        matrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < matrix.size(); i++) matrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) matrix[0][i] = i;

    size_t answer = levenshteinNonRecursiveMatrix(fWord, sWord, matrix);

    std::vector<std::vector<int>> newMatrix;
    for (int i = 0; i <= sWord.size(); i++)
        newMatrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < newMatrix.size(); i++) newMatrix[i][0] = i;

    for (size_t i = 0; i < newMatrix[0].size(); i++) newMatrix[0][i] = i;
    counterStart = li.QuadPart;
    levenshteinNonRecursiveMatrix(fWord, sWord, newMatrix);
    QueryPerformanceCounter(&notli);
    result = notli.QuadPart - counterStart;
    newMatrix.erase(newMatrix.begin(), newMatrix.end());
    qDebug() << result;

    createCSVfileForMatrix("result.csv", fWord, sWord, matrix, result, answer);

    //    qDebug() << "READY IN levenshtein NON-RECURSIVE MAT" << answer;
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

void MainWindow::on_damerauLevenshtein_clicked()
{
    QString fWord = "VxgtUsx2u39dtX81sxy8GInrYeVNmJvvG7WkaA7Qjs82qP6bJGOoryez5fYpJWcPRhm7"
                    "TEjeUoD49M26XDtCJrGtjJXf3aZ9La9nshv3cAbwuAJuKc00ndp6EWNHQcArjwXQzAtd"
                    "pnHs2uOF1kfhWjzXUS44zKnHVNCaeLyzBlce3RCdGwbJx8s2SlfvYoyBZsKrN1cX",
            sWord = "jRMFAyCfiVxyhmILtGMG4IVZTjPQ7laMIEG6xv9zbdXq9WcJY2G4J0JV1XP8ecmHkTYd"
                    "Y1uzSm8WFY3KjgGggAw3GrPISl76Mzb1f3ElDEyOeorQGS6CxLWS3lH8sNgZta9vSDML"
                    "vnbPaXP24H5dYkBXLRruvzSlLs1T8hyezy0U3awz65ctATEclCBG4H1pC9mMusWF";
    //    if (getTwoWords(fWord, sWord))
    //        return;

    LARGE_INTEGER li;
    LARGE_INTEGER notli;
    QueryPerformanceCounter(&li);
    __int64 counterStart = 0;
    __int64 result = 0;

    std::vector<std::vector<int>> matrix;
    for (int i = 0; i <= sWord.size(); i++)
        matrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < matrix.size(); i++) matrix[i][0] = i;

    for (size_t i = 0; i < matrix[0].size(); i++) matrix[0][i] = i;
    size_t answer = damerauLev(fWord, sWord, matrix);

    std::vector<std::vector<int>> newMatrix;
    for (int i = 0; i <= sWord.size(); i++)
        newMatrix.push_back(std::vector<int>(fWord.size() + 1));

    for (size_t i = 0; i < newMatrix.size(); i++) newMatrix[i][0] = i;

    for (size_t i = 0; i < newMatrix[0].size(); i++) newMatrix[0][i] = i;
    counterStart = li.QuadPart;
    damerauLev(fWord, sWord, newMatrix);
    QueryPerformanceCounter(&notli);
    result += notli.QuadPart - counterStart;
    newMatrix.erase(newMatrix.begin(), newMatrix.end());
    qDebug() << result;

    createCSVfileForMatrix("result.csv", fWord, sWord, matrix, result, answer);

    //    qDebug() << "READY IN levenshtein-LEV" << answer;
}
