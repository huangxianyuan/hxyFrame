/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/9/19
 */

public class Prize {
    private int id;//奖品id
    private String prize_name;//奖品名称
    private int prize_amount;//奖品（剩余）数量 当为-2时，表示不限制抽奖总数
    private double prize_weight;//奖品权重

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrize_name() {
        return prize_name;
    }

    public void setPrize_name(String prize_name) {
        this.prize_name = prize_name;
    }

    public int getPrize_amount() {
        return prize_amount;
    }

    public void setPrize_amount(int prize_amount) {
        this.prize_amount = prize_amount;
    }

    public double getPrize_weight() {
        return prize_weight;
    }

    public void setPrize_weight(double prize_weight) {
        this.prize_weight = prize_weight;
    }

}
