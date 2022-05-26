package com.projekt.czat;

import org.springframework.data.jpa.repository.JpaRepository;

interface MessageRepository extends JpaRepository<Message, Long> {
}
