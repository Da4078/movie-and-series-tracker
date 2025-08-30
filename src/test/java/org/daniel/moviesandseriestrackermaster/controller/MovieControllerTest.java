package org.daniel.moviesandseriestrackermaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.service.MovieService;
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


@WebMvcTest(MovieController.class)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    private UUID movieId;
    private Movie movie;
    private MovieDTO movieDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void set_up(){
        movieId = UUID.randomUUID();
        movieDTO = new MovieDTO("Inception",
                List.of(GenreEnum.SCI_FI),
                "Nolan",
                2010,
                "Dreams",
                148);

        movie = Movie.builder()
                .id(movieId)
                .title("Inception")
                .genres(List.of(GenreEnum.SCI_FI))
                .director("Nolan")
                .releaseYear(2010)
                .description("Dreams")
                .duration(148)
                .build();
    }

    @Test
    void should_create_movie() throws Exception{
        when(movieService.createMovie(any(MovieDTO.class))).thenReturn(movie);

        mockMvc.perform(post("/api/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(movieId.toString())))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genres[0]").value("SCI_FI"))
                .andExpect(jsonPath("$.releaseYear").value(2010))
                .andExpect(jsonPath("$.director").value("Nolan"))
                .andExpect(jsonPath("$.duration").value(148));
    }

    @Test
    void should_return_movie_by_id() throws Exception {
        when(movieService.getMovieById(movieId)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movieId.toString()))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genres[0]").value("SCI_FI"))
                .andExpect(jsonPath("$.releaseYear").value(2010))
                .andExpect(jsonPath("$.director").value("Nolan"))
                .andExpect(jsonPath("$.duration").value(148));
    }

    @Test
    void should_return_movie_not_found_by_id() throws Exception{
        when(movieService.getMovieById(movieId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movies/{id}", movieId)).andExpect(status().isNotFound());
    }

    @Test
    void should_filter_movies() throws Exception{
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieService.getMovies("Inception", GenreEnum.SCI_FI, "Nolan",
                "title", "asc", 0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/movies/filter")
                        .param("title", "Inception")
                        .param("genreEnum", "SCI_FI")
                        .param("director", "Nolan")
                        .param("sortBy", "title")
                        .param("direction", "asc")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Inception"))
                .andExpect(jsonPath("$.content[0].director").value("Nolan"));
    }

    @Test
    void should_update_movie() throws Exception {
        when(movieService.updateMovie(eq(movieId), any(MovieDTO.class))).thenReturn(movie);

        mockMvc.perform(put("/api/movies/{id}", movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movieId.toString()))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.director").value("Nolan"));
    }

    @Test
    void should_delete_movie()throws Exception{
        doNothing().when(movieService).deleteMovie(movieId);
        mockMvc.perform(delete("/api/movies/{id}", movieId))
                .andExpect(status().isNoContent());
    }
}
