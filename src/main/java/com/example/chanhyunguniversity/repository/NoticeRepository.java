package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity , Long> {


    List<NoticeEntity> findByFixNoticeOrderByCreatedAtDesc(boolean fixNotice);
    @Query("SELECT n FROM NoticeEntity n WHERE n.fixNotice = true ORDER BY n.createdAt DESC LIMIT 1")
    Optional<NoticeEntity> findLatestFixedNotice();

}