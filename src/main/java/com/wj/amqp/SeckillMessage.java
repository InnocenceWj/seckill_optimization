package com.wj.amqp;


import com.wj.entity.SeckillUser;
import lombok.Data;

/**
 * @author ShallowAn
 */
@Data
public class SeckillMessage {
    private SeckillUser seckillUser;
    private long goodsId;

    @Override
    public String toString() {
        return "SeckillMessage{" +
                "seckillUser=" + seckillUser +
                ", goodsId=" + goodsId +
                '}';
    }
}
