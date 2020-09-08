#ifndef MAINWINDOW_HPP
#define MAINWINDOW_HPP

#include <QMainWindow>

#include "stringdialog.hpp"

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

    void getTwoWords(QString &fWord_, QString &sWord_);

private slots:
    void on_DamerauRecursive_clicked();

private:
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_HPP
