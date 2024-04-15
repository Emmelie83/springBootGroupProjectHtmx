package se.iths.springbootgroupproject.repos;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.dtos.PublicMessage;
import se.iths.springbootgroupproject.entities.User;

import java.util.List;


public interface MessageRepository extends PagingAndSortingRepository<Message, Long>, ListCrudRepository<Message, Long> {

    List<Message> findAllBy(Pageable pageable);

    List<PublicMessage> findAllByIsPublicIsTrue();


    List<Message> findAllByUserId(Long userId);

    @Query(value = """
            select * from message where id > ?1 limit ?2
            """, nativeQuery = true)
    List<Message> findMessagesBy(long cursor, int pageSize);


    @Query(value = "SELECT m.* FROM message m JOIN user u ON m.user = u.id WHERE m.id > :id AND u.id = :userId LIMIT :pageSize", nativeQuery = true)
    List<Message> findMessagesByUserId(@Param("id") long id, @Param("pageSize") int pageSize, @Param("userId") long userId);






}
