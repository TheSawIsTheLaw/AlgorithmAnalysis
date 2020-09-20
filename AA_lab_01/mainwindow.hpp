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

    size_t damerauRecursive(QString fWord, QString sWord);
    size_t damerauRecursiveMatrix(QString fWord, QString sWord, std::vector<std::vector<int> > &matrix);
    size_t damerauNonRecursiveMatrix(QString fWord, QString sWord, std::vector<std::vector<int> > &matrix);
    size_t damerauLev(QString fWord, QString sWord, std::vector<std::vector<int>> &matrix);

    bool getTwoWords(QString &fWord_, QString &sWord_);

private slots:
    void on_DamerauRecursive_clicked();

    void on_DamerauRecursiveMatrix_clicked();

    void on_DamerauNonRecursiveMatrix_clicked();

    void on_DamerauLevenshtein_clicked();

private:
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_HPP
