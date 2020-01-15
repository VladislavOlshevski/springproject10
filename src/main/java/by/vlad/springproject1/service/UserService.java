package by.vlad.springproject1.service;

@Service
public class UserService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    @Autowired
    public UserService(TokenRepository tokenRepository, UserRepository
            userRepository, UserCredentialsRepository userCredentialsRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.userCredentialsRepository = userCredentialsRepository;
    }
    public String authenticate(AuthUserDto authUserDto) throws
            NoSuchEntityException, AuthenticationException {
        User user = userCredentialsRepository.findByLogin(authUserDto.login)
                .orElseThrow(() -> new NoSuchEntityException("no user with such
                        login")).getUser();
        if (!Hasher.check(authUserDto.password,
                user.getUserCredentials().getHash()))
            throw new AuthenticationException("invalid userCredentials");
        Token token = new Token(user, TokenGenerator.generate());
        return tokenRepository.save(token).getToken();
    }
    public User validate(String tokenValue) throws NoSuchEntityException {
        return tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new NoSuchEntityException("no such token"))
                .getUser();
    }
    public void register(RegisterUserDto registerUserDto) throws
            RegistrationException {
        if
        (userCredentialsRepository.findByLogin(registerUserDto.login).isPresent())
            throw new RegistrationException("user with such login already
                    exist");
                    User user = new User(LocalDate.now());
        userRepository.save(user);
        user.setUserCredentials(new UserCredentials(user,
                registerUserDto.login, Hasher.getHash(registerUserDto.password)));
        user.setUserInfo(new UserInfo(user));
        userRepository.save(user);
    }
    public User getUser(User user, String login) throws NoSuchEntityException
    {
        return userCredentialsRepository.findByLogin(login).orElseThrow(() -> new NoSuchEntityException("no such user by
                given login"))
                .getUser();
    }
    public User edit(User user, EditUserDto editUserDto) throws
            NoSuchEntityException, AuthorizationException {
        User editingUser = getUser(user, editUserDto.login);
        if (!user.getId().equals(editingUser.getId())) throw new
                AuthorizationException("no permission");
        editingUser.getUserCredentials().setLogin(editUserDto.login);
        UserInfo userInfo = editingUser.getUserInfo();
        userInfo.setName(editUserDto.name);
        userInfo.setBio(editUserDto.bio);
        userInfo.setCompany(editUserDto.company);
        userInfo.setUrl(editUserDto.url);
        userInfo.setLocation(editUserDto.location);
        editingUser.setUserInfo(userInfo);
        return userRepository.save(editingUser);
    }
}