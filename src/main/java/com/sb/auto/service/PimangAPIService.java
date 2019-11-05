package com.sb.auto.service;

import com.sb.auto.mapper.PimangMapper;
import com.sb.auto.model.EtcUser;
import com.sb.auto.model.StockEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PimangAPIService {

    PimangMapper pimangMapper;

    public PimangAPIService(PimangMapper pimangMapper) {
        this.pimangMapper = pimangMapper;
    }

    /**
     * 유저정보 조회
     * @param stockEntity
     * @return
     */
    public EtcUser selectUser(StockEntity stockEntity) {
        return pimangMapper.selectUser(stockEntity.getUserId());
    }

    /**
     * 유저 유효성 체크
     * @param stockEntity
     * @return
     */
    public String validateUser(StockEntity stockEntity) {
        EtcUser etcUser = selectUser(stockEntity);
        if (etcUser == null) return "유저없음";
        else if (!(etcUser.getUserPw().equals(stockEntity.getUserPw()))) return "비밀번호불일치";
        else return  "성공";
    }
    /**
     * 유저 정보 확인 후 유효하면 해당 Stock 업데이트 또는 삽입 후 결과를 성공으로 응답
     * @param stockEntity
     * @return
     */
    public String updateStock(StockEntity stockEntity) {
        String resultString = validateUser(stockEntity);
        if (!resultString.equals("성공")) return resultString;
        else {
            int number = pimangMapper.countNumber(stockEntity);
            if (number == 0) pimangMapper.insertStock(stockEntity);
            else pimangMapper.updateStock(stockEntity);
            return  resultString;
        }
    }

    /**
     * 해당 유저의 Stock 정보 전체 조회
     * @param stockEntity
     * @return
     */
    public List<StockEntity> selectStock(StockEntity stockEntity) {
        String resultString = validateUser(stockEntity);
        if (!resultString.equals("성공")) return null;
        else return pimangMapper.selectStock(stockEntity.getUserId());
    }

    /**
     * 해당 number Stock삭제
     * @param stockEntity
     * @return
     */
    public String deleteByStockNumber(StockEntity stockEntity) {
        String resultString = validateUser(stockEntity);
        if (!resultString.equals("성공")) return resultString;
        else pimangMapper.deleteStockNumber(stockEntity);
        return  resultString;
    }

    /**
     * 유저의 모든 Stock삭제
     * @param stockEntity
     * @return
     */
    public String allDeleteStock(StockEntity stockEntity) {
        String resultString = validateUser(stockEntity);
        if (!resultString.equals("성공")) return resultString;
        else pimangMapper.allDeleteStock(stockEntity.getUserId());
        return  resultString;
    }
}
