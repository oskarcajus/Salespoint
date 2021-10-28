package model;

public class AmountRabat implements RabatStrategy {

    private double aftaltRabat; // ex) -100kr discount, hvis en kunde køber mere end 1000kr produkter.
    private double limitBeløb; // minimum purchase --> 1000kr

    public AmountRabat(int aftaltRabat, int limitBeløb) {
      this.aftaltRabat = aftaltRabat;
      this.limitBeløb = limitBeløb;
    }

    public double getAftaltRabat() {
        return aftaltRabat;
    }

    public void setAftaltRabat(double aftaltRabat) {
        this.aftaltRabat = aftaltRabat;
    }

    public double getLimitBeløb() {
        return limitBeløb;
    }

    public void setLimitBeløb(double limitBeløb) {
        this.limitBeløb = limitBeløb;
    }

    @Override
    public double getRabat(double pris) {
        double sum = 0;
        if(pris >= limitBeløb){
            sum = pris - aftaltRabat;
            return sum;
        }
        else
            return 0.0; // no discounts
    }
}
