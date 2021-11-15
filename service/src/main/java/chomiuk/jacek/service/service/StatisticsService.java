package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CityRepository cityRepository;
    /*case 1 -> mostProfitableCity();
                case 2 -> mostPopularFilmAndGenrePerCity();
                case 3 -> averageTicketPricePerPerson();
                case 4 -> totalCinemasIncomePerCity();
                case 5 -> mostPopularWeekDayPerCity();
                case 6 -> mostPopularTicketTypePerCity();*/

    public void mostProfitableCity(){
        var cities = cityRepository.findAll();
        
    }
}
