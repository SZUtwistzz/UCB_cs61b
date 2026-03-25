package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    NGramMap map;

    public HistoryTextHandler(NGramMap map){
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();

        for (String word : words) {
            // 1. 获取指定年份范围内的相对频率 (直接调用 weightHistory 更简单)
            TimeSeries ts = this.map.weightHistory(word, startYear, endYear);

            // 2. 利用自带的 toString() 直接生成 {2006=..., 2007=...} 格式
            response.append(word).append(": ").append(ts.toString()).append("\n");
        }

        return response.toString();
    }
}