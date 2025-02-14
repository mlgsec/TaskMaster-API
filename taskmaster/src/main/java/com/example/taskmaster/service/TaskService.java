package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskRequest;
import com.example.taskmaster.exeption.ResourceNotFoundException;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.title())
                .completed(request.completed())
                .description(request.description())
                .dueDate(request.dueDate())
                .build();
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(taskDetails.getTitle());
                    existingTask.setDescription(taskDetails.getDescription());
                    existingTask.setDueDate(taskDetails.getDueDate());
                    existingTask.setCompleted(taskDetails.isCompleted());
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task with ID " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

}