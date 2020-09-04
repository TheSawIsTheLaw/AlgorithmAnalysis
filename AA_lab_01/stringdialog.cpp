#include "stringdialog.hpp"
#include "ui_stringdialog.h"

StringDialog::StringDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::StringDialog)
{
    ui->setupUi(this);
}

StringDialog::~StringDialog()
{
    delete ui;
}
