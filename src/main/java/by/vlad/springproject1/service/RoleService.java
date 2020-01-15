package by.vlad.springproject1.service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public UserRole getRole(User user, Project project) {
        Optional<Role> roleOptional = roleRepository.findByUserAndProject(user,
                project);
        if (!roleOptional.isPresent()) return UserRole.UNAUTHORIZED;return roleOptional.get().getRole();
    }
    public boolean hasPermission(User user, Project project, UserRole role) {
        if (!project.getPublic() && role == UserRole.UNAUTHORIZED) return
                false;
        return getRole(user, project).level >= role.level;
    }
    public void authorize(User user, Project project, UserRole role) throws
            AuthorizationException {
        if (!hasPermission(user, project, role)) throw new
                AuthorizationException("no permission");
    }
}