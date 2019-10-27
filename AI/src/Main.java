public class Main {


    public static String trycard(String string) {
        System.out.println(string);
        String gcard =  string;
        Scard scard = new Scard(gcard);
        String res = scard.Doit();
        System.out.println(res);
        return res;
    }

}
