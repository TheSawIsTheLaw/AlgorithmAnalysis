#ifndef STRINGDIALOG_HPP
#define STRINGDIALOG_HPP

#include <QDialog>

namespace Ui {
class StringDialog;
}

class StringDialog : public QDialog
{
    Q_OBJECT

public:
    explicit StringDialog(QWidget *parent = nullptr);
    ~StringDialog();

    bool areStringsValid();
    QString &getFirstWord();
    QString &getSecondString();

private slots:
    void on_buttonBox_accepted();

private:
    Ui::StringDialog *ui;

    bool validity = false;

    QString firstWord;
    QString secondWord;
};

#endif // STRINGDIALOG_HPP
