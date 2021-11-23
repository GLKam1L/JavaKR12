package ru.mirea.tourismapp.repo;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.tourismapp.domain.Message;


public interface MessageRepo extends CrudRepository<Message, Long> {
    Message findByMessage (String message);
    Iterable <Message> findAllByUserId(Long userId);

}