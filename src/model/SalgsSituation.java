package model;

public class SalgsSituation {
    private String navn;

    public SalgsSituation(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    @Override
    public String toString() {
        return navn.toString();
    }
}

