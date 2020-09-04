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

private:
    Ui::StringDialog *ui;
};

#endif // STRINGDIALOG_HPP
