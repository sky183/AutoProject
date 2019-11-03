package com.sb.auto.service;

import com.sb.auto.mapper.PimangMapper;
import com.sb.auto.model.EtcUser;
import com.sb.auto.model.StockEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PimangAPIService {

    PimangMapper pimangMapper;

    public PimangAPIService(PimangMapper pimangMapper) {
        this.pimangMapper = pimangMapper;
    }

    public EtcUser selectEtcUser(StockEntity stockEntity) {
        return pimangMapper.validateUser(stockEntity.getUserId());
    }

    public String updateStock(StockEntity stockEntity) {
        EtcUser etcUser = selectEtcUser(stockEntity);
        if (etcUser == null) return "유저없음";
        else if (!(etcUser.getUserPw().equals(stockEntity.getUserPw()))) return "비밀번호불일치";
        else {
            int number = pimangMapper.countNumber(stockEntity);
            if (number == 0) pimangMapper.insertStock(stockEntity);
            else pimangMapper.updateStock(stockEntity);
            return  "성공";
        }
    }

    public List<StockEntity> selectStock(StockEntity stockEntity) {
        EtcUser etcUser = selectEtcUser(stockEntity);
        if (etcUser == null) return null;
        else if (!(etcUser.getUserPw().equals(stockEntity.getUserPw()))) return null;
        else return pimangMapper.selectStock(stockEntity.getUserId());
    }
}
