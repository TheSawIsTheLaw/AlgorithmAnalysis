#include "stringdialog.hpp"
#include "ui_stringdialog.h"

#include <QErrorMessage>

#include "QtDebug"

StringDialog::StringDialog(QWidget *parent) : QDialog(parent), ui(new Ui::StringDialog)
{
    ui->setupUi(this);
}

StringDialog::~StringDialog() { delete ui; }

void StringDialog::on_buttonBox_accepted()
{
    QString firstWord_ = ui->lineEdit->text();
    QString secondWord_ = ui->lineEdit_2->text();

    qDebug() << "IN STRDIALOG:" << firstWord_ << secondWord_;

    if (firstWord_.size() == 0 || secondWord_.size() == 0)
    {
        QErrorMessage *error = new QErrorMessage();
        error->showMessage(
        "One of words is empty. It's not so good. And not funny. Please, try again...");
        return;
    }

    firstWord = firstWord_;
    secondWord = secondWord_;
}

bool StringDialog::areStringsValid() { return firstWord.size() && secondWord.size(); }

QString &StringDialog::getFirstWord() { return firstWord; }

QString &StringDialog::getSecondString() { return secondWord; }
