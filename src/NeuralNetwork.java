public class NeuralNetwork {
    private int input_nodes;
    private int output_nodes;
    private int hidden_nodes;
    private Matrix weights_ih;
    private Matrix weights_ho;
    private Matrix bias_h;
    private Matrix bias_o;
    private double learning_rate;
    private Function activation_function;


    NeuralNetwork(int in_nodes, int hid_nodes, int out_nodes) {
        this.input_nodes = in_nodes;
        this.hidden_nodes = hid_nodes;
        this.output_nodes = out_nodes;

        this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
        this.weights_ho = new Matrix(this.output_nodes, this.hidden_nodes);
        this.weights_ih.randomize();
        this.weights_ho.randomize();

        this.bias_h = new Matrix(this.hidden_nodes, 1);
        this.bias_o = new Matrix(this.output_nodes, 1);
        this.bias_h.randomize();
        this.bias_o.randomize();


        this.setLearningRate();
        this.setActivationFunction();

    }

    Matrix predict(double[] input_array) {
        // Generating the Hidden Outputs
        Matrix inputs = Matrix.fromArray(input_array);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);

        hidden.map(this.activation_function);

        // Generating the output's output!
        Matrix output = Matrix.multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        output.map(this.activation_function);

        // Sending back to the caller!
        return output;
    }

    void setLearningRate(double learning_rate) {
        this.learning_rate = learning_rate;
    }

    void setLearningRate() {
        this.learning_rate = 0.1;
    }

    void setActivationFunction(Function function) {
        this.activation_function = function;
    }

    void setActivationFunction() {
        this.activation_function = new sigmoid();
    }

    // Adding function for neuro-evolution
    NeuralNetwork copy() throws CloneNotSupportedException {
        return (NeuralNetwork) this.clone();
    }

    // Accept an arbitrary function for mutation
    void mutate(Function func) {
        this.weights_ih.map(func);
        this.weights_ho.map(func);
        this.bias_h.map(func);
        this.bias_o.map(func);
    }

}

class sigmoid implements Function{
    public double resultReturn(double element) {
        return 1 / (1 + Math.exp(-element));
    }
}



