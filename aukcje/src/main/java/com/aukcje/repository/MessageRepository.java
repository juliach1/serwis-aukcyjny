package com.aukcje.repository;

import com.aukcje.entity.Message;
import com.aukcje.entity.MessageReceiver;
import com.aukcje.entity.MessageSender;
import com.aukcje.entity.MessageTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface MessageRepository extends JpaRepository<Message, Long> {

    Set<MessageReceiver> findBySenderIdOrderBySendTimeDesc(Long senderId);

    Set<MessageSender> findByReceiverIdOrderBySendTimeDesc(Long receiverId);

    MessageTime findTopByReceiverIdAndSenderIdOrderBySendTimeDesc(Long receiverId, Long senderId);

    List<Message> findByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderBySendTime(Long receiverId, Long senderId, Long senderId2, Long receiverId2);
}
