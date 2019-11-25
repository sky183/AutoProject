package com.sb.auto.mapper;

import com.sb.auto.model.EtcUserVO;
import com.sb.auto.model.StockVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PimangMapper {

    /**
     * user정보 반환
     * @param userId
     * @return
     */
    EtcUserVO selectUser(String userId);

    /**
     * stock업데이트
     * @param stockVO
     * @return
     */
    int countNumber(StockVO stockVO);
    int insertStock(StockVO stockVO);
    int updateStock(StockVO stockVO);

    /**
     * stock 조회
     * @param userId
     * @return
     */
    List<StockVO> selectStock(String userId);

    /**
     * number삭제
     * @param stockVO
     * @return
     */
    int deleteStockNumber(StockVO stockVO);

    /**
     * 유저의 모든 number삭제
     * @param userId
     * @return
     */
    int allDeleteStock(String userId);


}
