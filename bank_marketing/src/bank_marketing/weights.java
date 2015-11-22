package bank_marketing;

public class weights {
	double weights [][];
	double weights_temp [][];
	double delta[][];
	public weights(int from,int to,double value){
		weights = new double[from][to];
		weights_temp  = new double[from][to];
		delta = new double[from][to];
		for(int i = 0 ; i < from ; i++){
			for(int j = 0 ; j < to ; j++){
				weights[i][j] = /*((i+1)*(j+1))/((from+1)*(to+1))*/Math.random()/10/*0.2*/ ;
				weights_temp[i][j] = weights[i][j];
				delta[0][0] = 0;
			}
		}
	}
}
