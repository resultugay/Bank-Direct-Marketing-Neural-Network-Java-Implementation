package bank_marketing;

public class layer {
	//number of neurons for the given layer
	neuron neurons [];
	public layer(int number_of_neuron/*,int i ,int j*/){
		neurons = new neuron[number_of_neuron];
		for(int u = 0 ; u < number_of_neuron ; u++){
			neurons[u] = new neuron();
		}

		
	}
}
