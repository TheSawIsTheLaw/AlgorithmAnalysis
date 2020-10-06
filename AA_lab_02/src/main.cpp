#include<iostream>
#include<vector>

void makeMat(std::vector<std::vector<int>> &mat, size_t rows, size_t cols)
{
    for (size_t i = 0; i < rows; i++)
        mat.push_back(std::vector<int>(cols));
}

std::vector<int> *rowsComp(std::vector<std::vector<int>> &matrix)
{
    std::vector<int> *ret = new std::vector<int>(matrix.size());

    for (size_t i = 0; i < matrix.size(); i++)
        for (size_t j = 0; j < (matrix[0].size() - 1) / 2; j++)
            ret->at(i) = ret->at(i) + matrix[i][j * 2] * matrix[i][j * 2 + 1];

    return ret;
}

std::vector<int> *colsComp(std::vector<std::vector<int>> &matrix)
{
    std::vector<int> *ret = new std::vector<int>(matrix[0].size());

    for (size_t i = 0; i < (matrix.size() - 1) / 2; i++)
        for (size_t j = 0; j < matrix[0].size(); j++)
            ret->at(j) = ret->at(j) + matrix[i * 2][j] * matrix[i * 2 + 1][j];

    return ret;
}

std::vector<std::vector<int>> matricesMult(std::vector<std::vector<int>> &fMatrix, std::vector<std::vector<int>> &sMatrix)
{
    std::vector<std::vector<int>> product;
    makeMat(product, fMatrix.size(), sMatrix[0].size());
    for (size_t i = 0; i < fMatrix.size(); i++)
        for (size_t j = 0; j < sMatrix[0].size(); j++)
            for (size_t k = 0; k < fMatrix[0].size(); k++)
                product[i][j] += fMatrix[i][k] * sMatrix[k][j];
    return product;
}

std::vector<std::vector<int>> *WindogradMult(std::vector<std::vector<int>> &fMatrix, std::vector<std::vector<int>> &sMatrix)
{
    if (fMatrix[0].size() != sMatrix.size())
        return NULL;

    std::vector<int> *compRows = rowsComp(fMatrix);
    std::vector<int> *compCols = colsComp(sMatrix);

    std::vector<std::vector<int>> *product = new std::vector<std::vector<int>>;
    makeMat(*product, fMatrix.size(), sMatrix[0].size());

    for (size_t i = 0; i < product->size(); i++)
    {
        for (size_t j = 0; j < product->at(i).size(); j++)
        {
            product->at(i)[j] = -compRows->at(i) - compCols->at(j);

            for (size_t k = 0; k < sMatrix.size() / 2; k++)
                product->at(i)[j] = product->at(i)[j] + (fMatrix[i][k * 2] + sMatrix[k * 2 + 1][j]) * (fMatrix[i][k * 2 + 1] + sMatrix[k * 2][j]);
        }
    }

    if (sMatrix.size() % 2 != 0)
    {
        for (size_t i = 0; i < product->size(); i++)
            for (size_t j = 0; j < product->at(i).size(); j++)
                product->at(i)[j] = product->at(i)[j] + fMatrix[i][sMatrix.size() - 1] * sMatrix[sMatrix.size() - 1][j];
    }
    return product;
}

std::vector<int> *rowsCompMod(std::vector<std::vector<int>> &matrix)
{
    std::vector<int> *ret = new std::vector<int>(matrix.size());

    for (size_t i = 0; i < matrix.size(); i++)
        for (size_t j = 0; j < (matrix[0].size() - 1); j += 2)
            ret->at(i) = ret->at(i) + matrix[i][j] * matrix[i][j + 1];

    return ret;
}

std::vector<int> *colsCompMod(std::vector<std::vector<int>> &matrix)
{
    std::vector<int> *ret = new std::vector<int>(matrix[0].size());

    for (size_t i = 0; i < (matrix.size() - 1); i += 2)
        for (size_t j = 0; j < matrix[0].size(); j++)
            ret->at(j) += matrix[i][j] * matrix[i + 1][j];

    return ret;
}

std::vector<std::vector<int>> *WindogradMultMod(std::vector<std::vector<int>> &fMatrix, std::vector<std::vector<int>> &sMatrix)
{
    if (fMatrix[0].size() != sMatrix.size())
        return NULL;

    std::vector<int> *compRows = rowsCompMod(fMatrix);
    std::vector<int> *compCols = colsCompMod(sMatrix);

    std::vector<std::vector<int>> *product = new std::vector<std::vector<int>>;
    makeMat(*product, fMatrix.size(), sMatrix[0].size());

    for (size_t i = 0; i < product->size(); i++)
    {
        for (size_t j = 0; j < product->at(i).size(); j++)
        {
            product->at(i)[j] = -compRows->at(i) - compCols->at(j);

            for (size_t k = 0; k < sMatrix.size(); k += 2)
                product->at(i)[j] += (fMatrix[i][k] + sMatrix[k + 1][j]) * (fMatrix[i][k + 1] + sMatrix[k][j]);
        }
    }

    if (sMatrix.size() % 2 != 0)
    {
        for (size_t i = 0; i < product->size(); i++)
            for (size_t j = 0; j < product->at(i).size(); j++)
                product->at(i)[j] += fMatrix[i][sMatrix.size() - 1] * sMatrix[sMatrix.size() - 1][j];
    }
    return product;
}

void fullMatRandomly(std::vector<std::vector<int>> *matrix)
{
    for (size_t i = 0; i < matrix->size(); i++)
        for (size_t j = 0; j < matrix->at(0).size(); j++)
            matrix->at(i)[j] = rand() % 21 - 10;
}

void printOutMatrix(std::vector<std::vector<int>> &matrix)
{
    for (size_t i = 0; i < matrix.size(); i++)
    {
        for (size_t j = 0; j < matrix[0].size(); j++)
            printf("%6d", matrix[i][j]);
        printf("\n");
    }
}

int main()
{
    srand(time(NULL));
    std::vector<std::vector<int>> fMatrix;
    makeMat(fMatrix, 100, 100);
    fullMatRandomly(&fMatrix);
    std::vector<std::vector<int>> sMatrix;
    makeMat(sMatrix, 100, 100);
    fullMatRandomly(&sMatrix);

    clock_t start = clock();
    matricesMult(fMatrix, sMatrix);
    clock_t end = clock() - start;
    std::cout << "\nВремя работы 1:" << end;

    start = clock();
    WindogradMult(fMatrix, sMatrix);
    end = clock() - start;
    std::cout << "\nВремя работы 2:" << end;

    start = clock();
    WindogradMultMod(fMatrix, sMatrix);
    end = clock() - start;
    std::cout << "\nВремя работы 3:" << end;

}
