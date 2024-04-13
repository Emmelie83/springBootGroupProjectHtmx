package se.iths.springbootgroupproject.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.dtos.PublicMessage;

import java.util.List;


public interface MessageRepository extends PagingAndSortingRepository<Message, Long>,ListCrudRepository<Message, Long> {

    List<Message> findAllBy(Pageable pageable);

    List<PublicMessage> findAllByIsPublicIsTrue();


    List<Message> findAllByUserId(Long userId);

}
