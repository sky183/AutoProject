package com.sb.auto.controller;

import com.sb.auto.model.StockEntity;
import com.sb.auto.service.PimangAPIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PimangAPIController {

    PimangAPIService pimangAPIService;

    public PimangAPIController(PimangAPIService pimangAPIService) {
        this.pimangAPIService = pimangAPIService;
    }

    @GetMapping("/pimang/update")
    public String updateStockGet(){
        System.out.println("get완료");
        return "GET완료";
    }
    @PostMapping("/pimang/update")
    public String updateStock(@ModelAttribute StockEntity stockEntity){
        System.out.println("post완료");
        return pimangAPIService.updateStock(stockEntity);
    }

    @PostMapping("/pimang/select")
    public List<StockEntity> selectStock(@ModelAttribute StockEntity stockEntity) {
         return pimangAPIService.selectStock(stockEntity);
    }



}
