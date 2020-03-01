import java.util.Arrays;

public class Matrix {

    private int cols;
    private int rows;
    private double data[][];

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[this.rows][this.cols];

        // System.out.println(Arrays.deepToString(this.data));
    }

    public Matrix copy() {
        Matrix m = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            System.arraycopy(this.data[i], 0, m.data[i], 0, this.cols);
        }
        return m;
    }

    public static Matrix fromArray(double[] arr) {
        Matrix m = new Matrix(arr.length, 1);

        for (int i = 0; i < arr.length; i++) m.data[i][0] = arr[i];

        return m;
    }

    static Matrix subtract(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B.");
            return new Matrix(0, 0);
        }

        // Return a new Matrix a-b
        Matrix m = new Matrix(a.rows, a.cols);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                m.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }

        return m;
    }

    double[] toArray() {
        double arr[] = new double[this.rows * this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                arr[i + j] = this.data[i][j];
            }
        }
        return arr;
    }

    void randomize() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    void add(Matrix n) {
        if (this.rows != n.rows || this.cols != n.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B.");
        } else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    this.data[i][j] += n.data[i][j];
                }
            }
        }
    }


    static Matrix multiply(Matrix a, Matrix b) {
        // Matrix product
        if (a.cols != b.rows) {
            System.out.println("Columns of A must match rows of B.");
            return new Matrix(0,0);
        }

        Matrix m = new Matrix(a.rows, b.cols);

        for (int row = 0; row < a.rows; row++) {
            for (int col = 0; col < m.data[row].length; col++) {
                m.data[row][col] = multiplyMatricesCell(a.data, b.data, row, col);
            }
        }

        return m;
    }

    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    void multiply(double n) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = this.data[i][j] * n;
            }
        }
    }


    void map(Function function) {
        // Apply a function to every element of matrix
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = function.resultReturn(this.data[i][j]);
            }
        }
    }

    static Matrix map(Matrix matrix, Function function) {
        for (int i = 0; i < matrix.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                matrix.data[i][j] = function.resultReturn(matrix.data[i][j]);
            }
        }

        return matrix;

    }

    public double[][] getData() {
        return data;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.data);
    }

}
