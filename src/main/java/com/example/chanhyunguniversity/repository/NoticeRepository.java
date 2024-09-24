package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity , Long> {

    List<NoticeEntity> findByFixNoticeOrderByCreatedAtDesc(boolean fixNotice);

}
