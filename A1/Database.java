import java.util.ArrayList;
import java.util.HashMap;

public class Database implements IDatabase {

    HashMap<String, Integer> knownDrugs;

    Database() {
        knownDrugs = new HashMap<>();
        knownDrugs.put("amoxicillin", 120);
        knownDrugs.put("dolo", 30);
        knownDrugs.put("vicks", 80);
    }

    public int query(String drugname)
    {
        int quantityInStock = 0;
        if(knownDrugs.containsKey(drugname)) {
            quantityInStock = knownDrugs.get(drugname);
        }
        return quantityInStock;

    }

    public int claim(String drugname, int quantity)
    {
        int balanceInStock = query(drugname) - quantity;
        if(balanceInStock >= 0) {
            knownDrugs.put(drugname, balanceInStock);
        }
        return balanceInStock;
    }

    public boolean isDrugExist(String drugname) {
        boolean isExist;
        if(drugname != null && !drugname.trim().isEmpty() && knownDrugs.containsKey(drugname)) {
            isExist = true;
        }
        else {
            isExist = false;
        }
        return isExist;
    }
}