package foi.youcandoit;

public class KalkulatorKalorija{
double brojKalorija;
	
public double Izracun(int aktivnost, int vrijeme, double prosBrzina){
	    switch (aktivnost) {
		case 1:
			brojKalorija = vrijeme*prosBrzina*60;
			break;
			
		case 2:
			brojKalorija = vrijeme*prosBrzina*24;
			break;
			
		case 3:
			brojKalorija = vrijeme*420;
			break;
			
		default:
			brojKalorija = 3;
			break;
		}
		return brojKalorija;
	}

}
