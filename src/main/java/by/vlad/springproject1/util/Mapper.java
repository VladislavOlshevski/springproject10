package by.vlad.springproject1.util;

public class Mapper {
    private static ModelMapper modelMapper;
    static {
        modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }
    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
    public static <S, T> List<T> mapAll(Collection<? extends S> sourceList,
                                        Class<T> targetClass) {
        return sourceList.stream()
                .map(e -> map(e, targetClass))
                .collect(Collectors.toList());
    }
}