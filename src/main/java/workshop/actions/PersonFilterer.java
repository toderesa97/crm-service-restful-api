package workshop.actions;

import org.springframework.stereotype.Component;
import workshop.model.user.User;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class PersonFilterer {
    public List<User> filter(List<User> all, Predicate<User> predicate) {
        return all.stream().filter(predicate).collect(Collectors.toList());
    }
}
