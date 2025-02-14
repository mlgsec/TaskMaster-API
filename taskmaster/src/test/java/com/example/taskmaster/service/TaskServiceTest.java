package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskRequest;
import com.example.taskmaster.exeption.ResourceNotFoundException;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private static final Long TASK_ID = 1L;
    private static final String TASK_TITLE = "Test Task";
    private static final String TASK_DESCRIPTION = "Test Description";
    private static final LocalDate TASK_DUE_DATE = LocalDate.now();
    private static final boolean TASK_COMPLETED = false;

    @Test
    @DisplayName("Deve retornar todas as tarefas")
    public void testGetAllTasks() {
        // Arrange
        Task task1 = new Task(TASK_ID, TASK_TITLE, TASK_DESCRIPTION, TASK_DUE_DATE, TASK_COMPLETED);
        Task task2 = new Task(2L, "Task 2", "Description 2", TASK_DUE_DATE, true);
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        // Act
        List<Task> tasks = taskService.getAllTasks();

        // Assert
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve criar uma nova tarefa")
    public void testCreateTask() {
        // Arrange
        TaskRequest request = new TaskRequest(TASK_TITLE, TASK_DESCRIPTION, TASK_DUE_DATE, TASK_COMPLETED);
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .completed(request.completed())
                .build();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task createdTask = taskService.createTask(request);

        // Assert
        assertNotNull(createdTask);
        assertEquals(TASK_TITLE, createdTask.getTitle());
        assertEquals(TASK_DESCRIPTION, createdTask.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa existente")
    public void testUpdateTask() {
        // Arrange
        Task existingTask = new Task(TASK_ID, "Old Title", "Old Description", TASK_DUE_DATE, TASK_COMPLETED);
        Task updatedTaskDetails = new Task(TASK_ID, "New Title", "New Description", TASK_DUE_DATE.plusDays(1), true);

        when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTaskDetails);

        // Act
        Task updatedTask = taskService.updateTask(TASK_ID, updatedTaskDetails);

        // Assert
        assertNotNull(updatedTask);
        assertEquals("New Title", updatedTask.getTitle());
        assertEquals("New Description", updatedTask.getDescription());
        assertTrue(updatedTask.isCompleted());
        verify(taskRepository, times(1)).findById(TASK_ID);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar uma tarefa inexistente")
    public void testUpdateTaskNotFound() {
        // Arrange
        when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskService.updateTask(TASK_ID, new Task(TASK_ID, "New Title", "New Description", TASK_DUE_DATE, true)));
        verify(taskRepository, times(1)).findById(TASK_ID);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve excluir uma tarefa existente")
    public void testDeleteTask() {
        // Arrange
        when(taskRepository.existsById(TASK_ID)).thenReturn(true); // Simula que a tarefa existe
        doNothing().when(taskRepository).deleteById(TASK_ID);      // Simula exclusão sem erro

        // Act
        taskService.deleteTask(TASK_ID);

        // Assert
        verify(taskRepository, times(1)).existsById(TASK_ID);  // Verifica se a existência foi checada
        verify(taskRepository, times(1)).deleteById(TASK_ID);  // Verifica se a exclusão foi chamada
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir uma tarefa inexistente")
    public void testDeleteTaskNotFound() {
        // Arrange
        when(taskRepository.existsById(TASK_ID)).thenReturn(false); // Simula que a tarefa NÃO existe

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(TASK_ID));

        // Verifica que o deleteById NÃO foi chamado, pois a verificação falhou antes disso
        verify(taskRepository, never()).deleteById(TASK_ID);
    }

}