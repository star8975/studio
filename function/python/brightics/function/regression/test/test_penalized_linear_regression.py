import unittest
from brightics.common.datasets import load_iris
from brightics.function.transform import split_data
from brightics.function.regression import penalized_linear_regression_train, penalized_linear_regression_predict


class TestPenalizedLinearRegression(unittest.TestCase):

    def setUp(self):
        print("*** Penalized Linear Regression UnitTest Start ***")
        self.iris = load_iris()

    def tearDown(self):
        print("*** Penalized Linear Regression UnitTest End ***")

    def test_penalized_linear_regression_train_predict(self):
        input_dataframe = self.iris
        
        df_splitted = split_data(table=input_dataframe, train_ratio=7.0, test_ratio=3.0, random_state=1)
        train_df = df_splitted['train_table']
        test_df = df_splitted['test_table']
        
        res_train = penalized_linear_regression_train(table=train_df,
                                              feature_cols=['sepal_length', 'sepal_width'], label_col='petal_length', random_state=1)
        res_predict = penalized_linear_regression_predict(table=test_df, model=res_train['model'])
        
        print(res_predict['out_table'])
        table = res_predict['out_table'].values.tolist()
        self.assertListEqual(table[0][:5], [5.8, 4, 1.2, 0.2, 'setosa'])
        self.assertListEqual(table[1][:5], [5.1, 2.5, 3, 1.1, 'versicolor'])
        self.assertListEqual(table[2][:5], [6.6, 3, 4.4, 1.4 , 'versicolor'])
        self.assertListEqual(table[3][:5], [5.4, 3.9, 1.3, 0.4, 'setosa'])
        self.assertListEqual(table[4][:5], [7.9, 3.8, 6.4, 2, 'virginica'])
        self.assertAlmostEqual(table[0][5], 2.6620604866852506, places=10)
        self.assertAlmostEqual(table[1][5], 3.0806000137074365, places=10)
        self.assertAlmostEqual(table[2][5], 5.22006528333164, places=10)
        self.assertAlmostEqual(table[3][5], 2.0542480818158095, places=10)
        self.assertAlmostEqual(table[4][5], 6.664096854339767, places=10)


if __name__ == '__main__':
    unittest.main()
