package by.vlad.springproject1.service;

@Service
public class TaskService {
    private final ProjectService projectService;
    private final RoleService roleService;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;
    @Autowired
    public TaskService(ProjectService projectService, TaskRepository
            taskRepository, RoleService roleService, ProjectRepository projectRepository,
                       ActivityRepository activityRepository) {
        this.projectService = projectService;
        this.taskRepository = taskRepository;
        this.roleService = roleService;
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
    }
    public Task create(User user, NewTaskDto newTaskDto) throws
            NoSuchEntityException, AuthorizationException {
        Task task = new Task(
                projectService.get(user, newTaskDto.projectId),
                null,
                user,
                newTaskDto.type,
                newTaskDto.estimate,
                0d,
                LocalDateTime.now(),
                newTaskDto.due,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );if (newTaskDto.parentTaskId != null) {
            Task parentTask =
                    taskRepository.findById(newTaskDto.parentTaskId).orElseThrow(() -> new
                            NoSuchElementException("no such parent task"));
            task.setParent(parentTask);
        }
        task = taskRepository.save(task);
        task.setTaskInfo(new TaskInfo(
                task,
                newTaskDto.name,
                newTaskDto.description
        ));
        task.getActivities().add(new Activity(
                task,
                task.getCreator(),
                null,
                TaskStatus.OPEN,
                null,
                LocalDateTime.now(),
                null
        ));
        return taskRepository.save(task);
    }
    public Task edit(User user, EditTaskDto editTaskDto) throws
            NoSuchEntityException, AuthorizationException {
        Task task = taskRepository.findById(editTaskDto.id).orElseThrow(() ->
                new NoSuchEntityException("no such task to edit"));
        roleService.authorize(user, task.getProject(), UserRole.COLLABORATOR);
        TaskInfo taskInfo = task.getTaskInfo();
        taskInfo.setName(editTaskDto.name);
        taskInfo.setDescription(editTaskDto.description);
        task.setTaskInfo(taskInfo);
        return taskRepository.save(task);
    }
    public void delete(User user, Long taskId) throws NoSuchEntityException,
            AuthorizationException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new
                NoSuchEntityException("no such task to edit"));
        if (!(roleService.hasPermission(user, task.getProject(),
                UserRole.COLLABORATOR) ||
                (task.getCreator().getId().equals(user.getId())))) throw new
                AuthorizationException("no permission");
        taskRepository.delete(task);
    }
    public List<Task> all(User user, Long projectId, Pageable pageable) throws
            AuthorizationException {
        Project project = projectRepository.findById(projectId).orElseThrow(()
                -> new NoSuchElementException("no such project"));
        roleService.authorize(user, project, UserRole.VIEWER);
        return taskRepository.findAllByProject(project, pageable);}
    public Task get(User user, Long taskId) throws AuthorizationException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new
                NoSuchElementException("no such task"));
        roleService.authorize(user, task.getProject(), UserRole.VIEWER);
        return task;
    }
    public List<Task> owned(User user, Pageable pageable) {
        return taskRepository.findAllByCreator(user, pageable);
    }
    public List<Task> assignee(User user, Pageable pageable) {
        return activityRepository.findAllAssigneeActivities(user.getId(),
                pageable)
                .stream()
                .map(Activity::getTask)
                .collect(Collectors.toList());
    }
}