package gui.Salg;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.SalgsSituation;

public class SalgRundvisningPane extends GridPane {
    SalgsSituation salgsSituation;
    Label lblProdukter;
    ListView lvwProdukter;


    public SalgRundvisningPane (SalgsSituation salgsSituation) {
        this.salgsSituation = salgsSituation;
        this.setGridLinesVisible(false);
        this.initContent();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
    }
        public void initContent(){



        }


}
