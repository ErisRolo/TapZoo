package com.example.ghx.tapzoo.bean;

import java.util.List;

/**
 * Created by ghx on 2019/4/1.
 * 返回的Json数据
 */

public class JsonBean {

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 叉角羚
         * score : 0.993811
         * baike_info : {"baike_url": "http://baike.baidu.com/item/%E5%8F%89%E8%A7%92%E7%BE%9A/801703",
         * "description": "叉角羚(学名：Antilocapra americana)：在角的中部角鞘有向前伸的分枝，故名。体型中等，体长1-1.5米，尾长7.5-10厘米，肩高81-104厘米，成体重36-60千克，雌体比雄体小；背面为红褐色，颈部有黑色鬃毛，腹部和臀部为白色，颊面部和颈部两侧有黑色块斑；毛被下面为绒毛，上覆以粗糙、质脆的长毛，由于某些皮肤肌的作用，能使其毛被呈不同角度，以利于保暖或散热。植食。叉角羚奔跑速度非常快，最高时速达100千米。一次跳跃可达3.5-6米。善游泳。夏季组成小群活动，冬季则集结成上百只的大群。为寻找食物和水源，一年中常进行几次迁移。性机警，视觉敏锐，能看到数千米外的物体。遇险时，臀部的白色毛能立起，向同伴告警。分布于北美洲。"}
         */
        private String name;
        private int score;
        private BaikeInfoBean baike_info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public BaikeInfoBean getBaike_info() {
            return baike_info;
        }

        public void setBaike_info(BaikeInfoBean baike_info) {
            this.baike_info = baike_info;
        }

        public static class BaikeInfoBean {
            /**
             * baike_url : http://baike.baidu.com/item/%E6%85%95%E6%96%AF%E8%9B%8B%E7%B3%95/10639896
             * description :叉角羚(学名：Antilocapra americana)：在角的中部角鞘有向前伸的分枝，故名。体型中等，体长1-1.5米，尾长7.5-10厘米，肩高81-104厘米，成体重36-60千克，雌体比雄体小；背面为红褐色，颈部有黑色鬃毛，腹部和臀部为白色，颊面部和颈部两侧有黑色块斑；毛被下面为绒毛，上覆以粗糙、质脆的长毛，由于某些皮肤肌的作用，能使其毛被呈不同角度，以利于保暖或散热。植食。叉角羚奔跑速度非常快，最高时速达100千米。一次跳跃可达3.5-6米。善游泳。夏季组成小群活动，冬季则集结成上百只的大群。为寻找食物和水源，一年中常进行几次迁移。性机警，视觉敏锐，能看到数千米外的物体。遇险时，臀部的白色毛能立起，向同伴告警。分布于北美洲。
             */
            private String baike_url;
            private String description;


            public String getBaike_url() {
                return baike_url;
            }

            public void setBaike_url(String baike_url) {
                this.baike_url = baike_url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

    }
}
