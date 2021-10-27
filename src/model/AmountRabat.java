package model;

public class AmountRabat implements RabatStrategy {

    private double aftaltRabat; // -500kr discount, hvis firma kunder.
    private double limitDC; // minimum purchase --> 1000kr

    public AmountRabat(int aftaltRabat, int limitDC) {
      this.aftaltRabat = aftaltRabat;
      this.limitDC = limitDC;
    }

    public double getAftaltRabat() {
        return aftaltRabat;
    }

    public void setAftaltRabat(double aftaltRabat) {
        this.aftaltRabat = aftaltRabat;
    }

    public double getLimitDC() {
        return limitDC;
    }

    public void setLimitDC(double limitDC) {
        this.limitDC = limitDC;
    }

    //Override
    public double getRabat(double pris) {
        double sum;
        if(pris >= limitDC){
            sum = pris - aftaltRabat;
            return sum;
        }
        else
            return 0.0;
    }
}
