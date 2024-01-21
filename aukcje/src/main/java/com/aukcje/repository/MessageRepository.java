package com.aukcje.repository;

import com.aukcje.entity.Message;
import com.aukcje.entity.MessageReceiver;
import com.aukcje.entity.MessageSender;
import com.aukcje.entity.MessageTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Set;


public interface MessageRepository extends JpaRepository<Message, Long> {

    Set<MessageReceiver> findBySenderIdOrderBySendTimeDesc(Long senderId);

    Set<MessageSender> findByReceiverIdOrderBySendTimeDesc(Long receiverId);

    MessageTime findTopByReceiverIdAndSenderIdOrderBySendTimeDesc(Long receiverId, Long senderId);

    Message findTopMessageByReceiverIdAndSenderIdOrderBySendTimeDesc(Long receiverId, Long senderId);
}
