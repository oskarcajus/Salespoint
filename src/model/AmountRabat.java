package model;

public class AmountRabat implements RabatStrategy {

    private double aftaltRabat; // ex) -200kr discount, hvis en kunde køber mere end 3000kr produkter.
    private double limitBeløb; // minimum purchase --> 3000kr

    public AmountRabat(double aftaltRabat, double limitBeløb) {
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
        double discount = 0;
        if(pris >= limitBeløb){
            discount = aftaltRabat;
            return discount;
        }
        else
            return 0.0; // no discount
    }
}
