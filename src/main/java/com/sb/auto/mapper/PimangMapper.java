package com.sb.auto.mapper;

import com.sb.auto.model.EtcUser;
import com.sb.auto.model.StockEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PimangMapper {

    /**
     * user정보 반환
     * @param userId
     * @return
     */
    EtcUser selectUser(String userId);

    /**
     * stock업데이트
     * @param stockEntity
     * @return
     */
    int countNumber(StockEntity stockEntity);
    int insertStock(StockEntity stockEntity);
    int updateStock(StockEntity stockEntity);

    /**
     * stock 조회
     * @param userId
     * @return
     */
    List<StockEntity> selectStock(String userId);

    /**
     * number삭제
     * @param stockEntity
     * @return
     */
    int deleteStockNumber(StockEntity stockEntity);

    /**
     * 유저의 모든 number삭제
     * @param userId
     * @return
     */
    int allDeleteStock(String userId);


}
