package com.moonshine.utils;

import com.moonshine.domain.Discount;
import com.moonshine.domain.ListOfValue;
import com.moonshine.repository.DiscountRepository;
import com.moonshine.service.DiscountService;
import com.moonshine.service.dto.DiscountDTO;
import com.moonshine.service.mapper.DiscountMapper;
import com.moonshine.websocket.ActivityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.LocalDateTime.now;

@Component
public class CronoScheduler {
    private List<Discount> discounts = new ArrayList<>();
    private final DiscountService discountService;
    private final DiscountRepository discountRepository;
    ActivityService activityService = new ActivityService();

    public CronoScheduler(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountService = new DiscountService(discountRepository, discountMapper);
    }

    @Scheduled(cron = "0 * * * * *")
    public void activateDiscount()throws InterruptedException {
        Instant startDate, endDate, nowDate, now;
        LocalTime startHour, endHour, nowLT;
        Set<ListOfValue> listOfValue;

        if (discountService.findAll() != null) {

            System.out.println("Descuentos activados a las: " + new Date());
            System.out.println("Descuentos programados para hoy");

            discounts = discountRepository.findAll();

            for (Discount discount : discounts) {

                startDate = discount.getStarDate().minus(6, ChronoUnit.HOURS).truncatedTo(ChronoUnit.DAYS);
                endDate = discount.getEndDate().minus(6, ChronoUnit.HOURS).truncatedTo(ChronoUnit.DAYS);
                nowDate = Instant.now().minus(6, ChronoUnit.HOURS).truncatedTo(ChronoUnit.DAYS);
                listOfValue = discount.getDays();


                if (!discount.isStatus()) {
                    if (discount.getStartHour() != null) {
                        if (nowDate.equals(startDate) || nowDate.isAfter(startDate) && nowDate.isBefore(endDate) || nowDate.equals(endDate)) {

                            now = Instant.now().truncatedTo(ChronoUnit.MINUTES).minus(6, ChronoUnit.HOURS);
                            LocalDateTime ldtnowHour = LocalDateTime.ofInstant(now, ZoneOffset.UTC);

                            startHour = LocalTime.from(LocalDateTime.ofInstant(discount.getStartHour().truncatedTo(ChronoUnit.MINUTES).minus(6, ChronoUnit.HOURS), ZoneOffset.UTC));
                            endHour = LocalTime.from(LocalDateTime.ofInstant(discount.getEndHour().truncatedTo(ChronoUnit.MINUTES).minus(6, ChronoUnit.HOURS), ZoneOffset.UTC));
                            nowLT = LocalTime.from(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

                            if (listOfValue.stream().anyMatch(day -> Integer.parseInt(day.getValue()) == ldtnowHour.getDayOfWeek().getValue())) {

                                if (nowLT.equals(startHour) || nowLT.isAfter(startHour) && nowLT.isBefore(endHour)) {
                                    discount.setStatus(true);
                                    System.out.println(discount);
                                    discountRepository.save(discount);
                                    activityService.sendActivityDiscount("Discounts activate with hours");
                                }
                            }
                        }
                    } else {
                        if (nowDate.equals(startDate) || nowDate.isAfter(startDate) && nowDate.isBefore(endDate) || nowDate.equals(endDate)) {
                            discount.setStatus(true);
                            System.out.println(discount);
                            discountRepository.save(discount);
                            activityService.sendActivityDiscount("Discounts activate with dates");
                        }
                    }
                } else {
                    if (discount.getEndHour() != null) {

                        now = Instant.now().truncatedTo(ChronoUnit.MINUTES).minus(6, ChronoUnit.HOURS);

                        nowLT = LocalTime.from(LocalDateTime.ofInstant(now, ZoneOffset.UTC));
                        endHour = LocalTime.from(LocalDateTime.ofInstant(discount.getEndHour().truncatedTo(ChronoUnit.MINUTES).minus(6, ChronoUnit.HOURS), ZoneOffset.UTC));
                        startHour = LocalTime.from(LocalDateTime.ofInstant(discount.getStartHour().truncatedTo(ChronoUnit.MINUTES).minus(6, ChronoUnit.HOURS), ZoneOffset.UTC));

                        if (nowLT.equals(endHour) || !(nowLT.isAfter(startHour) && nowLT.isBefore(endHour))) {
                            discount.setStatus(false);
                            System.out.println(discount);
                            discountRepository.save(discount);
                            activityService.sendActivityDiscount("Discounts inactivate with hours");
                        }
                    } else {

                        if (!nowDate.equals(startDate) || nowDate.isBefore(startDate) && nowDate.isAfter(endDate) || !nowDate.equals(endDate)) {
                            discount.setStatus(false);
                            System.out.println(discount);
                            discountRepository.save(discount);
                            activityService.sendActivityDiscount("Discounts inactivate with dates");
                        }
                    }
                }
            }
        }

        Thread.sleep(9000);
    }

}
