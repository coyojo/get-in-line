package com.example.getinline.repository;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.domain.Event;
import com.example.getinline.dto.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//TODO: 인스턴스 생성 편의를 위해 임시로 default 사용, repository layer 구현이 완성되면 삭제할것
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    //JpaRepository를 사용하면 인터페이스에 작성한 메소드 이름이 곧 쿼리 표현이 됨
    List<Event> findByEventNameAndEventStatus(String eventName, EventStatus eventStatus);
    Optional<Event> findFirstByEventEndDatetimeBetween(LocalDateTime from, LocalDateTime to);


    default List<EventDTO> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ){
        return List.of();
    }

    default Optional<EventDTO> findEvent(Long eventId){
        return Optional.empty();
    }

    default boolean insertEvent(EventDTO eventDTO){return false;}

    default boolean updateEvent(Long eventId, EventDTO dto){
        return false;
    }
    default boolean deleteEvent(Long eventId){
        return false;
    }
}
