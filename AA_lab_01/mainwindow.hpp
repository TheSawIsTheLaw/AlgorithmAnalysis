#ifndef MAINWINDOW_HPP
#define MAINWINDOW_HPP

#include <QMainWindow>

#include "stringdialog.hpp"

#define Z_TEST 10

QT_BEGIN_NAMESPACE
namespace Ui
{
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    size_t levenshteinRecursive(QString fWord, QString sWord);
    size_t levenshteinRecursiveMatrix(QString fWord, QString sWord, std::vector<std::vector<int> > &matrix);
    size_t levenshteinNonRecursiveMatrix(QString fWord, QString sWord, std::vector<std::vector<int> > &matrix);
    size_t damerauLev(QString fWord, QString sWord, std::vector<std::vector<int>> &matrix);

    bool getTwoWords(QString &fWord_, QString &sWord_);

private slots:
    void on_levenshteinRecursive_clicked();

    void on_levenshteinRecursiveMatrix_clicked();

    void on_levenshteinNonRecursiveMatrix_clicked();

    void on_damerauLevenshtein_clicked();

private:
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_HPP
