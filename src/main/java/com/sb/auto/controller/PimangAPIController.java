package com.sb.auto.controller;

import com.sb.auto.model.StockVO;
import com.sb.auto.service.PimangAPIService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PimangAPIController {

    PimangAPIService pimangAPIService;

    public PimangAPIController(PimangAPIService pimangAPIService) {
        this.pimangAPIService = pimangAPIService;
    }

    @PostMapping("/pimang/update")
    public String updateStock(@ModelAttribute StockVO stockVO){
        return pimangAPIService.updateStock(stockVO);
    }

    @PostMapping("/pimang/select")
    public <T> T selectStock(@ModelAttribute StockVO stockVO) {
        return (T) pimangAPIService.selectStock(stockVO);
    }

    @PostMapping("/pimang/delete")
    public String deleteStock(@ModelAttribute StockVO stockVO){
        return pimangAPIService.deleteByStockNumber(stockVO);
    }

    @PostMapping("/pimang/allDelete")
    public String allDeleteStock(@ModelAttribute StockVO stockVO){
        return pimangAPIService.allDeleteStock(stockVO);
    }



}
