package model;

public class ProcentRabat implements RabatStrategy {

    private double discountProcent; // ex: 7%...

    public ProcentRabat(double discountProcent){
        this.discountProcent = discountProcent;
    }

    @Override
    public double getRabat(double pris) {
      return pris * (discountProcent / 100);
    }

    public double getDiscountProcent() {
        return discountProcent;
    }

    public void setDiscountProcent(double discountProcent) {
        this.discountProcent = discountProcent;
    }
}
