package SmartRobots.Utility;

import java.util.LinkedList;

public class NeuralNetwork {
    private int input_nodes;
    private int output_nodes;
    private int hidden_nodes;
    private int hidden_layers_qnt = 1;
    private Matrix weights_ih;
    private Matrix weights_ho;
    private Matrix bias_h;
    private Matrix bias_o;
    private LinkedList<Matrix> weights_hidden;
    private LinkedList<Matrix> biases_hidden;
    LinkedList<Matrix> hidden_layers;
    private double learning_rate;
    private Function activation_function;


    public NeuralNetwork(int in_nodes, int hid_nodes, int hidden_layers, int out_nodes) {
        this.input_nodes = in_nodes;
        this.hidden_nodes = hid_nodes;
        this.output_nodes = out_nodes;

        this.hidden_layers_qnt = hidden_layers;
        this.weights_hidden = new LinkedList<>();
        this.biases_hidden = new LinkedList<>();


        // Generate weights
        for (int i = 0; i < this.hidden_layers_qnt; i++) {
            if (i == 0) {
                this.weights_hidden.add(new Matrix(this.hidden_nodes, this.input_nodes));
            } else if (i == this.hidden_layers_qnt -1) {
                this.weights_hidden.add(new Matrix(this.output_nodes, this.hidden_nodes));
            } else {
                this.weights_hidden.add(new Matrix(this.hidden_nodes, this.hidden_layers_qnt));
            }
        }


        // Random weights start
        for (Matrix m : this.weights_hidden) {
            m.randomize();
        }

        // Generate biases
        for (int i = 0; i < this.hidden_layers_qnt; i++) {
            if (i != hidden_layers - 1) {
                this.biases_hidden.add(new Matrix(this.hidden_nodes, 1));
            } else {
                this.biases_hidden.add(new Matrix(this.output_nodes, 1));
            }
        }

        // Random biases start
        for (Matrix m : this.biases_hidden) {
            m.randomize();
        }

        this.setLearningRate();
        this.setActivationFunction();
    }

    public Matrix predict(double[] input_array) {
        // Generating the Hidden Outputs
        Matrix inputs = Matrix.fromArray(input_array);
        this.hidden_layers = new LinkedList<>();

        // Multiply
        // Add
        // Map
        for (int i = 0; i < this.hidden_layers_qnt; i++) {
            if (i == 0) {
                this.hidden_layers.add(Matrix.multiply(this.weights_hidden.get(0), inputs));
                this.hidden_layers.get(0).add(this.biases_hidden.get(0));
                this.hidden_layers.get(0).map(this.activation_function);
            } else {
                this.hidden_layers.add(Matrix.multiply(this.weights_hidden.get(i), this.hidden_layers.get(i - 1)));
                this.hidden_layers.get(i).add(this.biases_hidden.get(i));
                this.hidden_layers.get(i).map(this.activation_function);
            }
        }

        // Sending back to the caller!
        return this.hidden_layers.get(this.hidden_layers_qnt -1);
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
        this.activation_function = new tang();
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

class tang implements Function{
    @Override
    public double resultReturn(double element) {
        return Math.tanh(element);
    }
}

class sigmoid implements Function{
    public double resultReturn(double element) {
        return 1 / (1 + Math.exp(-element));
    }
}



