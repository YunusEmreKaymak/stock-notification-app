package com.yunus.stock_persistence_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunus.stock_persistence_service.dto.StockDto;
import com.yunus.stock_persistence_service.dto.StockDtoConverter;
import com.yunus.stock_persistence_service.dto.StockHistoryDto;
import com.yunus.stock_persistence_service.service.StockHistoryService;
import com.yunus.stock_persistence_service.service.StockService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class RecordSavingService {
    private static final Logger log = LoggerFactory.getLogger(RecordSavingService.class);
    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    private final StockDtoConverter stockDtoConverter;

    public RecordSavingService(StockService stockService, StockHistoryService stockHistoryService, StockDtoConverter stockDtoConverter) {
        this.stockService = stockService;
        this.stockHistoryService = stockHistoryService;
        this.stockDtoConverter = stockDtoConverter;
    }

    public void saveRecord(ConsumerRecord<String, String> record) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProcessedData processedData = objectMapper.readValue(record.value(), ProcessedData.class);
            List<String> dayList = processedData.getKeyList();
            String simpleDateTimeFormatPattern = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(simpleDateTimeFormatPattern);
            String symbol = record.key();
            if (!stockService.doesStockExistBySymbol(symbol)) {
                StockDto stockDto = new StockDto();
                stockDto.setSymbol(symbol);
                stockService.createStock(stockDto);
                stockHistoryService.createStockHistory(new StockHistoryDto(symbol, "", sdf.parse(dayList.getFirst()), stockDtoConverter.convertToModel(stockDto), processedData.getDailyDataMap().get(dayList.getFirst())));
            } else {
                StockDto stockDtoBySymbol = stockService.getStockBySymbol(symbol);
                stockHistoryService.createStockHistory(new StockHistoryDto(symbol, "", sdf.parse(dayList.getFirst()), stockDtoConverter.convertToModel(stockDtoBySymbol), processedData.getDailyDataMap().get(dayList.getFirst())));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
