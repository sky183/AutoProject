package com.sb.auto.mapper;

import com.sb.auto.model.EtcUser;
import com.sb.auto.model.StockEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PimangMapper {

    //user정보 반환
    EtcUser selectUser(String userId);
    //stock업데이트
    int countNumber(StockEntity stockEntity);
    int insertStock(StockEntity stockEntity);
    int updateStock(StockEntity stockEntity);
    //stock 조회
    List<StockEntity> selectStock(String userId);
    //number삭제
    int deleteStockNumber(StockEntity stockEntity);
    //유저의 모든 number삭제
    int allDeleteStock(String userId);


}
