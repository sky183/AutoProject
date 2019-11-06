package com.sb.auto.controller;

import com.sb.auto.model.StockEntity;
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
    public String updateStock(@ModelAttribute StockEntity stockEntity){
        return pimangAPIService.updateStock(stockEntity);
    }

    @PostMapping("/pimang/select")
    public <T> T selectStock(@ModelAttribute StockEntity stockEntity) {
        return (T) pimangAPIService.selectStock(stockEntity);
    }

    @PostMapping("/pimang/delete")
    public String deleteStock(@ModelAttribute StockEntity stockEntity){
        return pimangAPIService.deleteByStockNumber(stockEntity);
    }

    @PostMapping("/pimang/allDelete")
    public String allDeleteStock(@ModelAttribute StockEntity stockEntity){
        return pimangAPIService.allDeleteStock(stockEntity);
    }



}
