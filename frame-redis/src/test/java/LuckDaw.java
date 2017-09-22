import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/9/19
 */

public class LuckDaw {

    public static int getPrizeIndex(List<Prize> prizes) {
        int random = -1;
        try{
            //计算总权重
            double sumWeight = 0;
            for(Prize p : prizes){
                sumWeight += p.getPrize_weight();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for(int i=0;i<prizes.size();i++){
                int amount = prizes.get(i).getPrize_amount();
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getPrize_weight()))/sumWeight;
                if(i==0){
                    d1 = 0;
                }else{
                    d1 +=Double.parseDouble(String.valueOf(prizes.get(i-1).getPrize_weight()))/sumWeight;
                }
                //随机数范围[0.0,1.0)，1.0是拿不到的。
                if(randomNumber >= d1 && randomNumber < d2){
                    //如果中奖的产品还有,则返回中奖
                    if(amount>0 || amount == -1){
                        random = i;
                        break;
                    }else {
                        //数量用完,返回-2
                        return -2;
                    }
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return random;
    }

    public static void main(String[] agrs) {
        int i = 0;
        int[] result=new int[4];
        List<Prize> prizes = new ArrayList<Prize>();

        Prize p1 = new Prize();
        p1.setPrize_name("1000元返得到");
        p1.setPrize_weight(0.20);
        p1.setPrize_amount(1);

        Prize p2 = new Prize();
        p2.setPrize_name("20元满红包");
        p2.setPrize_weight(10);
        p2.setPrize_amount(100);

        Prize p3 = new Prize();
        p3.setPrize_name("加油吧，再来一把稳中");
        p3.setPrize_weight(20);
        p3.setPrize_amount(-1);

        Prize p4 = new Prize();
        p4.setPrize_name("JD卡");
        p4.setPrize_weight(10);
        p4.setPrize_amount(-1);

        prizes.add(p1);
        prizes.add(p3);
        prizes.add(p4);
        prizes.add(p2);


        System.out.println("抽奖开始");
        for (i = 0; i < 100000; i++)
        {
            int selected=getPrizeIndex(prizes);
            if(selected == -2){
                System.out.println("奖品已经为空");
                continue;
            }
            if(prizes.get(selected).getPrize_amount() >0){
                prizes.get(selected).setPrize_amount(prizes.get(selected).getPrize_amount()-1);
            }
            System.out.println("第"+i+"次抽中的奖品为："+prizes.get(selected).getPrize_name());
            result[selected]++;
            System.out.println("--------------------------------");
        }
        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：");
        for (int j=0;j<result.length;j++){
            String name = prizes.get(j).getPrize_name();
            System.out.println(name+"中奖次数:"+result[j]);
        }
    }
}
