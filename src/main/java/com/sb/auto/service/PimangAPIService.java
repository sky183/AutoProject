package com.sb.auto.service;

import com.sb.auto.mapper.PimangMapper;
import com.sb.auto.model.EtcUserVO;
import com.sb.auto.model.StockVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PimangAPIService {

    PimangMapper pimangMapper;

    public PimangAPIService(PimangMapper pimangMapper) {
        this.pimangMapper = pimangMapper;
    }

    /**
     * 유저정보 조회
     * @param stockVO
     * @return
     */
    public EtcUserVO selectUser(StockVO stockVO) {
        return pimangMapper.selectUser(stockVO.getUserId());
    }

    /**
     * 유저 유효성 체크
     * @param stockVO
     * @return
     */
    public String validateUser(StockVO stockVO) {
        EtcUserVO etcUserVO = selectUser(stockVO);
        if (etcUserVO == null) return "유저없음";
        else if (!(etcUserVO.getUserPw().equals(stockVO.getUserPw()))) return "비밀번호불일치";
        else return  "성공";
    }
    /**
     * 유저 정보 확인 후 유효하면 해당 Stock 업데이트 또는 삽입 후 결과를 성공으로 응답
     * @param stockVO
     * @return
     */
    public String updateStock(StockVO stockVO) {
        String resultString = validateUser(stockVO);
        if (!resultString.equals("성공")) return resultString;
        else {
            int number = pimangMapper.countNumber(stockVO);
            if (number == 0) pimangMapper.insertStock(stockVO);
            else pimangMapper.updateStock(stockVO);
            return  resultString;
        }
    }

    /**
     * 해당 유저의 Stock 정보 전체 조회
     * @param stockVO
     * @return
     */
    public <T> T selectStock(StockVO stockVO) {
        T resultString = (T) validateUser(stockVO);
        if (!resultString.equals("성공")) return resultString;
        else return (T) pimangMapper.selectStock(stockVO.getUserId());
    }

    /**
     * 해당 number Stock삭제
     * @param stockVO
     * @return
     */
    public String deleteByStockNumber(StockVO stockVO) {
        String resultString = validateUser(stockVO);
        if (!resultString.equals("성공")) return resultString;
        else pimangMapper.deleteStockNumber(stockVO);
        return  resultString;
    }

    /**
     * 유저의 모든 Stock삭제
     * @param stockVO
     * @return
     */
    public String allDeleteStock(StockVO stockVO) {
        String resultString = validateUser(stockVO);
        if (!resultString.equals("성공")) return resultString;
        else pimangMapper.allDeleteStock(stockVO.getUserId());
        return  resultString;
    }
}
