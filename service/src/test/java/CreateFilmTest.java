import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.CreateFilmDto;
import chomiuk.jacek.service.exception.FilmServiceException;
import chomiuk.jacek.service.mapper.Mappers;
import chomiuk.jacek.service.service.AdministrationService;
import chomiuk.jacek.service.service.FilmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateFilmTest {

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    AdministrationService administrationService;

    @Test
    @DisplayName("when film dto is null")
    private void dtoIsNull(){
        var error = Assertions.assertThrows(IllegalArgumentException.class, () -> administrationService.createFilm(null));
        assertEquals("create film validation errors:\ndto is null", error.getMessage());
    }

    @Test
    @DisplayName("when film dto name is not correct'")
    public void dtoNameIsNotCorrect() {

        // GIVEN
        CreateFilmDto createFilmDto = CreateFilmDto
                .builder()
                .name("1234")
                .genreId(1L)
                .startDate(LocalDate.of(2021, 1, 20))
                .endDate(LocalDate.of(2021, 3, 20))
                .build();

        //WHEN
        var filmServiceException = Assertions.assertThrows(FilmServiceException.class, () -> administrationService.createFilm(createFilmDto));
        String expectedExceptionMessage = "create film validation errors:\n" + "name is not correct";

        //THEN
        Assertions.assertEquals(filmServiceException.getMessage(),expectedExceptionMessage);
    }

    @Test
    @DisplayName("when film dto is correct")
    public void dtoIsCorrect() {

        // GIVEN
        CreateFilmDto createFilmDto = CreateFilmDto
                .builder()
                .name("Inception")
                .genreId(1L)
                .startDate(LocalDate.of(2021, 1, 20))
                .endDate(LocalDate.of(2021, 3, 20))
                .build();

        Film expectedFilm = Film
                .builder()
                .id(1L)
                .name("Inception")
                .genreId(1L)
                .startDate(LocalDate.of(2021, 1, 20))
                .endDate(LocalDate.of(2021, 3, 20))
                .build();

        //TODO do czego to potrzebne?
        Mockito
                .when(filmRepository.add(Mappers.fromCreateFilmDtoToFilm(createFilmDto)))
                .thenReturn(Optional.of(expectedFilm));


        // WHEN
        Long idFromService = administrationService.createFilm(createFilmDto);
        Long expectedId = expectedFilm.getId();


        // THEN
        Assertions.assertEquals(expectedId, idFromService);
    }
}
