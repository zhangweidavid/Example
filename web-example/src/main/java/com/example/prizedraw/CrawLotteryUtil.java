package com.example.prizedraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;


/**
 * @author lvyujun<hzlvyujun       @       corp.netease.com>
 * @since 2016年10月12日
 */
public class CrawLotteryUtil {
    private static final String NETEASE_LOTTERY_URL = "http://caipiao.163.com/award/cqssc/";

    /**
     * 一直获取彩票结果
     *
     * @param lastLotterIssue
     * @return
     * @author lvyujun<hzlvyujun               @               corp.netease.com>
     * @since 2016年10月13日
     */
    private static String getRemainLastLotteryNumber(String lastLotterIssue) {
        String lastLotteryNumber = "";
        Date nowDate = new Date();
        Date lastTime = DateUtils.addDays(nowDate, 1);
        while (true) {
            lastLotteryNumber = getLastLotteryNumber(lastLotterIssue);
            if (!StringUtils.isEmpty(lastLotteryNumber)) {
                break;
            }
            // 超过24小时返回默认值
            Date current = new Date();
            if (current.after(lastTime)) {
                return "00000";
            }
            try {
                TimeUnit.MINUTES.sleep(3);
            } catch (InterruptedException e) {
                // logger.error("", e);
            }
        }
        return lastLotteryNumber;
    }

    /**
     * 获取当前排期的彩票number(如果没有取到返回"00000")
     *
     * @param lotterIssue
     * @return
     */
    public static String getLotteryNumberByLotterIssue(String lotterIssue) {
        String lotteryNumber = getLastLotteryNumber(lotterIssue);
        if (!StringUtils.isEmpty(lotteryNumber)) {
            return lotteryNumber;
        } else {
            return "00000";
        }
    }

    /**
     * 获取当前排期的彩票number
     *
     * @return
     * @author lvyujun<hzlvyujun               @               corp.netease.com>
     * @since 2016年10月13日
     */
    private static String getLastLotteryNumber(String lastLotterIssue) {
        String responseBody = CrawUtil.get(NETEASE_LOTTERY_URL);
        Document doc = Jsoup.parse(responseBody);
        Elements dataPeriodEles = doc.getElementsByAttributeValue("data-period", lastLotterIssue);
        Element dataPeriodEle = dataPeriodEles.get(0);
        Attributes attributes = dataPeriodEle.attributes();
        String dataPeriod = attributes.get("data-period");
        String number = "";
        if (dataPeriod.equals(lastLotterIssue)) {
            number = attributes.get("data-win-number");
            if (!StringUtils.isEmpty(number)) {
                number = number.replaceAll(" ", "");
            }
        }
        return number;
    }

    /**
     * 获取当前排期的彩票number
     *
     * @return
     * @author lvyujun<hzlvyujun               @               corp.netease.com>
     * @since 2016年10月13日
     */
    public static Map<String, String> getLastLotteryNumberForMap(List<String> lastLotterIssues) {
        Map<String, String> resultMap = new HashMap<>();
        if (CollectionUtils.isEmpty(lastLotterIssues)) {
            return resultMap;
        }
        Set<String> urlSuffixSet = new HashSet<>();
        for (String strUrl : lastLotterIssues) { // 格式161021082
            if (StringUtils.isEmpty(strUrl) || strUrl.length() < 6) {
                continue;
            }
            String urlLast = strUrl.substring(0, 6);
            urlSuffixSet.add("20" + urlLast);
        }

        if (CollectionUtils.isEmpty(urlSuffixSet)) {
            return resultMap;
        }
        for (String urlSuffix : urlSuffixSet) {
            String responseBody = CrawUtil.get(NETEASE_LOTTERY_URL + urlSuffix + ".html");
            Document doc = Jsoup.parse(responseBody);

            for (String lastLotterIssue : lastLotterIssues) {
                Elements dataPeriodEles = doc.getElementsByAttributeValue("data-period", lastLotterIssue);
                if (dataPeriodEles == null || dataPeriodEles.size() <= 0) {
                    continue;
                }
                Element dataPeriodEle = dataPeriodEles.get(0);
                Attributes attributes = dataPeriodEle.attributes();
                String dataPeriod = attributes.get("data-period");
                String number = "";
                if (dataPeriod.equals(lastLotterIssue)) {
                    number = attributes.get("data-win-number");
                    if (!StringUtils.isEmpty(number)) {
                        number = number.replaceAll(" ", "");
                        resultMap.put(lastLotterIssue, number);
                    }
                }
            }
        }
        return resultMap;
    }

    public static void main(String[] args) {
        String period = getLastDataPeriod();
        System.out.println("---------period:" + period);
    }

    /**
     * 获取下一期的期号
     *
     * @return
     * @author lvyujun<hzlvyujun               @               corp.netease.com>
     * @since 2016年10月13日
     */
    public static String getLastDataPeriod() {
        String responseBody = CrawUtil.get(NETEASE_LOTTERY_URL);
        Document doc = Jsoup.parse(responseBody);
        List<com.example.prizedraw.LotteryBase> lotteryBases = new ArrayList<>();
        Elements trEles = doc.getElementsByClass("start");
        for (Element trEle : trEles) {
            Attributes attributes = trEle.attributes();
            String number = attributes.get("data-win-number");
            if (StringUtils.isEmpty(number)) {
                continue;
            }
            String dataPeriod = attributes.get("data-period");
            String text = trEle.text();
            if (!StringUtils.isNumeric(text)) {
                continue;
            }
            com.example.prizedraw.LotteryBase lotterBase = new com.example.prizedraw.LotteryBase();
            number = number.replaceAll(" ", "");
            lotterBase.setNumber(number);
            lotterBase.setText(text);
            lotterBase.setDataPeriod(dataPeriod);
            int textInt = Integer.parseInt(text);
            lotterBase.setTextInt(textInt);
            lotteryBases.add(lotterBase);
        }
        String lastLotterIssue = "";
        if (CollectionUtils.isEmpty(lotteryBases)) { // 为空需要获取当天的第一个排期
            Date newDate = new Date();
            Date tomorrowDate = DateUtils.addHours(newDate, 1); // 往后推一小时取时间
            String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyMMdd");
            lastLotterIssue = tomorrowDateStr + "001";
            return lastLotterIssue;
        }
        Collections.sort(lotteryBases, new Comparator<LotteryBase>() {
            @Override
            public int compare(LotteryBase o1, LotteryBase o2) {
                return o1.getTextInt() > o2.getTextInt() ? 1 : -1;
            }
        });

        LotteryBase lastLotterBase = lotteryBases.get(lotteryBases.size() - 1);
        if (lastLotterBase.getTextInt() == 120) {// 最后一期需要获取第二天第一期
            Date newDate = new Date();
            Date tomorrowDate = DateUtils.addDays(newDate, 1);
            String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyMMdd");
            lastLotterIssue = tomorrowDateStr + "001";
        } else {// 不是最后一期，获取下一期
            String currentPeriod = lastLotterBase.getDataPeriod();
            if (StringUtils.isNumeric(currentPeriod)) {
                int currentPeriodInt = Integer.parseInt(currentPeriod);
                int lastLotterIssueInt = currentPeriodInt + 1;
                lastLotterIssue = String.valueOf(lastLotterIssueInt);
            }
        }
        return lastLotterIssue;
    }
}
