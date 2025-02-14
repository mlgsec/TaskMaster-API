package com.example.taskmaster.controller;

import com.example.taskmaster.model.Task;
import com.example.taskmaster.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Garante que cada teste seja executado em uma transação isolada
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TASK_TITLE = "Test Task";
    private static final String TASK_DESCRIPTION = "Test Description";
    private static final LocalDate TASK_DUE_DATE = LocalDate.now();
    private static final boolean TASK_COMPLETED = false;

    @BeforeEach
    public void cleanup() {
        taskRepository.deleteAll(); // Limpa o banco de dados antes de cada teste
    }

    @Test
    @DisplayName("Deve retornar todas as tarefas")
    public void testGetAllTasks() throws Exception {
        // Arrange
        Task task = new Task(null, TASK_TITLE, TASK_DESCRIPTION, TASK_DUE_DATE, TASK_COMPLETED);
        taskRepository.save(task);

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(TASK_TITLE))
                .andExpect(jsonPath("$[0].description").value(TASK_DESCRIPTION))
                .andExpect(jsonPath("$[0].completed").value(TASK_COMPLETED));
    }

    @Test
    @DisplayName("Deve criar uma nova tarefa")
    public void testCreateTask() throws Exception {
        // Arrange
        Task task = new Task(null, TASK_TITLE, TASK_DESCRIPTION, TASK_DUE_DATE, TASK_COMPLETED);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(TASK_TITLE))
                .andExpect(jsonPath("$.description").value(TASK_DESCRIPTION))
                .andExpect(jsonPath("$.completed").value(TASK_COMPLETED));
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa existente")
    public void testUpdateTask() throws Exception {
        // Arrange
        Task existingTask = new Task(null, TASK_TITLE, TASK_DESCRIPTION, TASK_DUE_DATE, TASK_COMPLETED);
        existingTask = taskRepository.save(existingTask); // Salva e obtém o ID gerado

        Task updatedTaskDetails = new Task(existingTask.getId(), "Updated Task", "Updated Description", TASK_DUE_DATE.plusDays(1), true);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/" + existingTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingTask.getId()))
                .andExpect(jsonPath("$.title").value(updatedTaskDetails.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedTaskDetails.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(updatedTaskDetails.getDueDate().toString()))
                .andExpect(jsonPath("$.completed").value(updatedTaskDetails.isCompleted()));

        // Verifica se a tarefa foi atualizada no banco de dados
        Optional<Task> updatedTask = taskRepository.findById(existingTask.getId());
        assertTrue(updatedTask.isPresent());
        assertEquals(updatedTaskDetails.getTitle(), updatedTask.get().getTitle());
        assertEquals(updatedTaskDetails.getDescription(), updatedTask.get().getDescription());
        assertEquals(updatedTaskDetails.getDueDate(), updatedTask.get().getDueDate());
        assertEquals(updatedTaskDetails.isCompleted(), updatedTask.get().isCompleted());
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar uma tarefa inexistente")
    public void testUpdateTaskNotFound() throws Exception {
        // Arrange
        Long nonExistentTaskId = 999L;
        Task updatedTaskDetails = new Task(nonExistentTaskId, "Updated Task", "Updated Description", TASK_DUE_DATE, true);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/" + nonExistentTaskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskDetails)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found")); // Verifica a mensagem de erro
    }

    @Test
    @DisplayName("Deve excluir uma tarefa existente")
    public void testDeleteTask() throws Exception {
        // Arrange
        Task task = new Task(null, TASK_TITLE, TASK_DESCRIPTION, TASK_DUE_DATE, TASK_COMPLETED);
        task = taskRepository.save(task);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/" + task.getId()))
                .andExpect(status().isNoContent());

        // Verifica se a tarefa foi excluída do banco de dados
        Optional<Task> deletedTask = taskRepository.findById(task.getId());
        assertFalse(deletedTask.isPresent());
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar excluir uma tarefa inexistente")
    public void testDeleteTaskNotFound() throws Exception {
        // Arrange
        long nonExistentTaskId = 999L;

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/" + nonExistentTaskId))
                .andExpect(status().isNotFound());
    }
}
