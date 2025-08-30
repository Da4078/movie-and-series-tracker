package org.daniel.moviesandseriestrackermaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SeriesController.class)
public class SeriesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeriesService seriesService;

    private UUID seriesId;
    private Series series;
    private SeriesDTO seriesDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void set_up(){
        seriesId = UUID.randomUUID();
        seriesDTO = new SeriesDTO("Breaking Bad",
                List.of(GenreEnum.CRIME),
                "Gilligian",
                2008,
                "Drug deal");

        series = Series.builder()
                .id(seriesId)
                .title("Breaking Bad")
                .genres(List.of(GenreEnum.CRIME))
                .creator("Gilligian")
                .startYear(2008)
                .description("Drug deal")
                .build();
    }

    @Test
    void should_create_series() throws Exception{
        when(seriesService.createSeries(any(SeriesDTO.class))).thenReturn(series);

        mockMvc.perform(post("/api/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seriesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(seriesId.toString())))
                .andExpect(jsonPath("$.title").value("Breaking Bad"))
                .andExpect(jsonPath("$.genres[0]").value("CRIME"))
                .andExpect(jsonPath("$.startYear").value(2008))
                .andExpect(jsonPath("$.creator").value("Gilligian"));
    }

    @Test
    void should_return_series_by_id() throws Exception {
        when(seriesService.getSeriesById(seriesId)).thenReturn(Optional.of(series));

        mockMvc.perform(get("/api/series/{id}", seriesId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(seriesId.toString())))
                .andExpect(jsonPath("$.title").value("Breaking Bad"))
                .andExpect(jsonPath("$.genres[0]").value("CRIME"))
                .andExpect(jsonPath("$.startYear").value(2008))
                .andExpect(jsonPath("$.creator").value("Gilligian"));
    }

    @Test
    void should_return_series_not_found_by_id() throws Exception{
        when(seriesService.getSeriesById(seriesId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/series/{id}", seriesId)).andExpect(status().isNotFound());
    }

    @Test
    void should_filter_series() throws Exception{
        Page<Series> page = new PageImpl<>(List.of(series));
        when(seriesService.getSeries("Breaking Bad", GenreEnum.CRIME, "Gilligian",
                "title", "asc", 0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/series/filter")
                        .param("title", "Breaking Bad")
                        .param("genreEnum", "CRIME")
                        .param("creator", "Gilligian")
                        .param("sortBy", "title")
                        .param("direction", "asc")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Breaking Bad"))
                .andExpect(jsonPath("$.content[0].creator").value("Gilligian"));
    }

    @Test
    void should_update_series() throws Exception {
        when(seriesService.updateSeries(eq(seriesId), any(SeriesDTO.class))).thenReturn(series);

        mockMvc.perform(put("/api/series/{id}", seriesId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seriesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(seriesId.toString()))
                .andExpect(jsonPath("$.title").value("Breaking Bad"))
                .andExpect(jsonPath("$.creator").value("Gilligian"));
    }

    @Test
    void should_delete_series()throws Exception{
        doNothing().when(seriesService).deleteSeries(seriesId);
        mockMvc.perform(delete("/api/series/{id}", seriesId))
                .andExpect(status().isNoContent());
    }
}
