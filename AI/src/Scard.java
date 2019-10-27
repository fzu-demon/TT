import static java.lang.System.out;

public class Scard {

    public  String card,answer1="",answer2="",answer3="";
    char[] cardto;
    int[][] scard = new int[4][15];  // 1->$ 2->& 3->* 4->#
    int[] shun = new int[15];
    int[] huase = new int[4];
    int flag = 0,rank,id1 = 0,id2 = 0;


    public Scard(String gcard) {
        card = gcard;
        if (card != null){
            cardto = card.toCharArray();
        }
    }  //构造函数，把牌引入

    String Doit(){
        sort();
        //display();
        solve();

        //display();
        shift();
        if ((id1 == 1 && id2 ==1) ||  (id1 == 4 && id2 ==4 ) ){
                last();
        }
        String res = "["+"\""+answer1+"\","+"\""+answer2+"\","+"\""+answer3+"\""+"]";
        return res;
    }  //主函数

    void sort(){
        int x = 0,y = 0;
        for (int i = 0 ; i <cardto.length ; i++){
            if (cardto[i] == '$'){
                x = 0;
            }
            else if (cardto[i] == '&'){
                x = 1;
            }
            else if (cardto[i] == '*'){
                x = 2;
            }
            else if (cardto[i] =='#'){
                x = 3;
            }
            i ++;
            if ('1' < cardto[i] && cardto[i]<='9'){
                y = cardto[i] - '0';
            }
            else{
                if (cardto[i] == '1' && cardto[i+1] == '0'){
                    y = 10;
                    i++;
                }
                else if ( cardto[i] == 'J'){
                    y = 11;
                }
                else if (cardto[i] == 'Q'){
                    y = 12;
                }
                else if (cardto[i] == 'K'){
                    y = 13;
                }
                else if (cardto[i] == 'A'){
                    y = 14;
                }
            }
            i ++;
            scard[x][y] = 1;
            shun[y]++;
            huase[x]++;
        }
    }   //牌存入数组

    void display(){
        for (int i =0,j = 0;i<scard.length;i++){
            for ( j=scard[i].length - 1;j>=0;j--){
                out.print(scard[i][j]);
            }
            out.println();
        }
        for (int x : shun){
            out.print(x + " ");
        }
        out.println();
        /*for (int x : shun){
            out.print(x);
        }
        for (int x: huase){
            out.println(x);
        }*/
    } //观察数组  Debug

    void solve(){
        rank = 3;
        flag = 0;
        rankcard();
        id1 = flag;
        rank = 2;
        flag = 0;
        rankcard();
        id2 = flag;

    }  //排牌

    void rankcard(){
        flush();
        if (flag == 0){
            bomb();
        }
        if (flag == 0){
            gourd();
        }
        if (flag == 0){
            samef();
        }
        if (flag == 0){
            shunzi();
        }
        if (flag == 0){
            three();
        }
        if (flag == 0){
            twop();
        }
        if (flag == 0){
            onep();
        }
        if (flag == 0){
            dispersed();
        }
    } //执行两次排牌

    void flush(){
        flag = 0;
        int temp = 0;
        for (int i=0,j=0;i<scard.length && temp==0;i++){
            for ( j=scard[i].length -1  ;j>=0 && temp==0;j--){
                if ( scard[i][j] == 1 && j-4>=0){
                    if (scard[i][j-1] == 1 && scard[i][j-2] == 1 && scard[i][j-3] == 1 && scard[i][j-4] == 1){
                        scard[i][j] = rank;
                        scard[i][j-1] = rank;
                        scard[i][j-2] = rank;
                        scard[i][j-3] = rank;
                        scard[i][j-4] = rank;
                        shun[j]--;
                        shun[j-1]--;
                        shun[j-2]--;
                        shun[j-3]--;
                        shun[j-4]--;
                        huase[i] =huase[i] -5;
                        flag = 1;
                        temp = 5;
                        break;
                    }
                }
            }
        }
        return;
    } //同花顺

    void bomb(){
        flag = 0;
        for (int i=shun.length -1; i >= 0;i--){
            if (shun[i] == 4){
                scard[0][i] = rank;
                scard[1][i] = rank;
                scard[2][i] = rank;
                scard[3][i] = rank;
                shun[i] = 0;
                huase[0]--;
                huase[1]--;
                huase[2]--;
                huase[3]--;
                flag = 2;
                break;
            }
        }
        if (flag == 2){
            int temp = 0;
            for (int i=0; i<shun.length && temp == 0;i++){
                if (shun[i] == 1){
                    for (int j=0;j<huase.length;j++){
                        if (scard[j][i] == 1){
                            scard[j][i] = rank;
                            shun[i] = 0;
                            huase[j] --;
                        }
                    }
                    temp = 1;
                }
            }
            if (temp == 0){
                for (int i=0;i<shun.length && temp == 0 ;i++){
                    if (shun[i] > 1){
                        for (int j=0;j<huase.length && temp == 0;j++){
                            if (scard[j][i] == 1){
                                scard[j][i] = rank;
                                shun[i] --;
                                huase[j] --;
                                temp = 1;
                            }
                        }
                    }
                }
            }
        }
    } //炸弹

    void gourd(){
        flag = 0;
        int temp = 0;
        for (int i = shun.length -1 ;i>=0 && temp == 0;i--){
            if (shun[i] == 3){
                for (int j = 0;j < shun.length - 1 && temp == 0; j++){
                    if (shun[j] == 2){
                        shun[i] = 0;
                        shun[j] = 0;
                        for (int k=0;k<4;k++){
                            if (scard[k][i] == 1){
                                scard[k][i] =rank;
                                huase[k] --;
                            }
                        }
                        for (int k=0;k<4;k++){
                            if (scard[k][j] == 1){
                                scard[k][j] = rank;
                                huase[k] --;
                            }
                        }
                     flag = 3;
                     temp = 5;
                     break;
                    }
                }
            }
        }
    } //葫芦

    void samef(){
        int t = 0,temp = 0;
        flag = 0;
        for (int i=0;i<huase.length && temp==0;i++){
            if (huase[i] >= 5){
                for (int j = scard[i].length - 1;j>=0 && temp==0;j--){
                    if (scard[i][j] == 1){
                        scard[i][j] = rank;
                        huase[i] = huase[i] - 5;
                        shun[j]--;
                        t++;
                        if (t == 5){
                            temp = 5;
                            flag = 4;
                            break;
                        }
                    }
                }
            }
        }
    } //同花

    void shunzi(){
        flag = 0;
        for (int i = shun.length - 1;i>=0;i--){
            if (shun[i] != 0 && i-4>=0){
                if (shun[i-1] != 0 && shun[i-2] != 0 && shun[i-3] != 0 && shun[i-4] != 0){
                    int temp = 0;
                    for (int j=0;j < scard.length && temp == 0;j++){
                        if (scard[j][i] == 1){
                            scard[j][i] = rank;
                            huase[j] -- ;
                            shun[i] --;
                            temp = 1;
                        }
                    }
                    temp = 0;
                    for (int j=0;j < scard.length && temp == 0;j++){
                        if (scard[j][i-1] == 1){
                            scard[j][i-1] = rank;
                            huase[j] -- ;
                            shun[i-1] --;
                            temp = 1;
                        }
                    }
                    temp = 0;
                    for (int j=0;j < scard.length && temp == 0;j++){
                        if (scard[j][i-2] == 1){
                            scard[j][i-2] = rank;
                            huase[j] -- ;
                            shun[i-2] --;
                            temp = 1;
                        }
                    }
                    temp = 0;
                    for (int j=0;j < scard.length && temp == 0;j++){
                        if (scard[j][i-3] == 1){
                            scard[j][i-3] = rank;
                            huase[j] -- ;
                            shun[i-3] --;
                            temp = 1;
                        }
                    }
                    temp = 0;
                    for (int j=0;j < scard.length && temp == 0;j++){
                        if (scard[j][i-4] == 1){
                            scard[j][i-4] = rank;
                            huase[j] -- ;
                            shun[i-4] --;
                            temp = 1;
                        }
                    }
                    flag = 5;
                    break;
                }
            }
        }
    } //顺子

    void three(){
        flag = 0;
        for (int i=shun.length -1; i >= 0 && flag == 0;i--){
            if (shun[i] == 3){
                for (int j =0;j<huase.length;j++){
                    if ( scard[j][i] == 1){
                        scard[j][i] = rank;
                        huase[j] --;
                    }
                }
                shun[i] = 0;
                flag = 6;
            }
        }
        if (flag == 6){
            int temp = 0;
            for (int i=0; i<shun.length && temp != 2;i++){
                if (shun[i] == 1){
                    for (int j=0;j<huase.length;j++){
                        if (scard[j][i] == 1){
                            scard[j][i] = rank;
                            shun[i] = 0;
                            huase[j] --;
                        }
                    }
                    temp ++;
                }
            }

        }
    } //三条

    void twop() {
        flag = 0;
        int temp = 0;
        for (int i=shun.length - 1;i>0 && flag == 0;i--){
            if (shun[i] == 2){
                for (int j = 0;j<i;j++){
                    if (shun[j] == 2){
                        for (int k=0;k<huase.length;k++){
                            if (scard[k][i] == 1){
                                scard[k][i] = rank;
                                huase[k] --;
                                shun[i] --;
                            }
                            if (scard[k][j] == 1){
                                scard[k][j] = rank;
                                huase[k] --;
                                shun[j] --;
                            }
                        }

                        for (int k = 0;k<shun.length && temp == 0;k++){
                            if (shun[k] == 1){
                                for (int t = 0;t<huase.length;t++){
                                    if (scard[t][k] == 1){
                                        scard[t][k] = rank;
                                        huase[t] --;
                                        shun[k] --;
                                        temp++;
                                        break;
                                    }
                                }
                            }
                        }

                        if (temp == 0){
                            for (int k = 0;k<shun.length && temp == 0;k++){
                                if (shun[k] > 1){
                                    for (int t = 0;t<huase.length;t++){
                                        if (scard[t][k] == 1){
                                            scard[t][k] = rank;
                                            huase[t] --;
                                            shun[k] --;
                                            temp++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }


                        flag = 7;
                        break;
                    }
                }
            }
        }
    } //两对

    void onep() {
        flag = 0;
        int temp = 0;
        for (int i=shun.length - 1;i>=0 && flag == 0;i--){
            if (shun[i] == 2){
                for (int k =0;k<huase.length;k++){
                    if (scard[k][i] == 1){
                        scard[k][i] = rank;
                        huase[k] --;
                        shun[i] --;
                    }
                }
                for (int k = 0;k<shun.length && temp != 3;k++){
                    if (shun[k] == 1){
                        for (int j = 0;j<huase.length;j++){
                            if (scard[j][k] == 1){
                                scard[j][k] = rank;
                                huase[j] --;
                                shun[k] --;
                            }
                        }
                        temp ++;
                    }
                }
                flag = 8;
            }
        }
    } //一对

    void dispersed() {
        flag = 0;
        int temp = 0;
        for (int i = shun.length - 1;i>=0 && temp != 5;i--){
            if (shun[i] == 1){
                for (int j = 0;j<huase.length;j++){
                    if (scard[j][i] == 1){
                        scard[j][i] = rank;
                        huase[j] --;
                        shun[i] --;
                    }
                }
                temp ++ ;
            }
            flag = 9;
        }
    } //散牌

    void shift(){
        int temp1 = 0,temp2 = 0,temp3 = 0;
        for (int i = 0;i <scard.length;i++){
            for (int j = 0;j<scard[i].length;j++){
                if (scard[i][j] == 1){
                    if ( i == 0){
                        answer1 = answer1+"$";
                    }
                    else if (i == 1){
                        answer1 = answer1+"&";
                    }
                    else if (i == 2){
                        answer1 = answer1 +"*";
                    }
                    else if (i == 3){
                        answer1 = answer1 +"#";
                    }
                    if (2 <=j && j <= 10){
                        answer1 = answer1 + j;
                    }
                    else if (j == 11){
                        answer1 = answer1 + "J";
                    }
                    else if (j == 12){
                        answer1 = answer1 + "Q";
                    }
                    else if (j == 13){
                        answer1 = answer1 + "K";
                    }
                    else if (j == 14){
                        answer1 = answer1 + "A";
                    }
                    temp1 ++;
                    if (temp1 < 3){
                        answer1 = answer1 + " ";
                    }
                }
                if (scard[i][j] == 2){
                    if ( i == 0){
                        answer2 = answer2+"$";
                    }
                    else if (i == 1){
                        answer2 = answer2+"&";
                    }
                    else if (i == 2){
                        answer2 = answer2 +"*";
                    }
                    else if (i == 3){
                        answer2 = answer2 +"#";
                    }
                    if (2 <=j && j <= 10){
                        answer2 = answer2 + j;
                    }
                    else if (j == 11){
                        answer2 = answer2 + "J";
                    }
                    else if (j == 12){
                        answer2 = answer2 + "Q";
                    }
                    else if (j == 13){
                        answer2 = answer2 + "K";
                    }
                    else if (j == 14){
                        answer2 = answer2 + "A";
                    }
                    temp2 ++;
                    if (temp2 < 5){
                        answer2 = answer2 + " ";
                    }
                }
                if (scard[i][j] == 3){
                    if ( i == 0){
                        answer3 = answer3+"$";
                    }
                    else if (i == 1){
                        answer3 = answer3+"&";
                    }
                    else if (i == 2){
                        answer3 = answer3 +"*";
                    }
                    else if (i == 3){
                        answer3 = answer3 +"#";
                    }
                    if (2 <=j && j <= 10){
                        answer3 = answer3 + j;
                    }
                    else if (j == 11){
                        answer3 = answer3 + "J";
                    }
                    else if (j == 12){
                        answer3 = answer3 + "Q";
                    }
                    else if (j == 13){
                        answer3 = answer3 + "K";
                    }
                    else if (j == 14){
                        answer3 = answer3 + "A";
                    }
                    temp3 ++;
                    if (temp3 < 5){
                        answer3 = answer3 + " ";
                    }
                }
            }
        }
    }   //牌转成String

    void last(){
        int r3 = 0,r2 = 0,temp = 0;
            for (int i = shun.length - 1; i>=0 && temp == 0;i--){
                for (int j = 0;j < huase.length && temp ==0 ;j++){
                    if (scard[j][i] == 3){
                        r3 ++;
                    }
                    if (scard[j][i] ==2){
                        r2 ++;
                    }

                    if (r2 != r3){
                        if (r2 > r3){
                            String str = answer3;
                            answer3 = answer2;
                            answer2 = str;
                            temp = 1;
                        }
                        else {
                            temp = 1;
                        }
                    }
                }
            }
        }//比较牌的大小
}
