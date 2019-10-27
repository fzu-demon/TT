import net.sf.json.JSON;

import org.json.JSONObject;

public class USB {

    public static void main(String[] args) {
        String gcard =  "&9 $4 &6 *3 *10 #2 #K $5 *4 #J &3 #A *2";
        Scard scard = new Scard(gcard);
        String res = scard.Doit();
        System.out.println(res);
    }
}
